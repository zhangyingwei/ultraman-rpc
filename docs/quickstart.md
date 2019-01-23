# 快速开始

> 本来不准备些文档，但是作为一个反复无常的bug创造者。写文档的动力除了来自于装逼之外还为了防止以后自己都看不懂自己的写的bug. 索性乖乖的简单写点.

## 准备工作

在 pom 文件中添加仓库的配置

```xml
<repositories>
    <repository>
        <id>ultraman-rpc</id>
        <url>https://raw.github.com/zhangyingwei/ultraman-rpc/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```


## 服务端

```xml
<dependency>
    <groupId>com.zhangyingwei.ultraman-rpc</groupId>
    <artifactId>ultraman-core</artifactId>
    <version>0.0.1</version>
</dependency>
```

```java
public interface IHelloWorldService extends Serializable {
    String say(String word);
}
```

```java
public class HelloWorldService implements IHelloWorldService {
    @Override
    public String say(String word) {
        return "hello ".concat(Optional.ofNullable(word).orElse("default"));
    }
}
```

```java
public class UltramanRpcServerSample {
    public static void main(String[] args) throws InterruptedException {
        ServiceManager serviceManager = new ServiceManager();
        serviceManager.bindService(IHelloWorldService.class.getName(), new HelloWorldService());
        UltramanRpcServer server = new UltramanRpcServer(serviceManager);
        server.authorize("127.0.0.1");
        server.authorize("localhost");
        server.bind(8000);
    }
}
```

!> 这里需要注意的是，由于程序中需要将对象进行序列化与反序列化。因此，所有接口都必须继承 Serializable 接口方可.

## 客户端

```xml
<dependency>
    <groupId>com.zhangyingwei.ultraman-rpc</groupId>
    <artifactId>ultraman-client</artifactId>
    <version>0.0.1</version>
</dependency>
```

还需要将服务端定义 service 的接口导入程序中.

```java
public class UltramanRpcClientSample {
    public static void main(String[] args) {
        UltramanRpcClient client = new UltramanRpcClient("localhost", 8000);
        IHelloWorldService service = client.getBean(IHelloWorldService.class);
        String result = service.say("zhangyingwei");
        System.out.println(result);
    }
}
```

?> 输出结果： hello zhangyingwei