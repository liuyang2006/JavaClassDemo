package ch07.extend;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

public class Bar2DDemo {
    public static final int width = 400, height = 400;

    public void encode(String contents, int width, int height, String imgPath) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height);
            MatrixToImageWriter.writeToFile(bitMatrix, "png", new File(imgPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String decode(String imgPath) {
        BufferedImage image = null;
        Result result = null;
        try {
            image = ImageIO.read(new File(imgPath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
            // 注意要使用 utf-8，因为刚才生成二维码时，使用了utf-8
            hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
            result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        String imgPath = "zzuie-qrcode.png";
        String contents = "郑州大学信息工程学院 http://www5.zzu.edu.cn/ie/";
        try {
            // 如果不用更改源码，将字符串转换成ISO-8859-1编码
            contents = new String(contents.getBytes("UTF-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Bar2DDemo demo = new Bar2DDemo();

        demo.encode(contents, width, height, imgPath);
        System.out.printf("生成二维码到文件%s成功！\n", imgPath);

        String decodeContent = demo.decode(imgPath);
        System.out.printf("读取文件%s\n", imgPath);
        System.out.printf("二维码内容：%s\n", decodeContent);
        System.out.println("解析二维码成功！");

        Runtime.getRuntime().exec("open " + imgPath);
    }
}