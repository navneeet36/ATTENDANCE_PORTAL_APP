package com.example.hp.attendamce_portal.Adapers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.pojo.StudentList;

import java.util.ArrayList;

/**
 * Created by hp on 02-Apr-17.
 */

public class StuListAdapter  extends RecyclerView.Adapter<StuListAdapter.CustomHolder> {

    Context mContext;
    LayoutInflater inflater;
    View view;
    ArrayList<StudentList> items;

    public StuListAdapter(Context activity, ArrayList<StudentList> arrayList) {
        this.mContext = activity;
        inflater = LayoutInflater.from(mContext);
        this.items = arrayList;
    }


    @Override
    public StuListAdapter.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.stulistitem, parent, false);
        return new StuListAdapter.CustomHolder(view);
    }




    @Override
    public void onBindViewHolder(final StuListAdapter.CustomHolder holder, final int position) {
        StudentList item = items.get(position);
        holder.rollno.setText(item.getRollno());
        holder.name.setText(item.getName());
        holder.branchid.setText(item.getBranchid());

    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {


        TextView rollno;
        TextView name;
        TextView branchid;

        public CustomHolder(View itemView) {
            super(itemView);
            rollno=(TextView)itemView.findViewById(R.id.rollno);
            name = (TextView) itemView.findViewById(R.id.name);
            branchid=(TextView)itemView.findViewById(R.id.branchid);



        }

    }
}





