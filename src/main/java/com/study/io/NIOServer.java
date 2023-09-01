package com.study.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) {
        try {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            // 设置不阻塞
            serverChannel.configureBlocking(false);
            serverChannel.bind(new InetSocketAddress(9999));
            System.out.println("NIO NIOServer has started ,listening on port:" + serverChannel.getLocalAddress());

            Selector selector = Selector.open();

            // 每个客户端来了之后，就把客户端注册到selector选择器上，默认状态就是ACCEPT
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            RequestHandler requestHandler = new RequestHandler();
            // 轮询，服务端不断轮询，等待客户端的连接
            while (true) {
                int select = selector.select();
                if (select == 0) {
                    continue;
                }
                // 如果selector有的话，那么就取出对应的channel
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    // 判断SelectionKey中的channel状态如何
                    if (key.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        SocketChannel clientChannel = channel.accept();
                        // 客户端的channel来源打出来
                        System.out.println("Connection from " + clientChannel.getRemoteAddress());
                        // 将客户端的也设置为非阻塞
                        clientChannel.configureBlocking(false);
                        // 将channel的状态设置为read
                        clientChannel.register(selector, SelectionKey.OP_READ);
                    }
                    // 接下来轮询到的时候发现状态是readable
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        // 数据的交互是以buffer为中间桥梁的
                        channel.read(buffer);
                        // 用buffer取数据也用buffer返回数据
                        String request = new String(buffer.array()).trim();
                        buffer.clear();
                        System.out.println(String.format("From %s : %s", channel.getRemoteAddress(), request));
                        String response = requestHandler.handle(request);
                        channel.write(ByteBuffer.wrap(response.getBytes()));
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
