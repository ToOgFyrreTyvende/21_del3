package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.MettlerScale;

import static org.junit.jupiter.api.Assertions.*;

// TODO More tests
class MettlerScaleTest {
    MettlerScale testSocket;

    @BeforeEach
    void setUp(){
        if (testSocket == null){
            testSocket = new MettlerScale("127.0.0.1", 8000);
        }
    }

    @Test
    void ConnectionTest(){
        //MettlerScale scaleSocket = new MettlerScale("127.0.0.1", 8000);
        //System.out.println(scaleSocket.requestUserInput("Indtast mem"));
        //assertEquals(1, 1);
        assertTrue(testSocket.isConnected());
    }

    @Test
    void requestUserInputTest(){
        // THIS REQUIRES MANUAL INPUT THROUGH WEIGHT!!!!
        //MettlerScale scaleSocket = new MettlerScale("127.0.0.1", 8000);
        String returnedStr = testSocket.requestUserInput("Tast 1, derefter 'ok'");
        assertEquals("1", returnedStr);
    }

    //@Test
    void getWeightTest(){
        // Requires current weight is 0kg
        //MettlerScale scaleSocket = new MettlerScale("127.0.0.1", 8000);
        String returnedStr = testSocket.getWeight();
        assertEquals("0.000", returnedStr);
    }
}