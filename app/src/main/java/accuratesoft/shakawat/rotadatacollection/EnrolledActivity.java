package accuratesoft.shakawat.rotadatacollection;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import accuratesoft.shakawat.rotadatacollection.dialogs.CalenderDialog;
import accuratesoft.shakawat.rotadatacollection.interfaces.CalenderInterface;
import accuratesoft.shakawat.rotadatacollection.models.MyDB;
import accuratesoft.shakawat.rotadatacollection.utils.Loading;
import accuratesoft.shakawat.rotadatacollection.utils.Util;

public class EnrolledActivity extends AppCompatActivity implements CalenderInterface {
    TextView enrollment_id,in_date,dia_onset;
    EditText abs_doc_name,rel_oth,dia_episode,vomit_episode,fever_temp,rehy_type_oth,probiotic_name,
            probiotic_dose;
    Spinner rel,dia,blood,vomit,fever,dehy,dehy_deg,rehy,rehy_type,probiotic,cond,heart,pulse,tear,tongue,
            skin,capillary,extrem;
    TableRow rel_oth_row,dia_onset_row,dia_episode_row,vomit_episode_row,fever_temp_row,dehy_deg_row,
            rehy_type_row,rehy_type_oth_row,probiotic_dose_row,probiotic_name_row;
    Button submit;
    ProgressBar pBar;
    Loading loading;
    MyDB myDB;
    int enrolled_id,screen_id;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolled);
        init();
        setData();
    }
    private void  init(){
        enrollment_id = (TextView) findViewById(R.id.enrollment_id);
        in_date = (TextView) findViewById(R.id.in_date);
        dia_onset = (TextView) findViewById(R.id.dia_onset);
        abs_doc_name = (EditText) findViewById(R.id.abs_doc_name);
        rel_oth = (EditText) findViewById(R.id.rel_oth);
        dia_episode = (EditText) findViewById(R.id.dia_episode);
        vomit_episode = (EditText) findViewById(R.id.vomit_episode);
        fever_temp = (EditText) findViewById(R.id.fever_temp);
        rehy_type_oth = (EditText) findViewById(R.id.rehy_type_oth);
        probiotic_name = (EditText) findViewById(R.id.probiotic_name);
        probiotic_dose = (EditText) findViewById(R.id.probiotic_dose);
        rel = (Spinner) findViewById(R.id.rel);
        dia = (Spinner) findViewById(R.id.dia);
        blood = (Spinner) findViewById(R.id.blood);
        vomit = (Spinner) findViewById(R.id.vomit);
        fever = (Spinner) findViewById(R.id.fever);
        dehy = (Spinner) findViewById(R.id.dehy);
        dehy_deg = (Spinner) findViewById(R.id.dehy_deg);
        rehy = (Spinner) findViewById(R.id.rehy);
        rehy_type = (Spinner) findViewById(R.id.rehy_type);
        probiotic = (Spinner) findViewById(R.id.probiotic);
        cond = (Spinner) findViewById(R.id.cond);
        heart = (Spinner) findViewById(R.id.heart);
        pulse = (Spinner) findViewById(R.id.pulse);
        tear = (Spinner) findViewById(R.id.tear);
        tongue = (Spinner) findViewById(R.id.tongue);
        skin = (Spinner) findViewById(R.id.skin);
        capillary = (Spinner) findViewById(R.id.capillary);
        extrem = (Spinner) findViewById(R.id.extrem);
        rel_oth_row = (TableRow) findViewById(R.id.rel_oth_row);
        dia_onset_row = (TableRow) findViewById(R.id.dia_onset_row);
        dia_episode_row = (TableRow) findViewById(R.id.dia_episode_row);
        vomit_episode_row = (TableRow) findViewById(R.id.vomit_episode_row);
        fever_temp_row = (TableRow) findViewById(R.id.fever_temp_row);
        dehy_deg_row = (TableRow) findViewById(R.id.dehy_deg_row);
        rehy_type_row = (TableRow) findViewById(R.id.rehy_type_row);
        rehy_type_oth_row = (TableRow) findViewById(R.id.rehy_type_oth_row);
        probiotic_dose_row = (TableRow) findViewById(R.id.probiotic_dose_row);
        probiotic_name_row = (TableRow) findViewById(R.id.probiotic_name_row);
        submit = (Button) findViewById(R.id.submit);
        pBar = (ProgressBar) findViewById(R.id.pBar);
        loading = new Loading(submit,pBar);
        myDB = new MyDB(this);
        enrolled_id=0;
        sharedPreferences = getSharedPreferences(Util.secret_key,MODE_PRIVATE);
    }
    @SuppressLint("Range")
    private void setData(){
        rel.setOnItemSelectedListener(itemSelectedListener);
        dia.setOnItemSelectedListener(itemSelectedListener);
        blood.setOnItemSelectedListener(itemSelectedListener);
        vomit.setOnItemSelectedListener(itemSelectedListener);
        fever.setOnItemSelectedListener(itemSelectedListener);
        dehy.setOnItemSelectedListener(itemSelectedListener);
        rehy.setOnItemSelectedListener(itemSelectedListener);
        rehy_type.setOnItemSelectedListener(itemSelectedListener);
        probiotic.setOnItemSelectedListener(itemSelectedListener);
        in_date.setOnClickListener(clickListener);
        dia_onset.setOnClickListener(clickListener);
        submit.setOnClickListener(clickListener);
        Intent intent = getIntent();
        screen_id = intent.getIntExtra("screen_id",0);
        Cursor c = myDB.getEnrolled(screen_id);
        if(c.moveToFirst()){
            Log.d("Load","From DB");
            enrolled_id = c.getInt(c.getColumnIndex("id"));
            enrollment_id.setText(intent.getStringExtra("enrollment_id"));
//            enrollment_id.setText(c.getString(c.getColumnIndex("enrollment_id")));
            in_date.setText(c.getString(c.getColumnIndex("in_date")));
            dia_onset.setText(c.getString(c.getColumnIndex("dia_onset")));
            abs_doc_name.setText(c.getString(c.getColumnIndex("abs_doc_name")));
            rel_oth.setText(c.getString(c.getColumnIndex("rel_oth")));
            dia_episode.setText(c.getString(c.getColumnIndex("dia_episode")));
            vomit_episode.setText(c.getString(c.getColumnIndex("vomit_episode")));
            fever_temp.setText(c.getString(c.getColumnIndex("fever_temp")));
            rehy_type_oth.setText(c.getString(c.getColumnIndex("rehy_type_oth")));
            probiotic_name.setText(c.getString(c.getColumnIndex("probiotic_name")));
            probiotic_dose.setText(c.getString(c.getColumnIndex("probiotic_dose")));
            rel.setSelection(c.getInt(c.getColumnIndex("rel")));
            dia.setSelection(c.getInt(c.getColumnIndex("dia")));
            blood.setSelection(c.getInt(c.getColumnIndex("blood")));
            vomit.setSelection(c.getInt(c.getColumnIndex("vomit")));
            fever.setSelection(c.getInt(c.getColumnIndex("fever")));
            dehy.setSelection(c.getInt(c.getColumnIndex("dehy")));
            dehy_deg.setSelection(c.getInt(c.getColumnIndex("dehy_deg")));
            rehy.setSelection(c.getInt(c.getColumnIndex("rehy")));
            rehy_type.setSelection(c.getInt(c.getColumnIndex("rehy_type")));
            probiotic.setSelection(c.getInt(c.getColumnIndex("probiotic")));
            cond.setSelection(c.getInt(c.getColumnIndex("cond")));
            heart.setSelection(c.getInt(c.getColumnIndex("heart")));
            pulse.setSelection(c.getInt(c.getColumnIndex("pulse")));
            tear.setSelection(c.getInt(c.getColumnIndex("tear")));
            tongue.setSelection(c.getInt(c.getColumnIndex("tongue")));
            skin.setSelection(c.getInt(c.getColumnIndex("skin")));
            capillary.setSelection(c.getInt(c.getColumnIndex("capillary")));
            extrem.setSelection(c.getInt(c.getColumnIndex("extrem")));
        }else{
            Log.d("Load","Fresh load");
            enrollment_id.setText(intent.getStringExtra("enrollment_id"));
            in_date.setText(intent.getStringExtra("in_date"));
        }
    }
    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            abs_doc_name.clearFocus();
            rel_oth.clearFocus();
            dia_episode.clearFocus();
            vomit_episode.clearFocus();
            fever_temp.clearFocus();
            rehy_type_oth.clearFocus();
            probiotic_name.clearFocus();
            probiotic_dose.clearFocus();
            if (view != null) {
                if (view.getParent()==rel){
                    if (i==3) rel_oth_row.setVisibility(View.VISIBLE);
                    else rel_oth_row.setVisibility(View.GONE);
                } else if (view.getParent()==dia) {
                    if (i==1) {
                        dia_onset_row.setVisibility(View.VISIBLE);
                        dia_episode_row.setVisibility(View.VISIBLE);
                    }
                    else {
                        dia_onset_row.setVisibility(View.GONE);
                        dia_episode_row.setVisibility(View.GONE);
                    }
                } else if (view.getParent()==vomit) {
                    if (i==1) vomit_episode_row.setVisibility(View.VISIBLE);
                    else vomit_episode_row.setVisibility(View.GONE);
                } else if (view.getParent()==fever) {
                    if (i==1) fever_temp_row.setVisibility(View.VISIBLE);
                    else fever_temp_row.setVisibility(View.GONE);
                } else if (view.getParent()==dehy) {
                    if (i==1) dehy_deg_row.setVisibility(View.VISIBLE);
                    else dehy_deg_row.setVisibility(View.GONE);
                }else if (view.getParent()==rehy) {
                    if (i==1) rehy_type_row.setVisibility(View.VISIBLE);
                    else rehy_type_row.setVisibility(View.GONE);
                }else if (view.getParent()==rehy_type) {
                    if (i==3) rehy_type_oth_row.setVisibility(View.VISIBLE);
                    else rehy_type_oth_row.setVisibility(View.GONE);
                } else if (view.getParent()==probiotic) {
                    if (i==1) {
                        probiotic_dose_row.setVisibility(View.VISIBLE);
                        probiotic_name_row.setVisibility(View.VISIBLE);
                    }
                    else {
                        probiotic_dose_row.setVisibility(View.GONE);
                        probiotic_name_row.setVisibility(View.GONE);
                    }
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private View.OnClickListener clickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            if (view.getId()==in_date.getId()){
                new CalenderDialog(EnrolledActivity.this,EnrolledActivity.this, in_date.getText().toString(),"Interview Date",in_date).show();
            } else if (view.getId()==dia_onset.getId()) {
                new CalenderDialog(EnrolledActivity.this,EnrolledActivity.this, dia_onset.getText().toString(),"Diarrhoea Onset Date",dia_onset).show();
            } else if (view.getId()==submit.getId()) {
                if (check()){
                    saveData();
                }
            }
        }
    };
    private boolean check(){
        boolean is_ok = true;
        if (in_date.getText().toString().isEmpty()){
            is_ok = false;
            Util.makeToast(EnrolledActivity.this,"Please select interview date");
        }
        if (abs_doc_name.getText().toString().isEmpty()){
            is_ok = false;
            Util.makeToast(EnrolledActivity.this,"Please write doctor name");
        }
        if (rel.getSelectedItemPosition()==0){
            is_ok = false;
            Util.makeToast(EnrolledActivity.this,"Please select relationship");
        } else if (rel.getSelectedItemPosition()==3 && rel_oth.getText().toString().isEmpty()){
            is_ok = false;
            Util.makeToast(EnrolledActivity.this,"Please enter relationship other");
        }
        if (dia.getSelectedItemPosition()==0){
            is_ok = false;
            Util.makeToast(EnrolledActivity.this,"Please select Diarrhoea");
        } else if (dia.getSelectedItemPosition()==1 && (dia_onset.getText().toString().isEmpty() || dia_episode.getText().toString().isEmpty())){
            is_ok = false;
            Util.makeToast(EnrolledActivity.this,"Please select Diarrhoea onset date or episode");
        }
        if (blood.getSelectedItemPosition()==0){
            is_ok = false;
            Util.makeToast(EnrolledActivity.this,"Please select bloody Diarrhoea");
        }
        if (vomit.getSelectedItemPosition()==0){
            is_ok = false;
            Util.makeToast(EnrolledActivity.this,"Please select vomit");
        } else if (vomit.getSelectedItemPosition() == 1) {
            if (vomit_episode.getText().toString().isEmpty()) {
                is_ok = false;
                Util.makeToast(EnrolledActivity.this, "Please select vomit episode");
            }
        }
        if (fever.getSelectedItemPosition()==0){
            is_ok = false;
            Util.makeToast(EnrolledActivity.this,"Select fever presence");
        } else if (fever.getSelectedItemPosition()==1 && fever_temp.getText().toString().isEmpty()) {
            is_ok = false;
            Util.makeToast(EnrolledActivity.this,"Please write fever temprature");
        }
        if (dehy.getSelectedItemPosition()==0){
            is_ok=false;
            Util.makeToast(EnrolledActivity.this,"Please select dehydration");
        } else if (dehy.getSelectedItemPosition()==1 && dehy_deg.getSelectedItemPosition()==0) {
            is_ok=false;
            Util.makeToast(EnrolledActivity.this,"Please select dehydration status");
        }
        if (rehy.getSelectedItemPosition()==0){
            is_ok=false;
            Util.makeToast(EnrolledActivity.this,"Please select rehydration");
        } else if (rehy.getSelectedItemPosition()==1) {
            if (rehy_type.getSelectedItemPosition()==0) {
                is_ok = false;
                Util.makeToast(EnrolledActivity.this, "Please select rehydration therapy");
            } else if (rehy_type.getSelectedItemPosition()==3 && rehy_type_oth.getText().toString().isEmpty()) {
                is_ok = false;
                Util.makeToast(EnrolledActivity.this, "Please specify rehydration therapy");
            }
        }
        if (probiotic.getSelectedItemPosition()==0){
            is_ok = false;
            Util.makeToast(EnrolledActivity.this,"Please select probiotic");
        }else if (probiotic.getSelectedItemPosition()==1){
            if (probiotic_name.getText().toString().isEmpty() || probiotic_dose.getText().toString().isEmpty()) {
                is_ok = false;
                Util.makeToast(EnrolledActivity.this, "Please select probiotic name and doses");
            }
        }
        if (cond.getSelectedItemPosition()==0){
            is_ok=false;
            Util.makeToast(EnrolledActivity.this,"Please select condition");
        }
        if (heart.getSelectedItemPosition()==0){
            is_ok=false;
            Util.makeToast(EnrolledActivity.this,"Please select heart");
        }
        if (pulse.getSelectedItemPosition()==0){
            is_ok=false;
            Util.makeToast(EnrolledActivity.this,"Please select pulse");
        }
        if (tear.getSelectedItemPosition()==0){
            is_ok=false;
            Util.makeToast(EnrolledActivity.this,"Please select tear");
        }
        if (tongue.getSelectedItemPosition()==0){
            is_ok=false;
            Util.makeToast(EnrolledActivity.this,"Please select tongue");
        }
        if (skin.getSelectedItemPosition()==0){
            is_ok=false;
            Util.makeToast(EnrolledActivity.this,"Please select skin");
        }
        if (capillary.getSelectedItemPosition()==0){
            is_ok=false;
            Util.makeToast(EnrolledActivity.this,"Please select capillary");
        }
        if (extrem.getSelectedItemPosition()==0){
            is_ok=false;
            Util.makeToast(EnrolledActivity.this,"Please select extrem");
        }
        return is_ok;
    }
    private void saveData(){
        ContentValues cv = new ContentValues();
        cv.put("id",enrolled_id);
        cv.put("screen_id",screen_id);
        cv.put("enrollment_id",enrollment_id.getText().toString());
        cv.put("hos_name",sharedPreferences.getString("hos_name",""));
        cv.put("hos_id",sharedPreferences.getInt("hos_id",0));
        cv.put("in_date",in_date.getText().toString());
        cv.put("rel",rel.getSelectedItemPosition());
        cv.put("dia",dia.getSelectedItemPosition());
        cv.put("blood",blood.getSelectedItemPosition());
        cv.put("vomit",vomit.getSelectedItemPosition());
        cv.put("fever",fever.getSelectedItemPosition());
        cv.put("dehy",dehy.getSelectedItemPosition());
        cv.put("dehy_deg",dehy_deg.getSelectedItemPosition());
        cv.put("rehy",rehy.getSelectedItemPosition());
        cv.put("rehy_type",rehy_type.getSelectedItemPosition());
        cv.put("probiotic",probiotic.getSelectedItemPosition());
        cv.put("cond",cond.getSelectedItemPosition());
        cv.put("heart",heart.getSelectedItemPosition());
        cv.put("pulse",pulse.getSelectedItemPosition());
        cv.put("tear",tear.getSelectedItemPosition());
        cv.put("tongue",tongue.getSelectedItemPosition());
        cv.put("skin",skin.getSelectedItemPosition());
        cv.put("capillary",capillary.getSelectedItemPosition());
        cv.put("extrem",extrem.getSelectedItemPosition());
        cv.put("rel_oth",rel_oth.getText().toString());
        cv.put("dia_onset",dia_onset.getText().toString());
        cv.put("dia_episode",dia_episode.getText().toString());
        cv.put("vomit_episode",vomit_episode.getText().toString());
        cv.put("fever_temp",fever_temp.getText().toString());
        cv.put("rehy_type_oth",rehy_type_oth.getText().toString());
        cv.put("probiotic_name",probiotic_name.getText().toString());
        cv.put("probiotic_dose",probiotic_dose.getText().toString());
        cv.put("abs_doc_name",abs_doc_name.getText().toString());
        myDB.insertEnrolled(cv);
        finish();
    }
    @Override
    public void getDate(String date, TextView editText) {
        editText.setText(date);
    }
}