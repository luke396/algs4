import java.util.Scanner;

public class Add36 {
    // 三十六进制加法
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String a = scanner.next();
            String b = scanner.next();
            // 将三十六进制字符串转换成十进制
            int A = Integer.parseInt(a, 36);
            int B = Integer.parseInt(b, 36);
            int C = A + B;
            System.out.println(Integer.toString(C, 36));
        }
    }

}
