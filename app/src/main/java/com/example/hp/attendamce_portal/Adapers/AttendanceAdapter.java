package com.example.hp.attendamce_portal.Adapers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.pojo.AttendanceList;
import com.example.hp.attendamce_portal.pojo.BeanAttendance;

import java.util.ArrayList;


/**
 * Created by hp on 11-Apr-17.
 */

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.CustomHolder> {
    Context mContext;
    LayoutInflater inflater;
    View view;
    ArrayList<BeanAttendance> items;
    OnAttendanceClickListener onAttendanceClickListener;

    public AttendanceAdapter(Context activity, ArrayList<BeanAttendance> arrayList) {
        this.mContext = activity;
        inflater = LayoutInflater.from(mContext);
        this.items = arrayList;
    }


    @Override
    public AttendanceAdapter.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.attendanceitem, parent, false);
        return new AttendanceAdapter.CustomHolder(view);
    }




    @Override
    public void onBindViewHolder(final AttendanceAdapter.CustomHolder holder, final int position) {
        final BeanAttendance item = items.get(position);
        holder.rollno.setText(item.getRollNo());
        holder.ispresent.setText(item.getIsPresent().equals("yes")?"Present":"Absent");
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onAttendanceClickListener!=null)
                    onAttendanceClickListener.onClick(item);
            }
        });

    }
    public interface OnAttendanceClickListener{
        void onClick(BeanAttendance beanAttendance);
    }

    public void setOnAttendanceClickListener(OnAttendanceClickListener onAttendanceClickListener) {
        this.onAttendanceClickListener = onAttendanceClickListener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {


        TextView rollno;
        TextView ispresent;
        View row;

        public CustomHolder(View itemView) {
            super(itemView);
            rollno=(TextView)itemView.findViewById(R.id.rollno);
            ispresent = (TextView) itemView.findViewById(R.id.ispresent);
            row=itemView;


        }

    }
}


