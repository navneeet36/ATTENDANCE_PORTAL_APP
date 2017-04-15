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
import com.example.hp.attendamce_portal.pojo.BeanBranchInfo;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;


public class AddBranchInfo extends BaseFragment {
//static boolean d=true;
    public static AddBranchInfo newInstance(int sectionNumber) {
        AddBranchInfo fragment = new AddBranchInfo();
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
        View v= inflater.inflate(R.layout.fragment_add_branch_info, container, false);
        final EditText branchid=(EditText)v.findViewById(R.id.branchid);
        final EditText branchname=(EditText)v.findViewById(R.id.branchname);
        final   EditText totalsem=(EditText)v.findViewById(R.id.totalsem);
        final   Button addbranch=(Button)v.findViewById(R.id.add);
        final Button deletebranch=(Button)v.findViewById(R.id.delete);
        final Button updatebranch=(Button)v.findViewById(R.id.update);
        addbranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean allvalid = true;

                if (branchname.getText().toString().matches("") || branchid.getText().toString().matches("") || totalsem.getText().toString().matches("") ) {
                    allvalid = false;
                    Toast.makeText(getContext(), "Fill All The Blanks", Toast.LENGTH_SHORT).show();
                }
                if (allvalid) {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    BeanBranchInfo b = new BeanBranchInfo();
                    b.setBranchID(branchid.getText().toString());
                    b.setBranchName(branchname.getText().toString());
                    b.setTotalSem(totalsem.getText().toString());
                    Gson gson = new Gson();
                    hashMap.put("data", gson.toJson(b));
                    VolleyHelper.postRequestVolley(getActivity(), AddBranchInfo.this, URL_API.NewBranch, hashMap, RequestCodes.NewBranch, false);
                }
            }
        });

        updatebranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean allvalid = true;

                if (branchname.getText().toString().matches("") || branchid.getText().toString().matches("") || totalsem.getText().toString().matches("") ) {
                    allvalid = false;
                    Toast.makeText(getContext(), "Fill All The Blanks", Toast.LENGTH_SHORT).show();
                }
                if (allvalid) {
  //                  showDialog();
    //                if(!d) {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        BeanBranchInfo b = new BeanBranchInfo();
                        b.setBranchID(branchid.getText().toString());
                        b.setBranchName(branchname.getText().toString());
                        b.setTotalSem(totalsem.getText().toString());
                        Gson gson = new Gson();
                        hashMap.put("data", gson.toJson(b));
                        VolleyHelper.postRequestVolley(getActivity(), AddBranchInfo.this, URL_API.UpdateBranch, hashMap, RequestCodes.UpdateBranch, false);
      //              }
                }
            }
        });

        deletebranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean allvalid = true;
                String branch_id=branchid.getText().toString();

                if ( branchid.getText().toString().matches("")  ) {
                    allvalid = false;
                    Toast.makeText(getContext(), "Fill All The Blanks", Toast.LENGTH_SHORT).show();
                }
                if (allvalid) {
        //            showDialog();
          //          if (!d) {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("branch_id", branch_id);
                        VolleyHelper.postRequestVolley(getActivity(), AddBranchInfo.this, URL_API.DeleteBranch, hashMap, RequestCodes.DeleteBranch, false);
                    }
            //    }
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
            /*    void showDialog()
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





