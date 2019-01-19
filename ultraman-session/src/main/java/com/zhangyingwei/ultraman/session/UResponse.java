package com.zhangyingwei.ultraman.session;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.xson.core.XSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
@Data
@ToString
@Slf4j
public class UResponse implements IUSession {
    private static final String KEY_RESULT = "result";
    private static final String KEY_STATE = "state";
    private Object result;
    private State state;

    public UResponse(Object result, State state) {
        this.result = result;
        this.state = state;
    }

    public UResponse(byte[] bytes) {
        this.initByBytes(bytes);
    }

    private void initByBytes(byte[] bytes) {
        Map<String, Object> paramsMap = JSON.parseObject(bytes, Map.class, Feature.AllowSingleQuotes);
        this.result = paramsMap.get(KEY_RESULT);
        this.state = State.valueOf((String) paramsMap.get(KEY_STATE));
    }

    public byte[] toBytes() {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(KEY_RESULT, this.result);
        paramsMap.put(KEY_STATE, this.state);
        return JSON.toJSONBytes(paramsMap, SerializerFeature.UseSingleQuotes);
    }

    public enum State {
        SUCCESS,ERROR,PERMISSION_DENIED
    }
}
