package com.example.hp.attendamce_portal.Fragments;

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
import com.example.hp.attendamce_portal.Adapers.AttendanceAdapter;
import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.Utils.DividerItemDecoration;
import com.example.hp.attendamce_portal.Utils.RequestCodes;
import com.example.hp.attendamce_portal.Utils.URL_API;
import com.example.hp.attendamce_portal.Utils.VolleyHelper;
import com.example.hp.attendamce_portal.pojo.AttendanceList;
import com.example.hp.attendamce_portal.pojo.BeanAttendance;
import com.example.hp.attendamce_portal.pojo.BeanDates;
import com.example.hp.attendamce_portal.pojo.BeanSubjectInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ShowDayWiseAttendance extends BaseFragment {


    String subid, fid, date;
    Integer pos;
    ArrayList<BeanSubjectInfo> list;
    ArrayList<BeanDates> datelist;
    ArrayList<BeanAttendance> attendancelist;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AttendanceAdapter adapter;
    public ShowDayWiseAttendance() {
    }

    public static ShowDayWiseAttendance newInstance(int sectionNumber) {
        ShowDayWiseAttendance fragment = new ShowDayWiseAttendance();
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

        View v = inflater.inflate(R.layout.fragment_show_day_wise_attendance, container, false);
        final Button choosesubjects = (Button) v.findViewById(R.id.choosesubject);
        final Button choosedates = (Button) v.findViewById(R.id.choosedates);
        final Button showattendance = (Button) v.findViewById(R.id.showattendance);
        SharedPreferences editor = PreferenceManager.getDefaultSharedPreferences(getActivity());

        fid = editor.getString("faculty_id", "-1 ");

        choosesubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("faculty_id", fid);

                VolleyHelper.postRequestVolley(getActivity(), ShowDayWiseAttendance.this, URL_API.RecieveSubjects, hashMap, RequestCodes.RecieveSubjects, false);

            }
        });

        choosedates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("faculty_id", fid);
                hashMap.put("subject_id", subid);
                VolleyHelper.postRequestVolley(getActivity(), ShowDayWiseAttendance.this, URL_API.RecieveDates, hashMap, RequestCodes.RecieveDates, false);

            }
        });
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
        showattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> hashMap1 = new HashMap<String, String>();
                hashMap1.put("faculty_id", fid);
                hashMap1.put("subject_id", subid);
                hashMap1.put("attendance_date", date);
                VolleyHelper.postRequestVolley(getActivity(), ShowDayWiseAttendance.this, URL_API.RecieveAttendance, hashMap1, RequestCodes.RecieveAttendance, false);
            }
        });
        return v;

    }

    @Override
    public void requestStarted(int requestCode) {
        super.requestStarted(requestCode);

    }

    @Override
    public void requestCompleted(int requestCode, String response) {
        super.requestCompleted(requestCode, response);
        if (requestCode == 21) {
            try {
                JSONObject jsonObject = new JSONObject(response);

                int i = jsonObject.getInt("success");
                if (i == 1) {

                    list = new ArrayList<BeanSubjectInfo>();
                    list = new Gson().fromJson(jsonObject.get("subject_list").toString(), new TypeToken<ArrayList<BeanSubjectInfo>>() {
                    }.getType());
                    showDialog(list);
                    Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } else
                    Snackbar.make(getView(), jsonObject.getString("message"), Snackbar.LENGTH_LONG).show();

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        else if (requestCode == 25) {
            try {
                JSONObject jsonObject = new JSONObject(response);

                int i = jsonObject.getInt("success");
                if (i == 1) {

                    datelist = new ArrayList<BeanDates>();
                    datelist = new Gson().fromJson(jsonObject.get("dates").toString(), new TypeToken<ArrayList<BeanDates>>() {
                    }.getType());
                    showDialogdates(datelist);
                    Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } else
                    Snackbar.make(getView(), jsonObject.getString("message"), Snackbar.LENGTH_LONG).show();

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }    else if (requestCode == 27) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int i = jsonObject.getInt("success");
                    if (i == 1) {

                        attendancelist = new ArrayList<BeanAttendance>();
                        attendancelist = new Gson().fromJson(jsonObject.get("attendance").toString(), new TypeToken<ArrayList<BeanAttendance>>() {
                        }.getType());
                        ArrayList<AttendanceList> arrayList = new ArrayList<>();
                        for(BeanAttendance b:attendancelist)
                        {
                            arrayList.add(new AttendanceList(b.getRollNo(),b.getIsPresent()));
                        }

                        adapter = new AttendanceAdapter(getContext(), arrayList);
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

    void showDialogdates(final ArrayList<BeanDates> datelist) {
        final ArrayList<String> subjects = new ArrayList<>();
        for (BeanDates info : datelist)
            subjects.add(info.getAttendanceDate());
        new MaterialDialog.Builder(getContext())
                .title("Select Dates ")
                .items(subjects)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        pos = which;
                        date = datelist.get(pos).getAttendanceDate().toString();
                        return true;

                    }
                })
                .positiveText("Done")

                .show();
    }

    @Override
    public void requestEndedWithError(int requestCode, VolleyError error) {
        super.requestEndedWithError(requestCode, error);

    }

}