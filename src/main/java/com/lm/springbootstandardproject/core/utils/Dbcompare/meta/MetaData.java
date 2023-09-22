package com.lm.springbootstandardproject.core.utils.Dbcompare.meta;


import com.alibaba.druid.util.JdbcUtils;
import com.lm.springbootstandardproject.core.utils.Dbcompare.SqlUtil;
import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class MetaData {
    private Connection conn;
    private String user;
    private String password;
    private String schema;
    private String jdbcUrl;

    private Map<String, Table> tables = new LinkedHashMap<String, Table>();

    public void init() {
        try {
            Class.forName(JdbcUtils.getDriverClassName(jdbcUrl));
            conn = DriverManager.getConnection(jdbcUrl, user, password);
            getTablesFromDb();
            getCreateTables();
            getAllTableColumns();
            getAllTableKeys();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getTablesFromDb() throws SQLException {
        ResultSet rs = SqlUtil.executeSql(conn, "show tables from " + schema);
        while (rs.next()) {
            Table table = new Table();
            table.setTableName(rs.getString("Tables_in_" + schema));
            tables.put(table.getTableName(), table);
        }
    }

    private void getCreateTables() throws SQLException {
        for (Table table : tables.values()) {
            getCreateTable(table);
        }
    }

    private void getCreateTable(Table table) throws SQLException {
        ResultSet rs = SqlUtil.executeSql(conn, "show create table " + schema + "." + table.getTableName());
        while (rs.next()) {
            table.setCreateTable(rs.getString("Create Table"));
        }
    }

    private void getAllTableColumns() throws SQLException {
        for (Table table : tables.values()) {
            getTableColumns(table);
        }
    }

    private void getTableColumns(Table table) throws SQLException {
        ResultSet rs = SqlUtil.executeSql(conn, "select COLUMN_NAME,COLUMN_TYPE,IS_NULLABLE,COLUMN_DEFAULT,"
                + "COLUMN_COMMENT,EXTRA from information_schema.columns where TABLE_SCHEMA=" + "'" + schema + "'"
                + " and "
                + "TABLE_NAME=" + "'" + table.getTableName() + "'" + " order by ORDINAL_POSITION asc");
        while (rs.next()) {
            Column column = new Column();
            column.setName(rs.getString("COLUMN_NAME"));
            column.setType(rs.getString("COLUMN_TYPE"));
            column.setDefaultValue(rs.getString("COLUMN_DEFAULT"));
            column.setIsNull(rs.getString("IS_NULLABLE"));
            column.setExtra(rs.getString("EXTRA"));
            column.setComment(rs.getString("COLUMN_COMMENT"));
            table.getColumns().put(column.getName(), column);
        }
    }

    private void getAllTableKeys() throws SQLException {
        for (Table table : tables.values()) {
            getTableKeys(table);
        }
    }

    private void getTableKeys(Table table) throws SQLException {
        ResultSet rs = SqlUtil.executeSql(conn, "show keys from " + schema + "." + table.getTableName());
        Index last = null;
        Index tmp = null;
        while (rs.next()) {
            String keyName = rs.getString("Key_name");
            if (last == null || !keyName.equals(last.getIndexName())) {
                last = new Index();
                last.setIndexName(keyName);
                last.getColumns().add(rs.getString("Column_name"));
                last.setNotUnique(rs.getString("Non_unique"));
                table.getIndexes().put(last.getIndexName(), last);
            } else {
                // 表明这两个key在同一索引中
                last.getColumns().add(rs.getString("Column_name"));
            }
        }
    }
}