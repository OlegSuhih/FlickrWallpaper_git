package ua.ho.webdiz.flickr;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FlickrJSON {

   private static JSONObject jsonObject = FlickrUrl.getFlickrJSONObject();

    public static List<FlickrItem> getListFlickrItems() {
        List<FlickrItem> result = new ArrayList<>();
        String id = "0";
        String url_s = "https://live.staticflickr.com/65535/48776677487_f07323a2cc_m.jpg";
        String url_z = "https://live.staticflickr.com/65535/48776391596_d383fe9f6f.jpg";

        try {
//            if(jsonObject == null) {
//                Log.d("MyTag", "jsonObject null!!!");
//                Intent intent = new Intent(MyApp.getAppContext(), FlickrOffNet.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                MyApp.getAppContext().startActivity(intent);
//
//            }
            JSONObject sub_object = jsonObject.getJSONObject("photos");
            JSONArray jsonArray = sub_object.getJSONArray("photo");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject_i = jsonArray.getJSONObject(i);
                id = jsonObject_i.getString("id");

                try {
                    url_z = jsonObject_i.getString("url_c");
                    url_s = jsonObject_i.getString("url_s");
                } catch (JSONException e) {
                    Log.d("MyTag", "url_m is null");
                    url_s = "https://live.staticflickr.com/65535/48776677487_f07323a2cc_m.jpg";
                    url_z = "https://live.staticflickr.com/65535/48776391596_d383fe9f6f.jpg";

                } finally {
                    FlickrItem item = new FlickrItem(id, url_s, url_z);
                    result.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
