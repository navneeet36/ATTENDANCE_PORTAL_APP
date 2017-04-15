package com.example.hp.attendamce_portal.Utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.attendamce_portal.R;


/**
 * Created by arpitkh96 on 20/9/16.
 */

public class CustomDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View rootView = inflater.inflate(R.layout.progressbar, container,
                    false);
        return rootView;
    }

}
