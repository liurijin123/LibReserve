import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class LogTest {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger("test");
        Thread thread1 =  new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (count < 100) {
                    count++;
                    Util.getLogger("liutong").info("liutong");
                    //logger.info("liutong");
                }
            }
        });
        thread1.start();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (count < 100) {
                    count++;
                    Util.getLogger("goutou").info("goutou");
                }
            }
        });
        thread2.start();


    }
}
