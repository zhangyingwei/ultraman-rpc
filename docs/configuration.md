# 配置

## 授权配置

总的来说 ultraman-rpc 非常牛逼。相对于性能未知、bug 未知来说，授权机制则显得 `‘非常细腻’` (<del>异常粗暴</del>)，只要把 IP 添加到白名单中就可以完成授权.是不是非常细腻!!!

```java
UltramanRpcServer server = new UltramanRpcServer(serviceManager);
server.authorize("127.0.0.1");
server.authorize("localhost");
```

如上，只需要将 IP 添加到授权名单中，就可以了!!!