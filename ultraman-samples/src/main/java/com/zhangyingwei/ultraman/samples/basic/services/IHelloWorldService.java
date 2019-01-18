package com.zhangyingwei.ultraman.samples.basic.services;

import java.util.List;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public interface IHelloWorldService {
    List<String> say();
    String say(String word);
}
