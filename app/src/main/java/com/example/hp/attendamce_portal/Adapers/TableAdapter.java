package com.example.hp.attendamce_portal.Adapers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.pojo.TableList;

import java.util.ArrayList;

/**
 * Created by hp on 13-Apr-17.
 */

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.CustomHolder> {
    Context mContext;
    LayoutInflater inflater;
    View view;
    ArrayList<TableList> items;



    public TableAdapter(Context activity, ArrayList<TableList> arrayList) {
        this.mContext = activity;
        inflater = LayoutInflater.from(mContext);
        this.items = arrayList;
    }


    @Override
    public TableAdapter.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.tableitem, parent, false);
        return new TableAdapter.CustomHolder(view);
    }




    @Override
    public void onBindViewHolder(final TableAdapter.CustomHolder holder, final int position) {
        TableList item = items.get(position);
        holder.totallec.setText(item.getTotalLecture());
        holder.ispresent.setText(item.getIsPresent());
        holder.subjectid.setText(item.getSubjectId());

    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {


        TextView totallec,subjectid;
        TextView ispresent;


        public CustomHolder(View itemView) {
            super(itemView);
            totallec=(TextView)itemView.findViewById(R.id.totallec);
            ispresent = (TextView) itemView.findViewById(R.id.ispresent);
            subjectid=(TextView)itemView.findViewById(R.id.subjectid);



        }

    }
}



