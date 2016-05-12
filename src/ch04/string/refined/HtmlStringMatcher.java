package ch04.string.refined;

import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlStringMatcher {

    public static List<News> newsList = new ArrayList<News>();

    public static String getZZUHomePageHtml() throws IOException {
        String htmlStr = Request.Get("http://www.zzu.edu.cn/")
                .connectTimeout(3000)
                .socketTimeout(3000)
                .execute().returnContent().asString(StandardCharsets.UTF_8);
        return htmlStr;
    }

    public static void parseZZUHomePageNews(String htmlStr) {
        Pattern pattern = Pattern.compile("<span class=\"zzj_f[12]\">(.*)</span>.*href=\"(.*)\">(.*)</a><br />\\r");
        Matcher matcher = pattern.matcher(htmlStr);

        int i = 0;

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

//        System.out.println("\nList<News>新闻:\t" + newsList);
//        System.out.println("List<News>新闻总条数:" + newsList.size());

        if (newsList.size() < 1) {
            throw new IllegalStateException("Parsing news from html document encounter error, please check if the html doc is zzu homepage or if the regex matching algorithm is not correct.");
        }
    }

    public static String getNewsListAsString() throws IOException {
        StringBuffer ret = new StringBuffer();
        parseZZUHomePageNews(getZZUHomePageHtml());

        ret.append("新闻数组结果为:\n");
        for (News news : newsList) {
            ret.append(news).append("\n");
        }
        return ret.toString();

    }

    public static void main(String[] args) throws IOException {
        //parseZZUHomePageNews(getZZUHomePageHtml());
        System.out.println(getNewsListAsString());
    }
}

class News {
    private String date;
    private String title;
    private String href;


    public News(String title, String href, String date) {
        this.title = title;
        this.href = href;
        this.date = date;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", href='" + href + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}