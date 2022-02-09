package com.example.aadharscanner.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.aadharscanner.R;
import com.example.aadharscanner.database.InactiveReasons;
import com.example.aadharscanner.database.ShgLoanStatusData;

import java.util.HashMap;
import java.util.List;


public class ShgReasonlistExpendableAdapter extends BaseExpandableListAdapter {

    private Context context;
    List<InactiveReasons> expandableListTitle;
    HashMap<String, List<ShgLoanStatusData>> expandableListDetail;

    public ShgReasonlistExpendableAdapter(Context context, List<InactiveReasons> expandableListTitle,
                                          HashMap<String, List<ShgLoanStatusData>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition).getReason()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition).getReason()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Nullable
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, @Nullable View convertView, ViewGroup parent) {
        InactiveReasons listTitle = (InactiveReasons) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_list_view, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle.getReason());
        return convertView;
    }

    @Nullable
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, @Nullable View convertView, ViewGroup parent) {
        final ShgLoanStatusData expandedListText = (ShgLoanStatusData) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_view, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText.getLoanStatus());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

