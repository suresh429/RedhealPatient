package com.medoske.www.redhealpatient.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.medoske.www.redhealpatient.Items.AlternateTreatmentItem;
import com.medoske.www.redhealpatient.Items.TopSpelitiesItem;
import com.medoske.www.redhealpatient.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by USER on 17.8.17.
 */

public class AlternateRecyclerAdapter extends RecyclerView.Adapter<AlternateRecyclerAdapter.MyViewHolder> {
    Context context;
    private List<AlternateTreatmentItem> list;

    public AlternateRecyclerAdapter(Context context, List<AlternateTreatmentItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public AlternateRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alternate_treatments_item, parent, false);
        AlternateRecyclerAdapter.MyViewHolder holder = new AlternateRecyclerAdapter.MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final AlternateRecyclerAdapter.MyViewHolder holder, int position) {

        AlternateTreatmentItem currentItem =list.get(position);

       /* holder.txtTreatment.setText(currentItem.getName());
        holder.txtTreatment.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));*/

        Picasso.with(context).load(currentItem.getImage()).into(holder.imageView);


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTreatment;
        public ImageView imageView;


        public MyViewHolder(View v) {
            super(v);
           // txtTreatment=(TextView)v.findViewById(R.id.txtTreatment);
            imageView=(ImageView)v.findViewById(R.id.imageView);


        }
    }
}

