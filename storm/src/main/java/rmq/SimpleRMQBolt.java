package rmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import rmq.connection.RMQConnection;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by arun on 4/3/17.
 */
public class SimpleRMQBolt implements IRichBolt {

    private final static String QUEUE_NAME = "Receiver";
    private Connection connection;
    private Channel channel = null;
    private OutputCollector collector;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = collector;
        connection = RMQConnection.getInstance();
        try {
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void execute(Tuple tuple) {
        String message = tuple.getString(0);
        try {
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cleanup() {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("call", "duration"));
    }

    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
