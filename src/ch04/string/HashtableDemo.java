package ch04.string;

import java.util.Enumeration;
import java.util.Hashtable;

public class HashtableDemo {
    public static void main(String[] args) {
        Hashtable<String, A> htable = new Hashtable<>();

        String str = "key1";
        A a = new A();
        htable.put(str, a);

        str = "key2"; a = new A();
        htable.put(str, a);

        str = "key3"; a = new A();
        htable.put(str, a);

        System.out.println(htable.size());

        Enumeration<String> keys = htable.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            System.out.printf("KEY=%s, VALUE=%s\n", key, htable.get(key));
        }

    }
}

