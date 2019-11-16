import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by 沈毅 on 2019/3/19.
 */
public class MyJsonParser {
    //访问sflow的URL获取json
    //同时进行JSON的解析
    public static void main(String args[]){

        //这个是sflow获取json的地址
        String urlStr = "http://192.168.203.137:8008/metric/10.0.0.3/json";

        StringBuffer jsonStringBuffer = new StringBuffer();

        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String inputLine = null;

            while ((inputLine=bufferedReader.readLine())!=null){
                jsonStringBuffer.append(inputLine);
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //举个例子
        //String应该是类似：{ "7.ifoutoctets": 0, "5.ifindex": "5", "5.ifoutoctets": 0, "6.ifinucastpkts": 0}
        //注意前后没有[]
        String jsonString = jsonStringBuffer.toString();

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(jsonString).getAsJsonObject();

        System.out.println(jsonObject.get("7.ifoutoctets"));

    }
}
