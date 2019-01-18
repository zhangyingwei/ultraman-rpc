package com.zhangyingwei.ultraman.session;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import lombok.ToString;
import org.xson.core.XSON;
import java.util.HashMap;
import java.util.Map;

/**
 * request of rpc
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
@Data
@ToString
public class URequest implements IUSession {
    private String serviceClassName;
    private String methodName;
    private Object[] args;
    private String[] argsTypes;
    private final static String KEY_CLAZZ = "className";
    private static final String KEY_METHOD = "methodName";
    private final static String KEY_ARGS = "args";
    private static final String KEY_ARG_TYPES = "argTypes";

    public URequest(String serviceClassName, String methodName, String[] argsTypes, Object[] args) {
        this.serviceClassName = serviceClassName;
        this.methodName = methodName;
        this.args = args;
        this.argsTypes = argsTypes;
    }

    public URequest(byte[] bytes) throws ClassNotFoundException {
        this.initByBytes(bytes);
    }

    public byte[] toBytes() {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(KEY_CLAZZ, this.serviceClassName);
        paramsMap.put(KEY_METHOD, this.methodName);
        paramsMap.put(KEY_ARG_TYPES, argsTypes);
        paramsMap.put(KEY_ARGS, args);
        String json = JSON.toJSONString(paramsMap).concat("\n");
        return JSON.toJSONBytes(json, SerializerFeature.UseSingleQuotes);
    }

    private void initByBytes(byte[] bytes) {
        Map<String, Object> params = JSON.parseObject(bytes, Map.class, Feature.AllowSingleQuotes);
//        String jsonString = (String) XSON.decode(bytes);
//        Map<String,Object> params = (Map<String, Object>) JSON.parse(jsonString);
        this.serviceClassName= (String) params.get(KEY_CLAZZ);
        this.methodName = (String) params.get(KEY_METHOD);
        if (params.containsKey(KEY_ARG_TYPES)) {
            JSONArray typeArrray = (JSONArray) params.get(KEY_ARG_TYPES);
            String[] types = new String[typeArrray.size()];
            JSON.parseArray(JSONArray.toJSONString(typeArrray),String.class).toArray(types);
            this.argsTypes = types;
            this.args = JSON.parseArray(JSONArray.toJSONString(params.get(KEY_ARGS)),Object.class).toArray();
        }
    }
}
