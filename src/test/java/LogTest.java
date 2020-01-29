import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogTest {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.error("123");
    }

}
