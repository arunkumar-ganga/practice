package rmq.helper;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rmq.connection.RMQConnection;

import java.util.concurrent.TimeoutException;

/**
 * Created by arun on 4/3/17.
 */
public class RMQMessageSender {
    private final static String QUEUE_NAME = "Sender";

    public static void main(String... args) throws java.io.IOException, TimeoutException {
        Connection connection = RMQConnection.getInstance();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for(int i=0; i<100000; i++){
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        }
        System.out.println(" [x] Sent '");

        channel.close();
        RMQConnection.close();
    }

}