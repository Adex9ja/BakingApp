package com.example.adeolu.bakingapp.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adeolu.bakingapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ADEOLU on 6/5/2017.
 */

public class StepsAdapter extends  RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    private List<JSonResponse.Steps> steps;
    private StepsListener mListener;
    private Context context;
    public StepsAdapter(List<JSonResponse.Steps> vsteps, StepsListener vListener,Context vcontext){
        steps = vsteps;
        mListener = vListener;
        context = vcontext;
    }
    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.steps_card_view,parent,false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {

        holder.itemView.setTag(steps.get(position).getId());
        holder.description.setText(steps.get(position).getDescription());
        holder.step_image.setImageResource(getAppropriateResource(position));
    }

    private int getAppropriateResource(int position) {
        switch (position){
            case 0:
                return R.drawable.rnd1;
            case 1:
                return R.drawable.rnd2;
            case 2:
                return R.drawable.rnd3;
            case 3:
                return R.drawable.rnd4;
            case 4:
                return R.drawable.rnd5;
            case 5:
                return R.drawable.rnd6;
            case 6:
                return R.drawable.rnd7;
            case 7:
                return R.drawable.rnd8;
            case 8:
                return R.drawable.rnd9;
            case 9:
                return R.drawable.rnd10;
            case 10:
                return R.drawable.rnd11;
            case 11:
                return R.drawable.rnd12;
            case 12:
                return R.drawable.rnd13;
            case 13:
                return R.drawable.rnd14;
            default:
                return R.drawable.rnd14;
        }
    }

    @Override
    public int getItemCount() {
        return steps == null ? 0 : steps.size();
    }

    public interface StepsListener{
        void onClick(int id, String stepsList);
    }
    public class StepsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.description) TextView description;
        @BindView(R.id.step_image) ImageView step_image;
        public StepsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClick(steps.get(getPosition()).getId(),new Gson().toJson(steps));
                }
            });
        }
    }
}
