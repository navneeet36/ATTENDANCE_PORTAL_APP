package com.example.hp.attendamce_portal.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hp.attendamce_portal.Activities.ManageFaculty;
import com.example.hp.attendamce_portal.R;
import com.example.hp.attendamce_portal.Utils.RequestCodes;
import com.example.hp.attendamce_portal.Utils.URL_API;
import com.example.hp.attendamce_portal.Utils.VolleyHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class SearchFaculty extends BaseFragment {
ManageFaculty mainActivity;
    public static SearchFaculty newInstance(int sectionNumber) {
        SearchFaculty fragment = new SearchFaculty();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (ManageFaculty) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_search_faculty, container, false);
        final EditText facultyid=(EditText)v.findViewById(R.id.facultyid);
        Button button=(Button)v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roll=facultyid.getText().toString();
                if (roll.equals(null) ) {
                    Toast.makeText(getContext(), "Please Fill Facultyid", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("faculty_id", roll);

                    VolleyHelper.postRequestVolley(getActivity(),SearchFaculty.this, URL_API.SearchFaculty, hashMap, RequestCodes.SearchFaculty, false);

                }
            }
        });
        return  v;
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
            if (i == 1)
            {
                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

            } else
                Snackbar.make(getView(), jsonObject.getString("message"), Snackbar.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}



