import java.util.Scanner;

public class SumOfTwoArray {
    // 从给定的无序、不重复的数组A中，取出2个数，使其相加和为M
    // 输入：
    // 第一行两个数 N 和 M，N 为数组长度，M 为相加之和。第二行为实际的数组元素。

    // 输出： 满足条件的个数

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();

        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = scanner.nextInt();
        }

        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (A[i] + A[j] == M) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }

}
