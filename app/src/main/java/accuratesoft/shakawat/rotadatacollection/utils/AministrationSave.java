package accuratesoft.shakawat.rotadatacollection.utils;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import accuratesoft.shakawat.rotadatacollection.models.MyDB;

public class AministrationSave {
    JSONArray jsonArray;
    Context context;
    Runnable r;
    MyDB myDB;
    public AministrationSave(JSONArray jsonArray,Context context){
        this.jsonArray = jsonArray;
        this.context = context;
        myDB = new MyDB(context);
        this.r = new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i<jsonArray.length();i++){
                    JSONObject jobj = null;
                    try {
                        jobj = jsonArray.getJSONObject(i);
                        ContentValues cv = new ContentValues();
                        cv.put("id",jobj.getInt("id"));
                        cv.put("parent_id",jobj.getInt("parent_id"));
                        cv.put("name",jobj.getString("name"));
                        cv.put("category",jobj.getInt("category"));
                        myDB.insert(cv);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        this.r.run();
    }
}
