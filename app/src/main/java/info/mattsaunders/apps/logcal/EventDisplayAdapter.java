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

    public EventDisplayAdapter(Context context, List<Event> events) {
        mInflater = LayoutInflater.from(context);
        mEvents = events;
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
            view = mInflater.inflate(R.layout.row_layout, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView)view.findViewById(R.id.title);
            holder.time = (TextView)view.findViewById(R.id.time);
            holder.description = (TextView)view.findViewById(R.id.description);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        Event event = mEvents.get(position);
        holder.title.setText(event.getTitle());
        if (!event.checkAllDay()) {
            holder.time.setText(event.getStartDate().toString());
        } else {
            holder.time.setText("All Day");
        }
        if (event.getDescription() != null) {
            holder.description.setText(event.getDescription());
        }

        return view;
    }

    private class ViewHolder {
        public TextView title, time, description;
    }
}