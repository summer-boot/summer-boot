package io.github.summer.boot.sql.filter;

import io.github.summer.boot.filter.Group;
import io.github.summer.boot.filter.GroupParser;

import java.util.List;

/**
 * @author changebooks@qq.com
 */
public class GroupParserImpl implements GroupParser {

    @Override
    public String parseGroup(List<Group> groups) {
        if (groups == null) {
            return null;
        }

        String pattern = PatternParser.parseGroup(groups);
        if (pattern.isEmpty()) {
            return null;
        } else {
            return "GROUP BY " + pattern;
        }
    }

    @Override
    public String parseGroup(Group group) {
        if (group == null) {
            return null;
        }

        String pattern = PatternParser.parseGroup(group);
        if (pattern.isEmpty()) {
            return null;
        } else {
            return "GROUP BY " + pattern;
        }
    }

}
