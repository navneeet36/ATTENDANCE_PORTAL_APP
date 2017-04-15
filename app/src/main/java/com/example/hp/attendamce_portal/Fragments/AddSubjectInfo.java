package com.example.hp.attendamce_portal.Fragments;

import android.os.Bundle;
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
import com.example.hp.attendamce_portal.pojo.BeanSubjectInfo;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;


public class AddSubjectInfo extends BaseFragment {
//static Boolean d=true;
    public static AddSubjectInfo newInstance(int sectionNumber) {
        AddSubjectInfo fragment = new AddSubjectInfo();
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
        View v = inflater.inflate(R.layout.fragment_add_subject_info, container, false);

        final EditText subjectid=(EditText)v.findViewById(R.id.subjectid);
        final EditText subjectname=(EditText)v.findViewById(R.id.subjectname);
        final   EditText semno=(EditText)v.findViewById(R.id.semno);
        final EditText branchid=(EditText)v.findViewById(R.id.branchid);
        final   Button addsubject=(Button)v.findViewById(R.id.add);
        final Button deletesubject=(Button)v.findViewById(R.id.delete);
        final Button updatesubject=(Button)v.findViewById(R.id.update);
        addsubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean allvalid = true;

                if (subjectname.getText().toString().matches("") || branchid.getText().toString().matches("") || semno.getText().toString().matches("") || branchid.getText().toString().matches("") ) {
                    allvalid = false;
                    Toast.makeText(getContext(), "Fill All The Blanks", Toast.LENGTH_SHORT).show();
                }
                if (allvalid) {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    BeanSubjectInfo b = new BeanSubjectInfo();
                    b.setBranchID(branchid.getText().toString());
                    b.setSubjectID(subjectid.getText().toString());
                    b.setSemNo(semno.getText().toString());
                    b.setSubjectName(subjectname.getText().toString());
                    Gson gson = new Gson();
                    hashMap.put("data", gson.toJson(b));
                    VolleyHelper.postRequestVolley(getActivity(), AddSubjectInfo.this, URL_API.NewSubject, hashMap, RequestCodes.NewSubject, false);
                }
            }
        });

        updatesubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean allvalid = true;
                if (subjectname.getText().toString().matches("") || branchid.getText().toString().matches("") || semno.getText().toString().matches("") || branchid.getText().toString().matches("") ) {

                    allvalid = false;
                    Toast.makeText(getContext(), "Fill All The Blanks", Toast.LENGTH_SHORT).show();
                }
                if (allvalid) {
                   // showDialog();
                   // if(!d) {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        BeanSubjectInfo b = new BeanSubjectInfo();
                        b.setBranchID(branchid.getText().toString());
                        b.setSubjectName(subjectname.getText().toString());
                        b.setSemNo(semno.getText().toString());
                        b.setSubjectID(subjectid.getText().toString());
                        Gson gson = new Gson();
                        hashMap.put("data", gson.toJson(b));
                        VolleyHelper.postRequestVolley(getActivity(), AddSubjectInfo.this, URL_API.UpdateSubject, hashMap, RequestCodes.UpdateSubject, false);
                   // }
                }
            }
        });

        deletesubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean allvalid = true;
                String subject_id=subjectid.getText().toString();

                if ( subjectid.getText().toString().matches("")  ) {
                    allvalid = false;
                    Toast.makeText(getContext(), "Fill Subject id", Toast.LENGTH_SHORT).show();
                }
                if (allvalid) {
                 //   showDialog();
                 //   if (!d) {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("subject_id", subject_id);
                        VolleyHelper.postRequestVolley(getActivity(), AddSubjectInfo.this, URL_API.DeleteSubject, hashMap, RequestCodes.DeleteSubject, false);
                   // }
                }
            }
        });
        return v;
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
  /*  void showDialog()
    {
                d=true;
        new MaterialDialog.Builder(getContext())
                .title("Confirmation ")
                .content("Are you sure ")
                .positiveText("Yes")
                .negativeText("No")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        d=false;
                    }
                })
                .show();
    }*/
}
