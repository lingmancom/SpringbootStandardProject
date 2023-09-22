package com.lm.SpringBootProject.core.utils.Dbcompare.meta;

import lombok.Data;

@Data
public class Column {
    private String name;
    private String type;
    private String isNull;
    private String defaultValue;
    private String comment;
    private String extra;
}
