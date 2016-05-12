package ch04.string;

import org.apache.http.client.fluent.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class WeatherTest {

    public static void main(String[] args) throws IOException, JSONException {
        String httpUrl = "http://apis.baidu.com/heweather/weather/free";
        String httpArg = "city=zhengzhou";
        String apiKey = args[0];

        httpUrl = httpUrl + "?" + httpArg;
        String str = Request.Get(httpUrl).addHeader("apikey", apiKey).execute().returnContent().asString();

        JSONObject resultJson = new JSONObject(str);

        System.out.println("\n\n获取的当前天气数据对象如下:\n");
        System.out.println(resultJson);

        JSONArray heWeatherArray = resultJson.getJSONArray("HeWeather data service 3.0");
        JSONObject heWeather = heWeatherArray.getJSONObject(0);
        JSONObject aqi = heWeather.getJSONObject("aqi");
        System.out.println("\n\naqi is " + aqi);

    }
}
