package com.example.hp.attendamce_portal.Adapers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.pojo.SubjectList;


import java.util.ArrayList;

/**
 * Created by hp on 02-Apr-17.
 */

public class SubListAdapter  extends RecyclerView.Adapter<SubListAdapter.CustomHolder> {

    Context mContext;
    LayoutInflater inflater;
    View view;
    ArrayList<SubjectList> items;

    public SubListAdapter(Context activity, ArrayList<SubjectList> arrayList) {
        this.mContext = activity;
        inflater = LayoutInflater.from(mContext);
        this.items = arrayList;
    }


    @Override
    public SubListAdapter.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.sublistitem, parent, false);//is layout ka n
        return new SubListAdapter.CustomHolder(view);
    }




    @Override
    public void onBindViewHolder(final SubListAdapter.CustomHolder holder, final int position) {
        SubjectList item = items.get(position);
        holder.subjectid.setText(item.getSubjectid());
        holder.subjectname.setText(item.getSubjectname());
        holder.branchid.setText(item.getBranchid());
        holder.semno.setText(item.getSemno());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {


        TextView subjectid;
        TextView subjectname;
        TextView branchid;
        TextView semno;

        public CustomHolder(View itemView) {
            super(itemView);
            subjectid=(TextView)itemView.findViewById(R.id.subjectid);
            subjectname = (TextView) itemView.findViewById(R.id.subjectname);
            branchid=(TextView)itemView.findViewById(R.id.branchid);
            semno = (TextView) itemView.findViewById(R.id.semno);


        }

    }
}



