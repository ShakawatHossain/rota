package accuratesoft.shakawat.rotadatacollection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import accuratesoft.shakawat.rotadatacollection.dialogs.CalenderDialog;
import accuratesoft.shakawat.rotadatacollection.interfaces.CalenderInterface;
import accuratesoft.shakawat.rotadatacollection.models.MDC;
import accuratesoft.shakawat.rotadatacollection.models.MyDB;
import accuratesoft.shakawat.rotadatacollection.utils.Loading;
import accuratesoft.shakawat.rotadatacollection.utils.Util;

public class DCActivity extends AppCompatActivity implements CalenderInterface {
    TextView in_date,hos_name,hos_id,dob,admission,sample,fdose,sdose,tdose;
    EditText screen_id,dc_name,health_reason_oth,r_reason_oth,name,mobile,address,enrollment_id,
            vac_info_oth,vaccine_doses;
    Spinner less5,is_gastro,parent_av,gastro7,bf_symptom,healthy,evaluation,r_reason,sex,div,dis,upz,
            un,is_accept,is_vaccinated,vaccine_name,vac_info;
    Button submit;
    ProgressBar pBar;
    CardView accepted_card,vaccinated_card;
    TableRow health_reason_oth_row,r_reason_row,r_reason_oth_row,fdose_row,sdose_row,tdose_row,vac_info_oth_row;
    Loading loading;
    SharedPreferences sharedPreferences;
    MyDB myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcactivity);
        init();
        setData();
    }
    private void init(){
        in_date = (TextView) findViewById(R.id.in_date);
        hos_name = (TextView) findViewById(R.id.hos_name);
        hos_id = (TextView) findViewById(R.id.hos_id);
        dob = (TextView) findViewById(R.id.dob);
        admission = (TextView) findViewById(R.id.admission);
        sample = (TextView) findViewById(R.id.sample);
        screen_id = (EditText) findViewById(R.id.screen_id);
        dc_name = (EditText) findViewById(R.id.dc_name);
        health_reason_oth = (EditText) findViewById(R.id.health_reason_oth);
        r_reason_oth = (EditText) findViewById(R.id.r_reason_oth);
        name = (EditText) findViewById(R.id.name);
        mobile = (EditText) findViewById(R.id.mobile);
        address = (EditText) findViewById(R.id.address);
        enrollment_id = (EditText) findViewById(R.id.enrollment_id);
        less5 = (Spinner) findViewById(R.id.less5);
        is_gastro = (Spinner) findViewById(R.id.is_gastro);
        parent_av = (Spinner) findViewById(R.id.parent_av);
        gastro7 = (Spinner) findViewById(R.id.gastro7);
        bf_symptom = (Spinner) findViewById(R.id.bf_symptom);
        healthy = (Spinner) findViewById(R.id.healthy);
        evaluation = (Spinner) findViewById(R.id.evaluation);
        r_reason = (Spinner) findViewById(R.id.r_reason);
        sex = (Spinner) findViewById(R.id.sex);
        div = (Spinner) findViewById(R.id.div);
        dis = (Spinner) findViewById(R.id.dis);
        upz = (Spinner) findViewById(R.id.upz);
        un = (Spinner) findViewById(R.id.un);
        is_accept = (Spinner) findViewById(R.id.is_accept);
        is_vaccinated = (Spinner) findViewById(R.id.is_vaccinated);
        vac_info = (Spinner) findViewById(R.id.vac_info);
        submit = (Button) findViewById(R.id.submit);
        pBar = (ProgressBar) findViewById(R.id.pBar);
        accepted_card = (CardView) findViewById(R.id.accepted_card);
        vaccinated_card = (CardView) findViewById(R.id.vaccinated_card);
        health_reason_oth_row = (TableRow) findViewById(R.id.health_reason_oth_row);
        r_reason_row = (TableRow) findViewById(R.id.r_reason_row);
        r_reason_oth_row = (TableRow) findViewById(R.id.r_reason_oth_row);
        loading = new Loading(submit,pBar);
        sharedPreferences = getSharedPreferences(Util.secret_key,MODE_PRIVATE);
        vac_info_oth = (EditText) findViewById(R.id.vac_info_oth);
        vaccine_name = (Spinner) findViewById(R.id.vaccine_name);
        vaccine_doses = (EditText) findViewById(R.id.vaccine_doses);
        fdose = (TextView) findViewById(R.id.fdose);
        sdose = (TextView) findViewById(R.id.sdose);
        tdose = (TextView) findViewById(R.id.tdose);
        fdose_row = (TableRow) findViewById(R.id.fdose_row);
        sdose_row = (TableRow) findViewById(R.id.sdose_row);
        tdose_row = (TableRow) findViewById(R.id.tdose_row);
        vac_info_oth_row = (TableRow) findViewById(R.id.vac_info_oth_row);
        myDB = new MyDB(DCActivity.this);
    }
    private void setData(){
        hos_name.setText(sharedPreferences.getString("hos_name",""));
        hos_id.setText(String.valueOf(sharedPreferences.getInt("hos_id",0)));
        in_date.setText(MDC.in_date);
        sample.setText(MDC.sample);
        dob.setText(MDC.dob);
        admission.setText(MDC.admission);
        screen_id.setText(MDC.screen_id);
        dc_name.setText(MDC.dc_name);
        name.setText(MDC.name);
        mobile.setText(MDC.mobile);
        address.setText(MDC.address);
        enrollment_id.setText(MDC.enrollment_id);
        health_reason_oth.setText(MDC.health_reason_oth);
        r_reason_oth.setText(MDC.r_reason_oth);
        if(!MDC.health_reason_oth.equals("")){
            health_reason_oth_row.setVisibility(TableRow.VISIBLE);
        }
        if(!MDC.r_reason_oth.equals("")){
            r_reason_oth_row.setVisibility(TableRow.VISIBLE);
        }
        less5.setSelection(MDC.less5);
        is_gastro.setSelection(MDC.is_gastro);
        parent_av.setSelection(MDC.parent_av);
        gastro7.setSelection(MDC.gastro7);
        bf_symptom.setSelection(MDC.bf_symptom);
        healthy.setSelection(MDC.healthy);
        evaluation.setSelection(MDC.evaluation);
        if(MDC.evaluation==2){
            r_reason_row.setVisibility(View.VISIBLE);
        }
        r_reason.setSelection(MDC.r_reason);

        sex.setSelection(MDC.sex);
        div.setSelection(MDC.div);
        dis.setSelection(MDC.dis);
        upz.setSelection(MDC.upz);
        un.setSelection(MDC.un);
        is_accept.setSelection(MDC.is_accept);
        is_vaccinated.setSelection(MDC.is_vaccinated);
        vac_info.setSelection(MDC.vac_info);
        if (MDC.is_accept==1){
            accepted_card.setVisibility(CardView.VISIBLE);
        }
        if (MDC.is_vaccinated==1){
            vaccinated_card.setVisibility(CardView.VISIBLE);
        }
        vac_info_oth.setText(MDC.vac_info_oth);
        vaccine_name.setSelection(MDC.vaccine_name);
        if (MDC.vaccine_doses>0)
            vaccine_doses.setText(String.valueOf(MDC.vaccine_doses));
        if(MDC.vaccine_doses==1)
            fdose_row.setVisibility(TableRow.VISIBLE);
        if(MDC.vaccine_doses==2)
            sdose_row.setVisibility(TableRow.VISIBLE);
        if(MDC.vaccine_doses==3)
            tdose_row.setVisibility(TableRow.VISIBLE);
        fdose.setText(MDC.fdose);
        sdose.setText(MDC.sdose);
        tdose.setText(MDC.tdose);
        setDiv(MDC.div);
        setDis(MDC.div,MDC.dis);
        setUpz(MDC.dis,MDC.upz);
        setUn(MDC.upz,MDC.un);

        submit.setOnClickListener(clickListener);
        in_date.setOnClickListener(clickListener);
        dob.setOnClickListener(clickListener);
        sample.setOnClickListener(clickListener);
        admission.setOnClickListener(clickListener);
        fdose.setOnClickListener(clickListener);
        sdose.setOnClickListener(clickListener);
        tdose.setOnClickListener(clickListener);
        div.setOnItemSelectedListener(itemSelectedListener);
        dis.setOnItemSelectedListener(itemSelectedListener);
        upz.setOnItemSelectedListener(itemSelectedListener);
        un.setOnItemSelectedListener(itemSelectedListener);
        is_vaccinated.setOnItemSelectedListener(itemSelectedListener);
        healthy.setOnItemSelectedListener(itemSelectedListener);
        evaluation.setOnItemSelectedListener(itemSelectedListener);
        r_reason.setOnItemSelectedListener(itemSelectedListener);
        is_accept.setOnItemSelectedListener(itemSelectedListener);
        vac_info.setOnItemSelectedListener(itemSelectedListener);
        vaccine_doses.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String doses = vaccine_doses.getText().toString();
                tdose_row.setVisibility(TableRow.GONE);
                sdose_row.setVisibility(TableRow.GONE);
                fdose_row.setVisibility(TableRow.GONE);
                if(!doses.equals("")){
                    if(Integer.parseInt(doses)>2){
                        tdose_row.setVisibility(TableRow.VISIBLE);
                    }
                    if(Integer.parseInt(doses)>1){
                        sdose_row.setVisibility(TableRow.VISIBLE);
                    }
                    if(Integer.parseInt(doses)>0){
                        fdose_row.setVisibility(TableRow.VISIBLE);
                    }
                }
            }
        });
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.submit) {
                if(check()){
                    saveData();
                }
            } else if (view.getId() == in_date.getId()) {
                new CalenderDialog(DCActivity.this,DCActivity.this::getDate,in_date.getText().toString(),"Interview Date",in_date).show();
            } else if (view.getId()==dob.getId()) {
                new CalenderDialog(DCActivity.this,DCActivity.this::getDate,dob.getText().toString(),"Date of Birth",dob).show();
            } else if (view.getId()==sample.getId()) {
                new CalenderDialog(DCActivity.this,DCActivity.this::getDate,sample.getText().toString(),"Date of sample collection",sample).show();
            } else if (view.getId()==admission.getId()) {
                new CalenderDialog(DCActivity.this,DCActivity.this::getDate,admission.getText().toString(),"Admission Date",admission).show();
            } else if (view.getId() == fdose.getId()) {
                new CalenderDialog(DCActivity.this,DCActivity.this::getDate,fdose.getText().toString(),"First Dose",fdose).show();
            } else if (view.getId()==sdose.getId()) {
                new CalenderDialog(DCActivity.this,DCActivity.this::getDate,sdose.getText().toString(),"Second Dose",sdose).show();
            } else if (view.getId()==tdose.getId()) {
                new CalenderDialog(DCActivity.this,DCActivity.this::getDate,tdose.getText().toString(),"Third Dose",tdose).show();
            }
        }
    };
    private boolean check(){
        boolean is_ok = true;
        if (in_date.getText().toString().isEmpty()){
            is_ok = false;
            Util.makeToast(DCActivity.this, "Interview Date is required");
        }
        if (screen_id.getText().toString().isEmpty()){
            is_ok = false;
            Util.makeToast(DCActivity.this, "Screen ID is required");
        }
        if (dc_name.getText().toString().isEmpty()){
            is_ok = false;
            Util.makeToast(DCActivity.this, "Data collectors name is required");
        }
        if(less5.getSelectedItemPosition()==0){
            is_ok = false;
            Util.makeToast(DCActivity.this, "Less than 5 years is required");
        }
        if (is_gastro.getSelectedItemPosition()==0){
            is_ok = false;
            Util.makeToast(DCActivity.this, "Gastro is required");
        }
        if (parent_av.getSelectedItemPosition()==0){
            is_ok = false;
            Util.makeToast(DCActivity.this, "Parent willingness is required");
        }
        if (gastro7.getSelectedItemPosition()==0){
            is_ok = false;
            Util.makeToast(DCActivity.this, "Onset of Gastro is required");
        }
        if(bf_symptom.getSelectedItemPosition()==0){
            is_ok = false;
            Util.makeToast(DCActivity.this, "Admission before symptom is required");
        }
        if (healthy.getSelectedItemPosition()==0){
            is_ok = false;
            Util.makeToast(DCActivity.this, "Healthy is required");
        }
        else if (healthy.getSelectedItemPosition()==2){
            if (health_reason_oth.getText().toString().isEmpty()){
                is_ok = false;
                Util.makeToast(DCActivity.this, "Unhealthy reason is required");
            }
        }
        if (evaluation.getSelectedItemPosition()==0){
            is_ok = false;
            Util.makeToast(DCActivity.this, "Evaluation is required");
        } else if (evaluation.getSelectedItemPosition()==2) {
            if(r_reason.getSelectedItemPosition()==0){
                is_ok = false;
                Util.makeToast(DCActivity.this, "Reason is required");
            } else if (r_reason.getSelectedItemPosition()==5 && r_reason_oth.getText().toString().isEmpty()) {
                is_ok = false;
                Util.makeToast(DCActivity.this, "Specify reason is required");
            }
        }
        if(is_accept.getSelectedItemPosition()==0){
            is_ok = false;
            Util.makeToast(DCActivity.this, "Acceptance is required");
        }else if(is_accept.getSelectedItemPosition()==1){
            if (enrollment_id.getText().toString().isEmpty()){
                is_ok = false;
                Util.makeToast(DCActivity.this, "Enrollment ID is required");
            }
            if (sample.getText().toString().isEmpty()){
                is_ok = false;
                Util.makeToast(DCActivity.this, "Date of sample collection is required");
            }
        }
        if (name.getText().toString().isEmpty()){
            is_ok = false;
            Util.makeToast(DCActivity.this, "Name is required");
        }
        if (dob.getText().toString().isEmpty()){
            is_ok = false;
            Util.makeToast(DCActivity.this, "Date of Birth is required");
        }
        if (sex.getSelectedItemPosition()==0){
            is_ok = false;
            Util.makeToast(DCActivity.this, "Sex is required");
        }
        if (admission.getText().toString().isEmpty()){
            is_ok = false;
            Util.makeToast(DCActivity.this, "Admission Date is required");
        }
        if (mobile.getText().toString().isEmpty() || mobile.getText().toString().length()!=11){
            is_ok = false;
            Util.makeToast(DCActivity.this, "Mobile is required");
        }

        if(is_vaccinated.getSelectedItemPosition()==1) {
            if (vac_info.getSelectedItemPosition()==0){
                is_ok = false;
                Util.makeToast(DCActivity.this, "Vaccine Info is required");
            }
            if ( vac_info.getSelectedItemPosition()==3 && vac_info_oth.getText().toString().isEmpty()) {
                is_ok = false;
                Util.makeToast(DCActivity.this, "Vaccine Info is required");
            }
            if (vaccine_name.getSelectedItemPosition()==0) {
                is_ok = false;
                Util.makeToast(DCActivity.this, "Vaccine Name is required");
            }
            if (vaccine_doses.getText().toString().isEmpty()) {
                is_ok = false;
                Util.makeToast(DCActivity.this, "Vaccine doses is required");
            }else {
                if (Integer.parseInt(vaccine_doses.getText().toString())>0 && fdose.getText().toString().isEmpty()) {
                    is_ok = false;
                    Util.makeToast(DCActivity.this, "First Dose is required");
                }
                if (Integer.parseInt(vaccine_doses.getText().toString())>1 && sdose.getText().toString().isEmpty()) {
                    is_ok = false;
                    Util.makeToast(DCActivity.this, "Second Dose is required");
                }
                if (Integer.parseInt(vaccine_doses.getText().toString())>2 && tdose.getText().toString().isEmpty()) {
                    is_ok = false;
                    Util.makeToast(DCActivity.this, "Third Dose is required");
                }
            }
        }
        return is_ok;
    }
    @Override
    public void getDate(String date, TextView editText) {
        editText.setText(date);
    }
    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            screen_id.clearFocus();
            dc_name.clearFocus();
            health_reason_oth.clearFocus();
            r_reason_oth.clearFocus();
            name.clearFocus();
            mobile.clearFocus();
            address.clearFocus();
            enrollment_id.clearFocus();
            vac_info_oth.clearFocus();
            vaccine_doses.clearFocus();
            if (view!=null){
                if (view.getParent() == div) {
                    int id = myDB.getId(div.getSelectedItem().toString(), 1);
                    setDis(id, MDC.dis);
                } else if (view.getParent() == dis) {
                    int id = myDB.getId(dis.getSelectedItem().toString(), 2);
                    setUpz(id, MDC.upz);
                } else if (view.getParent() == upz) {
                    int id = myDB.getId(upz.getSelectedItem().toString(), 3);
                    Log.d("upz", ": "+id);
                    setUn(id, MDC.un);
                }
                else if (view.getParent() == is_vaccinated){
                    if(i==1) vaccinated_card.setVisibility(View.VISIBLE);
                    else vaccinated_card.setVisibility(View.GONE);
                } else if (view.getParent()==healthy) {
                    if (i==2) health_reason_oth_row.setVisibility(View.VISIBLE);
                    else health_reason_oth_row.setVisibility(View.GONE);
                } else if (view.getParent()==evaluation) {
                    if (i==2) {
                        r_reason_row.setVisibility(View.VISIBLE);
                    }
                    else {
                        r_reason_row.setVisibility(View.GONE);
                        r_reason_oth_row.setVisibility(View.GONE);
                    }
                } else if (view.getParent()==r_reason) {
                    if (i==5) r_reason_oth_row.setVisibility(View.VISIBLE);
                    else r_reason_oth_row.setVisibility(View.GONE);
                } else if (view.getParent() == is_accept) {
                    if (i==1) accepted_card.setVisibility(View.VISIBLE);
                    else accepted_card.setVisibility(View.GONE);
                }  else if (view.getParent() == vac_info) {
                    if (i==3) vac_info_oth_row.setVisibility(View.VISIBLE);
                    else vac_info_oth_row.setVisibility(View.GONE);
                }
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
    private void setDiv(int selectedValue){
        int i=1,selectedIndex=0;
        ArrayList<String> divs = new ArrayList<>();
        divs.add("Please select");
        Cursor c = myDB.getDiv();
        if (c.moveToFirst()){
            do {
                divs.add(c.getString(2));
                if (selectedValue==c.getInt(0)){
                    selectedIndex=i;
                }
                i++;
            }while (c.moveToNext());
        }
        div.setAdapter(new ArrayAdapter<String>(DCActivity.this, android.R.layout.simple_spinner_dropdown_item,divs));
        if (selectedIndex>0){
            div.setSelection(selectedIndex);
        }
    }
    private void setDis(int parentIndex,int selectedValue){
        int i=1,selectedIndex=0;
        ArrayList<String> diss = new ArrayList<>();
        diss.add("Please select");
        Cursor c = myDB.getDis(parentIndex);
        if (c.moveToFirst()){
            do {
                diss.add(c.getString(2));
                if (selectedValue==c.getInt(0)){
                    selectedIndex=i;
                }
                i++;
            }while (c.moveToNext());
        }
        dis.setAdapter(new ArrayAdapter<String>(DCActivity.this, android.R.layout.simple_spinner_dropdown_item,diss));
        if (selectedIndex>0){
            dis.setSelection(selectedIndex);
        }
    }
    private void setUpz(int parentIndex,int selectedValue){
        int i=1,selectedIndex=0;
        ArrayList<String> upzs = new ArrayList<>();
        upzs.add("Please select");
        Cursor c = myDB.getUpz(parentIndex);
        if (c.moveToFirst()){
            do {
                upzs.add(c.getString(2));
                if (selectedValue==c.getInt(0)){
                    selectedIndex=i;
                }
                i++;
            }while (c.moveToNext());
        }
        upz.setAdapter(new ArrayAdapter<String>(DCActivity.this, android.R.layout.simple_spinner_dropdown_item,upzs));
        if (selectedIndex>0){
            upz.setSelection(selectedIndex);
        }
    }
    private void setUn(int parentIndex,int selectedValue){
        int i=1,selectedIndex=0;
        ArrayList<String> uns = new ArrayList<>();
        uns.add("Please select");
        Cursor c = myDB.getUn(parentIndex);
        if (c.moveToFirst()){
            do {
                uns.add(c.getString(2));
                if (selectedValue==c.getInt(0)){
                    selectedIndex=i;
                }
                i++;
            }while (c.moveToNext());
        }
        un.setAdapter(new ArrayAdapter<String>(DCActivity.this, android.R.layout.simple_spinner_dropdown_item,uns));
        if (selectedIndex>0){
            un.setSelection(selectedIndex);
        }
    }
    private void saveData(){
        ContentValues cv = new ContentValues();
        cv.put("id",String.valueOf(MDC.id));
        cv.put("in_date",in_date.getText().toString());
        cv.put("hos_name",hos_name.getText().toString());
        cv.put("hos_id",hos_id.getText().toString());
        cv.put("dob",dob.getText().toString());
        cv.put("admission",admission.getText().toString());
        cv.put("sample",sample.getText().toString());
        cv.put("screen_id",screen_id.getText().toString());
        cv.put("dc_name",dc_name.getText().toString());
        cv.put("health_reason_oth",health_reason_oth.getText().toString());
        cv.put("r_reason_oth",r_reason_oth.getText().toString());
        cv.put("name",name.getText().toString());
        cv.put("mobile",mobile.getText().toString());
        cv.put("address",address.getText().toString());
        cv.put("enrollment_id",enrollment_id.getText().toString());
        cv.put("vac_info_oth",vac_info_oth.getText().toString());
        cv.put("vaccine_name",vaccine_name.getSelectedItemPosition());
        if (vaccine_doses.getText().toString().equals("")) cv.put("vaccine_doses","0");
        else cv.put("vaccine_doses",vaccine_doses.getText().toString());
        cv.put("fdose",fdose.getText().toString());
        cv.put("sdose",sdose.getText().toString());
        cv.put("tdose",tdose.getText().toString());
        cv.put("less5",String.valueOf(less5.getSelectedItemPosition()));
        cv.put("is_gastro",String.valueOf(is_gastro.getSelectedItemPosition()));
        cv.put("parent_av",String.valueOf(parent_av.getSelectedItemPosition()));
        cv.put("gastro7",String.valueOf(gastro7.getSelectedItemPosition()));
        cv.put("bf_symptom",String.valueOf(bf_symptom.getSelectedItemPosition()));
        cv.put("healthy",String.valueOf(healthy.getSelectedItemPosition()));
        cv.put("evaluation",String.valueOf(evaluation.getSelectedItemPosition()));
        cv.put("r_reason",String.valueOf(r_reason.getSelectedItemPosition()));
        cv.put("sex",String.valueOf(sex.getSelectedItemPosition()));
        cv.put("div",myDB.getId(div.getSelectedItem().toString(),1));
        cv.put("dis",myDB.getId(dis.getSelectedItem().toString(),2));
        cv.put("upz",myDB.getId(upz.getSelectedItem().toString(),3));
        cv.put("un",myDB.getId(un.getSelectedItem().toString(),4));
        cv.put("is_accept",String.valueOf(is_accept.getSelectedItemPosition()));
        cv.put("is_vaccinated",String.valueOf(is_vaccinated.getSelectedItemPosition()));
        cv.put("vac_info",String.valueOf(vac_info.getSelectedItemPosition()));
        myDB.insertScreening(cv);
        finish();
    }
}