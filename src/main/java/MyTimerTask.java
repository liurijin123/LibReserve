import java.util.Map;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    private Map<String, String> header;
    private String bookUrl;
    private Request request;

    public MyTimerTask(Map<String, String> header, String bookUrl, Request request) {
        this.header = header;
        this.bookUrl = bookUrl;
        this.request = request;
    }
    @Override
    public void run() {
        String bookHtml = request.sentHttps(bookUrl,header);
        System.out.println(bookHtml);
    }
}
