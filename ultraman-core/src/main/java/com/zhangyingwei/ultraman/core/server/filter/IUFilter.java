package com.zhangyingwei.ultraman.core.server.filter;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public interface IUFilter<T> {
    boolean accept(T t);
}
