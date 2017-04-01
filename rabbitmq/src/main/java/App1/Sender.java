package App1; /**
 * Created by arun.kumarg on 31/03/17.
 */

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import connection.RMQConnection;

import java.util.concurrent.TimeoutException;

public class Sender {
  private final static String QUEUE_NAME = "Test";

  public void run() throws java.io.IOException, TimeoutException {
    Connection connection = RMQConnection.getInstance();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    for(int i=0; i<100000; i++){
      String message = "Hello World!";
      channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
    }
    System.out.println(" [x] Sent '");

    channel.close();
    //RMQConnection.close();
  }

}
