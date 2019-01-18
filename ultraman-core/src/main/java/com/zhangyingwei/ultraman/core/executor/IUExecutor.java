package com.zhangyingwei.ultraman.core.executor;

import com.zhangyingwei.ultraman.common.exceptions.UServiceExecuteException;
import com.zhangyingwei.ultraman.common.exceptions.UServiceNotFoundException;
import com.zhangyingwei.ultraman.session.URequest;
import com.zhangyingwei.ultraman.session.UResponse;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public interface IUExecutor {
    UResponse execure(URequest request) throws UServiceNotFoundException, NoSuchMethodException, ClassNotFoundException, UServiceExecuteException;
}
