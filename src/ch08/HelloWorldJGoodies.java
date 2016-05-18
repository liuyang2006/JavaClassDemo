package ch08;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class HelloWorldJGoodies {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("HelloWorld");

        FormLayout layout = new FormLayout(//
                "right:max(40dlu;p), 4dlu, 80dlu, 7dlu, " // 1st major column
                        + "right:max(40dlu;p), 4dlu, 80dlu", // 2nd major column
                ""); // add rows dynamically

        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();

        builder.appendSeparator("Segment");
        builder.append("Identifier", new JTextField());
        builder.nextLine();
        builder.append("PTI [kW]", new JTextField());
        builder.append("Power [kW]", new JTextField());
        builder.append("len [mm]", new JTextField());
        builder.nextLine();
        builder.appendSeparator("Diameters");
        builder.append("da [mm]", new JTextField());
        builder.append("di [mm]", new JTextField());
        builder.append("da2 [mm]", new JTextField());
        builder.append("di2 [mm]", new JTextField());
        builder.append("R [mm]", new JTextField());
        builder.append("D [mm]", new JTextField());

        Container contentPane = jFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(builder.getPanel(), BorderLayout.CENTER);

        jFrame.setVisible(true);
        jFrame.setSize(600, 400);
    }
}
