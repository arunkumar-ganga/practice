/**
 * Created by arun.kumarg on 31/03/17.
 */
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.util.concurrent.TimeoutException;

public class Sender {
  private final static String QUEUE_NAME = "Test";

  public static void main(String[] argv) throws java.io.IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("192.168.0.105");
    factory.setUsername("test");
    factory.setPassword("test");
    //factory.setPort(15672);
    //factory.setVirtualHost("vHost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    for(int i=0; i<100000; i++){
      String message = "Hello World!";
      channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
    }
    System.out.println(" [x] Sent '");

    channel.close();
    connection.close();
  }

}
