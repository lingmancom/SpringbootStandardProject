package com.lm.SpringBootProject.core.utils.Dbcompare.meta;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class Table {
    private String tableName;
    private String createTable;
    private Map<String, Column> columns;
    private Map<String, Index> indexes;

    public Table() {
        columns = new LinkedHashMap<String, Column>();
        indexes = new LinkedHashMap<String, Index>();
    }
}
