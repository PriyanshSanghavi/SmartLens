package com.example.smartlens.Driver;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.smartlens.R;

import java.util.HashMap;
import java.util.List;

public class DriverExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHader;
    private HashMap<String, List<String>> listHashMap;

    public DriverExpandableListViewAdapter(Context context, List<String> listDataHader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listDataHader = listDataHader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listDataHader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listDataHader.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) this.getGroup(i);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.driver_custom_header, null);
        }
        TextView txt = (TextView) view.findViewById(R.id.dlistGroup);
        txt.setTypeface(null, Typeface.BOLD);
        txt.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) this.getChild(i,i1);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.driver_custom_childitems, null);
        }
        TextView txt = (TextView) view.findViewById(R.id.dlistChild);
        txt.setTypeface(null, Typeface.BOLD);
        txt.setText(headerTitle);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
