package com.example.hp.attendamce_portal.Fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hp.attendamce_portal.Activities.StudentPage;
import com.example.hp.attendamce_portal.Adapers.TableAdapter;
import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.Utils.DividerItemDecoration;
import com.example.hp.attendamce_portal.Utils.RequestCodes;
import com.example.hp.attendamce_portal.Utils.URL_API;
import com.example.hp.attendamce_portal.Utils.VolleyHelper;
import com.example.hp.attendamce_portal.pojo.BeanTable;
import com.example.hp.attendamce_portal.pojo.TableList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Table2 extends BaseFragment {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    String rno;
    ArrayList<BeanTable> attendancelist;
    TableAdapter adapter;
    StudentPage mainActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (StudentPage) activity;
    }
    public static Table2 newInstance(int sectionNumber) {
        Table2 fragment = new Table2();
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
        View v = inflater.inflate(R.layout.fragment_table2, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    recyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

        SharedPreferences editor = PreferenceManager.getDefaultSharedPreferences(getActivity());

        rno = editor.getString("roll_no", "-1 ");
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("roll_no", rno);
        VolleyHelper.postRequestVolley(getActivity(), Table2.this, URL_API.Recievetable2, hashMap, RequestCodes.Recievetable2, false);

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
    public void requestCompleted(int requestCode, String response) {
        super.requestCompleted(requestCode, response);
        if (mainActivity != null)
            mainActivity.dismissDialog();

        try {
            JSONObject jsonObject = new JSONObject(response);

            int i = jsonObject.getInt("success");
            if (i == 1) {

                attendancelist = new ArrayList<BeanTable>();
                attendancelist = new Gson().fromJson(jsonObject.get("attendance").toString(), new TypeToken<ArrayList<BeanTable>>() {
                }.getType());
                ArrayList<TableList> arrayList = new ArrayList<>();
                for (BeanTable b : attendancelist) {
                    arrayList.add(new TableList(b.getSubjectId(),b.getTotalLecture(),b.getIsPresent()));
                }

                adapter = new TableAdapter(getContext(), arrayList);
                recyclerView.setAdapter(adapter);

                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            } else
                Snackbar.make(getView(), jsonObject.getString("message"), Snackbar.LENGTH_LONG).show();

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }
}

