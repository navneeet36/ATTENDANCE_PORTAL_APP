package com.example.hp.attendamce_portal.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hp.attendamce_portal.Fragments.ShowStudentInfo;
import com.example.hp.attendamce_portal.Fragments.Table1;
import com.example.hp.attendamce_portal.Fragments.Table2;
import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.Utils.BitmapHandler;
import com.example.hp.attendamce_portal.Utils.RequestCodes;
import com.example.hp.attendamce_portal.Utils.URL_API;
import com.example.hp.attendamce_portal.Utils.VolleyHelper;
import com.example.hp.attendamce_portal.pojo.BeanLoginInfo;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

public class StudentPage extends BaseActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    TabLayout tabLayout;
    boolean showAddFace = false;
    BeanLoginInfo b;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(mViewPager);
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        String logininfo = sharedPreferences.getString("loginInfo", "-1");
        if (!logininfo.equals("-1")) {
            b = new Gson().fromJson(logininfo, BeanLoginInfo.class);
            showAddFace = b.getFace_registered() == 0;
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_page, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (showAddFace) {
            menu.findItem(R.id.add_face).setVisible(true);
        } else
            menu.findItem(R.id.add_face).setVisible(false);
        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();

            editor.putString("username", null).commit();
            editor.putString("password", null).commit();
            editor.putString("loginInfo", null).commit();
            startActivity(new Intent(this, LOGIN.class));
            finish();
            return true;
        }
        if (id == R.id.add_face) {
            showAddFace=false;
            b.setFace_registered(1);
            sharedPreferences.edit().putString("loginInfo", new Gson().toJson(b)).commit();
            Intent intent = new Intent(this, FaceTrackerActivity.class);
            intent.putExtra("rollno",b.getRollNo());
            startActivityForResult(intent, 1);

            return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void requestStarted(int requestCode) {
        super.requestStarted(requestCode);
        showDialog();
    }

    @Override
    public void requestEndedWithError(int requestCode, VolleyError error) {
        super.requestEndedWithError(requestCode, error);
        showDialog();
    }

    @Override
    public void requestCompleted(int requestCode, String response) {
        super.requestCompleted(requestCode, response);
        dismissDialog();
        if (requestCode == RequestCodes.RegisterFace) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                int i = jsonObject.getInt("success");
                if (i == 1) {
                    Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } else
                    Snackbar.make(findViewById(R.id.main_frame), jsonObject.getString("message"), Snackbar.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return ShowStudentInfo.newInstance(position + 1);

            else if (position == 1)
                return Table1.newInstance(position + 1);
            else
                return Table2.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "PERSONAL";
                case 1:
                    return "SUBWISE ATTENDANCE";
                case 2:
                    return "VIEW ATTENDANCE";

            }
            return null;
        }
    }
}



