package sparx1126.com.powerup.custom_layouts;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import sparx1126.com.powerup.R;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private final List<String> expandableListTitle;
    private final HashMap<String, List<String>> expandableListDetail;

    public CustomExpandableListAdapter(Context _context, List<String> _expandableListTitle,
                                       HashMap<String, List<String>> _expandableListDetail) {
        context = _context;
        expandableListTitle = _expandableListTitle;
        expandableListDetail = _expandableListDetail;
    }

    @Override
    public Object getChild(int _listPosition, int _expandedListPosition) {
        return expandableListDetail.get(this.expandableListTitle.get(_listPosition)).get(_expandedListPosition);
    }

    @Override
    public long getChildId(int _listPosition, int _expandedListPosition) {
        // NOT IMPLEMENTED
        return _expandedListPosition;
    }

    @Override
    public View getChildView(int _listPosition, final int _expandedListPosition,
                             boolean _isLastChild, View _convertView, ViewGroup _parent) {
        final String expandedListText = (String) getChild(_listPosition, _expandedListPosition);
        if (_convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView)_convertView.findViewById(R.id.expandedListItem);
        expandedListTextView.setText(Html.fromHtml(expandedListText));
        return _convertView;
    }

    @Override
    public int getChildrenCount(int _listPosition) {
        return expandableListDetail.get(this.expandableListTitle.get(_listPosition)).size();
    }

    @Override
    public Object getGroup(int _listPosition) {
        return expandableListTitle.get(_listPosition);
    }

    @Override
    public int getGroupCount() {
        return expandableListTitle.size();
    }

    @Override
    public long getGroupId(int _listPosition) {
        // NOT IMPLEMENTED
        return _listPosition;
    }

    @Override
    public View getGroupView(int _listPosition, boolean _isExpanded,
                             View _convertView, ViewGroup _parent) {
        String listTitle = (String)getGroup(_listPosition);
        if (_convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView)_convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return _convertView;
    }

    @Override
    public boolean hasStableIds() {
        // NOT IMPLEMENTED
        return false;
    }

    @Override
    public boolean isChildSelectable(int _listPosition, int _expandedListPosition) {
        // NOT IMPLEMENTED
        return true;
    }
}