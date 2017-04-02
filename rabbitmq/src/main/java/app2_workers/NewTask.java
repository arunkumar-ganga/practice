package app2_workers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import connection.RMQConnection;

import java.util.concurrent.TimeoutException;

/**
 * Created by arun on 4/1/17.
 */
public class NewTask {
    private final static String QUEUE_NAME = "Worker";

    private static String getMessage(String[] strings){
        if (strings.length < 1)
            return "Hello World!";
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }

    public static void main(String[] arg) throws java.io.IOException, TimeoutException {
        Connection connection = RMQConnection.getInstance();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);


        String message = getMessage(new String[]{"First","message."});
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        message = getMessage(new String[]{"Second","message.."});
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        message = getMessage(new String[]{"Thrid","message..."});
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        message = getMessage(new String[]{"Fourth","message...."});
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
    }
}
