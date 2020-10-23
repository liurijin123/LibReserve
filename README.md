## 使用说明

### 一、抓包抓取wechatSESS_ID
如何抓包：https://github.com/liurijin123/LibReserve/blob/master/%E6%8A%93%E5%8C%85%E6%95%99%E7%A8%8B.md

### 二、操作起来
>可以添加多个座位，增加成功率
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


---------------------------------


## 开发了一个桌面版，想用的加qq群 1160811045

