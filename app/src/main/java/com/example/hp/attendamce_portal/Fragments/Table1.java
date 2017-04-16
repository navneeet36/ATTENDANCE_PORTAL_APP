package com.example.hp.attendamce_portal.Fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.VolleyError;
import com.example.hp.attendamce_portal.Activities.StudentPage;
import com.example.hp.attendamce_portal.Adapers.Table1Adapter;
import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.Utils.DividerItemDecoration;
import com.example.hp.attendamce_portal.Utils.RequestCodes;
import com.example.hp.attendamce_portal.Utils.URL_API;
import com.example.hp.attendamce_portal.Utils.VolleyHelper;
import com.example.hp.attendamce_portal.pojo.BeanAttendance;
import com.example.hp.attendamce_portal.pojo.BeanSubjectInfo;
import com.example.hp.attendamce_portal.pojo.Table1List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Table1 extends BaseFragment {
    String rno, subid;
    Integer pos;
    ArrayList<BeanSubjectInfo> list;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<BeanAttendance> attendancelist;
    Table1Adapter adapter;
    StudentPage mainActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (StudentPage) activity;
    }
    public static Table1 newInstance(int sectionNumber) {
        Table1 fragment = new Table1();
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
        View v = inflater.inflate(R.layout.fragment_table1, container, false);
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
        Button choosesubject = (Button) v.findViewById(R.id.choosesubject);
        Button getattendance = (Button) v.findViewById(R.id.getAttendance);
        SharedPreferences editor = PreferenceManager.getDefaultSharedPreferences(getActivity());

        rno = editor.getString("roll_no", "-1 ");

        choosesubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("roll_no", rno);

                VolleyHelper.postRequestVolley(getActivity(), Table1.this, URL_API.RecieveStuSubjects, hashMap, RequestCodes.RecieveStuSubjects, false);

            }
        });
        getattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              boolean allvalid=true;
                        if(subid==null) {
                            allvalid = false;
                        }
                Toast.makeText(getActivity(), "please select subject", Toast.LENGTH_SHORT).show();
                if(allvalid) {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("roll_no", rno);
                    hashMap.put("subject_id", subid);

                    VolleyHelper.postRequestVolley(getActivity(), Table1.this, URL_API.Recievetable1, hashMap, RequestCodes.Recievetable1, false);
                }

            }
        });

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

    public void requestCompleted(int requestCode, String response) {
        super.requestCompleted(requestCode, response);
        if (mainActivity != null)
            mainActivity.dismissDialog();
        if (requestCode == 30) {
            try {
                JSONObject jsonObject = new JSONObject(response);

                int i = jsonObject.getInt("success");
                if (i == 1) {

                    list = new ArrayList<BeanSubjectInfo>();
                    list = new Gson().fromJson(jsonObject.get("subjects").toString(), new TypeToken<ArrayList<BeanSubjectInfo>>() {
                    }.getType());
                    showDialog(list);
                    Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } else
                    Snackbar.make(getView(), jsonObject.getString("message"), Snackbar.LENGTH_LONG).show();

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        else if (requestCode == 31) {
            try {
                JSONObject jsonObject = new JSONObject(response);

                int i = jsonObject.getInt("success");
                if (i == 1) {

                    attendancelist = new ArrayList<BeanAttendance>();
                    attendancelist = new Gson().fromJson(jsonObject.get("attendance").toString(), new TypeToken<ArrayList<BeanAttendance>>() {
                    }.getType());
                    ArrayList<Table1List> arrayList = new ArrayList<>();
                    for(BeanAttendance b1:attendancelist)
                    {
                        arrayList.add(new Table1List(b1.getAttendanceDate(),b1.getIsPresent()));
                    }
                    adapter = new Table1Adapter(getContext(), arrayList);
                    recyclerView.setAdapter(adapter);

                    Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } else
                    Snackbar.make(getView(), jsonObject.getString("message"), Snackbar.LENGTH_LONG).show();

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

    }

    void showDialog(final ArrayList<BeanSubjectInfo> list) {
        final ArrayList<String> subjects = new ArrayList<>();
        for (BeanSubjectInfo info : list)
            subjects.add(info.getSubjectID());
        new MaterialDialog.Builder(getContext())
                .title("Select Subject ")
                .items(subjects)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        pos = which;
                        subid = list.get(pos).getSubjectID().toString();
                        return true;

                    }
                })
                .positiveText("Done")

                .show();
    }

}