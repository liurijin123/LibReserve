package fun.liutong;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: Util
 * @Description: 公共工具类
 * @Author: 刘通
 * @Date: 2020/1/29 15:21
 * @Version: 1.0
 */
public class Util {

    private static boolean getTestCode(String jsText) {
        // 创建 Pattern 对象
        Pattern r = Pattern.compile("\\w{1}\\(\\w{10}\\)");
        // 现在创建 matcher 对象
        Matcher m = r.matcher(jsText);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    private static String getJsonStr(String jsText) {
        // 创建 Pattern 对象
        Pattern r = Pattern.compile("JSON\\.parse\\(\"(\\S+)\"\\)");
        // 现在创建 matcher 对象
        Matcher m = r.matcher(jsText);
        if (m.find()) {
            return m.group(1);
        } else {
            return "NO MATCH";
        }
    }

    private static String getDecStr(String jsText) {
        // 创建 Pattern 对象
        Pattern r = Pattern.compile("dec\\(\"(\\w+)\"\\)");
        // 现在创建 matcher 对象
        Matcher m = r.matcher(jsText);
        if (m.find()) {
            return m.group(1);
        } else {
            return "NO MATCH";
        }
    }

    private static String getCodeStr(String jsonStr, String decStr) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        String codeStr = null;
        try {
            engine.eval(Config.JS);

            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;    // 调用merge方法，并传入两个参数
                codeStr = (String) invoke.invokeFunction("getCode", jsonStr, decStr); //调用了js的aa方法
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codeStr;
    }

    //获取加密js文件
    private static String getJsUrl(String html) {
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(Config.URL_JS);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(html);
        if (m.find()) {
            return m.group();
        } else {
            return "NO MATCH";
        }
    }

    //获取加密校验码
    public static String getCodeStr(Site site) {
        Downloader downloader = new Downloader();
        String jsText;
        do {
            String indexHtml = downloader.download(Config.indexUrl,site);
            //获取加密js文件
            String jsUrl = Util.getJsUrl(indexHtml);
            jsText = downloader.download(jsUrl,site);
        } while (Util.getTestCode(jsText)); //判断是否为简单算法

        //提取jsonStr和decStr
        String jsonStr = Util.getJsonStr(jsText);
        String decStr = Util.getDecStr(jsText);

        //获取校验码
        String codeStr = Util.getCodeStr(jsonStr, decStr);
        return codeStr;
    }

    //设置定时任务
    public static void noBlockTiming(String setDate, List<String> seatUrlist, Site site) {
        try {
            MyTimerTask timerTask = new MyTimerTask(site, seatUrlist);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date date = dateFormat.parse(setDate);
            Timer timer = new Timer("time");
            timerTask.setTimer(timer);
//            timerTask.setName(name);
            timer.schedule(timerTask, date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
