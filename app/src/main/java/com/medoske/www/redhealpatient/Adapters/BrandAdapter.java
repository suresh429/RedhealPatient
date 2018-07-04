package com.medoske.www.redhealpatient.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.medoske.www.redhealpatient.Items.CategoryItem;
import com.medoske.www.redhealpatient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 2.9.17.
 */

public class BrandAdapter implements ExpandableListAdapter {

    private Context context;
    private List<CategoryItem> brands = new ArrayList<CategoryItem>();

    public BrandAdapter(Context context, List<CategoryItem> brands) {
        this.context = context;
        this.brands = brands;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return brands.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return brands.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return brands.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ParentHolder parentHolder = null;

        CategoryItem brand = (CategoryItem) getGroup(groupPosition);

        if(convertView == null) {
            LayoutInflater userInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = userInflater.inflate(R.layout.item_parent, null);
            convertView.setHorizontalScrollBarEnabled(true);

            parentHolder = new ParentHolder();
            convertView.setTag(parentHolder);

        } else {
            parentHolder = (ParentHolder) convertView.getTag();
        }

        parentHolder.text_Categoery = (TextView) convertView.findViewById(R.id.text_Categoery);
        parentHolder.text_Categoery.setText(brand.categoryName);

        parentHolder.indicator = (ImageView) convertView.findViewById(R.id.image_indicator);

        if(isExpanded) {
            parentHolder.indicator.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
        } else {
            parentHolder.indicator.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildHolder childHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_group_child, parent, false);
            childHolder = new ChildHolder();
            convertView.setTag(childHolder);
        }
        else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        childHolder.horizontalListView = (RecyclerView) convertView.findViewById(R.id.mobiles);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        childHolder.horizontalListView.setLayoutManager(layoutManager);

        MobileAdapter horizontalListAdapter = new MobileAdapter(context, brands.get(groupPosition).tests);
        childHolder.horizontalListView.setAdapter(horizontalListAdapter);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    private static class ChildHolder {
        static RecyclerView horizontalListView;
    }

    private static class ParentHolder {
        TextView text_Categoery;
        ImageView indicator;
    }
}