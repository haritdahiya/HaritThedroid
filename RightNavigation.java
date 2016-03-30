package com.zorbando.harit.zorbandocontests;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by harit on 2/28/2016.
 */
public class RightNavigation extends BaseExpandableListAdapter
{
    private Context context;
    private ArrayList<String> headerlist;
   private ArrayList<ArrayList<ChildList>> childlist;
    private LayoutInflater inflater;
    ExpandableListView expandableListView;

    public RightNavigation(Context context, ArrayList<String> headerlist,ArrayList<ArrayList<ChildList>> childlist)
    {
        this.context = context;
        this.headerlist = headerlist;
        this.childlist = childlist;
        inflater = LayoutInflater.from(context);

    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
       String gt= (String)  getGroup(groupPosition);
        View v = null;
        if (convertView != null)
        {
            v = convertView;
        }
        else
        {
            v = inflater.inflate(R.layout.right_row,parent,false);

            TextView header = (TextView) v.findViewById(R.id.menuright);
            if( gt != null )
                header.setText( gt );

        }
        return v;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
       View v =null;
        if (convertView!=null)
        {
            v=convertView;
        }
        else {
            v = inflater.inflate(R.layout.filter,parent,false);
            ChildList cl = (ChildList)getChild(groupPosition,childPosition);
            TextView li = (TextView) v.findViewById(R.id.checkchildname);
            if(li!=null)
            {
                li.setText(cl.getHeader());
            }
            CheckBox cb = (CheckBox) v.findViewById(R.id.checkBoxchild);
            cb.setChecked(cl.getState());

        }
        return v;
    }
    @Override
    public int getGroupCount() {
        return headerlist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childlist.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headerlist.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childlist.get(groupPosition).get(childPosition);
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
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void onGroupCollapsed (int groupPosition) {}
    public void onGroupExpanded(int groupPosition) {}


}
