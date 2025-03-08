package io.github.summer.boot.worksheet;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisStopException;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 逐行读
 * 读csv、xls和xlsx
 *
 * @author changebooks@qq.com
 */
public final class ReadLine {
    /**
     * 工作表
     */
    private final ReadSheet sheet;

    private ReadLine(ReadSheet sheet) {
        this.sheet = sheet;
    }

    public static ReadLine create() {
        return create(null);
    }

    public static ReadLine create(ReadSheet sheet) {
        return new ReadLine(sheet);
    }

    /**
     * 读文件
     *
     * @param file     File
     * @param sheet    ReadSheet
     * @param listener Listener
     * @throws IOException read failures
     */
    public static void read(File file, ReadSheet sheet, Listener listener) throws IOException {
        Preconditions.checkNonNull(file, "file must not be null");

        WorksheetType type = WorksheetType.fromFile(file);
        try (InputStream stream = new FileInputStream(file)) {
            read(type, stream, sheet, listener);
        }
    }

    /**
     * 读文件流
     *
     * @param type     WorksheetType
     * @param stream   InputStream
     * @param sheet    ReadSheet
     * @param listener Listener
     */
    public static void read(WorksheetType type, InputStream stream, ReadSheet sheet, Listener listener) {
        WorksheetType.checkSupport(type);
        Preconditions.checkNonNull(stream, "stream must not be null");
        Preconditions.checkNonNull(listener, "listener must not be null");

        ExcelReaderBuilder builder = EasyExcel.read(stream, new AnalysisEventListener<Map<Integer, String>>() {
            /**
             * columnIndex : keyName
             */
            private Map<Integer, String> keys;

            @Override
            public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                super.invokeHeadMap(headMap, context);

                keys = ReadUtils.asKey(headMap);
            }

            @Override
            public void invoke(Map<Integer, String> valueMap, AnalysisContext context) {
                Integer rowIndex = ReadUtils.getRowIndex(context);

                try {
                    Map<String, String> data = ReadUtils.combine(keys, valueMap);
                    listener.invoke(rowIndex, data);
                } catch (StopException ex) {
                    throw new ExcelAnalysisStopException();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                Integer rowIndex = ReadUtils.getRowIndex(context);
                listener.onComplete(rowIndex);
            }
        });

        if (WorksheetType.isCsv(type)) {
            builder.excelType(ExcelTypeEnum.CSV);
        }

        try (ExcelReader reader = builder.build()) {
            if (reader == null) {
                return;
            }

            if (sheet == null) {
                sheet = EasyExcel.readSheet(0).build();
            }

            reader.read(sheet);
        }
    }

    /**
     * 读文件
     *
     * @param file     File
     * @param listener Listener
     * @throws IOException read failures
     */
    public void read(File file, Listener listener) throws IOException {
        read(file, sheet, listener);
    }

    /**
     * 读文件流
     *
     * @param type     WorksheetType
     * @param stream   InputStream
     * @param listener Listener
     */
    public void read(WorksheetType type, InputStream stream, Listener listener) {
        read(type, stream, sheet, listener);
    }

    public ReadSheet getSheet() {
        return sheet;
    }

    /**
     * 监听行
     */
    public interface Listener {
        /**
         * 回调监听
         *
         * @param rowIndex 行索引
         * @param data     行数据，key : value
         */
        void invoke(Integer rowIndex, Map<String, String> data);

        /**
         * 完成
         * 终止任务-StopException，不执行该方法
         *
         * @param rowIndex 最后一行，行索引
         */
        void onComplete(Integer rowIndex);

    }

}
