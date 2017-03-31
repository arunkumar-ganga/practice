/**
 * Created by arun.kumarg on 31/03/17.
 */
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver {

  private final static String QUEUE_NAME = "Test";

  public static void main(String[] argv) throws java.io.IOException,
             java.lang.InterruptedException, TimeoutException {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("192.168.0.105");
    factory.setUsername("test");
    factory.setPassword("test");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
      }
    };

      channel.basicConsume(QUEUE_NAME, true, consumer);

  }
}
