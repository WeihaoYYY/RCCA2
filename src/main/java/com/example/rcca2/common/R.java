package com.example.rcca2.common;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class R<T> {

    private Integer code; // 编码：1成功，0和其它数字为失败
    private String msg; // 错误信息
    private T data; // 数据
    private Map<String, Object> map = new HashMap<>(); // 动态数据

    // 成功响应的静态方法，可以设置数据
    public static <T> R<T> ok(T object) {
        R<T> r = new R<>();
        r.data = object;
        r.code = 1;
        r.msg = "Success"; // 可以考虑增加一个默认的成功信息
        return r;
    }

    // 成功响应的方法，可以设置自定义消息和数据
    public static <T> R<T> ok(String msg, T object) {
        R<T> r = new R<>();
        r.data = object;
        r.code = 1;
        r.msg = msg;
        return r;
    }

    // 错误响应的静态方法
    public static <T> R<T> error(String msg) {
        R<T> r = new R<>();
        r.msg = msg;
        r.code = 0; // 默认错误码，可以自定义
        return r;
    }

    // 错误响应的方法，可以设置自定义错误码
    public static <T> R<T> error(int code, String msg) {
        R<T> r = new R<>();
        r.msg = msg;
        r.code = code;
        return r;
    }

    // 增加动态数据
    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    // 设置自定义的返回码（如 HTTP 状态码）
    public R<T> code(int code) {
        this.code = code;
        return this;
    }

    // 设置自定义的消息
    public R<T> message(String msg) {
        this.msg = msg;
        return this;
    }
}
