package com.example.hp.attendamce_portal.Adapers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.pojo.FacultyList;

import java.util.ArrayList;

/**
 * Created by hp on 02-Apr-17.
 */
public class FacListAdapter  extends RecyclerView.Adapter<FacListAdapter.CustomHolder> {

    Context mContext;
    LayoutInflater inflater;
    View view;
    ArrayList<FacultyList> items;



    public FacListAdapter(Context activity, ArrayList<FacultyList> arrayList) {
        this.mContext = activity;
        inflater = LayoutInflater.from(mContext);
        this.items = arrayList;
    }


    @Override
    public FacListAdapter.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.faclistitem, parent, false);//is layout ka n
        return new FacListAdapter.CustomHolder(view);
    }




    @Override
    public void onBindViewHolder(final FacListAdapter.CustomHolder holder, final int position) {
        FacultyList item = items.get(position);
        holder.facultyid.setText(item.getFacultyid());
        holder.facultyname.setText(item.getFacultyname());

    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {


        TextView facultyid;
        TextView facultyname;


        public CustomHolder(View itemView) {
            super(itemView);
            facultyid=(TextView)itemView.findViewById(R.id.facultyid);
            facultyname = (TextView) itemView.findViewById(R.id.facultyname);



        }

    }
}




