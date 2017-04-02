package app3_exchanges;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import connection.RMQConnection;

import java.util.concurrent.TimeoutException;

/**
 * Created by arun on 4/2/17.
 */
public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

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

    public static void main(String[] argv) throws java.io.IOException, TimeoutException {

        Connection connection = RMQConnection.getInstance();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        for (int i=0; i<200; i++){
            String message = getMessage(new String[]{"First","message."});
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            message = getMessage(new String[]{"Second","message.."});
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            message = getMessage(new String[]{"Thrid","message..."});
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            message = getMessage(new String[]{"Fourth","message...."});
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }

        channel.close();
        connection.close();
    }

}
