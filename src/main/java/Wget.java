/******************************************************************************
 *  Compilation:  javac Wget.java
 *  Execution:    java Wget url
 *  Dependencies: In.java Out.java
 *
 *  Reads in a URL specified on the command line and saves its contents
 *  in a file with the given name.
 *
 *  % java Wget http://introcs.cs.princeton.edu/java/data/codes.csv
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

/*
 * 耗时五个小时左右......
 * 这里必须要做一些标注和说明，这里一直编译都不通过，有时无法找到符号，这是没有完全引入依赖的问题
 * 为此我甚至学习了Maven的一些基础操作
 *
 * 当通过Maven配置和编译依赖之后，出现了index超出的error
 * 通过看’运行‘界面的报错和运行语句发现，是因为没有直接给出args，导致index超出
 * 通过在’终端‘指定 -cp(-classpath) 完成了最后的编译和运行。
 * `java -cp D:\Java\apache-maven-3.6.3\maven_repository\edu\princeton\cs\algs4\1.0.4\algs4-1.0.4.jar src/main/java/Wget.java http://introcs.cs.princeton.edu/java/data/codes.csv`
 * */

// 同样可以在编辑配置中 设置程序实参实现运行
// 对于In Out的使用熟悉
public class Wget {

    public static void main(String[] args) {

        // read in data from URL
        String url = args[0];
        In in = new In(url); // In 是个自定义类
        String data = in.readAll();

        // write data to a file
        String filename = url.substring(url.lastIndexOf('/') + 1);
        Out out = new Out(filename);
        out.println(data);
        out.close();
    }

}