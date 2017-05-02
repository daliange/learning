
package org.rabbitmq.demo;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Reqv {
	
	private final static String QUEUE_NAME = "sandgw.notice.shoukuanbao.midfee.queue";
	//private final static String ROUTINGKEY_NAME = "sandgw.notice.shoukuanbao.midfee.routingkey";
	//private final static String EXCHANGE_NAME = "sandgw.notice.shoukuanbao.exchange";
  
    public static void main(String[] argv) throws java.io.IOException,  
            java.lang.InterruptedException, TimeoutException  
    {  
        //打开连接和创建频道，与发送端一样  
        ConnectionFactory factory = new ConnectionFactory();  
        factory.setHost("localhost");  
        //设置端口
        factory.setPort(5672);
        //设置用户名
        //factory.setUsername("admin");
        //设置密码
        //factory.setPassword("admin");
        Connection connection = factory.newConnection();  
        Channel channel = connection.createChannel();  
        //声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。  
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);  
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");  
          
        //创建队列消费者  
        QueueingConsumer consumer = new QueueingConsumer(channel);  
        //指定消费队列  
        channel.basicConsume(QUEUE_NAME, true, consumer);  
        while (true)  
        {  
            //nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）  
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
            String message = new String(delivery.getBody());  
            System.out.println(" [x] Received '" + message + "'");  
        }  
  
    }  
}