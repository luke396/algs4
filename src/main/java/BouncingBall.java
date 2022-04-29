/******************************************************************************
 *  Compilation:  javac BouncingBall.java
 *  Execution:    java BouncingBall
 *  Dependencies: StdDraw.java
 *
 *  Implementation of a 2-d bouncing ball in the box from (-1, -1) to (1, 1).
 *
 *  % java BouncingBall
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

public class BouncingBall {
    public static void main(String[] args) {

        // set the scale of the coordinate system
        StdDraw.setXscale(-1.0, 1.0); // 将x轴设定为指定的范围以方便引用
        StdDraw.setYscale(-1.0, 1.0);
        StdDraw.enableDoubleBuffering(); // set up double buffering for animation 屏幕刷新规则

        // initial values
        double rx = 0.480, ry = 0.860;     // position
        double vx = 0.015, vy = 0.023;     // velocity 移动速率
        double radius = 0.05;              // radius 半径

        // main animation loop
        while (true) { // 一直执行

            // bounce off wall according to law of elastic collision 弹性碰撞
            if (Math.abs(rx + vx) > 1.0 - radius) vx = -vx; // 碰撞检测， 1.0是设置出来的边界
            if (Math.abs(ry + vy) > 1.0 - radius) vy = -vy;

            // update position
            rx = rx + vx;
            ry = ry + vy;

            // clear the background 并填充背景颜色
            StdDraw.setPenColor(StdDraw.GRAY);
            StdDraw.filledSquare(0, 0, 1.0);

            // draw ball on the screen
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledCircle(rx, ry, radius);

            // display and pause for 20 ms 通过控制while的间隔来控制窗口刷新速率以控制小球的运动速度
            StdDraw.show();
            StdDraw.pause(20);
        }
    }
} 