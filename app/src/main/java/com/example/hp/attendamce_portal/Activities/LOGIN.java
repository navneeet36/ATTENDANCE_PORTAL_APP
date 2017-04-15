package com.example.hp.attendamce_portal.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.Utils.RequestCodes;
import com.example.hp.attendamce_portal.Utils.URL_API;
import com.example.hp.attendamce_portal.Utils.VolleyHelper;
import com.example.hp.attendamce_portal.pojo.BeanLoginInfo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LOGIN extends BaseActivity {
    Toolbar toolbar;
    String user_name;
    String pass_word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
        final TextView forget=(TextView)findViewById(R.id.forgetpass);

        final EditText username = (EditText) findViewById(R.id.editText);
        final EditText password = (EditText) findViewById(R.id.editText1);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.equals(null) ) {
                    Toast.makeText(getApplicationContext(), "Please Fill UserName", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    user_name = username.getText().toString();
                    Intent intent = new Intent(LOGIN.this, Forget.class);
                    intent.putExtra("username",user_name);
                    startActivity(intent);
                }
            }
        });
        Button stulogin = (Button) findViewById(R.id.stuLogin);
        stulogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = username.getText().toString();
                pass_word = password.getText().toString();

                if (username.equals(null) && username.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Fill All The Details", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("username", user_name);
                hashMap.put("password", pass_word);

                VolleyHelper.postRequestVolley(LOGIN.this, URL_API.LOGIN_API, hashMap, RequestCodes.LOGIN, false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signup:
                startActivity(SignUpActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void requestStarted(int requestCode) {
        super.requestStarted(requestCode);
        showDialog();
    }

    @Override
    public void requestCompleted(int requestCode, String response) {
        super.requestCompleted(requestCode, response);
        dismissDialog();
        try {
            JSONObject jsonObject = new JSONObject(response);
            int i = jsonObject.getInt("success");
            if (i == 1) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
                Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
                BeanLoginInfo b = new Gson().fromJson(jsonObject.getString("ot"), BeanLoginInfo.class);
                /*                jsonObject.getJSONObject("ot").*/
                editor.putString("username", user_name).commit();
                editor.putString("password", pass_word).commit();
                editor.putString("loginInfo", jsonObject.getString("ot")).commit();
                String role=null;
                if (b.getRoleName(role).equalsIgnoreCase("admin")) {
                    startActivity(new Intent(this, AdminPanel.class));
                } else if (b.getRoleName(role).equalsIgnoreCase("student")) {
                    editor.putString("roll_no", b.getRollNo()).commit();
                    Intent intent = new Intent(this, StudentPage.class);
                    intent.putExtra("roll_no", b.getRollNo());
                    startActivity(intent);
                } else {
                    editor.putString("faculty_id", b.getFacultyID()).commit();
                    startActivity(new Intent(this, FacultyPage.class));
                }


                finish();
            } else
                Snackbar.make(findViewById(R.id.main_frame), jsonObject.getString("message"), Snackbar.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

