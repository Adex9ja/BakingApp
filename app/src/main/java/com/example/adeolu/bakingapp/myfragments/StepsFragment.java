package com.example.adeolu.bakingapp.myfragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adeolu.bakingapp.DetailActivity;
import com.example.adeolu.bakingapp.R;
import com.example.adeolu.bakingapp.utils.JSonResponse.Ingredients;
import com.example.adeolu.bakingapp.utils.StepsAdapter;

/**
 * Created by ADEOLU on 6/16/2017.
 */
public class StepsFragment extends Fragment implements StepsAdapter.StepsListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    TextView textView;
    private RecyclerView step_list;
    static String STACK_RECIPE_STEP_DETAIL="STACK_RECIPE_STEP_DETAIL";
    public static String description,uristring;
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
        textView = (TextView)rootView.findViewById(R.id.recipe_detail_text);
        step_list = (RecyclerView) rootView.findViewById(R.id.list_steps);
        step_list.setAdapter(new StepsAdapter(DetailActivity.steps, this, getContext()));
        step_list.addItemDecoration(new DividerItemDecoration(step_list.getContext(), DividerItemDecoration.VERTICAL));
        loadIngredients();
        return rootView;
    }

    private void loadIngredients() {
        textView.append(getString(R.string.ingredients) + "\n");
        for(Ingredients str: DetailActivity.ingredients){
            textView.append("\u2022 "+ str.getIngredient());
            textView.append("\t ("+str.getQuantity().toString());
            textView.append(" - "+str.getMeasure()+")\n");
        }
    }
    @Override
    public void onClick(int id, String stepsList) {
        description = DetailActivity.steps.get(id).getDescription();
        uristring = DetailActivity.steps.get(id).getVideoURL();
        if(DetailActivity.TwoPane){
            RecipePlayerFragment.swapVideo(uristring,description);
        }
        else {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, new RecipePlayerFragment().newInstance(uristring,description)).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit();
        }

    }
}
