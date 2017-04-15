package com.example.hp.attendamce_portal.Adapers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.pojo.BranchList;

import java.util.ArrayList;




public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CustomHolder> {

    Context mContext;
    LayoutInflater inflater;
    View view;
    ArrayList<BranchList> items;

    public ListAdapter(Context activity, ArrayList<BranchList> arrayList) {
        this.mContext = activity;
        inflater = LayoutInflater.from(mContext);
        this.items = arrayList;
    }


    @Override
    public ListAdapter.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.brlistitem, parent, false);
        return new CustomHolder(view);
    }

    /*
    This method is written to open or close a particular section, by passing the position of the element
    and then calling notifydataSetchanged to make the changes visible
     */


    @Override
    public void onBindViewHolder(final ListAdapter.CustomHolder holder, final int position) {
        BranchList item = items.get(position);
        holder.branchid.setText(item.getBranchid());
        holder.branchname.setText(item.getBranchname());
        holder.totalsem.setText(item.getTotalsem());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {


        TextView branchid;
        TextView branchname;
        TextView totalsem;

        public CustomHolder(View itemView) {
            super(itemView);
            branchid=(TextView)itemView.findViewById(R.id.branchid);
            branchname = (TextView) itemView.findViewById(R.id.branchname);

            totalsem = (TextView) itemView.findViewById(R.id.totalsem);


        }

    }
}




