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
import com.example.hp.attendamce_portal.pojo.BeanStudentInfo;
import com.example.hp.attendamce_portal.pojo.BeanStudentSemInfo;
import com.example.hp.attendamce_portal.pojo.BeanSubjectInfo;
import com.example.hp.attendamce_portal.pojo.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ShowStudentInfo extends BaseFragment
{
    TextView rollno,name,fathername,mothername,dob,admissiondate,branchid,semno;
    private ArrayList<List> array;
    ArrayList<BeanSubjectInfo> sublist = new ArrayList<BeanSubjectInfo>();
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Adapter listAdapter;



   public static ShowStudentInfo newInstance(int sectionNumber) {
        ShowStudentInfo fragment = new ShowStudentInfo();
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

        View v= inflater.inflate(R.layout.fragment_show_student_info, container, false);
        rollno=(TextView)v.findViewById(R.id.rollno);
        name=(TextView)v.findViewById(R.id.name);
        fathername=(TextView)v.findViewById(R.id.fathername);
        mothername=(TextView)v.findViewById(R.id.mothername);
        dob=(TextView)v.findViewById(R.id.dob);
        admissiondate=(TextView)v.findViewById(R.id.joiningdate);
        branchid=(TextView)v.findViewById(R.id.branchid);
        semno=(TextView)v.findViewById(R.id.semno);
        SwipeRefreshLayout mSwipe = (SwipeRefreshLayout)v.findViewById(R.id.swipe);
        recyclerView = (RecyclerView)v. findViewById(R.id.recycler);
        recyclerView.setNestedScrollingEnabled(false);
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
        String rno = editor.getString("roll_no", "-1 ");
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("roll_no", rno);
        VolleyHelper.postRequestVolley(getActivity(), ShowStudentInfo.this, URL_API.RecieveStudentInfo, hashMap, RequestCodes.RecieveStudentInfo, false);

        HashMap<String, String> hashMap2 = new HashMap<String, String>();
        hashMap2.put("roll_no", rno);
        VolleyHelper.postRequestVolley(getActivity(), ShowStudentInfo.this, URL_API.RecieveStudentSemInfo, hashMap2, RequestCodes.RecieveStudentSemInfo, false);


        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put("roll_no", rno);
        VolleyHelper.postRequestVolley(getActivity(), ShowStudentInfo.this, URL_API.RecieveStuSubjects, hashMap1, RequestCodes.RecieveStuSubjects, false);

        rollno.setText(rno);

        return  v;
    }

    @Override
    public void requestStarted(int requestCode) {
        super.requestStarted(requestCode);

    }

    @Override
    public void requestCompleted(int requestCode, String response) {
        super.requestCompleted(requestCode, response);
        if (requestCode == 28) {
            try {
                JSONObject jsonObject = new JSONObject(response);

                int i = jsonObject.getInt("success");
                if (i == 1) {
                    BeanStudentInfo list = new BeanStudentInfo();
                    list = new Gson().fromJson(jsonObject.get("student_info").toString(), BeanStudentInfo.class);
                    name.setText(list.getName());
                    fathername.setText(list.getFathersName());
                    mothername.setText(list.getMothersName());
                    branchid.setText(list.getBranchID());
                    dob.setText(list.getDOB());
                    admissiondate.setText(list.getAdmissionDate());
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
       else if (requestCode == 29) {
            try {
                JSONObject jsonObject = new JSONObject(response);

                int i = jsonObject.getInt("success");
                if (i == 1) {
                    BeanStudentSemInfo list = new BeanStudentSemInfo();
                    list = new Gson().fromJson(jsonObject.get("student_seminfo").toString(), BeanStudentSemInfo.class);
                    semno.setText(list.getSemNo());

                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        else if (requestCode == 30) {
            try {
                JSONObject jsonObject = new JSONObject(response);

                int i = jsonObject.getInt("success");
                if (i == 1) {

                    sublist = new Gson().fromJson(jsonObject.get("subjects").toString(), new TypeToken<ArrayList<BeanSubjectInfo>>() {
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
