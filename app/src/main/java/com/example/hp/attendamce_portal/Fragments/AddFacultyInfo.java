package com.example.hp.attendamce_portal.Fragments;

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
import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.Utils.RequestCodes;
import com.example.hp.attendamce_portal.Utils.URL_API;
import com.example.hp.attendamce_portal.Utils.VolleyHelper;
import com.example.hp.attendamce_portal.pojo.BeanFacultyInfo;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;


public class AddFacultyInfo extends BaseFragment {

    public static AddFacultyInfo newInstance(int sectionNumber) {
        AddFacultyInfo fragment = new AddFacultyInfo();
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
        View v = inflater.inflate(R.layout.fragment_add_faculty_info, null);

        return v;
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        final EditText facultyid = (EditText) v.findViewById(R.id.facultyid);
        final EditText facultyname = (EditText) v.findViewById(R.id.facultyname);
        final EditText fathername = (EditText) v.findViewById(R.id.fathername);
        final EditText dob = (EditText) v.findViewById(R.id.dob);
        final EditText gender = (EditText) v.findViewById(R.id.gender);
        final EditText mothername = (EditText) v.findViewById(R.id.mothername);
        final EditText qualification = (EditText) v.findViewById(R.id.qualification);
        final EditText joiningdate = (EditText) v.findViewById(R.id.joiningdate);
        Button submit = (Button) v.findViewById(R.id.btn_submit);
        Button update =(Button)v.findViewById(R.id.btn_update);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean allvalid = true;

                if (facultyname.getText().toString().matches("") || facultyid.getText().toString().matches("") || fathername.getText().toString().matches("") || mothername.getText().toString().matches("") || gender.getText().toString().matches("") || qualification.getText().toString().matches("") || dob.getText().toString().matches("") || joiningdate.getText().toString().matches("")) {
                    allvalid = false;
                    Toast.makeText(getContext(), "Fill All The Blanks", Toast.LENGTH_SHORT).show();
                }
                if (allvalid) {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    BeanFacultyInfo b = new BeanFacultyInfo();
                    b.setFacultyID(facultyid.getText().toString());
                    b.setFacultyName(facultyname.getText().toString());
                    b.setFathersName(fathername.getText().toString());
                    b.setMothersName(mothername.getText().toString());
                    b.setGender(gender.getText().toString());
                    b.setQualification(qualification.getText().toString());
                    b.setDOB(dob.getText().toString());
                    b.setWorkingDate(joiningdate.getText().toString());
                    Gson gson = new Gson();
                    hashMap.put("data", gson.toJson(b));
                    VolleyHelper.postRequestVolley(getActivity(), AddFacultyInfo.this, URL_API.NewFaculty, hashMap, RequestCodes.NewFaculty, false);
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean allvalid = true;

                if (facultyname.getText().toString().matches("") || facultyid.getText().toString().matches("") || fathername.getText().toString().matches("") || mothername.getText().toString().matches("") || gender.getText().toString().matches("") || qualification.getText().toString().matches("") || dob.getText().toString().matches("") || joiningdate.getText().toString().matches("")) {
                    allvalid = false;
                    Toast.makeText(getContext(), "Fill All The Blanks", Toast.LENGTH_SHORT).show();
                }
                if (allvalid) {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    BeanFacultyInfo b = new BeanFacultyInfo();
                    b.setFacultyID(facultyid.getText().toString());
                    b.setFacultyName(facultyname.getText().toString());
                    b.setFathersName(fathername.getText().toString());
                    b.setMothersName(mothername.getText().toString());
                    b.setGender(gender.getText().toString());
                    b.setQualification(qualification.getText().toString());
                    b.setDOB(dob.getText().toString());
                    b.setWorkingDate(joiningdate.getText().toString());
                    Gson gson = new Gson();
                    hashMap.put("data", gson.toJson(b));
                    VolleyHelper.postRequestVolley(getActivity(), AddFacultyInfo.this, URL_API.UpdateFaculty, hashMap, RequestCodes.UpdateFaculty, false);
                }
            }
        });
    }
    @Override
    public void requestStarted(int requestCode) {
        super.requestStarted(requestCode);

    }

    @Override
    public void requestEndedWithError(int requestCode, VolleyError error) {
        super.requestEndedWithError(requestCode, error);

    }

    @Override
    public void requestCompleted(int requestCode, String response) {
        super.requestCompleted(requestCode, response);

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