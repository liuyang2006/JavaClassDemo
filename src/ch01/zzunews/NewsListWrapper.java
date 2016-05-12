package ch01.zzunews;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class NewsListWrapper {

    public static void main(String[] args) {

        NewsConfig.getConfigs("zzunewslist.properties");
        NewsListWrapper demo = new NewsListWrapper("学术动态", 1);

        System.out.println(demo.requestJSONObjectByHttp());

        demo = new NewsListWrapper("学术动态", 12);
        System.out.println(demo.requestJSONObjectByHttp());
    }

    private NewsList newsList;

    private String typeStr;

    private int page;

    private String requestUrl;

    private JSONObject jsonObject;

    private int n_pages;

    private int n_total_news;


    /**
     * Before or after DoNetwork(), IsNetOK is used to judge if the net
     * connection and population into ArrayListis success.
     *
     * @return
     */
    public boolean IsNetOK() {
        if (newsList.getNewsSize() > 0)
            return true;
        return false;
    }

    public NewsList getNewsList() {
        return newsList;
    }

    /**
     * This is a real net request method for zzunews into NewsList.
     * true- if http is ok, and get results, otherwise is false.
     */
    public boolean requestNewsListByHttp() {
        parseHtmlToNewsList(new HTTPTool(requestUrl).GetHtml());

        if (newsList.getNewsSize() == 0)
            return false;
        return true;
    }


    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("第" + page);
        sb.append("页/共" + n_pages);
        sb.append("页 " + n_total_news + "条");
        return sb.toString();
    }

    /**
     * Ensure page is from 2 to N, if page == 1, omit the extra params.
     *
     * @param typeStr
     * @param page
     */
    public NewsListWrapper(String typeStr, int page) {

        int type = NewsConfig.getTypeId(typeStr);
        this.typeStr = typeStr;

        if (page <= 1) {
            this.requestUrl = NewsConfig.typeUrl[type];
            this.page = 1;
        } else {
            this.requestUrl = NewsConfig.typeUrl[type] + NewsConfig.pagePrefix + page;
            this.page = page;
        }
        this.newsList = new NewsList(this.typeStr, this.page);
    }


    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public JSONObject requestJSONObjectByHttp() {

        jsonObject = new JSONObject();
        if (requestNewsListByHttp()) {
            try {
                jsonObject.put("status", "OK");
                jsonObject.put("results", newsList.getJSONObject());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                jsonObject.put("status", "ERROR");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;

    }

    public ArrayList<HashMap<String, String>> getAsArrayList() {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        for (int i = 0; i < newsList.getNewsSize(); i++) {
            News news = newsList.getNewsAt(i);
            HashMap<String, String> oneNews = news.getAsHashMap();
            list.add(oneNews);
        }

        return list;
    }

    private void parseHtmlToNewsList(String inputHtml) {
        ByteArrayInputStream is = new ByteArrayInputStream(inputHtml.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String inputLine;
        try {
            while ((inputLine = br.readLine()) != null) {

                if (inputLine.contains("<div class=\"zzj_4\">")) {
                    getSummaryData(inputLine.trim());
                }


                if (inputLine.contains("<div class=\"zzj_5a\">")) {
                    News oneNews = parseStrLineToNews(inputLine.trim());
                    newsList.addNews(oneNews);
                }

            }
            br.close();
            is.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void getSummaryData(String inputLine) {

        // <div class="zzj_4">共308条，每页50条，分7页，第1页
        int i, j;
        i = inputLine.indexOf("共");
        j = inputLine.indexOf("条", i + 1);
        String n_total_news = inputLine.substring(i + 1, j);

        i = inputLine.indexOf("分");
        j = inputLine.indexOf("页", i + 1);
        String n_pages = inputLine.substring(i + 1, j);

        this.n_pages = Integer.valueOf(n_pages);
        this.n_total_news = Integer.valueOf(n_total_news);
    }


    private News parseStrLineToNews(String inputLine) {

        int start, end;
        // split date
        start = inputLine.indexOf("]");
        end = inputLine.indexOf("&");

        String dateStr = inputLine.substring(start + 1, end);
        dateStr = dateStr.trim();

        // split href
        start = inputLine.indexOf("href=\"");
        end = inputLine.indexOf("\" target=");

        String hrefStr = inputLine.substring(start + 6, end);
        hrefStr = hrefStr.trim();

        // split title by "zzj_f6_c\">" and "</span>"
        start = inputLine.indexOf("zzj_f6_");
        end = inputLine.indexOf("</span>");

        String titleStr = inputLine.substring(start + 10, end);
        titleStr = titleStr.trim();

        // split others by "zzj_f7\">" and "</span></div>"

        start = inputLine.indexOf("zzj_f7\">");
        end = inputLine.indexOf("</span></div>");

        String othersStr = inputLine.substring(start + 8, end);
        othersStr = othersStr.trim();

        News news = new News(titleStr, dateStr, hrefStr, othersStr);

        return news;

    }

    public int getN_pages() {
        return n_pages;
    }

    public int getN_total_news() {
        return n_total_news;
    }
}
