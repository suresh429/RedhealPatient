package com.medoske.www.redhealpatient;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.medoske.www.redhealpatient.Adapters.QuestionsAdapter;
import com.medoske.www.redhealpatient.Items.Question;
import com.medoske.www.redhealpatient.Items.Tag;
import com.yalantis.filter.adapter.FilterAdapter;
import com.yalantis.filter.animator.FiltersListItemAnimator;
import com.yalantis.filter.listener.FilterListener;
import com.yalantis.filter.widget.Filter;
import com.yalantis.filter.widget.FilterItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FeedsActivity extends AppCompatActivity implements FilterListener<Tag> {

    private RecyclerView mRecyclerView;

    private int[] mColors;
    private String[] mTitles;
    private List<Question> mAllQuestions;
    private Filter<Tag> mFilter;
    private QuestionsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feeds");

        ImagePipelineConfig config = ImagePipelineConfig
                .newBuilder(this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config);

        mColors = getResources().getIntArray(R.array.colors);
        mTitles = getResources().getStringArray(R.array.job_titles);

        mFilter = (Filter<Tag>) findViewById(R.id.filter);
        mFilter.setAdapter(new Adapter(getTags()));
        mFilter.setListener(this);

        //the text to show when there's no selected items
        mFilter.setNoSelectedItemText(getString(R.string.str_all_selected));
        mFilter.build();


        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter = new QuestionsAdapter(this, mAllQuestions = getQuestions()));
        mRecyclerView.setItemAnimator(new FiltersListItemAnimator());
    }

    private void calculateDiff(final List<Question> oldList, final List<Question> newList) {
        DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldList.size();
            }

            @Override
            public int getNewListSize() {
                return newList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
            }
        }).dispatchUpdatesTo(mAdapter);
    }

    private List<Tag> getTags() {
        List<Tag> tags = new ArrayList<>();

        for (int i = 0; i < mTitles.length; ++i) {
            tags.add(new Tag(mTitles[i], mColors[i]));
        }

        return tags;
    }

    @Override
    public void onNothingSelected() {
        if (mRecyclerView != null) {
            mAdapter.setQuestions(mAllQuestions);
            mAdapter.notifyDataSetChanged();
        }
    }

    private List<Question> getQuestions() {
        return new ArrayList<Question>() {{
            add(new Question("Kiranmai", "Cardiologist.",
                    "http://tribzap2it.files.wordpress.com/2014/09/hannah-simone-new-girl-season-4-cece.jpg", "Nov 20, 6:12 PM",
                    "What is the first step to transform an idea into an actual project?","Tip Title one", new ArrayList<Tag>() {{
                add(new Tag(mTitles[2], mColors[2]));

            }}));
            add(new Question("Anju", "Gynecologist",
                    "http://weknowyourdreams.com/images/girl/girl-03.jpg", "Nov 20, 3:48 AM",
                    "What is your biggest frustration with taking your business/career (in a corporate) to the next level?","Tip Title two", new ArrayList<Tag>() {{
                add(new Tag(mTitles[1], mColors[1]));

            }}));
            add(new Question("Hema", "Hand surgeon",
                    "http://www.viraldoza.com/wp-content/uploads/2014/03/8876509-lily-pretty-girl.jpg", "Nov 20, 6:12 PM",
                    "What is the first step to transform an idea into an actual project?","Tip Title three", new ArrayList<Tag>() {{
                add(new Tag(mTitles[7], mColors[7]));

            }}));
            add(new Question("Sagarika", "Neonatologist",
                    "http://kingofwallpapers.com/girl/girl-019.jpg", "Nov 20, 6:12 PM",
                    "What is the first step to transform an idea into an actual project?","Tip Title four", new ArrayList<Tag>() {{
                add(new Tag(mTitles[3], mColors[3]));

            }}));
            add(new Question("Indu", "Neurologist",
                    "http://tribzap2it.files.wordpress.com/2014/09/hannah-simone-new-girl-season-4-cece.jpg", "Nov 20, 6:12 PM",
                    "What is the first step to transform an idea into an actual project?","Tip Title five", new ArrayList<Tag>() {{
                add(new Tag(mTitles[1], mColors[1]));

            }}));
        }};
    }

    private List<Question> findByTags(List<Tag> tags) {
        List<Question> questions = new ArrayList<>();

        for (Question question : mAllQuestions) {
            for (Tag tag : tags) {
                if (question.hasTag(tag.getText()) && !questions.contains(question)) {
                    questions.add(question);
                }
            }
        }

        return questions;
    }

    @Override
    public void onFiltersSelected(@NotNull ArrayList<Tag> filters) {
        List<Question> newQuestions = findByTags(filters);
        List<Question> oldQuestions = mAdapter.getQuestions();
        mAdapter.setQuestions(newQuestions);
        calculateDiff(oldQuestions, newQuestions);
    }

    @Override
    public void onFilterSelected(Tag item) {
        if (item.getText().equals(mTitles[0])) {
            mFilter.deselectAll();
            mFilter.collapse();
        }
    }

    @Override
    public void onFilterDeselected(Tag item) {

    }

    class Adapter extends FilterAdapter<Tag> {

        Adapter(@NotNull List<? extends Tag> items) {
            super(items);
        }

        @NotNull
        @Override
        public FilterItem createView(int position, Tag item) {
            FilterItem filterItem = new FilterItem(FeedsActivity.this);

            filterItem.setStrokeColor(mColors[0]);
            filterItem.setTextColor(mColors[0]);
            filterItem.setCornerRadius(14);
            filterItem.setCheckedTextColor(ContextCompat.getColor(FeedsActivity.this, android.R.color.white));
            filterItem.setColor(ContextCompat.getColor(FeedsActivity.this, android.R.color.white));
            filterItem.setCheckedColor(mColors[position]);
            filterItem.setText(item.getText());
            filterItem.deselect();

            return filterItem;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

