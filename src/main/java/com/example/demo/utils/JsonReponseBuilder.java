package com.example.demo.utils;

import org.springframework.stereotype.Component;

/**
 * @author zhanghaoyang
 */
@Component
public class JsonReponseBuilder {
    private StringBuilder stringBuilder = new StringBuilder("{");

    public StringBuilder addList(String key, String... values) {
        // if there is an array to implement this method
        return null;
    }

    // 处理特殊字符串 key,value,key1,value1这种格式的字符串可能存在bug所以我们以约定
    // 去处理这种情况 必须为双数 否则构建失败
    public JsonReponseBuilder addItems(String... items) {
        if (items.length % 2 != 0 && items.length <= 2) {
            throw new RuntimeException("没有按照约定方法构建消息");
        } else {
            for (int i = 0; i < items.length; i += 2) {
                add(items[i], items[i + 1]);
            }
        }
        return this;
    }

    public JsonReponseBuilder add(String key, String value) {
        this.stringBuilder.append("\"");
        this.stringBuilder.append(key);
        this.stringBuilder.append("\":\"");
        this.stringBuilder.append(value);
        this.stringBuilder.append("\",");
        return this;
    }

    public String build() {
        this.stringBuilder.deleteCharAt(this.stringBuilder.length() - 1);
        this.stringBuilder.append("}");
        return this.stringBuilder.toString();
    }
}
