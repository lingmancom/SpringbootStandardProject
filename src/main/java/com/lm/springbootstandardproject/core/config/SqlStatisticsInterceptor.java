package com.lm.springbootstandardproject.core.config;

import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.lm.tools.DemonProjectConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;

import java.sql.Connection;
import java.util.List;


/**
 * SQL 统计信息拦截器
 *
 * @author cq
 * @date 2023/11/09
 */
@Slf4j
public class SqlStatisticsInterceptor implements InnerInterceptor {


    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        BoundSql boundSql = sh.getBoundSql();
        // sql为空直接跳过
        if (!DemonProjectConfig.useApiLog || StringUtils.isBlank(boundSql.getSql())) {
            InnerInterceptor.super.beforePrepare(sh, connection, transactionTimeout);
            return;
        }
        // 获取填充的sql，去掉换行符
        String sql = fillSql(boundSql).replace("\n", "");
        SqlStatisticsThreadLocalUtil.setCurrentMap(sql);
        InnerInterceptor.super.beforePrepare(sh, connection, transactionTimeout);
    }

    /**
     * 填充 SQL
     *
     * @param boundSql boundSql
     * @return {@link String}
     */
    public static String fillSql(BoundSql boundSql) {
        String sql = boundSql.getSql();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        for (ParameterMapping parameterMapping : parameterMappings) {
            String property = parameterMapping.getProperty();
            Object parameterObj = boundSql.getAdditionalParameter(property);

            // 如果没有取到，尝试从_parameter.属性名取
            if (parameterObj == null) {
                parameterObj = boundSql.getAdditionalParameter("_parameter." + property);
            }

            // 如果还是没有取到，那么就意味着有一个Java自带的封装类型，直接获取即可
            if (parameterObj == null) {
                parameterObj = boundSql.getParameterObject();
            }

            sql = sql.replaceFirst("\\?", "'" + parameterObj + "'");
        }
        return sql;
    }
}
