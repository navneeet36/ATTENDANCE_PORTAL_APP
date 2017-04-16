package com.example.hp.attendamce_portal.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hp.attendamce_portal.Fragments.AddAttendance;
import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.Utils.RequestCodes;
import com.example.hp.attendamce_portal.Utils.URL_API;
import com.example.hp.attendamce_portal.Utils.VolleyHelper;
import com.example.hp.attendamce_portal.pojo.BeanAttendance;
import com.example.hp.attendamce_portal.pojo.BeanDates;
import com.example.hp.attendamce_portal.pojo.BeanStudentSemInfo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FacialAttendance extends BaseActivity {
    String fid, branch_id, sem_no, sub;
    ArrayList<BeanStudentSemInfo> stulist;
    ArrayList<BeanAttendance> beanAttendanceArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facial_attendance);
        fid = getIntent().getStringExtra("fid");
        branch_id = getIntent().getStringExtra("branch_id");
        sem_no = getIntent().getStringExtra("sem_no");
        stulist = getIntent().getParcelableArrayListExtra("stulist");
        sub = getIntent().getStringExtra("sub");
        beanAttendanceArrayList = new ArrayList<BeanAttendance>();
        for (int j = 0; j < stulist.size(); j++) {
            BeanStudentSemInfo info = stulist.get(j);
            BeanAttendance b1 = new BeanAttendance();
            b1.setFacultyID(fid);
            b1.setRollNo(info.getRollNo());
            b1.setSubjectID(sub);
            b1.setBranchID(branch_id);
            b1.setIsPresent("no");
            b1.setSemNo(sem_no);
            beanAttendanceArrayList.add(b1);
        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        BeanDates b = new BeanDates();
        b.setFacultyID(fid);
        b.setSubjectID(sub);
        Gson gson = new Gson();
        hashMap.put("data", gson.toJson(b));
        VolleyHelper.postRequestVolley(this, URL_API.InsertDates, hashMap, RequestCodes.InsertDates, false);
    }

    @Override
    public void requestStarted(int requestCode) {
        super.requestStarted(requestCode);
        showDialog();
    }

    @Override
    public void requestEndedWithError(int requestCode, VolleyError error) {
        super.requestEndedWithError(requestCode, error);
        dismissDialog();
    }

    @Override
    public void requestCompleted(int requestCode, String response) {
        super.requestCompleted(requestCode, response);
        dismissDialog();
        if (requestCode == 23) {
            try {
                JSONObject jsonObject = new JSONObject(response);

                int i = jsonObject.getInt("success");
                if (i == 1) {

                    HashMap<String, String> hashMap1 = new HashMap<String, String>();

                    Gson gson1 = new Gson();
                    hashMap1.put("data", gson1.toJson(beanAttendanceArrayList));
                    VolleyHelper.postRequestVolley(this, URL_API.AddAttendance, hashMap1, RequestCodes.AddAttendance, false);

                } else
                    Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
