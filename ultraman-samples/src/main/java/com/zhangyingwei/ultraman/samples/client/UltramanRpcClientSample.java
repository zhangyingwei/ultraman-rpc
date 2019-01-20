package com.zhangyingwei.ultraman.samples.client;

import com.zhangyingwei.ultraman.client.UltramanRpcClient;
import com.zhangyingwei.ultraman.samples.basic.services.IHelloWorldService;

import java.util.List;

/**
 * @desc: UltramanRpc 客户端示例代码
 */
public class UltramanRpcClientSample {
    public static void main(String[] args) {
        UltramanRpcClient client = new UltramanRpcClient("localhost", 8000);
        IHelloWorldService service = client.getBean(IHelloWorldService.class);
        String result = service.say("zhangyingwei");
        System.out.println(result);
        List<String> resultList = service.say();
        for (String item : resultList) {
            System.out.println(item);
        }
    }
}
