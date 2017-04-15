package com.example.hp.attendamce_portal.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.VolleyError;
import com.example.hp.attendamce_portal.Activities.AddStudent;
import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.Utils.RequestCodes;
import com.example.hp.attendamce_portal.Utils.URL_API;
import com.example.hp.attendamce_portal.Utils.VolleyHelper;
import com.example.hp.attendamce_portal.pojo.BeanStudentSemInfo;
import com.example.hp.attendamce_portal.pojo.BeanSubjectInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class AddStudentSemInfo extends BaseFragment {

    View v;
    ArrayList<BeanSubjectInfo> si = new ArrayList<BeanSubjectInfo>();
AddStudent mainActivity;

    public static AddStudentSemInfo newInstance(int sectionNumber) {
        AddStudentSemInfo fragment = new AddStudentSemInfo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (AddStudent) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_student_sem_info, container, false);

        super.onViewCreated(v, savedInstanceState);
        final EditText rollno = (EditText) v.findViewById(R.id.roll);
        final EditText branchid = (EditText) v.findViewById(R.id.branchid);
        final EditText semno = (EditText) v.findViewById(R.id.semno);
        final EditText isactive = (EditText) v.findViewById(R.id.isactive);
        Button getsubject = (Button) v.findViewById(R.id.btn_getsubjects);

        getsubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String branch_id = branchid.getText().toString();
                String sem_no = semno.getText().toString();
                if (branch_id.matches("") && sem_no.matches("")) {
                    Toast.makeText(getContext(), "Fill All The Details", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("branch_id", branch_id);
                hashMap.put("sem_no", sem_no);

                VolleyHelper.postRequestVolley(getActivity(), AddStudentSemInfo.this, URL_API.RecieveSubject, hashMap, RequestCodes.RecieveSubject, false);
            }
        });
        Button submit = (Button) v.findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean allvalid = true;
                if (isactive.getText().toString().matches("") || rollno.getText().toString().matches("")) {
                    allvalid = false;
                    Toast.makeText(getContext(), "Fill All The Blanks", Toast.LENGTH_SHORT).show();
                }
                if (allvalid) {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    ArrayList<BeanStudentSemInfo> arra=new ArrayList<>();
                    for (BeanSubjectInfo info:si) {
                        BeanStudentSemInfo b = new BeanStudentSemInfo();
                        b.setRollNo(rollno.getText().toString());
                        b.setBranchID(branchid.getText().toString());
                        b.setSemNo(semno.getText().toString());
                        b.setIsActive(isactive.getText().toString());
                        b.setSubjectID(info.getSubjectID());
                        arra.add(b);
                    }
                    Gson gson = new Gson();
                    hashMap.put("data", gson.toJson(arra));
                    VolleyHelper.postRequestVolley(getActivity(), AddStudentSemInfo.this, URL_API.StudentSemInfo, hashMap, RequestCodes.StudentSemInfo, false);
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

    @Override
    public void requestCompleted(int requestCode, String response) {
        super.requestCompleted(requestCode, response);
        if(mainActivity!=null)
            mainActivity.dismissDialog();
        try {
            JSONObject jsonObject = new JSONObject(response);

            int i = jsonObject.getInt("success");
            if (i == 1) {
                if(requestCode==4) {
                    ArrayList<BeanSubjectInfo> list = new ArrayList<BeanSubjectInfo>();
                    list = new Gson().fromJson(jsonObject.get("subjects").toString(), new TypeToken<ArrayList<BeanSubjectInfo>>() {
                    }.getType());
                    showDialog(list);
                }
                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

            } else
                Snackbar.make(getView(), jsonObject.getString("message"), Snackbar.LENGTH_LONG).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void showDialog(final ArrayList<BeanSubjectInfo> list) {
        final ArrayList<String> subjects = new ArrayList<>();
        for (BeanSubjectInfo info : list)
            subjects.add(info.getSubjectName());
        final ArrayList<Integer> sublist = new ArrayList<Integer>();
        new MaterialDialog.Builder(getContext())
                .title("Select subjects")
                .items(subjects)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        sublist.clear();
                        sublist.addAll(Arrays.asList(which));
                        return true;
                    }
                })
                .positiveText("done")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        si = new ArrayList<BeanSubjectInfo>();
                        for (Integer pos : sublist) {
                            si.add(list.get(pos));
                        }
                    }
                })
                .show();

    }

}
