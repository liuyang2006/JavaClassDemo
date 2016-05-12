package ch05;

import org.apache.http.client.fluent.Request;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HtmlStringMatcher {

    public static String getHtmlWebPage(String urlStr) throws IOException {
        String htmlStr = Request.Get(urlStr)
                .connectTimeout(3000)
                .socketTimeout(3000)
                .execute().returnContent().asString(StandardCharsets.UTF_8);
        return htmlStr;
    }

    public static List<News> parseZZUHomePageNews(String htmlStr) {
        Pattern pattern = Pattern.compile("<span class=\"zzj_f[12]\">(.*)</span>.*href=\"(.*)\">(.*)</a><br />\\r");
        Matcher matcher = pattern.matcher(htmlStr);

        List<News> newsList = new ArrayList<>();

        while (matcher.find()) {
            if (matcher.groupCount() < 3)
                throw new RuntimeException(matcher.group() + "\nParsing above news encountered an error.");
            String date = matcher.group(1);
            String title = matcher.group(3);
            String href = matcher.group(2);
            date = date.replaceAll(":", "").replaceAll(" ", "").replaceAll("：", "");

            News news = new News(title, href, date);
            newsList.add(news);
        }

        if (newsList.size() < 1) {
            throw new IllegalStateException("Parsing news from html document encounter error, please check if the html doc is zzu homepage or if the regex matching algorithm is not correct.");
        }
        return newsList;
    }

    public static List<News> getNewsListFromZZU() throws IOException {
        return parseZZUHomePageNews(getHtmlWebPage("http://202.196.64.199"));
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<News> newsList = getNewsListFromZZU();

        System.out.println(newsList.toString());

//        printListToPrintStream(newsList, System.out);
//        printListToPrintStream(newsList, System.err);
//        printListToPrintStream(newsList, new PrintStream(new FileOutputStream("news_list.txt")));
        System.exit(0);

//        printList1(newsList);
//        printList2(newsList);
//        printList3(newsList);
//        printList4(newsList);


//        saveListAsObject(newsList, "newsList.ser");
//        saveListAsTxt(newsList, "newsList.txt");
//        printList1(readListFromObjectFile("newsList.ser"));

        List<News> todayNewsList = newsList.stream()
                .filter(news -> news.getDate().contains("今天"))
                .collect(Collectors.toList());
//        printList1(todayNewsList);

        List<News> academicNewsList = newsList.stream()
                .filter(news -> news.getTitle().contains("学术"))
                .collect(Collectors.toList());
//        printList1(academicNewsList);

        List<News> todayActivityNewsList = newsList.stream()
                .filter(news -> isActivityAtToday(news))
                .collect(Collectors.toList());
//        printList1(todayActivityNewsList);

        newsList.stream()
                .filter(news -> news.getDate().contains("今天"))
                .filter(news -> news.getTitle().contains("活动"))
                .forEach(news -> System.out.println(news));

    }

    private static boolean isActivityAtToday(News news) {
        return news.getTitle().contains("活动") &&
                news.getDate().contains("今天");
    }


    public static void printListToPrintStream(List<News> list, PrintStream printStream) {
        for (int i = 0; i < list.size(); i++) {
            printStream.println(list.get(i));
        }

    }


    public static void printList1(List<News> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

    }

    public static void printList2(List<News> list) {
        for (News news : list) {
            System.out.println(news);
        }
    }

    public static void printList3(List<News> list) {
        Iterator<News> it = list.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void printList4(List<News> list) {
        list.stream().forEach(news -> System.out.println(news));
    }

    public static void saveListAsObject(List<News> list, String fn) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File(fn));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(list);
        oos.close();
        fos.close();
    }

    public static List<News> readListFromObjectFile(String fn) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(new File(fn));
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<News> ret = (List<News>) ois.readObject();
        ois.close();
        fis.close();
        return ret;
    }

    public static void saveListAsTxt(List<News> list, String fn) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File(fn));
        PrintWriter pw = new PrintWriter(fos);
        for (News news : list) {
            pw.println(news);
        }
        pw.close();
        fos.close();
    }


}

