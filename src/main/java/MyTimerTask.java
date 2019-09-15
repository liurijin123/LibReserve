import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    private Logger logger = LogManager.getLogger("test");

    private Map<String, String> header;
    private String bookUrl;
    private Request request;
    private Timer timer;

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public MyTimerTask(Map<String, String> header, String bookUrl, Request request) {
        this.header = header;
        this.bookUrl = bookUrl;
        this.request = request;
    }
    @Override
    public void run() {
        String bookHtml = request.sentHttps(bookUrl,header);
        logger.info("抢座结果" + bookHtml);
        timer.cancel();
    }
}
