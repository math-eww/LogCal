package info.mattsaunders.apps.logcal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalView extends Activity {


    public Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_view);

        //toggle debug
        boolean debugMe = false;

        //Set text labels for days:
        //First get what day it is
        int weekDay;
        Calendar calendar = Calendar.getInstance();
        weekDay = calendar.get(Calendar.DAY_OF_WEEK);

        if (debugMe) {
            System.out.println(weekDay);
        }

        //Set strings to corresponding days for display
        String day3 = "test";
        String day4 = "test";
        String day5 = "test";
        String day6 = "test";
        String day7 = "test";
        //Switch based on int weekDay to determine what string each day should be set to
        switch (weekDay) {
            case 1: //Sunday
                day3 = "TUESDAY";
                day4 = "WEDNESDAY";
                day5 = "THURSDAY";
                day6 = "FRIDAY";
                day7 = "SATURDAY";
                break;
            case 2: //Monday
                day3 = "WEDNESDAY";
                day4 = "THURSDAY";
                day5 = "FRIDAY";
                day6 = "SATURDAY";
                day7 = "SUNDAY";
                break;
            case 3: //Tuesday
                day3 = "THURSDAY";
                day4 = "FRIDAY";
                day5 = "SATURDAY";
                day6 = "SUNDAY";
                day7 = "MONDAY";
                break;
            case 4: //Wednesday
                day3 = "FRIDAY";
                day4 = "SATURDAY";
                day5 = "SUNDAY";
                day6 = "MONDAY";
                day7 = "TUESDAY";
                break;
            case 5: //Thursday
                day3 = "SATURDAY";
                day4 = "SUNDAY";
                day5 = "MONDAY";
                day6 = "TUESDAY";
                day7 = "WEDNESDAY";
                break;
            case 6: //Friday
                day3 = "SUNDAY";
                day4 = "MONDAY";
                day5 = "TUESDAY";
                day6 = "WEDNESDAY";
                day7 = "THURSDAY";
                break;
            case 7: //Saturday
                day3 = "MONDAY";
                day4 = "TUESDAY";
                day5 = "WEDNESDAY";
                day6 = "THURSDAY";
                day7 = "FRIDAY";

                break;
        }
        if (debugMe) {
            System.out.println(day3);
        }
        //Finally print out the correct labels for the days of the week
        TextView text=(TextView)findViewById(R.id.dayThree);
        text.setText(day3);
        text=(TextView)findViewById(R.id.dayFour);
        text.setText(day4);
        text=(TextView)findViewById(R.id.dayFive);
        text.setText(day5);
        text=(TextView)findViewById(R.id.daySix);
        text.setText(day6);
        text=(TextView)findViewById(R.id.daySeven);
        text.setText(day7);

        //Get calendar events: query google calendar for event list, sort by date

        //SETTING DATES //for today
        Date stTimeD = new Date();
        stTimeD = getStartOfDay(stTimeD);
        //String stTimeD = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        long stTime = stTimeD.getTime();

        Date enTimeD = new Date();
        enTimeD = getEndOfDay(enTimeD);
        //String stTimeD = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        long enTime = enTimeD.getTime();



        //Debug Print outs: //////////////////////////////////////////////////////////////////////
        System.out.println("START TIME: " + stTimeD + " " + stTime);
        System.out.println("END TIME: " + enTimeD + " " + enTime);
        //////////////////////////////////////////////////////////////////////////////////////////


        //Call Utility.java to get events within dates
        Context context = getApplicationContext();
        Utility.readCalendarEvent(context);
        ArrayList<String> eventList = Utility.nameOfEvent;
        ArrayList<String> startDate = Utility.startDates;
        //ArrayList<String> endDate = Utility.endDates;
        ArrayList<String> descr = Utility.descriptions;

        //long[] startDateD = new long[startDate.size()];
        List<Long> startDateD = new ArrayList<Long>();

        //DEBUG Check lists for items - should all have same num of items//////////////////////////

        System.out.println(eventList);
        System.out.println(startDate);
        System.out.println(descr);

        System.out.println(eventList.size());
        System.out.println(startDate.size());
        System.out.println(descr.size());

        //Build list of dates as milliseconds so we can compare with current/desired times, and see if we should display items
        for (String s : startDate) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
            //Date d = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH).parse(s);
            try {
                Date d = formatter.parse(s);
                long mili = d.getTime();
                //System.out.println(mili);
                startDateD.add(mili);
            } catch (ParseException e) {
                System.out.println("ERROR: Can't parse date from string!");
                e.printStackTrace();
            }
        }

        System.out.println("StartDateD built! Length: " + startDateD.size());



        //Determine where to display items, if at all: index array stores integers indicating which elements of eventList, etc, fall withing specific times.
        int i = 0;
        List<Integer> index = new ArrayList<Integer>();
        for (long dateTime : startDateD) {
            if (stTime <= dateTime && enTime >= dateTime) {
                index.add(i);
            }
        i++;
        }
        System.out.println("Printing events that fall within time " + stTimeD + " and " + enTimeD);
        String[] values = new String[index.size()];
        i = 0;
        for (int ind : index) {
            System.out.println(eventList.get(ind));
            values[i] = eventList.get(ind);
            i++;
        }

        System.out.println(values);

        //get place to store values
        ListView todayItems = (ListView) findViewById( R.id.todayItems );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        todayItems.setAdapter(adapter);







        /*
        //make this a method, to accept stTime and enTime as start date and end date - ie one call for each day displayed
        Uri CALENDAR_URI = CalendarContract.Events.CONTENT_URI;
        Context context;
        context = getApplicationContext();
        Cursor cursors = context.getContentResolver().query(CALENDAR_URI, new String[]{ "_id", "title", "description", "dtstart", "dtend", "eventLocation" },
                null,null, null);

        cursors.moveToFirst();
        String[] CalNames = new String[cursors.getCount()]; //String array of calendar names
        int[] CalIds = new int[cursors.getCount()]; //Int array of calendar IDs

        //Debug Print Outs:///////////////////////////////////////////////////////////////////////
        System.out.println("Begin Looping through Calendars to find events within start and end time");
        System.out.println("CalNames Length = " + CalNames.length);
        /////////////////////////////////////////////////////////////////////////////////////////

        //Begin loop through returned calendars
        for (int i = 0; i < CalNames.length; i++) {
            CalIds[i] = cursors.getInt(0);
            CalNames[i] = "Event" + cursors.getInt(0) + ": \nTitle: " + cursors.getString(1) + "\nDescription: " + cursors.getString(2) + "\nStart Date: " + new Date(cursors.getLong(3)) + "\nEnd Date : " + new Date(cursors.getLong(4)) + "\nLocation : " + cursors.getString(5);

            Date mDate = new Date(cursors.getLong(3)); //gets event time
            Date nDate = new Date(cursors.getLong(4)); //gets event time

            long mTime = mDate.getTime();
            long lTime = nDate.getTime();
            //Debug Print Outs://////////////////////////////////////////////////////////////////
            System.out.println("mDate = " + mDate + " / nDate = " + nDate + " / mTime = " + mTime + " / lTime = " + lTime);
            //System.out.println("CalNames::i:: " + CalNames[i]);
            /////////////////////////////////////////////////////////////////////////////////////

            if (stTime <= mTime && enTime >= lTime) { //checks if event occurs within day we're looking at
                String eid = cursors.getString(0); //event ID

                int eID = Integer.parseInt(eid); //event ID int

                String desc = cursors.getString(2); //event description
                String title = cursors.getString(1); //event title

                //Debug Print Outs:///////////////////////////////////////////////////////////////
                System.out.println("EVENT LISTING: "+ eid + " " + eID + " - " + title + " " + desc);
                //////////////////////////////////////////////////////////////////////////////////
            }
        }

        */
        //end get events by date
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cal_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
