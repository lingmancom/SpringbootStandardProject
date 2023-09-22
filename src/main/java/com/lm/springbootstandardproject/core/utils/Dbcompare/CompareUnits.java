/*
 * Copyright (C) 2016 alchemystar, Inc. All Rights Reserved.
 */
package com.lm.springbootstandardproject.core.utils.Dbcompare;


import com.alibaba.druid.util.StringUtils;
import com.lm.springbootstandardproject.core.utils.Dbcompare.meta.Column;
import com.lm.springbootstandardproject.core.utils.Dbcompare.meta.Index;
import com.lm.springbootstandardproject.core.utils.Dbcompare.meta.MetaData;
import com.lm.springbootstandardproject.core.utils.Dbcompare.meta.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lizhuyang
 */
public class CompareUnits {

    private MetaData source;
    private MetaData target;

    private List<String> changeSql;
    private List<String> content;

    public CompareUnits(MetaData source, MetaData target) {
        this.source = source;
        this.target = target;
        changeSql = new ArrayList<String>();
        content = new ArrayList<String>();
    }

    public void compare() {
        compareSchema();
        compareTables();
        compareKeys();
    }

    private void compareSchema() {
        // if not exist
        // changeSql.add("create database if not exists " + SqlUtil.getDbString(source.getSchema())+";");
        source.init();
        target.init();
    }

    private void compareTables() {
        for (Table table : target.getTables().values()) {
            if (source.getTables().get(table.getTableName()) == null) {
                // 如果对应的source没有这张表,直接把drop Table拿出
                content.add("多余表:"+table.getTableName());
                changeSql.add("drop table " + target.getSchema() + "." + table.getTableName()+";");
                continue;
            }
        }

        for (Table table : source.getTables().values()) {
            if (target.getTables().get(table.getTableName()) == null) {
                // 如果对应的target没有这张表,直接把create Table拿出
                content.add("缺少表:"+table.getTableName());
                changeSql.add(table.getCreateTable()+";");
                continue;
            }
            // 这样就需要比较两者的字段
            compareSingelTable(table, target.getTables().get(table.getTableName()));
        }

    }

    private void compareSingelTable(Table sourceTable, Table targetTable) {
        compareColumns(sourceTable, targetTable);
    }

    private void compareColumns(Table sourceTable, Table targetTable) {
        // 记录最后一个比较的column
        String after = null;
        for (Column column : sourceTable.getColumns().values()) {
            if (targetTable.getColumns().get(column.getName()) == null) {
                // 如果对应的target没有这个字段,直接alter
                String sql = "alter table " + target.getSchema() + "." + targetTable.getTableName() + " add " + column
                        .getName() + " ";
                sql += column.getType() + " ";
                if (column.getIsNull().equals("NO")) {
                    sql += "NOT NULL ";
                } else {
                    sql += "NULL ";
                }
                if (column.getDefaultValue() != null) {
                    sql += "DEFAULT " + SqlUtil.getDbString(column.getDefaultValue()) + " ";
                }
                if (column.getComment() != null) {
                    sql += "COMMENT " + SqlUtil.getDbString(column.getComment()) + " ";
                }
                if (after != null) {
                    sql += "after " + after;
                }
                content.add("表:"+targetTable.getTableName()+"缺少字段:"+column.getName());
                changeSql.add(sql+";");
            } else {
                // 检查对应的source 和 target的属性
                String sql =
                        "alter table " + target.getSchema() + "." + targetTable.getTableName() + " change " + column
                                .getName() + " ";
                Column sourceColumn = column;
                Column targetColumn = targetTable.getColumns().get(sourceColumn.getName());
                // 比较两者字段,如果返回null,表明一致
                String sqlExtend = compareSingleColumn(sourceColumn, targetColumn);
                if (sqlExtend != null) {
                    content.add("表:"+targetTable.getTableName()+"字段:"+column.getName()+"属性不一致");
                    changeSql.add(sql + sqlExtend+";");
                }
            }
            after = column.getName();
        }

        // remove the target redundancy columns
        for (Column column : targetTable.getColumns().values()) {
            if (sourceTable.getColumns().get(column.getName()) == null) {
                // redundancy , so drop it
                String sql = "alter table " + target.getSchema() + "." + targetTable.getTableName() + " drop " + column
                        .getName() + " ";
                content.add("表:"+targetTable.getTableName()+"多余字段:"+column.getName());
                changeSql.add(sql+";");
            }
        }
    }

    private String compareSingleColumn(Column sourceColumn, Column targetColumn) {
        List<String> modify = new ArrayList<String>();
        if (sourceColumn.equals(targetColumn)) {
            return null;
        }
        String changeSql = "";
        if (!sourceColumn.getName().equals(targetColumn.getName())) {
            // never reach here
            throw new RuntimeException("the bug in this tool");
        }
        changeSql += sourceColumn.getName() + " ";
        changeSql += sourceColumn.getType() + " ";
        if (sourceColumn.getIsNull().equals("NO")) {
            changeSql += "NOT NULL ";
        } else {
            changeSql += "NULL ";
        }
        if (sourceColumn.getExtra().toUpperCase().indexOf("AUTO_INCREMENT") != -1) {
            changeSql += "AUTO_INCREMENT ";
        }
        if (!StringUtils.isEmpty(sourceColumn.getDefaultValue())) {
            changeSql += "DEFAULT " + sourceColumn.getDefaultValue() + " ";
        }
        if (sourceColumn.getComment() != null) {
            changeSql += "COMMENT " + SqlUtil.getDbString(sourceColumn.getComment()) + " ";
        }
        return changeSql;
    }

    // compare the index
    private void compareKeys() {
        for (Table table : source.getTables().values()) {
            // 这样就需要比较两者的索引)
            if (target.getTables().get(table.getTableName()) != null) {
                compareSingleKeys(table, target.getTables().get(table.getTableName()));
            }
        }
    }

    private void compareSingleKeys(Table sourceTable, Table targetTable) {
        for (Index index : sourceTable.getIndexes().values()) {
            String sql = "alter table " + target.getSchema() + "." + targetTable.getTableName() + " ";
            if (targetTable.getIndexes().get(index.getIndexName()) == null) {
                if (index.getIndexName().equals("PRIMARY")) {
                    sql += "add primary key ";
                } else {
                    if (index.getNotUnique().equals("0")) {
                        sql += "add unique "+index.getIndexName()+" ";
                    } else {
                        sql += "add index "+index.getIndexName()+" ";
                    }
                }
                sql += "(`";
                for (String key : index.getColumns()) {
                    sql += key.trim() + "`,`";
                }
                // 去掉最后一个,`
                sql = sql.substring(0, sql.length() - 2) + ")";
                content.add("表:"+targetTable.getTableName()+"缺少索引:"+index.getIndexName());
                changeSql.add(sql+";");
            }
        }
        for (Index index : targetTable.getIndexes().values()) {
            if (sourceTable.getIndexes().get(index.getIndexName()) == null) {
                // 表明此索引多余
                String sql = "alter table " + target.getSchema() + "." + targetTable.getTableName() + " ";
                if (index.getIndexName().equals("PRIMARY")) {
                    sql += "drop primary key ";
                } else {
                    sql += "drop index " + index.getIndexName();
                }
                content.add("表:"+targetTable.getTableName()+"多余索引:"+index.getIndexName());
                changeSql.add(sql+";");
            }
        }
    }

    public MetaData getSource() {
        return source;
    }

    public void setSource(MetaData source) {
        this.source = source;
    }

    public MetaData getTarget() {
        return target;
    }

    public void setTarget(MetaData target) {
        this.target = target;
    }

    public List<String> getChangeSql() {
        return changeSql;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public void setChangeSql(List<String> changeSql) {
        this.changeSql = changeSql;
    }
}
