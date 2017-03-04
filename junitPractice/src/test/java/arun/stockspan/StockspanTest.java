package arun.stockspan;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by arun on 04/03/17.
 */

public class StockspanTest {

    private static Stockspan sp;

    @BeforeClass
    public static void init(){
        sp = new Stockspan();
    }


    @Test
    public void testGetSpan(){


        Assert.assertArrayEquals(new int[]{1, 1, 2, 1, 4, 5, 7, 1}, sp.getSpan(new int[]{100, 60, 70, 65, 80, 85, 110, 90}));
        Assert.assertArrayEquals(new int[]{1, 1, 2, 1, 4, 5, 7, 1, 8},sp.getSpan(new int[]{100, 60, 70, 65, 80, 85, 110, 90, 110}));
    }

}