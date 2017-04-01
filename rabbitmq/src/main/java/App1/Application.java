package App1;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by arun on 4/1/17.
 */
public class Application {

    public static void main(String[] args) throws InterruptedException, TimeoutException, IOException {
        Receiver r = new Receiver();
        r.run();
        Receiver r2 = new Receiver();
        r2.run();
        Receiver r3 = new Receiver();
        r3.run();
        Sender s = new Sender();
        s.run();
    }
}
