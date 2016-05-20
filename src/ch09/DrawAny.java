package ch09;

import java.applet.Applet;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class DrawAny extends Applet {
    Image im;

    public void init() {
        URL url = null;
        try {
            url = new URL("http://www.zzu.edu.cn/images/logo.png");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//        String imageName = getParameter("image");
        im = getImage(url);

        System.out.println(url);
    }

    public void paint(Graphics g) {
        g.drawImage(im, 0, 0, this);
    }
}
