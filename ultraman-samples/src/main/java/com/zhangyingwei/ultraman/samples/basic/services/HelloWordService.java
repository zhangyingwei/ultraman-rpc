package com.zhangyingwei.ultraman.samples.basic.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public class HelloWordService implements IHelloWorldService {

    @Override
    public List<String> say() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10000; i++) {
            list.add(i + "asdfjaslkdfjalskdjfaklsdjfklasdjfklasjdfklasjdfklasjdfklajsdklfjaskldfjaslkdfjaklsdjfklasdjflkj");
        }
        return list;
    }

    @Override
    public String say(String word) {
        return "hello ".concat(Optional.ofNullable(word).orElse("default"));
    }
}
