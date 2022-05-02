package hello;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;


public class RandomWord {
    // 从输入中随机输出一个单词，根据Knuth的随机算法，第i个单词的出现概率为1/i

    public static void main(String[] args) {
        if (StdIn.isEmpty()) {
            StdOut.println("No words were read.");
        }
        String champion = null;
        int index = 1;
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            boolean isChampion = StdRandom.bernoulli(1.0 / index);
            index++;
            if (isChampion) {
                champion = word;
            }
        }
        StdOut.println(champion);
    }
}