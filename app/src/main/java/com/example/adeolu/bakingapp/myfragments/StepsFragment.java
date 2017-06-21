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

import com.example.adeolu.bakingapp.DetailActivity;
import com.example.adeolu.bakingapp.R;
import com.example.adeolu.bakingapp.RecipeStepPlayer;
import com.example.adeolu.bakingapp.utils.StepsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ADEOLU on 6/16/2017.
 */
public class StepsFragment extends Fragment implements StepsAdapter.StepsListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    @BindView(R.id.list_steps)
    RecyclerView step_list;

    public StepsFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.steps_layout, container, false);
        ButterKnife.bind(this, rootView);

        step_list.setLayoutManager(new LinearLayoutManager(container.getContext()));
        step_list.setAdapter(new StepsAdapter(DetailActivity.steps, this, container.getContext()));
        step_list.addItemDecoration(new DividerItemDecoration(step_list.getContext(), DividerItemDecoration.VERTICAL));
        return rootView;
    }

    @Override
    public void onClick(int id, String stepsList) {
        Intent intent = new Intent(getActivity(), RecipeStepPlayer.class);
        intent.putExtra(getString(R.string.id), id);
        intent.putExtra(getString(R.string.description), stepsList);
        intent.putExtra(getString(R.string.recipe_name), DetailActivity.name);
        startActivity(intent);
    }
}
