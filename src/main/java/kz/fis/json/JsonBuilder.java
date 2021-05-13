package kz.fis.json;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonBuilder {


    public static String getData() {
        String data = "https://api.github.com/users/proffix4/followers";
        return HttpClient.getHTMLData(data);
    }

    private static Bitmap getJsonImage(String code) {
        return HttpClient.getHTMLImage(code + ".jpg");
    }

    private static Json dataParsing(String json1, String nameF) {
        Json json = new Json();
        try {
            JSONArray _arr = new JSONArray(json1);

            for (int i = 0; i < _arr.length(); i++) {
                JSONObject _obj = _arr.getJSONObject(i);
                if (_obj.getString("login").equals(nameF)) {
                    json.setId(_obj.getInt("id"));
                    json.setName(_obj.getString("login"));
                    json.setUserURL(_obj.getString("html_url"));
                    json.setRepository(_obj.getString("repos_url"));
                    json.setIconName(_obj.getString("avatar_url"));
                    json.setIcon(getJsonImage(json.getIconName()));
                }
            }

        } catch (Exception ignore) {
        }
        return json;
    }

    public static Json buildJson(String nameF) {
        return dataParsing(getData(), nameF);
    }


}