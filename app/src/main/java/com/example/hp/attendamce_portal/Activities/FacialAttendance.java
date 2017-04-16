package com.example.hp.attendamce_portal.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hp.attendamce_portal.Adapers.AttendanceAdapter;
import com.example.hp.attendamce_portal.Fragments.AddAttendance;
import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.Utils.BitmapHandler;
import com.example.hp.attendamce_portal.Utils.RequestCodes;
import com.example.hp.attendamce_portal.Utils.URL_API;
import com.example.hp.attendamce_portal.Utils.VolleyHelper;
import com.example.hp.attendamce_portal.pojo.BeanAttendance;
import com.example.hp.attendamce_portal.pojo.BeanDates;
import com.example.hp.attendamce_portal.pojo.BeanStudentSemInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class FacialAttendance extends BaseActivity implements AttendanceAdapter.OnAttendanceClickListener {
    String fid, branch_id, sem_no, sub;
    ArrayList<BeanAttendance> beanAttendanceArrayList;
    AttendanceAdapter attendanceAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facial_attendance);
        recyclerView=(RecyclerView)findViewById(R.id.list);
        fid = getIntent().getStringExtra("fid");
        branch_id = getIntent().getStringExtra("branch_id");
        sem_no = getIntent().getStringExtra("sem_no");
        sub = getIntent().getStringExtra("sub");
        beanAttendanceArrayList = new ArrayList<BeanAttendance>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        attendanceAdapter=new AttendanceAdapter(this,beanAttendanceArrayList);
        attendanceAdapter.setOnAttendanceClickListener(this);
        recyclerView.setAdapter(attendanceAdapter);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        Calendar c= Calendar.getInstance();
        String date=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        hashMap.put("date",date);
        hashMap.put("fid",fid);
        hashMap.put("branch_id",branch_id);
        hashMap.put("subject_id",sub);
        hashMap.put("sem_no",sem_no);
        VolleyHelper.postRequestVolley(this, URL_API.StartFaceAttendance, hashMap, RequestCodes.AddAttendance, false);

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
        if(requestCode==RequestCodes.AddAttendance){
            try {
                JSONObject jsonObject = new JSONObject(response);

                int i = jsonObject.getInt("success");
                if (i == 1) {

                    beanAttendanceArrayList = new Gson().fromJson(jsonObject.get("list").toString(), new TypeToken<ArrayList<BeanAttendance>>() {
                    }.getType());
                    if(beanAttendanceArrayList==null){
                        Toast.makeText(this,"No students",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    Collections.sort(beanAttendanceArrayList,beanAttendanceComparator);
                    attendanceAdapter=new AttendanceAdapter(this,beanAttendanceArrayList);
                    attendanceAdapter.setOnAttendanceClickListener(this);
                    recyclerView.setAdapter(attendanceAdapter);

                } else
                    Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(requestCode==RequestCodes.AddFaceAttendance){
            try {
                JSONObject jsonObject = new JSONObject(response);

                int i = jsonObject.getInt("success");
                if (i == 1) {
                      BeanAttendance v= new Gson().fromJson(jsonObject.get("data").toString(),BeanAttendance.class);
                      if(beanAttendanceArrayList.contains(v))
                      {
                          beanAttendanceArrayList.remove(v);
                          beanAttendanceArrayList.add(v);
                          Collections.sort(beanAttendanceArrayList,beanAttendanceComparator);
                          attendanceAdapter=new AttendanceAdapter(this,beanAttendanceArrayList);
                          attendanceAdapter.setOnAttendanceClickListener(this);
                          recyclerView.setAdapter(attendanceAdapter);

                          Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                      }
                } else
                    Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onClick(BeanAttendance beanAttendance) {
        Intent intent=new Intent(this, FaceTrackerActivity.class);
        beanAttendance.setIsPresent("yes");
        intent.putExtra("bean",beanAttendance);
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 5) {
            Bitmap bitmap = BitmapHandler.getBitmap();
            HashMap<String, String> hash=new HashMap<String, String>();
            BeanAttendance beanAttendance=data.getParcelableExtra("bean");
            hash.put("data",new Gson().toJson(beanAttendance));
            VolleyHelper.uploadImage(this,URL_API.AddFaceAttendance,hash,RequestCodes.AddFaceAttendance,bitmap);
        }
    }
    Comparator<BeanAttendance> beanAttendanceComparator=new Comparator<BeanAttendance>() {
        @Override
        public int compare(BeanAttendance beanAttendance, BeanAttendance t1) {
            return beanAttendance.getRollNo().compareToIgnoreCase(t1.getRollNo());
        }
    };
}
