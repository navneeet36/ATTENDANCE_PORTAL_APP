package com.example.hp.attendamce_portal.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.VolleyError;
import com.example.hp.attendamce_portal.Activities.FacultyPage;
import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.Utils.RequestCodes;
import com.example.hp.attendamce_portal.Utils.URL_API;
import com.example.hp.attendamce_portal.Utils.VolleyHelper;
import com.example.hp.attendamce_portal.pojo.BeanAttendance;
import com.example.hp.attendamce_portal.pojo.BeanDates;
import com.example.hp.attendamce_portal.pojo.BeanStudentSemInfo;
import com.example.hp.attendamce_portal.pojo.BeanSubjectInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AddAttendance extends BaseFragment {
    private ArrayList<String> arraySpinner;
    ArrayList<BeanSubjectInfo> list = new ArrayList<BeanSubjectInfo>();
    AppCompatSpinner subjects;
    private ArrayList<BeanStudentSemInfo> studentlist;
    private ArrayList<Integer> stuPos = new ArrayList<>();
    FacultyPage mainActivity;

    public static AddAttendance newInstance(int sectionNumber) {
        AddAttendance fragment = new AddAttendance();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (FacultyPage) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_attendance, container, false);
        final EditText branchid = (EditText) v.findViewById(R.id.branchid);
        final EditText semno = (EditText) v.findViewById(R.id.semno);
        subjects = (AppCompatSpinner) v.findViewById(R.id.subject);
        final Button getstudents = (Button) v.findViewById(R.id.getstudents);
        final Button submit = (Button) v.findViewById(R.id.submit);
        SharedPreferences editor = PreferenceManager.getDefaultSharedPreferences(getActivity());

        final String fid = editor.getString("faculty_id", "-1 ");

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("faculty_id", fid);

        VolleyHelper.postRequestVolley(getActivity(), AddAttendance.this, URL_API.RecieveSubjects, hashMap, RequestCodes.RecieveSubjects, false);

        getstudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("branch_id", branchid.getText().toString());
                hashMap.put("sem_no", semno.getText().toString());
                String sub = list.get(subjects.getSelectedItemPosition()).getSubjectID();
                hashMap.put("subject_id", sub);
                VolleyHelper.postRequestVolley(getActivity(), AddAttendance.this, URL_API.GetStudents, hashMap, RequestCodes.GetStudents, false);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                HashMap<String, String> hashMap = new HashMap<String, String>();

                BeanDates b = new BeanDates();
                b.setFacultyID(fid);
                String sub = list.get(subjects.getSelectedItemPosition()).getSubjectID();
                b.setSubjectID(sub);
                Gson gson = new Gson();
                hashMap.put("data", gson.toJson(b));
                VolleyHelper.postRequestVolley(getActivity(), AddAttendance.this, URL_API.InsertDates, hashMap, RequestCodes.InsertDates, false);

                HashMap<String, String> hashMap1 = new HashMap<String, String>();
                ArrayList<BeanAttendance> arra;
                arra = new ArrayList<BeanAttendance>();
                for (int i = 0; i < studentlist.size(); i++) {
                    BeanStudentSemInfo info = studentlist.get(i);
                    BeanAttendance b1 = new BeanAttendance();
                    b1.setFacultyID(fid);
                    b1.setRollNo(info.getRollNo());
                    b1.setSubjectID(sub);
                    b1.setBranchID(branchid.getText().toString());
                    if (stuPos.contains(i)) {
                        b1.setIsPresent("yes");
                    } else
                        b1.setIsPresent("no");
                    b1.setSemNo(semno.getText().toString());
                    arra.add(b1);
                }
                Gson gson1 = new Gson();
                hashMap1.put("data", gson1.toJson(arra));
                VolleyHelper.postRequestVolley(getActivity(), AddAttendance.this, URL_API.AddAttendance, hashMap1, RequestCodes.AddAttendance, false);

            }
        });


        return v;
    }

    @Override
    public void requestStarted(int requestCode) {
        super.requestStarted(requestCode);
        //no need to show dialog for requestCode=RequestCodes.RecieveSubjects
        if(mainActivity!=null && requestCode!=RequestCodes.RecieveSubjects)
            mainActivity.showDialog();
    }

    @Override
    public void requestEndedWithError(int requestCode, VolleyError error) {
        super.requestEndedWithError(requestCode, error);
        if(mainActivity!=null && requestCode!=RequestCodes.RecieveSubjects)
            mainActivity.dismissDialog();

    }

    @Override
    public void requestCompleted(int requestCode, String response) {
        super.requestCompleted(requestCode, response);
        if(mainActivity!=null && requestCode!=RequestCodes.RecieveSubjects)
            mainActivity.dismissDialog();
        if (requestCode == 21) {
            try {
                JSONObject jsonObject = new JSONObject(response);

                int i = jsonObject.getInt("success");
                if (i == 1) {
                    list = new Gson().fromJson(jsonObject.get("subject_list").toString(), new TypeToken<ArrayList<BeanSubjectInfo>>() {
                    }.getType());
                    arraySpinner = new ArrayList<>();
                    for (BeanSubjectInfo b : list) {
                        arraySpinner.add(b.getSubjectID());
                    }
                    MyCustomAdapter adapter = new MyCustomAdapter(getContext(), R.layout.spinner_row, arraySpinner);
                    subjects.setAdapter(adapter);

                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        if (requestCode == 22) {
            try {
                JSONObject jsonObject = new JSONObject(response);

                int i = jsonObject.getInt("success");
                if (i == 1) {

                    studentlist = new ArrayList<BeanStudentSemInfo>();
                    studentlist = new Gson().fromJson(jsonObject.get("student_list").toString(), new TypeToken<ArrayList<BeanStudentSemInfo>>() {
                    }.getType());
                    showDialog(studentlist);
                } else
                    Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    void showDialog(final ArrayList<BeanStudentSemInfo> studentlist) {
        final ArrayList<String> students = new ArrayList<>();
        for (BeanStudentSemInfo info : studentlist)
            students.add(info.getRollNo());
        new MaterialDialog.Builder(getActivity())
                .title("Select students")
                .items(students)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        stuPos.clear();
                        stuPos.addAll(Arrays.asList(which));
                        return true;
                    }
                })
                .positiveText("done")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //    si=new ArrayList<BeanStudentSemInfo>();
                    }
                })
                .show();

    }

    public class MyCustomAdapter extends ArrayAdapter<String> {

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<String> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return getCustomView(position, false, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, true, parent);
        }


        public View getCustomView(int position, boolean white, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater(getArguments());
            View row = inflater.inflate(R.layout.spinner_row, parent, false);
            TextView label = (TextView) row.findViewById(android.R.id.text1);
            label.setTextColor(Color.BLACK);
            label.setText(getItem(position));
            return row;
        }
    }


}