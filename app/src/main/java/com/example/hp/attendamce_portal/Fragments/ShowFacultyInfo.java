package com.example.hp.attendamce_portal.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.hp.attendamce_portal.Adapers.Adapter;
import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.Utils.DividerItemDecoration;
import com.example.hp.attendamce_portal.Utils.RequestCodes;
import com.example.hp.attendamce_portal.Utils.URL_API;
import com.example.hp.attendamce_portal.Utils.VolleyHelper;
import com.example.hp.attendamce_portal.pojo.BeanFacultyInfo;
import com.example.hp.attendamce_portal.pojo.BeanSubjectInfo;
import com.example.hp.attendamce_portal.pojo.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ShowFacultyInfo extends BaseFragment {
    TextView facultyid, facultyname, dob, gender, fathername, mothername, qualification;
    private ArrayList<List> array;
    ArrayList<BeanSubjectInfo> sublist = new ArrayList<BeanSubjectInfo>();
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Adapter listAdapter;
    int clickCounter=0;


    public static ShowFacultyInfo newInstance(int sectionNumber) {
        ShowFacultyInfo fragment = new ShowFacultyInfo();
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
        View v = inflater.inflate(R.layout.fragment_show_faculty_info, container, false);
        facultyid = (TextView) v.findViewById(R.id.facultyid);
        facultyname = (TextView) v.findViewById(R.id.facultyname);
        dob = (TextView) v.findViewById(R.id.dob);
        gender = (TextView) v.findViewById(R.id.gender);
        fathername = (TextView) v.findViewById(R.id.fathername);
        mothername = (TextView) v.findViewById(R.id.mothername);
        qualification = (TextView) v.findViewById(R.id.qualification);
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

        SharedPreferences editor = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String fid = editor.getString("faculty_id", "-1 ");
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("faculty_id", fid);
        VolleyHelper.postRequestVolley(getActivity(), ShowFacultyInfo.this, URL_API.RecieveFacultyInfo, hashMap, RequestCodes.RecieveFacultyInfo, false);

        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put("faculty_id", fid);
        VolleyHelper.postRequestVolley(getActivity(), ShowFacultyInfo.this, URL_API.RecieveSubjects, hashMap1, RequestCodes.RecieveSubjects, false);

        facultyid.setText(fid);

        return v;
    }

    @Override
    public void requestStarted(int requestCode) {
        super.requestStarted(requestCode);

    }

    @Override
    public void requestCompleted(int requestCode, String response) {
        super.requestCompleted(requestCode, response);
        if (requestCode == 26) {
            try {
                JSONObject jsonObject = new JSONObject(response);

                int i = jsonObject.getInt("success");
                if (i == 1) {
                    BeanFacultyInfo list = new BeanFacultyInfo();
                    list = new Gson().fromJson(jsonObject.get("faculty_info").toString(), BeanFacultyInfo.class);
                    facultyname.setText(list.getFacultyName());
                    fathername.setText(list.getFathersName());
                    mothername.setText(list.getMothersName());
                    gender.setText(list.getGender());
                    dob.setText(list.getDOB());
                    qualification.setText(list.getQualification());
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        if (requestCode == 21) {
            try {
                JSONObject jsonObject = new JSONObject(response);

                int i = jsonObject.getInt("success");
                if (i == 1) {

                    sublist = new Gson().fromJson(jsonObject.get("subject_list").toString(), new TypeToken<ArrayList<BeanSubjectInfo>>() {
                    }.getType());
                    array = new ArrayList<>();
                    for (BeanSubjectInfo b : sublist) {
                        array.add(new List(b.getSubjectID()));

                    }
                    listAdapter = new Adapter(getContext(),array);
                    recyclerView.setAdapter(listAdapter);




                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

    }
}