package accuratesoft.shakawat.rotadatacollection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import accuratesoft.shakawat.rotadatacollection.adapters.ListRecAdapter;
import accuratesoft.shakawat.rotadatacollection.models.MDC;
import accuratesoft.shakawat.rotadatacollection.models.MyDB;
import accuratesoft.shakawat.rotadatacollection.utils.Loading;
import accuratesoft.shakawat.rotadatacollection.utils.Util;

public class ListActivity extends AppCompatActivity {
    TextView hos_name;
    Button upload,outcome,logout;
    RecyclerView rec;
    LinearLayoutManager linearLayoutManager;
    ListRecAdapter listRecAdapter;
    FloatingActionButton fab;
    SharedPreferences sharedPreferences;
    Loading loading;
    MyDB myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
    }
    private void init(){
        hos_name = (TextView) findViewById(R.id.hos_name);
        rec = (RecyclerView) findViewById(R.id.rec);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        logout = (Button) findViewById(R.id.logout);
        upload = (Button) findViewById(R.id.upload);
        outcome = (Button) findViewById(R.id.outcome);
        sharedPreferences = getSharedPreferences(Util.secret_key,MODE_PRIVATE);
        fab.setOnClickListener(clickListener);
        upload.setOnClickListener(clickListener);

        hos_name.setText(sharedPreferences.getString("hos_name",""));
        logout.setOnClickListener(clickListener);
        outcome.setOnClickListener(clickListener);
        myDB = new MyDB(ListActivity.this);
        linearLayoutManager = new LinearLayoutManager(ListActivity.this);
        rec.setLayoutManager(linearLayoutManager);
        loading = new Loading(upload,((ProgressBar) findViewById(R.id.pBar)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("list size: ",String.valueOf(myDB.getScreening().getCount()));
        listRecAdapter = new ListRecAdapter(ListActivity.this,ListActivity.this,rec,myDB.getScreening());
        rec.setAdapter(listRecAdapter);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId()==fab.getId()){
                MDC.clear();
                startActivity(new Intent(ListActivity.this,DCActivity.class));
            } else if (view.getId() ==logout.getId()){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                finish();
            } else if (view.getId() == upload.getId()) {
                sendData();
            } else if (view.getId() == outcome.getId()) {
                startActivity(new Intent(ListActivity.this, OutcomeListActivity.class));
            }
        }
    };
    private void sendData(){
        if (!myDB.getScreening().moveToFirst()){
            Util.makeToast(ListActivity.this,"No data to upload");
            return;
        }
        loading.alterVisibility();
        RequestQueue queue = Volley.newRequestQueue(ListActivity.this);
        String link = Util.url+"insert.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.alterVisibility();
                        Log.d("Upload Record",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String msg = jsonObject.getString("message");
                            if(success == 1) {
                                Util.makeToast(ListActivity.this,msg);
                                myDB.del_all();
                                startActivity(new Intent(ListActivity.this,ListActivity.class));
//                                            finish();
                            }else{
                                Util.makeToast(ListActivity.this,msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.alterVisibility();
                Log.e("Upload Record",error.toString());
                Util.makeToast(ListActivity.this,"Upload error!");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<String,String>();
                Cursor screening = myDB.getScreening();
                Cursor enrolled = myDB.getEnrolled();
                JSONObject jobj = new JSONObject();
                JSONArray jsonArray_screening = new JSONArray();
                JSONArray jsonArray_enrolled = new JSONArray();
                try {
                    if(screening.moveToFirst()){
                        do{
                            JSONObject jsonObject = new JSONObject();
                            for (int i=1;i<screening.getColumnCount();i++)
                                jsonObject.put(screening.getColumnName(i),screening.getString(i));
                            jsonArray_screening.put(jsonObject);
                        }while (screening.moveToNext());
                        jobj.put("screening",jsonArray_screening);
                    }
                    if(enrolled.moveToFirst()){
                        do{
                            JSONObject jsonObject = new JSONObject();
                            for (int i=2;i<enrolled.getColumnCount();i++)
                                jsonObject.put(enrolled.getColumnName(i),enrolled.getString(i));
                            jsonArray_enrolled.put(jsonObject);
                        }while (enrolled.moveToNext());
                        jobj.put("enrolled",jsonArray_enrolled);
                    }
                }catch (JSONException ex){
                    ex.printStackTrace();
                }
                params.put("request",jobj.toString());
                return params;
            }

        };
        // Add the request to the RequestQueue.
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20), //After the set time elapses the request will timeout
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }
    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }
}