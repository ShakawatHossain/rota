package accuratesoft.shakawat.rotadatacollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import accuratesoft.shakawat.rotadatacollection.models.MMain;
import accuratesoft.shakawat.rotadatacollection.models.MyDB;
import accuratesoft.shakawat.rotadatacollection.utils.AministrationSave;
import accuratesoft.shakawat.rotadatacollection.utils.Loading;
import accuratesoft.shakawat.rotadatacollection.utils.Util;

public class MainActivity extends AppCompatActivity {
    EditText user_id,pass;
    Button submit;
    MMain mMain;
    SharedPreferences sharedPreferences;
    Loading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init(){
        user_id = (EditText) findViewById(R.id.user_id);
        pass = (EditText) findViewById(R.id.pass);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(clickListener);
        mMain = new MMain();
        sharedPreferences = getSharedPreferences(Util.secret_key,MODE_PRIVATE);
        int hos_id = sharedPreferences.getInt("hos_id",0);
        if (hos_id>0){
            startActivity(new Intent(MainActivity.this,ListActivity.class));
            finish();
        }
        loading = new Loading(submit,(ProgressBar) findViewById(R.id.pBar));
        if (!new MyDB(MainActivity.this).getDiv().moveToFirst())
            getAdministration();
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!user_id.getText().toString().isEmpty() || !pass.getText().toString().isEmpty()){
                String hos_name = mMain.check_login(user_id.getText().toString(),pass.getText().toString());
                if (hos_name.isEmpty()){
                    Util.makeToast(MainActivity.this,"User id or password mismatched");
                    return;
                }
                String [] s = hos_name.split("_");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("hos_name",s[0]);
                editor.putInt("hos_id",Integer.parseInt(s[1]));
                editor.commit();
                startActivity(new Intent(MainActivity.this,ListActivity.class));
            }else{
                Util.makeToast(MainActivity.this,"User id or password empty");
            }
        }
    };
    private void getAdministration(){
        loading.alterVisibility();
        String url = "http://dashboard.iedcr.gov.bd:3000/"+"administration";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.alterVisibility();
                        Log.d("MenuActivity",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
//                            todo
                            int success = jsonObject.getInt("success");
                            if (success==1){
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                new AministrationSave(jsonArray,MainActivity.this);
                            }else{
                                String msg = jsonObject.getString("msg");
                                Util.makeToast(MainActivity.this,msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("MenuActivity",e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.alterVisibility();
                Util.makeToast(MainActivity.this,error.toString());
                Log.e("volley error Main",error.toString());
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20), //After the set time elapses the request will timeout
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}