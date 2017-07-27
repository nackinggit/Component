package com.pnj.niosocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright  (c) 2017</p>
 *
 * @author nanjing
 * @date 17-5-16:上午10:57
 */
public class Client {
    private Selector selector;

    public void initClient(String ip, int port) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        selector = Selector.open();
        socketChannel.connect(new InetSocketAddress(ip, port));
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
    }


    public void listen() {
        System.out.println("client start up");
        boolean isOK = true;
        while (true) {
            try {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isConnectable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        if(client.isConnectionPending()) {
                            client.finishConnect();
                        }
                        client.configureBlocking(false);
                        client.write(ByteBuffer.wrap("aaa".getBytes()));
                        client.register(selector, SelectionKey.OP_READ);
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
        Client client = new Client();
        client.initClient("localhost", 12543);
        client.listen();
    }
}
