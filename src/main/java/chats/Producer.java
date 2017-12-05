package chats;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {

        public static final String EXCHANGE_NAME="task_exchange";
        public static void producer() throws IOException, TimeoutException {
            ConnectionFactory factory=new ConnectionFactory();
            factory.setHost("120.79.25.175");
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setPort(5672);
            Connection connection=factory.newConnection();
            Channel channel=connection.createChannel();

            Scanner sc = new Scanner(System.in);
            System.out.println("Hello "+RunMethod.username+",可以和其他人聊天了（输入数字选择聊天方式：1.everyone；2.single）");//私聊未完成
            int method=sc.nextInt();
            if(method==1){
                channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
                    while (true) {
                        String message = sc.nextLine();
                        if ("q".equals(message)) {
                            channel.close();
                            connection.close();
                            return;
                        }
                        message = RunMethod.username + " --: " + message;
                        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
                    }
            }
            if(method==2){
                System.out.println("输入私聊对象：");
                String QUEUE_NAME=sc.next();
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                //发送的消息
                System.out.println("输入信息：");
                String message = sc.next();
                //6.通过channel向队列中添加消息
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                //7.关闭频道
                channel.close();
                //8.关闭连接
                connection.close();
            }
        }
}
