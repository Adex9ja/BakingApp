package com.example.adeolu.bakingapp.utils;

import android.content.Context;
import android.net.Uri;
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
        String imageURL = steps.get(position).getThumbnailURL();
        if (!TextUtils.isEmpty(imageURL)) {
            Uri builtUri = Uri.parse(imageURL).buildUpon().build();
            Picasso.with(context).load(builtUri).error(R.drawable.cake).into(holder.step_image);
        }
        else{
            Picasso.with(context).load(R.drawable.cake).into(holder.step_image);
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
