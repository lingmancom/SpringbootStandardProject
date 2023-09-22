package com.lm.SpringBootProject.core.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateSerializeUtils {

    public static void StringToLocalDateMap(Map<String, LocalDateTime> cacheMap, String timeStr) {

        if (Objects.isNull(cacheMap.get(timeStr))) {
            LocalDateTime localDateTime = StringToLocalDate(timeStr);
            cacheMap.put(timeStr, localDateTime);
        }
    }

    public static LocalDateTime StringToLocalDate(String timeStr) {
        LocalDateTime now = DateUtil.parseLocalDateTime("2010-01-01 00:00:00");
        if (StringUtils.isBlank(timeStr) || !hasConsecutiveDigits(timeStr)){
            return now;
        }
        // 去除中文字
        timeStr = removeChineseSurrounding(timeStr);
        timeStr = timeStr.replace("\"", "");
        // 包含AM/PM字眼的
        if (timeStr.contains("AM") || timeStr.contains("PM")) {
            String[] split = timeStr.replace("  ", " ").split(" ");
            String time = split[2] + split[0] + split[1];
            DateTime parse = DateUtil.parse(time, "yyyyMMdd");
            return parse.toLocalDateTime();
        }
        // 包含年月日的
        String[] split = getArray(timeStr, "-", "/");
        if (Optional.ofNullable(split).isPresent()) {
            String year = StrUtil.isNotBlank(split[0]) ? split[0] : "1900";
            String month = StrUtil.isNotBlank(split[1]) && !split[1].equals("0")? split[1] : "01";
            String day = StrUtil.isNotBlank(split[2]) && !split[2].equals("0") ? split[2]  : "01";
            String time = year + "-" + month + "-" + day;
            DateTime parse = DateUtil.parse(time, "yyyy-MM-dd");
            return parse.toLocalDateTime();
        }
        split = getArrayNoDay(timeStr, "-", "/", "\\.");
        if (Optional.ofNullable(split).isPresent()) {
            String year = StrUtil.isNotBlank(split[0]) ? split[0] : "1900";
            String month;
            String day;
            if (split[1].length() == 3){
                month = split[1].substring(0, 2);
                day = split[1].substring(2, 3);
                if (!NumberUtil.isNumber(month) ||  "00".equals(month)) month = "01";
                if (!NumberUtil.isNumber(day) ||  "0".equals(day)) day = "01";
            } else if (split[1].length() == 2) {
                month = split[1].substring(0, 1);
                day = split[1].substring(1, 2);
                if (!NumberUtil.isNumber(month) || "0".equals(month)) month = "01";
                if (!NumberUtil.isNumber(day) || "0".equals(day)) day = "01";
            } else {
                month = NumberUtil.isNumber(split[1]) && !split[1].equals("0") ? split[1] : "01";
                day = "01";
            }
            String time = year + "-" + month + "-" + day;
            DateTime parse = DateUtil.parse(time, "yyyy-MM-dd");
            return parse.toLocalDateTime();
        }
        // 取前4位
        String year = timeStr.substring(0, 4);
        if (StrUtil.isNotBlank(year)) {
            String time = year + "-01-01";
            DateTime parse = DateUtil.parse(time, "yyyy-MM-dd");
            return parse.toLocalDateTime();
        }
        return now;
    }
    private static String[] getArrayNoDay(String timeStr, String s, String s1, String s2) {
        String[] split = timeStr.split(s);
        if (split.length == 2) return split;
        String[] split1 = timeStr.split(s1);
        if (split1.length == 2) return split1;
        String[] split2 = timeStr.split(s2);
        if (split2.length == 2) return split2;
        return null;
    }
    private static String[] getArray(String timeStr, String s, String s1) {
        String[] split = timeStr.split(s);
        if (split.length == 3) return split;
        String[] split1 = timeStr.split(s1);
        if (split1.length == 3) return split1;
        return null;
    }

    public static String removeChineseSurrounding(String input) {
        if (isContainsChinese(input)) {
            // 匹配中文字符的正则表达式
            String chineseRegex = "[\\u4e00-\\u9fa5]";

            // 使用正则表达式匹配中文开始和中文结束的部分
            Pattern pattern = Pattern.compile(chineseRegex + "(.*?)" + chineseRegex);
            Matcher matcher = pattern.matcher(input);

            // 替换匹配到的部分为空字符串
            StringBuilder sb = new StringBuilder();
            while (matcher.find()) {
                matcher.appendReplacement(sb, "");
            }
            matcher.appendTail(sb);

            input = sb.toString();
            // 还剩遗留的数据
            if (isContainsChinese(input)) {
                // 只保留前4位
                return input.substring(0, 5);
            }
            return sb.toString();
        }
        return input;
    }
    public static boolean isContainsChinese(String input) {
        String pattern = "[\u4e00-\u9fa5]";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        return matcher.find();
    }

    public static boolean hasConsecutiveDigits(String str) {
        String pattern = "\\d{4}";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(str);

        return matcher.find();
    }
}
