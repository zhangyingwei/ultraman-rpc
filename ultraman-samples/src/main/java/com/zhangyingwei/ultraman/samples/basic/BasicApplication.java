package com.zhangyingwei.ultraman.samples.basic;

import com.zhangyingwei.ultraman.core.server.UltramanRpcServer;
import com.zhangyingwei.ultraman.core.service.ServiceManager;
import com.zhangyingwei.ultraman.samples.basic.services.HelloWordService;
import com.zhangyingwei.ultraman.samples.basic.services.IHelloWorldService;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public class BasicApplication {
    public static void main(String[] args) throws InterruptedException {
        ServiceManager serviceManager = new ServiceManager();
        serviceManager.bindService(IHelloWorldService.class.getName(), new HelloWordService());
        new UltramanRpcServer(serviceManager).bind(8000);
    }
}
