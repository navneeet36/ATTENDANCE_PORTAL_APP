package com.example.hp.attendamce_portal.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.VolleyError;
import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.Utils.BitmapHandler;
import com.example.hp.attendamce_portal.Utils.RequestCodes;
import com.example.hp.attendamce_portal.Utils.URL_API;
import com.example.hp.attendamce_portal.Utils.VolleyHelper;
import com.example.hp.attendamce_portal.pojo.BeanAttendance;
import com.example.hp.attendamce_portal.pojo.BeanLoginInfo;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

public class SignUpActivity extends BaseActivity {
    EditText rollno, facultyid, username, email, pass, cpass, securityans;
    Button signup;
    RadioButton rb1, rb2;
    private String[] arraySpinner;
    AppCompatSpinner securityques;
    Toolbar toolbar;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rb1 = (RadioButton) findViewById(R.id.rbstu);
        rb2 = (RadioButton) findViewById(R.id.rbFac);
        rollno = (EditText) findViewById(R.id.roll);
        facultyid = (EditText) findViewById(R.id.roll);
        username = (EditText) findViewById(R.id.fName);
        securityques = (AppCompatSpinner) findViewById(R.id.security_Question);
        securityans = (EditText) findViewById(R.id.security_Answer);
        email = (EditText) findViewById(R.id.Email);
        cpass = (EditText) findViewById(R.id.cpass);
        signup = (Button) findViewById(R.id.btn_signup);
        pass = (EditText) findViewById(R.id.pass);
        this.arraySpinner = new String[]{
                "Please Select Security Question", " Which is your favourite colour ?", "Who is your best friend ?",
                "Which is your favourite sport ?", "What is your maiden name ?"
        };
        MyCustomAdapter adapter = new MyCustomAdapter(this, R.layout.spinner_row, arraySpinner);
        securityques.setAdapter(adapter);
        //   securityques.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allvalid = true;
                if (username.getText().toString().matches("") || securityques.getSelectedItemPosition() == 0 || securityans.getText().toString().matches("") || email.getText().toString().matches("") || cpass.getText().toString().matches("") || pass.getText().toString().matches("")) {
                    allvalid = false;
                    Toast.makeText(getApplicationContext(), "Fill All The Blanks", Toast.LENGTH_SHORT).show();
                }
                String faculty, rolln;
                if (rb1.isChecked()) {
                    faculty = null;
                    rolln = rollno.getText().toString();
                    role = rb1.getText().toString();
                } else {
                    faculty = facultyid.getText().toString();
                    rolln = null;
                    role = rb2.getText().toString();
                }
                if (!isValidEmail(email.getText())) {
                    allvalid = false;
                    Toast.makeText(getApplicationContext(), "Check your email", Toast.LENGTH_SHORT).show();
                }

                if (!pass.getText().toString().equals(cpass.getText().toString())) {
                    allvalid = false;
                    Toast.makeText(getApplicationContext(), "Password not matched", Toast.LENGTH_SHORT).show();
                }
                if (allvalid) {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    BeanLoginInfo b = new BeanLoginInfo();
                    b.setRollNo(rolln);
                    b.setFacultyID(faculty);
                    b.setUserName(username.getText().toString());
                    b.setRoleName(role);
                    b.setPassword(pass.getText().toString());
                    b.setEmailID(email.getText().toString());
                    b.setSecurityAnswer(securityans.getText().toString());
                    b.setSecurityQuestion(arraySpinner[securityques.getSelectedItemPosition()]);
                    Gson gson = new Gson();
                    hashMap.put("data", gson.toJson(b));
                 /*   hashMap.put("roll_no",rollno.getText().toString());
                    hashMap.put("faculty_id",facultyid.getText().toString());
                    hashMap.put("username",username.getText().toString());
                    hashMap.put("role_name",roll);
                    hashMap.put("Security_Answer",securityans.getText().toString());
                    hashMap.put("Security_Question",arraySpinner[securityques.getSelectedItemPosition()]);
                    hashMap.put("email_id",email.getText().toString());
                    hashMap.put("password",pass.getText().toString());*/
                    VolleyHelper.postRequestVolley(SignUpActivity.this, URL_API.SIGN_In, hashMap, RequestCodes.SIGN_IN, false);
                }
            }
        });

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, LOGIN.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(LOGIN.class);

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
        if (requestCode == RequestCodes.SIGN_IN) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                int i = jsonObject.getInt("success");
                if (i == 1) {
                    Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    if(jsonObject.get("role").toString().equals("student"))
                    showFaceAddDialog();
                    else finish();
                } else
                    Snackbar.make(findViewById(R.id.main_frame), jsonObject.getString("message"), Snackbar.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (requestCode == RequestCodes.RegisterFace) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                int i = jsonObject.getInt("success");
                if (i == 1) {
                    Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Snackbar.make(findViewById(R.id.main_frame), jsonObject.getString("message"), Snackbar.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void showFaceAddDialog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        builder.title("Add face?").content("Do you want to add face?").positiveText("yes").negativeText("no").onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Intent intent = new Intent(SignUpActivity.this, FaceTrackerActivity.class);
                intent.putExtra("rollno", rollno.getText().toString());
                startActivityForResult(intent, 1);
            }
        }).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                finish();
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 5) {
            Bitmap bitmap = BitmapHandler.getBitmap();
            HashMap<String, String> hash = new HashMap<String, String>();
            String roll = data.getStringExtra("rollno");
            hash.put("roll_no", roll);
            VolleyHelper.uploadImage(this, URL_API.RegisterFace, hash, RequestCodes.RegisterFace, bitmap);
        }
    }

    public class MyCustomAdapter extends ArrayAdapter<String> {

        public MyCustomAdapter(Context context, int textViewResourceId,
                               String[] objects) {
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

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.spinner_row, parent, false);
            TextView label = (TextView) row.findViewById(android.R.id.text1);
            label.setTextColor(Color.BLACK);
            label.setText(getItem(position));

            return row;
        }
    }

}
