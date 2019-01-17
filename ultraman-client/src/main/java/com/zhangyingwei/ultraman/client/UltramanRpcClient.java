package com.zhangyingwei.ultraman.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyw
 * @date: 2019/1/17
 * @desc:
 */
public class UltramanRpcClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8000);
        OutputStream os=socket.getOutputStream();//字节输出流
        PrintWriter pw=new PrintWriter(os);//将输出流包装成打印流
        new Thread(() -> {
            while (true) {
                pw.write(System.currentTimeMillis() + System.getProperty("line.separator"));
                pw.flush();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
//        socket.shutdownOutput();//关闭输出流
        //3.获取输入流，用来读取服务器端的响应信息
        InputStream is=socket.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));//添加缓冲
        String info1 = br.readLine();
        System.out.println("我是客户端，服务器端说" + info1);
        new Thread(() -> {
            while (true) {
                try {
                    String info = br.readLine();
                    System.out.println("我是客户端，服务器端说" + info);
                    TimeUnit.SECONDS.sleep(1);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("receive start...");
        //4.关闭其他相关资源
//        br.close();
//        is.close();
//        pw.close();
//        os.close();
//        socket.close();
    }
}
