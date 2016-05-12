package ch01.zzunews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by liuyang on 16/4/2.
 */
public class NewsList {
    private ArrayList<News> news_list;
    private String typeStr;
    private int page;

    private NewsList() {
        news_list = new ArrayList<>();
    }

    public NewsList(String typeStr, int page) {
        this();
        if (page < 1)
            page = 1;
        this.typeStr = typeStr;
        this.page = page;
    }

    public int getNewsSize() {
        return news_list.size();
    }

    public void addNews(News news) {
        news_list.add(news);
    }

    public News getNewsAt(int i) {
        if (i >= 0 && i < news_list.size()) {
            return news_list.get(i);
        }
        return null;
    }

    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();

        JSONArray news_list = new JSONArray();
        for (int i = 0; i < this.news_list.size(); i++) {
            JSONObject news = this.news_list.get(i).getJSONObject();
            news_list.put(news);
        }

        try {
            jsonObject.put("news_type", typeStr);
            jsonObject.put("page_no", page);
            jsonObject.put("news_list", news_list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        return getJSONObject().toString();
    }
}
