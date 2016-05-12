package ch03.c31.s1;
// 文件名: J_MainCircle.java; 开发者: 雍俊海

import java.awt.Color;
import java.awt.Graphics;
import java.applet.Applet;

// 绘制两个圆 
public class J_MainCircle extends Applet {
    public void paint(Graphics g) {
        this.setSize(600, 600);
        J_Circle c = new J_Circle(50, 50, 40);
        c.mb_draw(g);
        c.mb_set(200, 50, 40, Color.red, true);
        c.mb_draw(g);
    } // 方法paint结束
} // 类J_MainCircle结束
