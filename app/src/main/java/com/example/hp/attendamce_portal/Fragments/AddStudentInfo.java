package com.example.hp.attendamce_portal.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hp.attendamce_portal.Activities.AddStudent;
import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.Utils.FLog;
import com.example.hp.attendamce_portal.Utils.RequestCodes;
import com.example.hp.attendamce_portal.Utils.URL_API;
import com.example.hp.attendamce_portal.Utils.VolleyHelper;
import com.example.hp.attendamce_portal.pojo.BeanStudentInfo;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;


public class AddStudentInfo extends BaseFragment {

    AddStudent mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FLog.d("AddStudentInfo", "Oncreatecalled");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (AddStudent) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FLog.d("AddStudentInfo", "Oncreateviewcalled");
        View v = inflater.inflate(R.layout.fragment_add_student_info, null);
        return v;
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        FLog.d("AddStudentInfo", "onviewcreate");
        final EditText rollno = (EditText) v.findViewById(R.id.roll);
        final EditText name = (EditText) v.findViewById(R.id.name);
        final EditText fathername = (EditText) v.findViewById(R.id.fathername);
        final EditText dob = (EditText) v.findViewById(R.id.dob);
        final EditText addate = (EditText) v.findViewById(R.id.addate);
        final EditText mothername = (EditText) v.findViewById(R.id.mothername);
        final EditText branch = (EditText) v.findViewById(R.id.branch);
        Button submit = (Button) v.findViewById(R.id.btn_submit);
        Button update = (Button) v.findViewById(R.id.btn_update);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean allvalid = true;
                FLog.d("button clicked");
                if (name.getText().toString().matches("") || rollno.getText().toString().matches("") || fathername.getText().toString().matches("") || mothername.getText().toString().matches("") || branch.getText().toString().matches("")) {
                    allvalid = false;
                    Toast.makeText(getContext(), "Fill All The Blanks", Toast.LENGTH_SHORT).show();
                }
                if (allvalid) {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    BeanStudentInfo b = new BeanStudentInfo();
                    b.setRollNo(rollno.getText().toString());
                    b.setName(name.getText().toString());
                    b.setFathersName(fathername.getText().toString());
                    b.setMothersName(mothername.getText().toString());
                    b.setBranchID(branch.getText().toString());
                    b.setDOB(dob.getText().toString());
                    b.setAdmissionDate(addate.getText().toString());
                    Gson gson = new Gson();
                    hashMap.put("data", gson.toJson(b));
                    VolleyHelper.postRequestVolley(getActivity(), AddStudentInfo.this, URL_API.NewStudent, hashMap, RequestCodes.NewStudent, false);
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean allvalid = true;
                FLog.d("button clicked");
                if (name.getText().toString().matches("") || rollno.getText().toString().matches("") || fathername.getText().toString().matches("") || mothername.getText().toString().matches("") || branch.getText().toString().matches("")) {
                    allvalid = false;
                    Toast.makeText(getContext(), "Fill All The Blanks", Toast.LENGTH_SHORT).show();
                }
                if (allvalid) {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    BeanStudentInfo b = new BeanStudentInfo();
                    b.setRollNo(rollno.getText().toString());
                    b.setName(name.getText().toString());
                    b.setFathersName(fathername.getText().toString());
                    b.setMothersName(mothername.getText().toString());
                    b.setBranchID(branch.getText().toString());
                    b.setDOB(dob.getText().toString());
                    b.setAdmissionDate(addate.getText().toString());
                    Gson gson = new Gson();
                    hashMap.put("data", gson.toJson(b));
                    VolleyHelper.postRequestVolley(getActivity(), AddStudentInfo.this, URL_API.UpdateStudent, hashMap, RequestCodes.UpdateStudent, false);
                }
            }
        });
    }

    public static AddStudentInfo newInstance(int sectionNumber) {
        AddStudentInfo fragment = new AddStudentInfo();
        Bundle args = new Bundle();
        //not required now
        fragment.setArguments(args);
        return fragment;
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
        if (mainActivity != null)
            mainActivity.dismissDialog();
        try {
            JSONObject jsonObject = new JSONObject(response);
            int i = jsonObject.getInt("success");
            if (i == 1) {
                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

            } else
                Snackbar.make(getView(), jsonObject.getString("message"), Snackbar.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
