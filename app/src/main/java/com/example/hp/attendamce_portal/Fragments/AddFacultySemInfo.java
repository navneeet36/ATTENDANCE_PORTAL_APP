package com.example.hp.attendamce_portal.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.VolleyError;
import com.example.hp.attendamce_portal.Activities.AddFaculty;
import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.Utils.RequestCodes;
import com.example.hp.attendamce_portal.Utils.URL_API;
import com.example.hp.attendamce_portal.Utils.VolleyHelper;
import com.example.hp.attendamce_portal.pojo.BeanFacultySemInfo;
import com.example.hp.attendamce_portal.pojo.BeanSubjectInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link AddFacultySemInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFacultySemInfo extends BaseFragment {
    View v;
    ArrayList<BeanSubjectInfo> si = new ArrayList<BeanSubjectInfo>();
AddFaculty mainActivity;
    public static AddFacultySemInfo newInstance(int sectionNumber) {
        AddFacultySemInfo fragment = new AddFacultySemInfo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (AddFaculty) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_faculty_sem_info, null);
        super.onViewCreated(v, savedInstanceState);

        final EditText facultyid = (EditText) v.findViewById(R.id.facultyid);
        final EditText isactive = (EditText) v.findViewById(R.id.isactive);
final Button btn=(Button)v.findViewById(R.id.choosesubjects);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                VolleyHelper.postRequestVolley(getActivity(), AddFacultySemInfo.this, URL_API.F_RecieveSubject, hashMap, RequestCodes.F_RecieveSubjects, false);
            }
        });

        Button submit = (Button) v.findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean allvalid = true;
                if (isactive.getText().toString().matches("") || facultyid.getText().toString().matches("")) {
                    allvalid = false;
                    Toast.makeText(getContext(), "Fill All The Blanks", Toast.LENGTH_SHORT).show();
                }
                if (allvalid) {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    ArrayList<BeanFacultySemInfo> arra=new ArrayList<>();
                    for (BeanSubjectInfo info:si) {
                        BeanFacultySemInfo b = new BeanFacultySemInfo();
                        b.setFacultyID(facultyid.getText().toString());
                        b.setIsTeaching(isactive.getText().toString());
                        b.setSubjectID(info.getSubjectID());
                        arra.add(b);
                    }
                    Gson gson = new Gson();
                    hashMap.put("data", gson.toJson(arra));
                    VolleyHelper.postRequestVolley(getActivity(), AddFacultySemInfo.this, URL_API.FacultySemInfo, hashMap, RequestCodes.FacultySemInfo, false);
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
                    if(requestCode==7) {
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
