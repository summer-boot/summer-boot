package io.github.summer.boot.worksheet;

import com.alibaba.excel.read.metadata.ReadSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 同步读
 * 读csv、xls和xlsx
 *
 * @author changebooks@qq.com
 */
public final class ReadSync {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadSync.class);

    /**
     * 工作表
     */
    private final ReadSheet sheet;

    private ReadSync(ReadSheet sheet) {
        this.sheet = sheet;
    }

    public static ReadSync create() {
        return create(null);
    }

    public static ReadSync create(ReadSheet sheet) {
        return new ReadSync(sheet);
    }

    /**
     * 读文件
     *
     * @param file  File
     * @param sheet ReadSheet
     * @return [key : value]
     * @throws IOException read failures
     */
    public static List<Map<String, String>> read(File file, ReadSheet sheet) throws IOException {
        Preconditions.checkNonNull(file, "file must not be null");

        WorksheetType type = WorksheetType.fromFile(file);
        try (InputStream stream = new FileInputStream(file)) {
            return read(type, stream, sheet);
        }
    }

    /**
     * 读文件流
     *
     * @param type   WorksheetType
     * @param stream InputStream
     * @param sheet  ReadSheet
     * @return [key : value]
     */
    public static List<Map<String, String>> read(WorksheetType type, InputStream stream, ReadSheet sheet) {
        List<Map<String, String>> result = new ArrayList<>();

        ReadLine.read(type, stream, sheet, new ReadLine.Listener() {
            @Override
            public void invoke(Integer rowIndex, Map<String, String> valueMap) {
                if (valueMap == null) {
                    LOGGER.error("read null, skip rowIndex: " + rowIndex);
                } else {
                    result.add(valueMap);
                }
            }

            @Override
            public void onComplete(Integer rowIndex) {
            }
        });

        return result;
    }

    /**
     * 读文件
     *
     * @param file File
     * @return [key : value]
     * @throws IOException read failures
     */
    public List<Map<String, String>> read(File file) throws IOException {
        return read(file, sheet);
    }

    /**
     * 读文件流
     *
     * @param type   WorksheetType
     * @param stream InputStream
     * @return [key : value]
     */
    public List<Map<String, String>> read(WorksheetType type, InputStream stream) {
        return read(type, stream, sheet);
    }

    public ReadSheet getSheet() {
        return sheet;
    }

}
