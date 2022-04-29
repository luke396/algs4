/******************************************************************************
 *  Compilation:  javac RightTriangle.java
 *  Execution:    java RightTriangle
 *  Dependencies: StdDraw.java
 *
 *  Draws a right triangle and a circumscribing circle.
 *
 ******************************************************************************/


// 介绍StdDraw

import edu.princeton.cs.algs4.StdDraw;

public class RightTriangle {

    public static void main(String[] args) {
        StdDraw.square(.5, .5, .5); // 这行没看懂是在干啥...注释掉了看起来结果也没差
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.line(.5, .5, .9, .5);
        StdDraw.line(.9, .5, .5, .8);
        StdDraw.line(.5, .8, .5, .5);
        StdDraw.circle(.7, .65, .25);
    }
}
