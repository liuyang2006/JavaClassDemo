package ch04.string;

import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZZUHomePageNewsStringMatcher {

    public static String getZZUHomePageHtml() throws IOException {
        String htmlStr = Request.Get("http://www.zzu.edu.cn/")
                .connectTimeout(3000)
                .socketTimeout(3000)
                .execute().returnContent().asString(StandardCharsets.UTF_8);
        return htmlStr;
    }

    public static void parseZZUHomePageNews(String htmlStr) throws Exception {
        Pattern pattern = Pattern.compile("<span class=\"zzj_f[12]\">(.*)</span>.*href=\"(.*)\">(.*)</a><br />");
        Matcher matcher = pattern.matcher(htmlStr);
        int i = 1;
        while (matcher.find()) {
            System.out.printf("新闻编号[%d]:\t", i++);

            if (matcher.groupCount() < 3)
                throw new Exception();
            String date = matcher.group(1);
            String title = matcher.group(3);
            String href = matcher.group(2);

            News news = new News(date, title, href);
            System.out.println(news);
            System.out.printf("\n");
        }
    }

    public static void main(String[] args) throws Exception {
        parseZZUHomePageNews(getZZUHomePageHtml());
    }
}

class News {
    private String date;
    private String title;
    private String href;

    public News(String date, String title, String href) {
        this.date = date;
        this.title = title;
        this.href = href;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    @Override
    public String toString() {
        return "News{" +
                "date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}