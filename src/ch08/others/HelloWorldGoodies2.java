package ch08.others;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class HelloWorldGoodies2 {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("HelloWorld");

        FormLayout layout = new FormLayout("right:max(50dlu;p), 4dlu, 75dlu, 7dlu, right:p, 4dlu, 75dlu", "p, 2dlu, p, 3dlu, p, 3dlu, p, 7dlu, " + "p, 2dlu, p, 3dlu, p, 3dlu, p");
        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();
        CellConstraints cc = new CellConstraints();
        builder.addSeparator("Segment", cc.xyw(1, 1, 7));
        builder.addLabel("Identifier", cc.xy(1, 3));
        builder.add(new JTextField(), cc.xy(3, 3));
        builder.addLabel("PTI [kW]", cc.xy(1, 5));
        builder.add(new JTextField(), cc.xy(3, 5));
        builder.addLabel("Power [kW]", cc.xy(5, 5));
        builder.add(new JTextField(), cc.xy(7, 5));
        builder.addLabel("len [mm]", cc.xy(1, 7));
        builder.add(new JTextField(), cc.xy(3, 7));
        builder.addSeparator("Diameters", cc.xyw(1, 9, 7));
        builder.addLabel("da [mm]", cc.xy(1, 11));
        builder.add(new JTextField(), cc.xy(3, 11));
        builder.addLabel("di [mm]", cc.xy(5, 11));
        builder.add(new JTextField(), cc.xy(7, 11));
        builder.addLabel("da2 [mm]", cc.xy(1, 13));
        builder.add(new JTextField(), cc.xy(3, 13));
        builder.addLabel("di2 [mm]", cc.xy(5, 13));
        builder.add(new JTextField(), cc.xy(7, 13));
        builder.addLabel("R [mm]", cc.xy(1, 15));
        builder.add(new JTextField(), cc.xy(3, 15));
        builder.addLabel("D [mm]", cc.xy(5, 15));
        builder.add(new JTextField(), cc.xy(7, 15));

        Container contentPane = jFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(builder.getPanel(), BorderLayout.CENTER);

        jFrame.setVisible(true);
        jFrame.setSize(600, 400);
    }
}
