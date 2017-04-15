package com.example.hp.attendamce_portal.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;

import com.example.hp.attendamce_portal.Adapers.AdminAdapter;
import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.pojo.AdminItem;

import java.util.ArrayList;

public class AdminPanel extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    GridLayoutManager linearLayoutManager;
    int columns = 2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        linearLayoutManager = new GridLayoutManager(this, columns);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int screen_width = recyclerView.getWidth();
                float dptopx = getResources().getDimension(R.dimen.column);
                columns = (int) (screen_width / dptopx);
                if (linearLayoutManager != null)
                    linearLayoutManager.setSpanCount(columns);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    recyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
        ArrayList<AdminItem> arrayList = new ArrayList<>();
      //  if (accesses.getOrderMgmt() == 1)
            arrayList.add(new AdminItem("Add Student", AddStudent.class, R.drawable.ic_action_new));
        //if (accesses.getGuestMgmt() == 1)
            arrayList.add(new AdminItem("Add Faculty", AddFaculty.class, R.drawable.ic_action_new));
       // if (accesses.getTableMgmt() == 1)
            arrayList.add(new AdminItem("Manage Student", ManageStudent.class, R.drawable.ic_create_white_24dp));
       // if (accesses.getMenuMgmt() == 1)
            arrayList.add(new AdminItem("Manage Faculty", ManageFaculty.class, R.drawable.ic_create_white_24dp));
        //if (accesses.getTableMgmt() == 1)
            arrayList.add(new AdminItem("Manage Branch", ManageBranch.class, R.drawable.ic_create_white_24dp));
       // if (accesses.getMenuMgmt() == 1)
            arrayList.add(new AdminItem("Manage Subject", ManageSubject.class, R.drawable.ic_create_white_24dp));


        AdminAdapter adminadapter = new AdminAdapter(this, arrayList);
        recyclerView.setAdapter(adminadapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //  addcredits.setText("Credits: Loading..");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     //   getMenuInflater().inflate(R.menu.dashboard_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
