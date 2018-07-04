package com.medoske.www.redhealpatient.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.medoske.www.redhealpatient.Items.Mobile;
import com.medoske.www.redhealpatient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2/3/16.
 */
public class MobileAdapter extends RecyclerView.Adapter<MobileAdapter.ViewHolder> {

    private Context context;
    private List<Mobile> mobiles = new ArrayList<Mobile>();
    List<String> selectedId  = new ArrayList<String>();

    public MobileAdapter(Context context, List<Mobile> mobiles) {
        this.context = context;
        this.mobiles = mobiles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cardView = inflater.inflate(R.layout.item_child, null, false);
        ViewHolder viewHolder = new ViewHolder(cardView);

        viewHolder.testName = (TextView) cardView.findViewById(R.id.txtTestName);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {



        holder.testName.setText(mobiles.get(position).getTestName());

        Log.e("testNAme1234",""+mobiles.get(position).getTestId());

        holder.checkBox.setTag(position);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                   // add into arraylist
                    selectedId.add(mobiles.get(position).getTestId());

                    Toast.makeText(context,"Checked True"+mobiles.get(position).getTestId(),Toast.LENGTH_SHORT).show();
                }
                else {
                    // remove from arraylist
                    selectedId.remove(mobiles.get(position).getTestId());

                    Toast.makeText(context,"false"+position,Toast.LENGTH_SHORT).show();
                }
            }
        });




        holder.testName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,""+position,Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mobiles.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView testName;
        CheckBox checkBox;


        public ViewHolder(View itemView) {
            super(itemView);

            testName = (TextView) itemView.findViewById(R.id.txtTestName);
            checkBox =(CheckBox)itemView.findViewById(R.id.checkBox);

        }
    }

}
