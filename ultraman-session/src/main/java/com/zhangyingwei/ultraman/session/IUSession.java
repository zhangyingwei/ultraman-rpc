package com.zhangyingwei.ultraman.session;

import java.io.Serializable;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public interface IUSession extends Serializable {
    byte[] toBytes();
}
