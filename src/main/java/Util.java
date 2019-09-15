import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private static Logger logger = LogManager.getLogger("test");

    public static boolean getTestCode(String jsText) {
        // 创建 Pattern 对象
        Pattern r = Pattern.compile("\\w{1}\\(\\w{10}\\)");
        // 现在创建 matcher 对象
        Matcher m = r.matcher(jsText);
        if (m.find()) {
            logger.info("复杂算法" + m.group());
            return true;
        } else {
            return false;
        }
    }

    public static String getJsonStr(String jsText) {
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

    public static String getDecStr(String jsText) {
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

    public static String getCodeStr(String jsonStr, String decStr) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        String jsFileName = "D:\\DATA\\IdeaProjects\\LibReserve\\src\\main\\java\\code.js";
        FileReader reader = null;   // 执行指定脚本
        String codeStr = null;
        try {
            reader = new FileReader(jsFileName);
            engine.eval(reader);

            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;    // 调用merge方法，并传入两个参数
                codeStr = (String) invoke.invokeFunction("getCode", jsonStr, decStr); //调用了js的aa方法
                //System.out.println(code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codeStr;
    }

    //获取加密js文件
    public static String getJsUrl(String html) {
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

    public static void timing(int hour, int minu, int seco) {
        int nowHour = 99;
        int nowMinu = 99;
        int nowSeco = 99;
        int oidSeco = 99;
        Calendar now;
        System.out.println("定时" + hour + ":" + minu + ":" + seco);
        do {
            now = Calendar.getInstance();
            oidSeco = nowSeco;
            nowHour = now.get(Calendar.HOUR_OF_DAY);
            nowMinu = now.get(Calendar.MINUTE);
            nowSeco = now.get(Calendar.SECOND);
            if (oidSeco != nowSeco) {
                System.out.println("当前时间" + nowHour + ":" + nowMinu + ":" + nowSeco);
            }
        } while (hour != nowHour || minu != nowMinu || seco != nowSeco);
    }

    public static void noBlockTiming(MyTimerTask timerTask, String setDate) {
        logger.info("设置时间-->" + setDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        try {
            Date date = dateFormat.parse(setDate);
//            Date date = dateFormat.parse("23:00:00.000");
            Timer timer = new Timer("time");
            timerTask.setTimer(timer);
            timer.schedule(timerTask, date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void setBJtime() throws IOException {
        URL u=new URL("http://api.k780.com/?app=life.time&appkey=45321&sign=e6a971729ffab837ddce922c85c08ea4&format=json");
        InputStream in=u.openStream();
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        try {
            byte buf[]=new byte[1024];
            int read = 0;
            while ((read = in.read(buf)) > 0) {
                out.write(buf, 0, read);
            }
        }  finally {
            if (in != null) {
                in.close();
            }
        }
        byte b[]=out.toByteArray( );
        System.out.println(new String(b,"utf-8"));
    }
}
