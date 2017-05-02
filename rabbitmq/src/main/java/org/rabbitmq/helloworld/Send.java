
package org.rabbitmq.helloworld;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
	private final static String QUEUE_NAME = "sandgw.notice.shoukuanbao.midfee.queue";
	private final static String ROUTINGKEY_NAME = "sandgw.notice.shoukuanbao.midfee.routingkey";
	private final static String EXCHANGE_NAME = "sandgw.notice.shoukuanbao.exchange";

	public static void main(String[] args) throws IOException, TimeoutException {
        /** 
         * 创建连接连接到MabbitMQ 
         */  
        ConnectionFactory factory = new ConnectionFactory();  
        //设置MabbitMQ所在主机ip或者主机名  
        factory.setHost("localhost");  
        //设置端口
        factory.setPort(5672);
        //设置用户名
        //factory.setUsername("admin");
        //设置密码
        //factory.setPassword("admin");
        
        //创建一个连接  
        Connection connection = factory.newConnection();  
        //创建一个频道  
        Channel channel = connection.createChannel();  
        //指定一个队列  
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);  
        //发送的消息  
        String message = "hello world!";  
        //往队列中发出一条消息  
        channel.basicPublish(EXCHANGE_NAME, ROUTINGKEY_NAME, null, message.getBytes());  
        System.out.println(" [x] Sent '" + message + "'");  
        //关闭频道和连接  
        channel.close();  
        connection.close(); 
	}
}