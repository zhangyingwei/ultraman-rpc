package com.zhangyingwei.ultraman.core.server.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public class AddressFilter implements IUFilter<String> {
    private List<String> ipList = new ArrayList<String>();

    @Override
    public boolean accept(String ip) {
        return ipList.contains(ip);
    }

    public AddressFilter authorize(String ip) {
        this.ipList.add(ip);
        return this;
    }
}
