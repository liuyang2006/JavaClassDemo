package ch01.zzunews;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by liuyang on 16/4/2.
 */
public class News {
    private String title;
    private String date;
    private String href;
    private String others;
    private String contents;

    public String getNaiveHtml() {
        return naiveHtml;
    }

    public void setNaiveHtml(String naiveHtml) {
        this.naiveHtml = naiveHtml;
    }

    private String naiveHtml;

    public News(String title, String date, String href, String others) {
        this.title = title;
        this.date = date;
        this.href = href;
        this.others = others;
    }

    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("title", title);
            jsonObject.put("date", date);
            jsonObject.put("href", href);
            jsonObject.put("others", others);
            jsonObject.put("contents", contents);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public String toString() {
        return getJSONObject().toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public HashMap<String, String> getAsHashMap() {
        HashMap<String, String> ret = new HashMap<>();
        ret.put("title", title);
        ret.put("date", date);
        ret.put("href", href);
        ret.put("others", others);
        ret.put("contents", contents);
        return ret;
    }
}
