package com.lm.SpringBootProject.core.common;

public enum ElasticsearchEnum {
    SEARCH_ONE(1, "初始不带任何条件"),
    SEARCH_TWO(2, "带上keyword1"),
    SEARCH_THREE(3, "追加KIND"),
    SEARCH_FOUR(4, "二次搜索追加keyword2"),
    SEARCH_FIVE(5, "带上时间"),
    SEARCH_SIX(6, "按时间或者相关度排序");
    private String msg;
    private int code;

    ElasticsearchEnum(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
