package accuratesoft.shakawat.rotadatacollection.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import accuratesoft.shakawat.rotadatacollection.OutcomeActivity;
import accuratesoft.shakawat.rotadatacollection.OutcomeListActivity;
import accuratesoft.shakawat.rotadatacollection.R;
import accuratesoft.shakawat.rotadatacollection.models.MOutcomeList;

public class OutcomeRecAdapter extends RecyclerView.Adapter<OutcomeRecAdapter.MyViewHolder> {
    Context context;
    RecyclerView recyclerView;
    OutcomeListActivity outcomeActivity;
    ArrayList<MOutcomeList> arrayList;

    public OutcomeRecAdapter(Context context, RecyclerView recyclerView, OutcomeListActivity outcomeActivity, ArrayList<MOutcomeList> arrayList) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.outcomeActivity = outcomeActivity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.custom_list,null);
        view.setOnClickListener(clickListener);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MOutcomeList moutcome = arrayList.get(position);
        holder.crt_at.setText("Interview date: "+moutcome.in_date);
        holder.mob.setText(moutcome.mobile);
        holder.name.setText(moutcome.name);
        holder.uni_id.setText(moutcome.enrollment_id);
        holder.sex.setText(moutcome.screen_id);
        holder.age.setVisibility(View.GONE);
        holder.enroll.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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
        @Override
        public void onClick(View v) {
            int pos = recyclerView.getChildLayoutPosition(v);
            Intent intent = new Intent(context, OutcomeActivity.class);
            intent.putExtra("id",arrayList.get(pos).id);
            intent.putExtra("enrollment_id",arrayList.get(pos).enrollment_id);
            context.startActivity(intent);
        }
    };
}
