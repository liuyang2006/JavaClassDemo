package ch09;

import javax.swing.*;
import java.awt.*;

public class MyFirstApplet extends JApplet {
    @Override
    public void paint(Graphics g) {
        g.drawString("To climb a ladder, start at the bottom rung.", 20, 90);
    }
}