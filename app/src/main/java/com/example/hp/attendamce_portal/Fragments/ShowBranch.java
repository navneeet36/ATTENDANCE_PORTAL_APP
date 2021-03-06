package com.example.hp.attendamce_portal.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hp.attendamce_portal.Activities.ManageBranch;
import com.example.hp.attendamce_portal.Adapers.ListAdapter;
import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.Utils.DividerItemDecoration;
import com.example.hp.attendamce_portal.Utils.RequestCodes;
import com.example.hp.attendamce_portal.Utils.URL_API;
import com.example.hp.attendamce_portal.Utils.VolleyHelper;
import com.example.hp.attendamce_portal.pojo.BeanBranchInfo;
import com.example.hp.attendamce_portal.pojo.BranchList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ShowBranch extends BaseFragment
{
    ManageBranch mainActivity;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<BeanBranchInfo> list = new ArrayList<>();
    ListAdapter listAdapter;
    public static ShowBranch newInstance(int sectionNumber) {
        ShowBranch fragment = new ShowBranch();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (ManageBranch) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_show_branch, container, false);
        SwipeRefreshLayout mSwipe = (SwipeRefreshLayout)v.findViewById(R.id.swipe);
 //       mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
   //         @Override
    //        public void onRefresh() {
      //          do
        //    }
        //});
        recyclerView = (RecyclerView)v. findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
       DividerItemDecoration mDividerItemDecoration =new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    recyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
        HashMap<String, String> hashMap = new HashMap<String, String>();

        VolleyHelper.postRequestVolley(getActivity(), ShowBranch.this, URL_API.ShowBranch, hashMap, RequestCodes.ShowBranch, false);

        return v;

    }
    @Override
    public void requestStarted(int requestCode) {
        super.requestStarted(requestCode);
        if (mainActivity != null)
            mainActivity.showDialog();
    }

    @Override
    public void requestEndedWithError(int requestCode, VolleyError error) {
        super.requestEndedWithError(requestCode, error);
        if (mainActivity != null)
            mainActivity.dismissDialog();

    }

    @Override
    public void requestCompleted(int requestCode, String response) {
        super.requestCompleted(requestCode, response);
        if(mainActivity!=null)
            mainActivity.dismissDialog();
        try {
            JSONObject jsonObject = new JSONObject(response);

            int i = jsonObject.getInt("success");
            if (i == 1) {


                    list = new Gson().fromJson(jsonObject.get("branch_list").toString(), new TypeToken<ArrayList<BeanBranchInfo>>() {
                    }.getType());
                ArrayList<BranchList> arrayList = new ArrayList<>();
                for(BeanBranchInfo b:list)
                {
                    arrayList.add(new BranchList(b.getBranchID(),b.getBranchName(),"Total Sem:"+ b.getTotalSem()));
                }

                 listAdapter = new ListAdapter(getContext(), arrayList);
                recyclerView.setAdapter(listAdapter);

                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

            } else
                Snackbar.make(getView(), jsonObject.getString("message"), Snackbar.LENGTH_LONG).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}