package ch01.zzunews;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewsWrapper {

    private final static String NullDIVClass = "<div class=\"\">";

    private News news;

    private JSONObject jsonObject;

    public String getTxtContent() {

        String ret = parseHtmlForTxtContent(news.getNaiveHtml());
        return ret;

    }

    /**
     * 计算位数
     *
     * @param str
     * @return
     */
    public static int calculatePlaces(String str) {
        int m = 0;
        char arr[] = str.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if ((c >= 0x0391 && c <= 0xFFE5))  //中文字符
            {
                m = m + 2;
            } else if ((c >= 0x0000 && c <= 0x00FF)) //英文字符
            {
                m = m + 1;
            }
        }
        return m;
    }

    public String getWeiboContent() {
        String title = news.getTitle();
        String url = news.getHref();
        String txtContent = getTxtContent();
        String ret = "《" + title + "》 " + url + "，内容为:" + txtContent;

        int l = title.length() + url.length() / 2;
        l = 140 - l;
        l = l + title.length() + url.length();

        ret = ret.substring(0, l);
        return ret + "...";

    }


    public static void main(String[] args) {

        String url = "http://www16.zzu.edu.cn/msgs/vmsgisapi.dll/onemsg?msgid=1510010920321041275";
        NewsWrapper demo = new NewsWrapper("新闻标题", url);

        System.out.println(demo.requestJSONObject());

        String txtcontent = demo.getTxtContent();
        System.out.println(txtcontent);
    }

    // 一条新闻的URL
    private String requestURL = null;

    // 过滤的行,删除该行的内容
    private String[] skipPatternStr = {"<div id=\"topbar\">", "<div class=\"zzj_2b\">", "<div class=\"zzj_2a\">"};//

    // 需要替换的行,替换为<div class=\"\">
    private String[] replacePatternStr = {"<div class=\"zzj_2\">", "<div class=\"zzj_3\">", "<div class=\"zzj_4\">",
            "<div class=\"zzj_5\">"};//

    public NewsWrapper(String title, String url) {
        requestURL = url;
        news = new News(title, "no date", url, "no others");
    }

    public NewsWrapper(News news) {
        this.news = news;
        requestURL = this.news.getHref();
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public JSONObject requestJSONObject() {

        jsonObject = new JSONObject();
        if (requestNewsDetailByHttp()) {
            try {
                jsonObject.put("status", "OK");
                jsonObject.put("results", news.getJSONObject());
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

    // 分析并过滤每一个的html代码
    private String parseEachLineForWebview(String inputLine) {
        String str = inputLine;
        str.trim();

        for (int i = 0; i < skipPatternStr.length; i++) {
            if (str.contains(skipPatternStr[i]))
                return "";
        }

        for (int i = 0; i < replacePatternStr.length; i++) {
            if (str.contains(replacePatternStr[i])) {
                str = str.replace(replacePatternStr[i], NullDIVClass);
                break;
            }
        }

        return str;
    }

    // 过滤html代码
    private String parseHtmlForWebviewContent(String inputHtml) {
        String outputHtml = "";

        InputStream is = new ByteArrayInputStream(inputHtml.getBytes());

        InputStreamReader in = new InputStreamReader(is);
        BufferedReader buffer = new BufferedReader(in);

        String inputLine = null;
        try {
            while ((inputLine = buffer.readLine()) != null) {
                outputHtml += parseEachLineForWebview(inputLine) + "\n";
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return outputHtml;
    }

    // 过滤html代码成txt
    private String parseHtmlForTxtContent(String inputHtml) {
        String outputTxt = "";

        InputStream is = new ByteArrayInputStream(inputHtml.getBytes());
        InputStreamReader in = new InputStreamReader(is);
        BufferedReader buffer = new BufferedReader(in);

        String inputLine = null;
        boolean findTxt = false;
        try {
            while ((inputLine = buffer.readLine()) != null) {
                inputLine = inputLine.trim();
                if (inputLine.contains("<div class=\"zzj_5\">")) {
                    findTxt = true;
                    continue;
                }

                if (!findTxt)
                    continue;

                if (findTxt && inputLine.contains("</div>"))
                    break;

                outputTxt += parseEachLineForTxt(inputLine) + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputTxt;
    }

    /**
     * Remove all xml nodes and &nbsp; characters.
     *
     * @param inputLine
     * @return
     */
    private String parseEachLineForTxt(String inputLine) {

        String ret = inputLine.replaceAll("&nbsp;", "");

        Pattern p = Pattern.compile("<(.*?)>");
        Matcher m = p.matcher(ret);
        ArrayList<String> strsList = new ArrayList<String>();
        while (m.find()) {
            strsList.add(m.group(1));
        }
        for (String s : strsList) {
            ret = ret.replaceAll("<" + s + ">", "");
        }
        return ret.trim();
    }

    // 获取修改后的ZZU新闻HTML代码
    public String GetHtml() {
        return news.getContents();
    }

    // 通过HTTP请求获取HTML代码，并对其进行了修改
    public boolean requestNewsDetailByHttp() {

        HTTPTool tool = new HTTPTool(requestURL);
        String htmlWebPage = tool.GetHtml();
        news.setNaiveHtml(htmlWebPage);
        if (htmlWebPage.equals(""))
            return false;
        // 过滤网页中无用的内容
        news.setContents(parseHtmlForWebviewContent(htmlWebPage));
        return true;
    }

}
