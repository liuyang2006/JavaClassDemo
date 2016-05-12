package ch04.string;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class StringTest {
    public void modifyTestForString(String strIn) {
        strIn = strIn.concat(" After Modified.");
    }

    public void modifyTestStringBuilder(StringBuilder strIn) {
        strIn = strIn.append("After Modified");
    }

    public void modifyTestStringBuffer(StringBuffer strIn) {
        strIn = strIn.append(" After Modified.");
    }
}

public class StringAndStringBufferDemo {

    public static void stringBasicTest() {
        String s1 = new String("zhengzhou ");
        String s2 = new String("zhengzhou ");

        s1 += 450001;
        s2 += 450001;
        System.out.println(s1);

        System.out.println(s1 == s2);//false
        System.out.println(s1.equals(s2));//true
        System.out.println(s1.hashCode());//s1.hashcode()是否等于s2.hashcode()
        System.out.println(s2.hashCode());

        s1 = s1.replaceAll("zhengzhou", "Zhengzhou University");
        System.out.println(s1);

        s1 = s1.replaceAll("(\\d+)", "postcode:$1");
        System.out.println(s1);

        String[] splitResult = s1.split("\\s");
        System.out.println("字符串分离到数组的结果:\t" + Arrays.toString(splitResult));

        RegularExpDemo();
    }

    private static void RegularExpDemo() {

        String str = "15838096697";
        Pattern p = Pattern.compile("1[358]\\d{9}");
        Matcher m = p.matcher(str);
        if (m.matches())
            System.out.printf("%s is a valid telephone number.\n", str);
        else
            System.out.printf("%s is not valid.\n", str);

        System.out.println("inputStr="+inputStr);
        p = Pattern.compile("\\s(1[358]\\d{9})\\s");
        m = p.matcher(inputStr);
        while (m.find()) {
            System.out.println(m.group(1));
        }
    }

    public static void main(String[] args) {

        stringBasicTest();

        String str1 = "Hello zzu.";
        StringBuilder str2 = new StringBuilder("Hello zzu.");
        StringBuffer str3 = new StringBuffer("Hello zzu.");

        StringTest stringTest = new StringTest();

        stringTest.modifyTestForString(str1);
        System.out.println(str1); //输出结果是什么?

        stringTest.modifyTestStringBuilder(str2);
        System.out.println(str2); //两次输出结果一样吗?为什么

        stringTest.modifyTestStringBuffer(str3);
        System.out.println(str3); //三次输出结果一样吗?为什么
    }


    public static final String inputStr = "13598800000 郑州 ￥193000 13526755555 郑州 ￥175000 13937195555 郑州 ￥160000 13523037777 郑州 ￥108000 18339800000 郑州 ￥107000 13903717890 郑州 ￥48000 18860393333 郑州 ￥43000 13803719933 郑州 ￥35000 18837115999 郑州 ￥35000 18837188883 郑州 ￥35000 13903837666 郑州 ￥33000 13503712233 郑州 ￥28000 13673699666 郑州 ￥28000 13598873999 郑州 ￥28000 13903711766 郑州 ￥28000 13903712933 郑州 ￥28000 13903714455 郑州 ￥28000 13903717533 郑州 ￥28000 13903718311 郑州 ￥28000 18837199993 郑州 ￥28000 18837199997 郑州 ￥28000 18203711234 郑州 ￥28000 15103710777 郑州 ￥28000 17839999994 郑州 ￥25000 13903719038 郑州 ￥18000 13903716079 郑州 ￥18000 13903715991 郑州 ￥18000 13903713029 郑州 ￥18000 13903712712";
}
