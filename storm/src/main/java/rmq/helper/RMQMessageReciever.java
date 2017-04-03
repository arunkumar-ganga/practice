package rmq.helper;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.GetResponse;
import rmq.connection.RMQConnection;

import java.util.concurrent.TimeoutException;

/**
 * Created by arun on 4/3/17.
 */
public class RMQMessageReciever {
    private final static String QUEUE_NAME = "Sender";

    public static void main(String... args) throws java.io.IOException, TimeoutException {
        Connection connection = RMQConnection.getInstance();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        GetResponse response = channel.basicGet(QUEUE_NAME,false);
        if (response == null) {
            System.out.println("No messages!!");
        } else {
            AMQP.BasicProperties props = response.getProps();
            String message = new String(response.getBody(), "UTF-8");
            long deliveryTag = response.getEnvelope().getDeliveryTag();
            System.out.println(message);
            channel.basicAck(deliveryTag, true);
        }
        channel.close();
    }

}