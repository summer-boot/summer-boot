package io.github.summer.boot.worksheet;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisStopException;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 读csv、xls和xlsx
 *
 * @author changebooks@qq.com
 */
public final class ReadUtils {

    private ReadUtils() {
    }

    /**
     * 行数，包括标题
     * csv，精确值
     * xls、xlsx，近似值
     *
     * @param file File
     * @return 行数
     * @throws IOException read failures
     */
    public static Integer getLineNum(File file) throws IOException {
        return getLineNum(file, null);
    }

    /**
     * 行数，包括标题
     * csv，精确值
     * xls、xlsx，近似值
     *
     * @param file  File
     * @param sheet ReadSheet
     * @return 行数
     * @throws IOException read failures
     */
    public static Integer getLineNum(File file, ReadSheet sheet) throws IOException {
        Preconditions.checkNonNull(file, "file must not be null");

        WorksheetType type = WorksheetType.fromFile(file);
        try (InputStream stream = new FileInputStream(file)) {
            return getLineNum(type, stream, sheet);
        }
    }

    /**
     * 行数，包括标题
     * csv，精确值
     * xls、xlsx，近似值
     *
     * @param type   WorksheetType
     * @param stream InputStream
     * @param sheet  ReadSheet
     * @return 行数
     * @throws IOException read failures
     */
    public static Integer getLineNum(WorksheetType type, InputStream stream, ReadSheet sheet) throws IOException {
        WorksheetType.checkSupport(type);
        Preconditions.checkNonNull(stream, "stream must not be null");

        switch (type) {
            case CSV:
                return getCsvRowSize(stream);
            case XLS:
                return getXlsApproximateRowSize(stream, sheet);
            default:
                throw new RuntimeException("unsupported type: " + type);
        }
    }

    /**
     * csv，精确行数
     *
     * @param stream InputStream
     * @return 精确行数
     * @throws IOException read failures
     */
    public static Integer getCsvRowSize(InputStream stream) throws IOException {
        if (stream == null) {
            return null;
        }

        try (Reader reader = new InputStreamReader(stream)) {
            try (LineNumberReader lineNumReader = new LineNumberReader(reader)) {
                long n = lineNumReader.skip(Long.MAX_VALUE);
                return lineNumReader.getLineNumber();
            }
        }
    }

    /**
     * xls、xlsx，近似行数
     *
     * @param stream InputStream
     * @param sheet  ReadSheet
     * @return 近似行数
     */
    public static Integer getXlsApproximateRowSize(InputStream stream, ReadSheet sheet) {
        if (stream == null) {
            return null;
        }

        final Integer[] result = new Integer[1];

        ExcelReaderBuilder readerBuilder = EasyExcel.read(stream, new AnalysisEventListener<Map<Integer, String>>() {
            @Override
            public void invoke(Map<Integer, String> data, AnalysisContext context) {
                result[0] = getRowSize(context);
                throw new ExcelAnalysisStopException();
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        });

        try (ExcelReader reader = readerBuilder.build()) {
            if (reader == null) {
                return null;
            }

            if (sheet == null) {
                sheet = EasyExcel.readSheet(0).build();
            }

            reader.read(sheet);
            return result[0];
        }
    }

    /**
     * read total row's number
     *
     * @param c AnalysisContext
     * @return total row's number
     */
    public static Integer getRowSize(AnalysisContext c) {
        if (c == null) {
            return null;
        }

        ReadSheetHolder sheetHolder = c.readSheetHolder();
        if (sheetHolder == null) {
            return null;
        }

        return sheetHolder.getApproximateTotalRowNumber();
    }

    /**
     * read current row's index
     *
     * @param c AnalysisContext
     * @return row's index
     */
    public static Integer getRowIndex(AnalysisContext c) {
        if (c == null) {
            return null;
        }

        ReadRowHolder rowHolder = c.readRowHolder();
        if (rowHolder == null) {
            return null;
        }

        return rowHolder.getRowIndex();
    }

    /**
     * combine keys and values
     * [index : key] + [index : value] = [key : value]
     *
     * @param keys   [i : key]
     * @param values [i : value]
     * @return [key : value]
     */
    public static Map<String, String> combine(Map<Integer, String> keys, Map<Integer, String> values) {
        if (values == null) {
            return null;
        }

        Preconditions.checkNonNull(keys, "keys must not be null");

        int keySize = keys.size();
        Preconditions.checkArgument(keySize > 0, "keys must not be empty");

        Map<String, String> result = new HashMap<>(keySize);

        for (Integer columnIndex : keys.keySet()) {
            String key = keys.get(columnIndex);
            String value = values.get(columnIndex);

            result.put(key, value);
        }

        return result;
    }

    /**
     * values to keys
     * if empty, ignore column
     * if duplicate, throw exception
     *
     * @param values head map
     * @return key map
     */
    public static Map<Integer, String> asKey(Map<Integer, String> values) {
        Preconditions.checkNonNull(values, "values must not be null");

        int size = values.size();
        Preconditions.checkArgument(size > 0, "values must not be empty");

        Map<Integer, String> result = new HashMap<>(size);

        for (Map.Entry<Integer, String> entry : values.entrySet()) {
            Integer columnIndex = entry.getKey();
            if (columnIndex == null || columnIndex < 0) {
                continue;
            }

            String value = entry.getValue();
            if (value == null) {
                continue;
            }

            String key = value.trim();
            if (key.isEmpty()) {
                continue;
            }

            Preconditions.checkArgument(!result.containsValue(key),
                    String.format("duplicated key: %s, columnIndex: %d", key, columnIndex));
            result.put(columnIndex, key);
        }

        return result;
    }

    /**
     * Array to Map
     *
     * @param values Array
     * @return Map
     */
    public static Map<Integer, String> asMap(String[] values) {
        if (values == null) {
            return null;
        }

        int len = values.length;
        Map<Integer, String> result = new HashMap<>(len);

        for (int i = 0; i < len; i++) {
            result.put(i, values[i]);
        }

        return result;
    }

}
