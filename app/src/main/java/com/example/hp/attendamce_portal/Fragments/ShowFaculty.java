package com.example.hp.attendamce_portal.Fragments;

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

import com.example.hp.attendamce_portal.Adapers.FacListAdapter;
import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.Utils.DividerItemDecoration;
import com.example.hp.attendamce_portal.Utils.RequestCodes;
import com.example.hp.attendamce_portal.Utils.URL_API;
import com.example.hp.attendamce_portal.Utils.VolleyHelper;
import com.example.hp.attendamce_portal.pojo.BeanFacultyInfo;
import com.example.hp.attendamce_portal.pojo.FacultyList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ShowFaculty extends BaseFragment {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<BeanFacultyInfo> list = new ArrayList<>();
    FacListAdapter listAdapter;


    public static ShowFaculty newInstance(int sectionNumber) {
        ShowFaculty fragment = new ShowFaculty();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_show_faculty, container, false);
        SwipeRefreshLayout mSwipe = (SwipeRefreshLayout)v.findViewById(R.id.swipe);
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

        VolleyHelper.postRequestVolley(getActivity(), ShowFaculty.this, URL_API.ShowFaculty, hashMap, RequestCodes.ShowFaculty, false);

        return v;

    }
    @Override
    public void requestStarted(int requestCode) {
        super.requestStarted(requestCode);

    }

    @Override
    public void requestCompleted(int requestCode, String response) {
        super.requestCompleted(requestCode, response);
        try {
            JSONObject jsonObject = new JSONObject(response);

            int i = jsonObject.getInt("success");
            if (i == 1) {


                list = new Gson().fromJson(jsonObject.get("faculty_list").toString(), new TypeToken<ArrayList<BeanFacultyInfo>>() {
                }.getType());
                ArrayList<FacultyList> arrayList = new ArrayList<>();
                for(BeanFacultyInfo b:list)
                {
                    arrayList.add(new FacultyList(b.getFacultyID(),b.getFacultyName()));
                }

                listAdapter = new FacListAdapter(getContext(), arrayList);
                recyclerView.setAdapter(listAdapter);

                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

            } else
                Snackbar.make(getView(), jsonObject.getString("message"), Snackbar.LENGTH_LONG).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}


