package info.mattsaunders.apps.logcal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EventDisplayAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Event> mEvents;
    private String mRowLayout;
    private Context mContext;

    public EventDisplayAdapter(Context context, List<Event> events, String rowLayout) {
        mInflater = LayoutInflater.from(context);
        mEvents = events;
        mRowLayout = rowLayout;
        mContext = context;

    }

    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return mEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;

        if(convertView == null) {
            //Get the ID for R.layout.mRowLayout
            int resID = mContext.getResources().getIdentifier(mRowLayout, "layout", mContext.getPackageName());
            view = mInflater.inflate(resID, parent, false);

            //view = mInflater.inflate(R.layout.row_layout, parent, false);
            holder = new ViewHolder();

            holder.title = (TextView)view.findViewById(R.id.title);
            if (mRowLayout.equals("row_layout")) {
                holder.time = (TextView) view.findViewById(R.id.time);
                holder.description = (TextView) view.findViewById(R.id.description);
            } else if (mRowLayout.equals("row_layout2")) {
                holder.time = (TextView) view.findViewById(R.id.time);
            }
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        Event event = mEvents.get(position);
        holder.title.setText(event.getTitle());
        if (mRowLayout.equals("row_layout") || mRowLayout.equals("row_layout2")) {
            if (!event.checkAllDay()) {
                holder.time.setText(event.getStartDate().substring(10).trim().replaceFirst("^0+(?!$)", ""));
            } else {
                holder.time.setText("All Day");
            }
            holder.time.setTextSize(8);
            //holder.time.setTextColor(ColorStateList.valueOf(R.color.white));
        }
        if (mRowLayout.equals("row_layout")) {
            if (event.getDescription() != null) {
                holder.description.setText(event.getDescription());
                holder.description.setTextSize(8);
                //holder.description.setTextColor(ColorStateList.valueOf(R.color.white));
            }
        }

        holder.title.setTextSize(15);
        //holder.title.setTextColor(ColorStateList.valueOf(R.color.white));

        if (mRowLayout.equals("row_layout3")) {
            holder.title.setTextSize(12);
        }



        return view;
    }

    private class ViewHolder {
        public TextView title, time, description;
    }
}