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
import com.medoske.www.redhealpatient.Items.TipsSeasonItem;
import com.medoske.www.redhealpatient.R;

import java.util.List;

/**
 * Created by USER on 17.8.17.
 */

public class TipsSeasonalAdapter extends RecyclerView.Adapter<TipsSeasonalAdapter.MyViewHolder> {
    Context context;
    private List<TipsSeasonItem> list;

    public TipsSeasonalAdapter(Context context, List<TipsSeasonItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public TipsSeasonalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tips_season_item, parent, false);
        TipsSeasonalAdapter.MyViewHolder holder = new TipsSeasonalAdapter.MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final TipsSeasonalAdapter.MyViewHolder holder, int position) {

        TipsSeasonItem currentItem =list.get(position);

        holder.txtTipName.setText(currentItem.getTipName());
        holder.txtTipName.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));

        holder.txtTipType.setText(currentItem.getTipType());
        holder.txtTipType.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTipName;
        public TextView txtTipType;
        public ImageView imageView;


        public MyViewHolder(View v) {
            super(v);
            txtTipName=(TextView)v.findViewById(R.id.txtTipName);
            txtTipType=(TextView)v.findViewById(R.id.txtTipType);
            imageView=(ImageView)v.findViewById(R.id.imageView);


        }
    }
}

