package com.lm.springbootstandardproject.core.config;

import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cq
 */
public class SqlStatisticsThreadLocalUtil {
    private static final ThreadLocal<Map<String, Integer>> SQL_STATISTICS_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置当前sql
     *
     * @param sql sql
     */
    public static void setCurrentMap(String sql) {
        Map<String, Integer> sqlToCountMap = SQL_STATISTICS_THREAD_LOCAL.get();
        if (ObjectUtils.isEmpty(sqlToCountMap)) {
            sqlToCountMap = new HashMap<>();
        }
        sqlToCountMap.put(sql, sqlToCountMap.getOrDefault(sql, 0) + 1);
        SQL_STATISTICS_THREAD_LOCAL.set(sqlToCountMap);
    }

    /**
     * 获取当前线程的sql
     *
     * @return {@link Map}<{@link String}, {@link Integer}>
     */
    public static Map<String, Integer> getCurrentMap() {
        return SQL_STATISTICS_THREAD_LOCAL.get();
    }

    /**
     * 删除当前线程记录的sql
     */
    public static void removeCurrentMap() {
        SQL_STATISTICS_THREAD_LOCAL.remove();
    }
}
