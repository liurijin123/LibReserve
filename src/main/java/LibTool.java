import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class LibTool {


    private String name;
    private String cookie;
    private String libId;
    private String x;
    private String y;
    private String setDate;

    public LibTool(String name, String cookie, String libId, String x, String y, String setDate) {
        this.name = name;
        this.cookie = cookie;
        this.libId = libId;
        this.x = x;
        this.y = y;
        this.setDate = setDate;
    }

    public void start() {
        Logger logger = LogManager.getLogger(name);

        Map<String, String> header = new HashMap<String, String>();
        header.put("UserAgent", Config.UserAgent);
        Request request = new Request(name,"wechatSESS_ID", cookie, "wechat.laixuanzuo.com");
        String jsText;
        do {
            String indexHtml = request.sentHttps(Config.indexUrl, header);
            //获取加密js文件
            String jsUrl = Util.getJsUrl(indexHtml);
            jsText = request.sentHttps(jsUrl, header);
        } while (Util.getTestCode(jsText)); //判断是否为简单算法

        //提取jsonStr和decStr
        String jsonStr = Util.getJsonStr(jsText);
        logger.info("提取jsonStr" + jsonStr);
        String decStr = Util.getDecStr(jsText);
        logger.info("提取decStr" + decStr);

        //获取校验码
        String codeStr = Util.getCodeStr(jsonStr, decStr);
        logger.info("获取校验码" + codeStr);
        String bookUrl = "https://wechat.laixuanzuo.com/index.php/reserve/get/libid=" + libId + "&" + codeStr + "=" + x + "," + y + "&yzm=";
        header.put("Referer", Config.Referer);

        //阻塞定时
//        Util.timing(7,0,0);
//        String bookHtml = request.sentHttps(bookUrl,header);
//        System.out.println(bookHtml);

        //非阻塞定时
        MyTimerTask timerTask = new MyTimerTask(header, bookUrl, request);
        Util.noBlockTiming(name,timerTask, setDate);
    }
}
