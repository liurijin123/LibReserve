package fun.liutong;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @ClassName: MyTimerTask
 * @Description: 定时任务
 * @Author: 刘通
 * @Date: 2020/1/29 15:21
 * @Version: 1.0
 */
public class MyTimerTask extends TimerTask {

    List<String> seatUrlist;
    private Site site;
    private Downloader downloader = new Downloader();
    private Timer timer;

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public MyTimerTask(Site site, List<String> seatUrlist) {
        this.site = site;
        this.seatUrlist = seatUrlist;
    }
    private void ramBook() throws InterruptedException {
        downloader.downloaderPool(seatUrlist,site);
    }

    @Override
    public void run() {
        try {
            ramBook();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            timer.cancel();
        }
    }
}
