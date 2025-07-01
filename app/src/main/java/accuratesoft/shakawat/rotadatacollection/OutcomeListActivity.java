package accuratesoft.shakawat.rotadatacollection;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import accuratesoft.shakawat.rotadatacollection.adapters.OutcomeRecAdapter;
import accuratesoft.shakawat.rotadatacollection.models.MOutcomeList;
import accuratesoft.shakawat.rotadatacollection.utils.Util;

public class OutcomeListActivity extends AppCompatActivity {
    RecyclerView rec;
    ProgressBar pBar;
    LinearLayoutManager linearLayoutManager;
    OutcomeRecAdapter outcomeRecAdapter;
    ArrayList<MOutcomeList> arrayList;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_outcome_list);
        init();
    }
    private void init(){
        rec = (RecyclerView) findViewById(R.id.rec);
        pBar = (ProgressBar) findViewById(R.id.pBar);
        linearLayoutManager = new LinearLayoutManager(this);
        rec.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        outcomeRecAdapter = new OutcomeRecAdapter(this,rec,this,arrayList);
        rec.setAdapter(outcomeRecAdapter);
        sharedPreferences = getSharedPreferences(Util.secret_key,MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (arrayList.size() > 0)
            arrayList.clear();
        getData();
    }

    private void getData(){
        pBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(OutcomeListActivity.this);
        String link = Util.url+"get_outcome.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pBar.setVisibility(View.GONE);
                        Log.d("OutcomeListActivity",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String msg = jsonObject.getString("message");
                            if(success == 1) {
                                Util.makeToast(OutcomeListActivity.this,msg);
                                JSONArray jsonArray = jsonObject.getJSONArray("patients");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    MOutcomeList moutcome = new MOutcomeList();
                                    moutcome.id = object.getInt("id");
                                    moutcome.enrollment_id = object.getString("enrollment_id");
                                    moutcome.mobile = object.getString("mobile");
                                    moutcome.name = object.getString("name");
                                    moutcome.in_date = object.getString("in_date");
                                    moutcome.screen_id = object.getString("screen_id");
                                    arrayList.add(moutcome);
                                }
                                outcomeRecAdapter.notifyDataSetChanged();
                            }else{
                                Util.makeToast(OutcomeListActivity.this,msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pBar.setVisibility(View.GONE);
                Util.makeToast(OutcomeListActivity.this,"get outcome error!");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("hos_name",sharedPreferences.getString("hos_name",""));
                params.put("hos_id",String.valueOf(sharedPreferences.getInt("hos_id",0)));
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
}