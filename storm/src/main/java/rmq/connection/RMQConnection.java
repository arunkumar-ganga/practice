package rmq.connection;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by arun.kumarg on 31/03/17.
 */
public class RMQConnection {

        private static Connection rmqConnection;

        private RMQConnection(){

        }

        public static Connection getInstance(){
            if(rmqConnection == null){

                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost("192.168.0.109");
                factory.setUsername("test");
                factory.setPassword("test");
                //factory.setPort(15672);
                //factory.setVirtualHost("vHost");

                try {
                    rmqConnection = factory.newConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            return rmqConnection;
        }

        public static void close(){
            try {
                rmqConnection.close();
                rmqConnection = null;
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(rmqConnection!=null){
                    try {
                        rmqConnection.close();
                        rmqConnection = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }