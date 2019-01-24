package com.zhangyingwei.ultraman.core.server.filter;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

public class BusFilter implements IUFilter {
    private Map<Type,IUFilter> filters = new HashMap<Type, IUFilter>();

    public BusFilter() {
        this.addFilter(Type.ADDRESS, new AddressFilter());
    }

    public void addFilter(Type key, IUFilter filter) {
        this.filters.put(key, filter);
    }

    public <T extends IUFilter>T get(Type key) {
        return (T) this.filters.get(key);
    }

    @Override
    public boolean accept(ChannelHandlerContext ctx, Object msg) {
        return filters.values().stream().allMatch(filter -> filter.accept(ctx, msg));
    }

    public enum Type {
        ADDRESS
    }
}
