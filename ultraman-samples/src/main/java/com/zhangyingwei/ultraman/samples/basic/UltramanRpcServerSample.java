package com.zhangyingwei.ultraman.samples.basic;

import com.zhangyingwei.ultraman.core.server.UltramanRpcServer;
import com.zhangyingwei.ultraman.core.service.ServiceManager;
import com.zhangyingwei.ultraman.samples.basic.services.HelloWordService;
import com.zhangyingwei.ultraman.samples.basic.services.IHelloWorldService;

/**
 * @desc: UltramanRpc 服务端示例代码
 */
public class UltramanRpcServerSample {
    public static void main(String[] args) throws InterruptedException {
        UltramanRpcServer server = new UltramanRpcServer();
        server.bindService(IHelloWorldService.class.getName(), new HelloWordService());
        server.authorize("127.0.0.1");
        server.authorize("localhost");
        server.bind(8000);
    }
}
