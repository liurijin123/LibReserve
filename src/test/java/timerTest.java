import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class timerTest {
    private static Logger logger = LogManager.getLogger("test");

    static class TestTask extends TimerTask {

        @Override
        public void run() {
            try {
                logger.info("开始执行任务");
                Thread.sleep(5000);
                logger.info("完成任务");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        System.out.println("main函数开始执行");

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {

                System.out.println("===task start===");
                Thread.sleep(5000);
                System.out.println("===task finish===");
                return 3;
            }
        });

        System.out.println("main函数执行结束");
        //这里需要返回值时会阻塞主线程，如果不需要返回值使用是OK的。倒也还能接收
        Integer result = future.get();
        System.out.println(result);
    }
}


