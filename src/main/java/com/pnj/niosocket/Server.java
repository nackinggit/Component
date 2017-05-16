package com.pnj.niosocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright  (c) 2017</p>
 * <p>Company: SenseTime</p>
 * <p>Email: pengnanjing@sensetime.com</p>
 *
 * @author nanjing
 * @date 17-5-16:上午10:41
 */
public class Server {
    private Selector selector;

    public void initServer(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }


    public void listen() {
        System.out.println("server start up");
        boolean isOK = true;
        while (isOK) {
            try {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = server.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.write(ByteBuffer.wrap("aaa".getBytes()));
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
                        int i = channel.read(byteBuffer);
                        byte[] data = new byte[i];
                        int j = 0;
                        byteBuffer.flip();
                        while (i-- > 0) {
                            data[j++] = byteBuffer.get();
                        }

                        System.out.println("get msg: " + new String(data));
                        Thread.sleep(1000);
                        channel.write(ByteBuffer.wrap(data));
                    }
                }
            } catch (Exception e) {
                isOK = false;
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.initServer(12543);
        server.listen();
    }
}
