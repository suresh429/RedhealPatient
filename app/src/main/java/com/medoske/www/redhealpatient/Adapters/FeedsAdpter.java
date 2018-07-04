package com.medoske.www.redhealpatient.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.medoske.www.redhealpatient.Api.Apis;
import com.medoske.www.redhealpatient.Items.AddFavItem;
import com.medoske.www.redhealpatient.Items.FeedsItem;
import com.medoske.www.redhealpatient.R;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by USER on 30.6.17.
 */

public class FeedsAdpter extends RecyclerView.Adapter<FeedsAdpter.MyViewHolder> {
    String favoriteValue,redHealId,doctorId,tipId;
    int i = 0;
    private Context mContext;
    private List<FeedsItem> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textAuthorName;
        TextView textJobTitle;
        TextView textDate;
        TextView textQuestion;
        TextView firstFilter;
        TextView secondFilter;
        TextView likesCount;
        ImageView imageViewTip;
        SimpleDraweeView avatar;
        AppCompatImageView appCompatImageView;
        MaterialFavoriteButton materialFavoriteButtonNice;

        public MyViewHolder(View view) {
            super(view);
            textAuthorName = (TextView) view.findViewById(R.id.text_name);
            textJobTitle = (TextView) view.findViewById(R.id.text_job_title);
            textDate = (TextView) view.findViewById(R.id.text_date);
           // textQuestion = (TextView) view.findViewById(R.id.text_question);
            firstFilter = (TextView) view.findViewById(R.id.filter_first);
            secondFilter = (TextView) view.findViewById(R.id.filter_second);
            likesCount = (TextView) view.findViewById(R.id.text_likes_count);
            avatar = (SimpleDraweeView) view.findViewById(R.id.avatar);
            appCompatImageView=(AppCompatImageView)view.findViewById(R.id.view_settings);
            materialFavoriteButtonNice=(MaterialFavoriteButton)view.findViewById(R.id.favorite_nice);
            imageViewTip =(ImageView)view.findViewById(R.id.imageViewTip);
        }
    }


    public FeedsAdpter(Context context, List<FeedsItem> moviesList) {

        this.mContext = context;
        this.moviesList = moviesList;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feeds_item_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final FeedsItem movie = moviesList.get(position);
        holder.avatar.setImageURI(movie.getDoctorImage());
        holder.textAuthorName.setText(movie.getDoctorName());
        holder.textJobTitle.setText(movie.getSpecialization());
        holder.textDate.setText(movie.getTip_date());
       // holder.textQuestion.setText(movie.getDescription());
        Picasso.with(mContext).load(movie.getImagePath()).into(holder.imageViewTip);
        holder.secondFilter.setText(movie.getTipName());

        holder.firstFilter.setText(movie.getCategoryName());
        Log.e("catagoeryjscb",""+movie.getCategoryName());
        holder.firstFilter.setBackgroundColor(Color.parseColor(movie.getColor()));
        Log.e("catagoeryjscolor",""+movie.getColor());
        /*Tag secondTag = question.getTags().get(1);
        holder.secondFilter.setText(secondTag.getText());*/


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

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = mContext.getSharedPreferences("sharedPrefName", 0); // 0 for private mode
        redHealId = sp.getString("patientRedhealId", "1");
        Log.e("redHealId", "" + redHealId);


        holder.materialFavoriteButtonNice.setFavorite(Boolean.parseBoolean(movie.getLike_status()));
        holder.materialFavoriteButtonNice.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {


                        if (favorite) {
                            // niceCounterValue++;
                            favoriteValue ="1" ;

                            Toast.makeText(mContext,"value :"+favoriteValue,Toast.LENGTH_SHORT).show();
                        } else {
                            //niceCounterValue--;
                            favoriteValue ="0" ;

                            Toast.makeText(mContext,"value :"+favoriteValue,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        holder.materialFavoriteButtonNice.setOnFavoriteAnimationEndListener(
                new MaterialFavoriteButton.OnFavoriteAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(MaterialFavoriteButton buttonView, boolean favorite) {
                        // niceCounter.setText(String.valueOf(niceCounterValue));

                        tipId =movie.getId();
                        Log.e("tipid",""+tipId);

                        new Asyncclass().execute();

                    }
                });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }



    public String POST(String[] params, AddFavItem addFavItem){
        InputStream inputStream = null;
        String result = "";
        HttpResponse response = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Apis.MY_FAV_UPDATE_URL);

            try {

                MultipartEntity entity = new MultipartEntity();
                entity.addPart("patient_redhealId", new StringBody(addFavItem.getPatientId()));
                entity.addPart("tipId", new StringBody(addFavItem.getTipId()));
                entity.addPart("like_status", new StringBody(addFavItem.getLikeStatus()));

                httppost.setEntity(entity);
                response = httpclient.execute(httppost);

                Log.e("test", "SC:" + response.getStatusLine().getStatusCode());

                HttpEntity resEntity = response.getEntity();

                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent(), "UTF-8"));
                String sResponse;
                StringBuilder s = new StringBuilder();

                while ((sResponse = reader.readLine()) != null) {
                    s = s.append(sResponse);
                }
                Log.e("test", "Response: " + s);
            } catch (ClientProtocolException e) {


            } catch (IOException e) {
                e.printStackTrace();

            }

            // 9. receive response as inputStream
            inputStream = response.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }
    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


    private class Asyncclass extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

          /*  //Create a new progress dialog
            progressDialog = new ProgressDialog(get);
            //Set the progress dialog to display a horizontal progress bar
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //Set the dialog message to 'Loading application View, please wait...'
            progressDialog.setMessage("Uploading ...");
            //This dialog can't be canceled by pressing the back key
            progressDialog.setCancelable(true);
            //This dialog isn't indeterminate
            progressDialog.setIndeterminate(false);
            progressDialog.show();*/
        }


        @Override
        protected String doInBackground(String... params) {

            AddFavItem addFavItem = new AddFavItem();
            addFavItem.setLikeStatus(favoriteValue);
            addFavItem.setPatientId(redHealId);
            addFavItem.setTipId(tipId);

            return POST(params,addFavItem);
        }

        @Override
        protected void onPostExecute(String jsonObject) {
            // progressDialog.dismiss();
            super.onPostExecute(jsonObject);

            /*Intent in = new Intent(get.this, PastAppointmentDetailsActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(AddReportActivity.this,"Report Saved Successfully",Toast.LENGTH_SHORT).show();
            startActivity(in);*/

        }
    }


}
