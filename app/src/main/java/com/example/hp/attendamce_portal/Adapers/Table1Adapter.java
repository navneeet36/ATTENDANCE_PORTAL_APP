package com.example.hp.attendamce_portal.Adapers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.pojo.Table1List;

import java.util.ArrayList;

/**
 * Created by hp on 16-Apr-17.
 */

public class Table1Adapter extends RecyclerView.Adapter<Table1Adapter.CustomHolder> {
    Context mContext;
    LayoutInflater inflater;
    View view;
    ArrayList<Table1List> items;



    public Table1Adapter(Context activity, ArrayList<Table1List> arrayList) {
        this.mContext = activity;
        inflater = LayoutInflater.from(mContext);
        this.items = arrayList;
    }


    @Override
    public Table1Adapter.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.attendanceitem, parent, false);
        return new Table1Adapter.CustomHolder(view);
    }




    @Override
    public void onBindViewHolder(final Table1Adapter.CustomHolder holder, final int position) {
        Table1List item = items.get(position);
        holder.date.setText(item.getDate());
        holder.ispresent.setText(item.getIspresent());


    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {


        TextView date;
        TextView ispresent;


        public CustomHolder(View itemView) {
            super(itemView);
            date=(TextView)itemView.findViewById(R.id.rollno);
            ispresent = (TextView) itemView.findViewById(R.id.ispresent);




        }

    }
}




