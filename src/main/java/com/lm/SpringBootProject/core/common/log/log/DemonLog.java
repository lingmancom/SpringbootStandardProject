package com.lm.SpringBootProject.core.common.log.log;


import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.aliyun.log.producer.LogProducer;
import com.aliyun.openservices.aliyun.log.producer.Producer;
import com.aliyun.openservices.aliyun.log.producer.ProducerConfig;
import com.aliyun.openservices.aliyun.log.producer.ProjectConfig;
import com.aliyun.openservices.aliyun.log.producer.errors.ProducerException;
import com.aliyun.openservices.log.common.LogItem;
import com.lm.SpringBootProject.core.common.log.DemonProjectConfig;

import java.util.UUID;


public class DemonLog {

    private static void Log(AliLogFullModel model, LogType logType) {
        ProducerConfig producerConfig = new ProducerConfig();
        Producer producer = new LogProducer(producerConfig);
        producer.putProjectConfig(new ProjectConfig("center", DemonProjectConfig.log_endpoint, DemonProjectConfig.log_accessKeyId, DemonProjectConfig.log_accessKeySecret));
        LogItem logItem = new LogItem();
        logItem.PushBack("apiElapseTime", String.valueOf(model.getApiElaspseTime()));
        logItem.PushBack("apiException", String.valueOf(model.getApiException()));
        logItem.PushBack("apiPath", String.valueOf(model.getApiPath()));
        logItem.PushBack("apiSenderId", String.valueOf(model.getApiSenderId()));
        logItem.PushBack("apiSenderName", String.valueOf(model.getApiSenderName()));
        logItem.PushBack("ip", String.valueOf(model.getIp()));
        logItem.PushBack("isException", String.valueOf(model.getIsException()));
        logItem.PushBack("itemId", UUID.randomUUID().toString());
        logItem.PushBack("query", model.getQuery());
        logItem.PushBack("logType", logType.toString());
        logItem.PushBack("message", model.getMessage());
        //下面需要自定义的
        logItem.PushBack("environment", DemonProjectConfig.environment.name());
        logItem.PushBack("project", DemonProjectConfig.project);
        try {
            producer.send(
                    "center",
                    "log",
                    logItem);
        } catch (InterruptedException e) {
        } catch (ProducerException e) {
        }
    }


    /**
     * 记录错误日志
     *
     * @param content
     * @param queryPara
     */
    public static void LogException(String content, String... queryPara) {
        String query = (queryPara.length >= 1) ? queryPara[0] : "";
        AliLogFullModel data = new AliLogFullModel();
        data.setQuery(query);
        data.setMessage(content);
        Log(data, LogType.exception);
    }

    /**
     * 记录错误日志
     *
     * @param content
     * @param queryPara
     */
    public static void LogException(Object content, String... queryPara) {
        String query = (queryPara.length >= 1) ? queryPara[0] : "";
        String contentString = JSON.toJSONString(content);
        LogException(contentString, query);
    }

    /**
     * 记录Api日志记录
     *
     * @param message
     */
    public static void LogApi(AliLogFullModel message) {
        Log(message, LogType.api);
    }

    /**
     * 记录debug日志
     *
     * @param content
     * @param queryPara
     */
    public static void LogDebug(String content, String... queryPara) {
        String query = (queryPara.length >= 1) ? queryPara[0] : "";
        AliLogFullModel data = new AliLogFullModel();
        data.setQuery(query);
        data.setMessage(content);
        Log(data, LogType.exception);
    }

    /**
     * 记录debug日志
     *
     * @param content
     * @param queryPara
     */
    public static void LogDebug(Object content, String... queryPara) {
        String query = (queryPara.length >= 1) ? queryPara[0] : "";
        String contentString = JSON.toJSONString(content);
        LogDebug(contentString, query);
    }
}
