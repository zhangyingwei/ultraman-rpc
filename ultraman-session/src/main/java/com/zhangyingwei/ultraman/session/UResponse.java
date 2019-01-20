package com.zhangyingwei.ultraman.session;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

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

    public enum State {
        SUCCESS,ERROR,PERMISSION_DENIED
    }
}
