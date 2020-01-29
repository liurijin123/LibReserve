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
        LibTool libTool = new LibTool("2d5bda1b800fc2a9be6d0c12af793eb7");
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
