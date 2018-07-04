package com.medoske.www.redhealpatient.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.medoske.www.redhealpatient.Items.Question;
import com.medoske.www.redhealpatient.Items.Tag;
import com.medoske.www.redhealpatient.R;

import java.util.List;

/**
 * Created by galata on 16.09.16.
 */
public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {
    int i = 0;
    private List<Question> mQuestions;
    private Context mContext;

    public QuestionsAdapter(Context context, List<Question> questions) {
        mContext = context;
        mQuestions = questions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false));
    }

    public List<Question> getQuestions() {
        return mQuestions;
    }

    public void setQuestions(List<Question> questions) {
        this.mQuestions = questions;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Question question = mQuestions.get(position);

        holder.avatar.setImageURI(question.getAuthorAvatar());
        holder.textAuthorName.setText(question.getAuthorName());
        holder.textJobTitle.setText(question.getAuthorJobTitle());
        holder.textDate.setText(question.getDate());
        holder.textQuestion.setText(question.getText());
        holder.secondFilter.setText(question.getTipTitle());

        Tag firstTag = question.getTags().get(0);
        holder.firstFilter.setText(firstTag.getText());
        /*Tag secondTag = question.getTags().get(1);
        holder.secondFilter.setText(secondTag.getText());*/

        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(1000);
        drawable.setColor(firstTag.getColor());
        holder.firstFilter.setBackgroundDrawable(drawable);
        GradientDrawable drawable1 = new GradientDrawable();
        drawable1.setCornerRadius(1000);
       // drawable1.setColor(secondTag.getColor());
       // holder.secondFilter.setBackgroundDrawable(drawable1);

        holder.appCompatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        holder.appCompatImageViewLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub

                i++;
                Handler handler = new Handler();
                Runnable r = new Runnable() {

                    @Override
                    public void run() {
                        i = 0;
                    }
                };

                /*int j =0;
                String countPlus = Integer.toString(j)+1;
                //String countMinus = Integer.toString(j)-1;*/


                if (i == 1) {
                    //Single click
                    holder.appCompatImageViewLikes.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                   // holder.likesCount.setText(countPlus);
                    Log.e("sunday123",""+holder.likesCount);

                } else if (i == 2) {
                    //Double click
                    i = 0;
                    holder.appCompatImageViewLikes.setBackgroundResource(R.drawable.ic_heart);
                   // holder.likesCount.setText(null);
                    Log.e("sunday123456",""+holder.likesCount);

                }

            }
        });
    }

    private int getColor(int color) {
        return ContextCompat.getColor(mContext, color);
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textAuthorName;
        TextView textJobTitle;
        TextView textDate;
        TextView textQuestion;
        TextView firstFilter;
        TextView secondFilter;
        TextView likesCount;
        SimpleDraweeView avatar;
        AppCompatImageView appCompatImageView;
        AppCompatImageView appCompatImageViewLikes;

        public ViewHolder(View itemView) {
            super(itemView);

            textAuthorName = (TextView) itemView.findViewById(R.id.text_name);
            textJobTitle = (TextView) itemView.findViewById(R.id.text_job_title);
            textDate = (TextView) itemView.findViewById(R.id.text_date);
            textQuestion = (TextView) itemView.findViewById(R.id.text_question);
            firstFilter = (TextView) itemView.findViewById(R.id.filter_first);
            secondFilter = (TextView) itemView.findViewById(R.id.filter_second);
            likesCount = (TextView) itemView.findViewById(R.id.text_likes_count);
            avatar = (SimpleDraweeView) itemView.findViewById(R.id.avatar);
            appCompatImageView=(AppCompatImageView)itemView.findViewById(R.id.view_settings);
            appCompatImageViewLikes=(AppCompatImageView)itemView.findViewById(R.id.favorite_nice);
        }
    }
}
