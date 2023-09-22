package com.lm.springbootstandardproject.core.utils.wxrobot;

public class MarkdownMessage extends WechatBotMessage {
    private Markdown markdown;

    public MarkdownMessage(String content) {
        super("markdown");
        this.markdown = new Markdown(content);
    }

    private static class Markdown {
        private String content;

        public Markdown(String content) {
            this.content = content;
        }
    }
}