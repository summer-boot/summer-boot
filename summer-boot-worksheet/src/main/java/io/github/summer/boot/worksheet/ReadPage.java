package io.github.summer.boot.worksheet;

import com.alibaba.excel.read.metadata.ReadSheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分页读
 * 读csv、xls和xlsx
 *
 * @author changebooks@qq.com
 */
public final class ReadPage {
    /**
     * 每页行数
     */
    private final int pageSize;

    /**
     * 工作表
     */
    private final ReadSheet sheet;

    private ReadPage(int pageSize, ReadSheet sheet) {
        Preconditions.checkArgument(pageSize > 0, "pageSize must be positive");

        this.pageSize = pageSize;
        this.sheet = sheet;
    }

    public static ReadPage create(int pageSize) {
        return create(pageSize, null);
    }

    public static ReadPage create(int pageSize, ReadSheet sheet) {
        return new ReadPage(pageSize, sheet);
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

    /**
     * 读文件
     *
     * @param file     File
     * @param sheet    ReadSheet
     * @param listener Listener
     * @throws IOException read failures
     */
    public void read(File file, ReadSheet sheet, Listener listener) throws IOException {
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
    public void read(WorksheetType type, InputStream stream, ReadSheet sheet, Listener listener) {
        Preconditions.checkNonNull(listener, "listener must not be null");

        // 当前页的首行索引
        final Integer[] startRow = {null};

        // 当前页的数据列表
        List<Map<String, String>> data = new ArrayList<>();

        ReadLine.read(type, stream, sheet, new ReadLine.Listener() {
            @Override
            public void invoke(Integer rowIndex, Map<String, String> valueMap) {
                if (startRow[0] == null) {
                    startRow[0] = rowIndex;
                }

                if (data.size() < pageSize) {
                    data.add(valueMap);
                }

                if (data.size() >= pageSize) {
                    listener.invoke(startRow[0], data);
                    startRow[0] = null;
                    data.clear();
                }
            }

            @Override
            public void onComplete(Integer rowIndex) {
            }
        });

        if (!data.isEmpty()) {
            listener.invoke(startRow[0], data);
            data.clear();
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public ReadSheet getSheet() {
        return sheet;
    }

    /**
     * 监听页
     */
    public interface Listener {
        /**
         * 回调监听
         *
         * @param rowIndex 当前页的首行索引
         * @param data     当前页的数据列表，key : value
         */
        void invoke(Integer rowIndex, List<Map<String, String>> data);

    }

}
