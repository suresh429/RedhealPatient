package com.medoske.www.redhealpatient.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.medoske.www.redhealpatient.DoctorsListActivity;
import com.medoske.www.redhealpatient.Items.CurrentItem;
import com.medoske.www.redhealpatient.Items.SpecilizationItem;
import com.medoske.www.redhealpatient.Items.TopSpelitiesItem;
import com.medoske.www.redhealpatient.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by USER on 17.8.17.
 */

public class TopSpecialitiesRecyclerview extends RecyclerView.Adapter<TopSpecialitiesRecyclerview.MyViewHolder> {
    Context context;
    private List<TopSpelitiesItem> list;

    public TopSpecialitiesRecyclerview(Context context, List<TopSpelitiesItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public TopSpecialitiesRecyclerview.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_specialities_item, parent, false);
        TopSpecialitiesRecyclerview.MyViewHolder holder = new TopSpecialitiesRecyclerview.MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final TopSpecialitiesRecyclerview.MyViewHolder holder, final int position) {

        TopSpelitiesItem currentItem =list.get(position);

        holder.speclilitiesName.setText(currentItem.getSpecialization());
        holder.speclilitiesName.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));

        try {
            Picasso.with(context).load(currentItem.getImage()).into(holder.imageView);
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TopSpelitiesItem specilizationItem = list.get(position);

                Intent intent = new Intent(context, DoctorsListActivity.class);
                intent.putExtra("specilization", specilizationItem.getSpecialization());
                intent.putExtra("specializationId", specilizationItem.getId());
                intent.putExtra("latitude",specilizationItem.getPresentLatitude());
                Log.e("LATITUDE :",""+specilizationItem.getPresentLatitude());
                intent.putExtra("longitude", specilizationItem.getPresentLongitude());
                intent.putExtra("placeName", specilizationItem.getLocality());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView speclilitiesName;
        public ImageView imageView;
        public LinearLayout linearLayout;


        public MyViewHolder(View v) {
            super(v);
            speclilitiesName=(TextView)v.findViewById(R.id.txtSpeclilities);
            imageView=(ImageView)v.findViewById(R.id.imageView);

            linearLayout=(LinearLayout)v.findViewById(R.id.defaltLayout);


        }
    }
}
