package rmq;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.testing.TestGlobalCount;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by arun on 4/3/17.
 */
public class Application {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("rmq-spout", new SimpleRMQSpout(), 1);
        builder.setBolt("rmq-bolt", new SimpleRMQBolt(), 1).shuffleGrouping("rmq-spout");

        Map conf = new HashMap();
        conf.put(Config.TOPOLOGY_WORKERS, 4);

        try {
            StormSubmitter.submitTopology("RMQ-topology", conf, builder.createTopology());
        } catch (AlreadyAliveException e) {
            e.printStackTrace();
        } catch (InvalidTopologyException e) {
            e.printStackTrace();
        } catch (AuthorizationException e) {
            e.printStackTrace();
        }
    }
}
