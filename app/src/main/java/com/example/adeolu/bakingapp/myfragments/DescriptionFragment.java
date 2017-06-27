package com.example.adeolu.bakingapp.myfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adeolu.bakingapp.DetailActivity;
import com.example.adeolu.bakingapp.R;
import com.example.adeolu.bakingapp.utils.JSonResponse;
import com.example.adeolu.bakingapp.utils.StepsAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ADEOLU on 6/16/2017.
 */
public class DescriptionFragment extends Fragment  {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static TextView description;
    private static String desc;

    public DescriptionFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DescriptionFragment newInstance(String vdesc) {
        DescriptionFragment fragment = new DescriptionFragment();
        desc = vdesc;
        return fragment;
    }

    public static void swapDescription(String vdesc){
        description.setText(vdesc);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.description_layout, container, false);
        description = (TextView) rootView.findViewById(R.id.description);
        description.setText(desc);
        return rootView;
    }


}
