package accuratesoft.shakawat.rotadatacollection.dialogs;

import static accuratesoft.shakawat.rotadatacollection.R.id.title;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import accuratesoft.shakawat.rotadatacollection.R;
import accuratesoft.shakawat.rotadatacollection.interfaces.CalenderInterface;

public class CalenderDialog extends Dialog {
    CalenderInterface calenderInterface;
    int day,mth,yr;
    DatePicker datePicker;
    Button btn;
    String title_name;
    TextView title,editText;
    public CalenderDialog(@NonNull Context context, CalenderInterface calenderInterface, String date, String name, TextView editText) {
        super(context);
        this.calenderInterface = calenderInterface;
        this.title_name = name;
        this.editText = editText;
        if(date.isEmpty() || date=="null"){
            Date dt = new Date();
//      System.out.println(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            day = cal.get(Calendar.DAY_OF_MONTH);
            mth = cal.get(Calendar.MONTH);
            yr = cal.get(Calendar.YEAR);
        }else{
            SimpleDateFormat spec_format = new SimpleDateFormat("yyyy-MM-dd");
            try{
                Date dt = spec_format.parse(date);
//          System.out.println(date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(dt);
                day = cal.get(Calendar.DAY_OF_MONTH);
                mth = cal.get(Calendar.MONTH);
                yr = cal.get(Calendar.YEAR);
            }catch (ParseException parseException){

            }
        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cld_view);
        title = (TextView) findViewById(R.id.title);
        title.setText(title_name);
        datePicker = (DatePicker) findViewById(R.id.cald);
        datePicker.setMaxDate(new Date().getTime());
        datePicker.init(this.yr,this.mth,this.day,null);
        btn = (Button) findViewById(R.id.submit);
        btn.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String dat = String.valueOf(datePicker.getYear())+"-"+
                    String.valueOf(datePicker.getMonth()+1)+"-"+String.valueOf(datePicker.getDayOfMonth());
            calenderInterface.getDate(dat,editText);
            dismiss();
        }
    };
}
