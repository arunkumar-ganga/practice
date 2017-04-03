package rmq;

import com.rabbitmq.client.*;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import rmq.connection.RMQConnection;

import java.io.IOException;
import java.util.Map;

/**
 * Created by arun on 4/3/17.
 */
public class SimpleRMQSpout implements IRichSpout {
    private final static String QUEUE_NAME = "Sender";
    private Connection connection;
    private Channel channel = null;
    private SpoutOutputCollector collector;
    private boolean completed = false;

    //Create instance for TopologyContext which contains topology data.
    private TopologyContext context;

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.context = topologyContext;
        this.collector = spoutOutputCollector;
        connection = RMQConnection.getInstance();
        try {
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void close() {

    }

    public void activate() {

    }

    public void deactivate() {

    }

    public void nextTuple() {

        try {
            GetResponse response = channel.basicGet(QUEUE_NAME,false);

                if (response == null) {
                    System.out.println("No messages!!");
                } else {
                    AMQP.BasicProperties props = response.getProps();
                    String message = new String(response.getBody(), "UTF-8");
                    long deliveryTag = response.getEnvelope().getDeliveryTag();
                    this.collector.emit(new Values(message));
                    channel.basicAck(deliveryTag, true);
                }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void ack(Object o) {

    }

    public void fail(Object o) {

    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("message"));
    }

    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
