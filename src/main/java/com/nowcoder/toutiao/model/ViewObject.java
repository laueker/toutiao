package com.nowcoder.toutiao.model;
import java.util.HashMap;
import java.util.Map;

public class ViewObject {     //为了方便展示
    private Map<String, Object> objs = new HashMap<String, Object>();
    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
