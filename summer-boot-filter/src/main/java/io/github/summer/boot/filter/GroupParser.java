package io.github.summer.boot.filter;

import java.util.List;

/**
 * 分组
 *
 * @author changebooks@qq.com
 */
public interface GroupParser {
    /**
     * 解析列表
     *
     * @param groups [ the {@link Group} instance ]
     * @return GROUP BY name, name
     */
    String parseGroup(List<Group> groups);

    /**
     * 解析
     *
     * @param group the {@link Group} instance
     * @return GROUP BY name
     */
    String parseGroup(Group group);

}
