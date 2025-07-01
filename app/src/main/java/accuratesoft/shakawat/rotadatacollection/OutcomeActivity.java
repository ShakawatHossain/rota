package accuratesoft.shakawat.rotadatacollection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import accuratesoft.shakawat.rotadatacollection.dialogs.CalenderDialog;
import accuratesoft.shakawat.rotadatacollection.interfaces.CalenderInterface;
import accuratesoft.shakawat.rotadatacollection.models.MOutcomeList;
import accuratesoft.shakawat.rotadatacollection.utils.Loading;
import accuratesoft.shakawat.rotadatacollection.utils.Util;

public class OutcomeActivity extends AppCompatActivity implements CalenderInterface {
    TextView enrollment_id,outcome_day;
    Spinner outcome;
    Button submit;
    ProgressBar pBar;
    Loading loading;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_outcome);
        init();
    }
    private void init(){
        enrollment_id = (TextView) findViewById(R.id.enrollment_id);
        outcome = (Spinner) findViewById(R.id.outcome);
        outcome_day = (TextView) findViewById(R.id.outcome_day);
        submit = (Button) findViewById(R.id.submit);
        pBar = (ProgressBar) findViewById(R.id.pBar);
        loading = new Loading(submit,pBar);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        enrollment_id.setText(intent.getStringExtra("enrollment_id"));
        submit.setOnClickListener(clickListener);
        outcome_day.setOnClickListener(clickListener);
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId()==outcome_day.getId()){
                new CalenderDialog(OutcomeActivity.this,OutcomeActivity.this,outcome_day.getText().toString(),"Day of outcome",outcome_day).show();
            } else if (v.getId()==submit.getId()) {
                if (outcome.getSelectedItemPosition()>0 && !outcome_day.getText().toString().isEmpty()){
                    sendData();
                }else {
                    Util.makeToast(OutcomeActivity.this,"Please select all fields");
                }
            }
        }
    };
    @Override
    public void getDate(String date, TextView editText) {
        editText.setText(date);
    }
    private void sendData(){
        loading.alterVisibility();
        RequestQueue queue = Volley.newRequestQueue(OutcomeActivity.this);
        String link = Util.url+"set_outcome.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.alterVisibility();
                        Log.d("OutcomeListActivity",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String msg = jsonObject.getString("message");
                            if(success == 1) {
                                Util.makeToast(OutcomeActivity.this,msg);
                                finish();
                            }else{
                                Util.makeToast(OutcomeActivity.this,msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.alterVisibility();
                Util.makeToast(OutcomeActivity.this,"get outcome error!");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("id",String.valueOf(id));
                params.put("outcome",String.valueOf(outcome.getSelectedItemPosition()));
                params.put("discharge_day",outcome_day.getText().toString());
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