package ch03.c39;

class StringTest {
    public void modifyTestForString(String strIn) {
        strIn = strIn.concat(" After Modified.");
    }
    public void modifyTestStringBuilder(StringBuilder strIn) {
        strIn.append(" After Modified.");
    }
}
public class WrapperTypeParamsDemo {

    public static void StringDemo() {
        String s1=new String("zhengzhou");
        String s2=new String("zhengzhou");
        System.out.println(s1==s2);//false
        System.out.println(s1.equals(s2));//true
        System.out.println(s1.hashCode());//s1.hashcode()等于s2.hashcode()
        System.out.println(s2.hashCode());
    }
    public static void main(String[] args) {
        String str = "Hello zzu.";
        StringBuilder strb = new StringBuilder("Hello zzu.");
        StringTest st = new StringTest();

        st.modifyTestForString(str);
        System.out.println(str); //输出结果是什么?

        st.modifyTestStringBuilder(strb);
        System.out.println(strb); //两次输出结果一样吗?为什么
    }
}
