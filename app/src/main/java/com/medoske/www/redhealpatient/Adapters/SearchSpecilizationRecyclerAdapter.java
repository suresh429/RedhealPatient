package com.medoske.www.redhealpatient.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medoske.www.redhealpatient.DoctorsListActivity;
import com.medoske.www.redhealpatient.Items.ClinicSearchItem;
import com.medoske.www.redhealpatient.Items.SpecailizationSearchItem;
import com.medoske.www.redhealpatient.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by USER on 22.8.17.
 */

public class SearchSpecilizationRecyclerAdapter extends RecyclerView.Adapter<SearchSpecilizationRecyclerAdapter.MyViewHolder> {
    Context context;
    private List<SpecailizationSearchItem> list;

    public SearchSpecilizationRecyclerAdapter(Context context, List<SpecailizationSearchItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public SearchSpecilizationRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.searchspecailizationlist_item, parent, false);
        SearchSpecilizationRecyclerAdapter.MyViewHolder holder = new SearchSpecilizationRecyclerAdapter.MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final SearchSpecilizationRecyclerAdapter.MyViewHolder holder, final int position) {

        SpecailizationSearchItem currentItem =list.get(position);

        holder.specialization.setText(currentItem.getSpecialization());


        /*try {
            Picasso.with(context).load(currentItem.getClinicImage()).placeholder(R.drawable.ic_action_placeholder).into(holder.imageView);
        }catch (Exception e){
            e.printStackTrace();
        }*/

        holder.searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpecailizationSearchItem specailizationSearchItem = list.get(position);

                Intent intent = new Intent(context, DoctorsListActivity.class);
                intent.putExtra("specilization", specailizationSearchItem.getSpecialization());
                intent.putExtra("specializationId", specailizationSearchItem.getId());
                intent.putExtra("latitude",specailizationSearchItem.getPresentLatitude());
                intent.putExtra("longitude", specailizationSearchItem.getPresentLongitude());
                intent.putExtra("placeName", specailizationSearchItem.getPresentPlaceName());
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

        public TextView specialization;
        public LinearLayout searchLayout;

        public MyViewHolder(View v) {
            super(v);
            specialization=(TextView)v.findViewById(R.id.txtSearchSpecialization);
            specialization.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));

            searchLayout=(LinearLayout)v.findViewById(R.id.searchLayout);

        }
    }
}




