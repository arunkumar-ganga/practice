package App2;

import com.rabbitmq.client.*;
import connection.RMQConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by arun on 4/1/17.
 */
public class Worker {
    private final static String QUEUE_NAME = "Worker";

    public static void main(String[] arg) throws java.io.IOException, java.lang.InterruptedException, TimeoutException,Exception {

        Connection connection = RMQConnection.getInstance();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                try {
                    doWork(message);
                    boolean autoAck = false; // acknowledgment is covered below
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        boolean autoAck = false; // acknowledgment is covered below
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);


    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(3000);
        }
    }
}
