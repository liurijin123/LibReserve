package fun.liutong;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: LibTool
 * @Description: TODO
 * @Author: liutong
 * @Date: 2020/1/29 15:21
 * @Version: 1.0
 */
public class LibTool {
    private Site site = new Site();
    private List<String> seatUrlist = new ArrayList<String>();
    private String codeStr = "";
    private String setDate;

    public LibTool(String sessionValue) {
        site.getCookies().put("wechatSESS_ID",sessionValue);
        codeStr = Util.getCodeStr(site);
    }

    //设置时间
    public LibTool setDate(String setDate) {
        this.setDate = setDate;
        return this;
    }
    //添加座位信息
    public LibTool addSeat(String libId, String x, String y) {
        String bookUrl = "https://wechat.laixuanzuo.com/index.php/reserve/get/libid=" + libId + "&" + codeStr + "=" + x + "," + y + "&yzm=";
        seatUrlist.add(bookUrl);
        return this;
    }

    //批量添加座位信息
    public LibTool addSeats(List<Map> elObjArray) {
        for (Map elObj : elObjArray) {
            String bookUrl = "https://wechat.laixuanzuo.com/index.php/reserve/get/libid=" + elObj.get("libId") + "&" + codeStr + "=" + elObj.get("x") + "," + elObj.get("y") + "&yzm=";
            seatUrlist.add(bookUrl);
        }
        return this;
    }

    //启动
    public void start() {
        site.getHeaders().put("Referer", Config.Referer);
        Util.noBlockTiming(setDate,seatUrlist,site);

    }
}
