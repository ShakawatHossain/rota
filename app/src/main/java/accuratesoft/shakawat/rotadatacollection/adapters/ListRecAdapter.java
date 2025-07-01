package accuratesoft.shakawat.rotadatacollection.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import accuratesoft.shakawat.rotadatacollection.DCActivity;
import accuratesoft.shakawat.rotadatacollection.EnrolledActivity;
import accuratesoft.shakawat.rotadatacollection.ListActivity;
import accuratesoft.shakawat.rotadatacollection.R;
import accuratesoft.shakawat.rotadatacollection.models.MDC;

public class ListRecAdapter extends RecyclerView.Adapter<ListRecAdapter.MyViewHolder> {
    Context context;
    ListActivity listActivity;
    RecyclerView recyclerView;
    Cursor cursor;
    public ListRecAdapter(Context context,ListActivity listActivity, RecyclerView recyclerView, Cursor cursor){
        this.context = context;
        this.listActivity = listActivity;
        this.recyclerView = recyclerView;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.custom_list,null);
        view.setOnClickListener(clickListener);
        return new MyViewHolder(view);
    }

    @SuppressLint("Range")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(cursor.moveToPosition(position)) {
            holder.uni_id.setText(cursor.getString(cursor.getColumnIndex("screen_id")));
            holder.name.setText(cursor.getString(cursor.getColumnIndex("name")));
            holder.mob.setText(cursor.getString(cursor.getColumnIndex("mobile")));
            String s="";
            if(cursor.getInt(cursor.getColumnIndex("sex"))==1){
                s="Male";
            } else if(cursor.getInt(cursor.getColumnIndex("sex"))==2){
                s="Female";
            }
            holder.sex.setText(s);
            holder.age.setText("Dob: "+cursor.getString(cursor.getColumnIndex("dob")));
            holder.crt_at.setText("Screen Date: "+cursor.getString(cursor.getColumnIndex("in_date")));
            if (cursor.getInt(cursor.getColumnIndex("is_accept"))!=1){
                holder.enroll.setVisibility(View.GONE);
            }else{
                holder.enroll.setText("Enrollment : "+cursor.getString(cursor.getColumnIndex("enrollment_id")));
                holder.enroll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                    todo
                        cursor.moveToPosition(position);
                        Intent intent = new Intent(context, EnrolledActivity.class);
                        intent.putExtra("screen_id",cursor.getInt(cursor.getColumnIndex("id")));
                        intent.putExtra("enrollment_id",cursor.getString(cursor.getColumnIndex("enrollment_id")));
                        intent.putExtra("in_date",cursor.getString(cursor.getColumnIndex("in_date")));
                        context.startActivity(intent);
                    }
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView uni_id,name,mob,age,sex,crt_at;
        Button enroll;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            uni_id = (TextView) itemView.findViewById(R.id.uni_id);
            name = (TextView) itemView.findViewById(R.id.name);
            mob = (TextView) itemView.findViewById(R.id.mob);
            sex= (TextView) itemView.findViewById(R.id.sex);
            age = (TextView) itemView.findViewById(R.id.age);
            crt_at = (TextView) itemView.findViewById(R.id.crt_at);
            enroll = (Button) itemView.findViewById(R.id.enroll);
        }
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @SuppressLint("Range")
        @Override
        public void onClick(View view) {
            int pos = recyclerView.getChildLayoutPosition(view);
            if (cursor.moveToPosition(pos)){
                MDC.in_date = cursor.getString(cursor.getColumnIndex("in_date"));
                MDC.dob = cursor.getString(cursor.getColumnIndex("dob"));
                MDC.admission = cursor.getString(cursor.getColumnIndex("admission"));
                MDC.sample = cursor.getString(cursor.getColumnIndex("sample"));
                MDC.screen_id = cursor.getString(cursor.getColumnIndex("screen_id"));
                MDC.dc_name = cursor.getString(cursor.getColumnIndex("dc_name"));
                MDC.health_reason_oth = cursor.getString(cursor.getColumnIndex("health_reason_oth"));
                MDC.r_reason_oth = cursor.getString(cursor.getColumnIndex("r_reason_oth"));
                MDC.name = cursor.getString(cursor.getColumnIndex("name"));
                MDC.mobile = cursor.getString(cursor.getColumnIndex("mobile"));
                MDC.address = cursor.getString(cursor.getColumnIndex("address"));
                MDC.enrollment_id = cursor.getString(cursor.getColumnIndex("enrollment_id"));
                MDC.vac_info_oth = cursor.getString(cursor.getColumnIndex("vac_info_oth"));
                MDC.vaccine_name = cursor.getInt(cursor.getColumnIndex("vaccine_name"));
                MDC.fdose = cursor.getString(cursor.getColumnIndex("fdose"));
                MDC.sdose = cursor.getString(cursor.getColumnIndex("sdose"));
                MDC.tdose = cursor.getString(cursor.getColumnIndex("tdose"));
                MDC.id = cursor.getInt(cursor.getColumnIndex("id"));
                MDC.vaccine_doses = cursor.getInt(cursor.getColumnIndex("vaccine_doses"));
                MDC.less5 = cursor.getInt(cursor.getColumnIndex("less5"));
                MDC.is_gastro = cursor.getInt(cursor.getColumnIndex("is_gastro"));
                MDC.parent_av = cursor.getInt(cursor.getColumnIndex("parent_av"));
                MDC.gastro7 = cursor.getInt(cursor.getColumnIndex("gastro7"));
                MDC.bf_symptom = cursor.getInt(cursor.getColumnIndex("bf_symptom"));
                MDC.healthy = cursor.getInt(cursor.getColumnIndex("healthy"));
                MDC.evaluation = cursor.getInt(cursor.getColumnIndex("evaluation"));
                MDC.r_reason = cursor.getInt(cursor.getColumnIndex("r_reason"));
                MDC.sex = cursor.getInt(cursor.getColumnIndex("sex"));
                MDC.div = cursor.getInt(cursor.getColumnIndex("div"));
                MDC.dis = cursor.getInt(cursor.getColumnIndex("dis"));
                MDC.upz = cursor.getInt(cursor.getColumnIndex("upz"));
                MDC.un = cursor.getInt(cursor.getColumnIndex("un"));
                MDC.is_accept = cursor.getInt(cursor.getColumnIndex("is_accept"));
                MDC.is_vaccinated = cursor.getInt(cursor.getColumnIndex("is_vaccinated"));
                MDC.vac_info = cursor.getInt(cursor.getColumnIndex("vac_info"));
                context.startActivity(new Intent(context, DCActivity.class));
            }
        }
    };
}
