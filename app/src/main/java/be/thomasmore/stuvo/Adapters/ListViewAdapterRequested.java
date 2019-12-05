package be.thomasmore.stuvo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import be.thomasmore.stuvo.Models.Activity;
import be.thomasmore.stuvo.R;

public class ListViewAdapterRequested extends ArrayAdapter<Activity> implements View.OnClickListener{

    private List<Activity> activities;
    Context context;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtDate;
        TextView txtDescription;
        LinearLayout rowItem;
    }

    public ListViewAdapterRequested(List<Activity> data, Context context) {
        super(context, R.layout.row_item_requested, data);
        this.activities = data;
        this.context = context;

    }

    @Override
    public void onClick(View v) {

//        int position=(Integer) v.getTag();
//        Object object= getItem(position);
//        Activity activity=(Activity)object;
//
//        switch (v.getId())
//        {
//            case R.id.rowItem:
//                Snackbar.make(v, "Release date " +activity.getName(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Activity activity = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_requested, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.rowItemName);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.rowItemDate);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.rowItemDescription);
//            viewHolder.rowItem = (LinearLayout) convertView.findViewById(R.id.rowListView);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(activity.getName());
        viewHolder.txtDate.setText(activity.getDate());
        viewHolder.txtDescription.setText(activity.toString());
//        viewHolder.rowItem.setOnClickListener(this);
//        viewHolder.rowItem.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}