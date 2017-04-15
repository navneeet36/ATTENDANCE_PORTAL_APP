package com.example.hp.attendamce_portal.Adapers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.pojo.List;

import java.util.ArrayList;

/**
 * Created by hp on 14-Apr-17.
 */

public class Adapter  extends RecyclerView.Adapter<Adapter.CustomHolder> {

    Context mContext;
    LayoutInflater inflater;
    View view;
    ArrayList<List> items;

    public Adapter(Context activity, ArrayList<List> arrayList) {
        this.mContext = activity;
        inflater = LayoutInflater.from(mContext);
        this.items = arrayList;
    }


    @Override
    public Adapter.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.item, parent, false);//is layout ka n
        return new Adapter.CustomHolder(view);
    }




    @Override
    public void onBindViewHolder(final Adapter.CustomHolder holder, final int position) {
        List item = items.get(position);
        holder.subjectid.setText(item.getSubjectid());

    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {


        TextView subjectid;

        public CustomHolder(View itemView) {
            super(itemView);
            subjectid=(TextView)itemView.findViewById(R.id.subjectid);


        }

    }
}


