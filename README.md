## 使用说明

### 一、抓包抓取wechatSESS_ID
**抓包APP下载**

链接：https://pan.baidu.com/s/1AUojh9IgEKMEMKaM8NGPTg 

提取码：4c3g 

**抓取wechatSESS_ID**

![](http://img.liutong.fun/QQ图片20200129200839.jpg)
![](http://img.liutong.fun/QQ图片20200129200854.jpg)
![](http://img.liutong.fun/QQ图片20200129200901.jpg)

### 二、操作起来
```
import fun.liutong.LibTool;

/**
 * @ClassName: Main
 * @Description: 测试
 * @Author: 刘通
 * @Date: 2020/1/29 15:21
 * @Version: 1.0
 */
public class Main {
    public static void main(String[] args) {
        LibTool libTool = new LibTool("7fbce513d288da8828f68ff93955c3b8");
        libTool.addSeat("10245","9","27")
                .addSeat("10245","9","21")
                .addSeat("10245","8","27")
                .addSeat("10245","9","23")
                .addSeat("10245","9","24")
                .addSeat("10245","9","26")
                .setDate("2020-01-22 12:45:59.000")
                .start();
    }
}

```
