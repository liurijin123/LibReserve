import java.util.HashMap;
import java.util.Map;

public class LibTool {
    private String cookie;
    private String libId;
    private String x;
    private String y;

    public LibTool(String cookie, String libId, String x, String y) {
        this.cookie = cookie;
        this.libId = libId;
        this.x = x;
        this.y = y;
    }

    public void start() {
        Map<String, String> header = new HashMap<String, String>();
        header.put("UserAgent", Config.UserAgent);
        Request request = new Request("wechatSESS_ID", cookie, "wechat.laixuanzuo.com");
        String jsText;
        int count = 0;
        do {
            String indexHtml = request.sentHttps(Config.indexUrl, header);
            //获取加密js文件
            String jsUrl = Util.getJsUrl(indexHtml);
            jsText = request.sentHttps(jsUrl, header);
        } while (Util.getTestCode(jsText)); //判断是否为简单算法

        //提取jsonStr和decStr
        String jsonStr = Util.getJsonStr(jsText);
        System.out.println(jsonStr);
        String decStr = Util.getDecStr(jsText);
        System.out.println(decStr);

        //获取校验码
        String codeStr = Util.getCodeStr(jsonStr, decStr);
        System.out.println(codeStr);
        String bookUrl = "https://wechat.laixuanzuo.com/index.php/reserve/get/libid=" + libId + "&" + codeStr + "=" + x + "," + y + "&yzm=";
        header.put("Referer", Config.Referer);
        //定时

//        Util.timing(7,0,0);
//        String bookHtml = request.sentHttps(bookUrl,header);
//        System.out.println(bookHtml);

        //非阻塞定时
        MyTimerTask timerTask = new MyTimerTask(header,bookUrl,request);
        Util.noBlockTiming(timerTask);
    }
}
