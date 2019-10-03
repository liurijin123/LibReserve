import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {

    Logger logger;
    private String name;
    private Map<String, String> header;
    private String bookUrl;
    private Request request;
    private Timer timer;


    public void setName(String name) {
        this.name = name;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public MyTimerTask(Map<String, String> header, String bookUrl, Request request) {
        this.header = header;
        this.bookUrl = bookUrl;
        this.request = request;
    }

    private void book() {

        String bookHtml = request.sentHttps(bookUrl, header);
        logger.info("抢座结果" + bookHtml);

    }

    private void asyBook() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            count++;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    book();
                }
            });
            thread.start();
            Thread.sleep(100);
        }

    }

    @Override
    public void run() {
        logger = LogManager.getLogger(name);
        try {
            asyBook();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
    }
}
