package com.lm.springbootstandardproject.core.utils.wxrobot;

import cn.hutool.core.bean.BeanUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextMessage extends WechatBotMessage {
    private Text text;

    public TextMessage(String content, List<String> mentionedList, List<String> mentionedMobileList) {
        super("text");
        this.text = new Text(content, mentionedList, mentionedMobileList);
    }

    private static class Text {
        private String content;
        private List<String> mentioned_list;
        private List<String> mentioned_mobile_list;

        public Text(String content, List<String> mentionedList, List<String> mentionedMobileList) {
            this.content = content;
            this.mentioned_list = mentionedList;
            this.mentioned_mobile_list = mentionedMobileList;
        }
    }

    public Map<String, Object> toHashMap() {
        Map<String, Object> hashMap = BeanUtil.beanToMap(this);

        Map<String, Object> textMap = new HashMap<>();
        textMap.put("content", this.text.content);
        textMap.put("mentioned_list", this.text.mentioned_list);
        textMap.put("mentioned_mobile_list", this.text.mentioned_mobile_list);

        hashMap.put("text", textMap);
        return hashMap;
    }
}