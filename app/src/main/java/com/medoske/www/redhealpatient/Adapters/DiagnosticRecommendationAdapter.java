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

import com.medoske.www.redhealpatient.DoctorsListActivity;
import com.medoske.www.redhealpatient.Items.DianoRecomItem;
import com.medoske.www.redhealpatient.Items.SpecilizationItem;
import com.medoske.www.redhealpatient.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by USER on 1.9.17.
 */

public class DiagnosticRecommendationAdapter extends RecyclerView.Adapter<DiagnosticRecommendationAdapter.MyViewHolder> {
    Context context;
    private List<DianoRecomItem> list;

    public DiagnosticRecommendationAdapter(Context context, List<DianoRecomItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public DiagnosticRecommendationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diagno_recommendation_item, parent, false);
        DiagnosticRecommendationAdapter.MyViewHolder holder = new DiagnosticRecommendationAdapter.MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final DiagnosticRecommendationAdapter.MyViewHolder holder, final int position) {

        DianoRecomItem currentItem =list.get(position);

        holder.diagnoText.setText(currentItem.getTestName());

       /* try {
            Picasso.with(context).load(currentItem.getImagepath()).into(holder.imageview);
        }catch (Exception e){
            e.printStackTrace();
        }*/

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*SpecilizationItem specilizationItem = list.get(position);

                Intent intent = new Intent(context, DoctorsListActivity.class);
                intent.putExtra("specilization", specilizationItem.getSpecialization());
                intent.putExtra("specializationId", specilizationItem.getSpecializationId());
                intent.putExtra("latitude",specilizationItem.getPresentLatitude());
                Log.e("LATITUDE :",""+specilizationItem.getPresentLatitude());
                intent.putExtra("longitude", specilizationItem.getPresentLongitude());
                intent.putExtra("placeName", specilizationItem.getPresentPlaceName());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);*/
            }
        });


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView diagnoText;
        public LinearLayout linearLayout;


        public MyViewHolder(View v) {
            super(v);
            diagnoText=(TextView)v.findViewById(R.id.diagText);
           // diagnoText.setTypeface(Typeface.createFromAsset(context.getAssets(),"Fonts/Roboto-Regular.ttf"));

            linearLayout=(LinearLayout) v.findViewById(R.id.defaltLayout);


        }
    }
}


