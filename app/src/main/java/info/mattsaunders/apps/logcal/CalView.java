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

    String[] findMeStrings = {"todayItems",
                              "tomorrowItems",
                              "day3Items",
                              "day4Items",
                              "day5Items",
                              "day6Items",
                              "day7Items"};


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

    public Date increaseDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

     public String[] getValues(long stTime,long enTime, ArrayList<String> eventList,List<Long> startDateD) {
        int i = 0;
        List<Integer> index = new ArrayList<Integer>();
        for (long dateTime : startDateD) {
            if (stTime <= dateTime && enTime >= dateTime) {
                index.add(i);
            }
            i++;
        }

        //System.out.println("Printing events that fall within time " + stTimeD + " and " + enTimeD);
        String[] values = new String[index.size()];
        i = 0;
        for (int ind : index) {
            System.out.println(eventList.get(ind));
            values[i] = eventList.get(ind);
            i++;
        }
        return values;
    }

    public void displayItems(String findView,String[] values) {
        int resID = getResources().getIdentifier(findView,
                "id", getPackageName());
        ListView todayItems = (ListView) findViewById(resID);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        todayItems.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_view);

        ////////////////////////////Set text labels for days:////////////////////////////////////
        //First get what day it is
        int weekDay;
        Calendar calendar = Calendar.getInstance();
        weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        //Set strings to corresponding days for display
        String day3 = "test"; //placeholder strings
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


        ///////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////
        //////////Get calendar events: query google calendar for event list, sort by date//////////

        //Call Utility.java to get events within dates
        Context context = getApplicationContext();
        Utility.readCalendarEvent(context);
        ArrayList<String> eventList = Utility.nameOfEvent;
        ArrayList<String> startDate = Utility.startDates;
        //ArrayList<String> endDate = Utility.endDates;
        ArrayList<String> descr = Utility.descriptions;

        //Build list of dates as milliseconds so we can compare with current/desired times, and see if we should display items
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        List<Long> startDateD = new ArrayList<Long>();
        for (String s : startDate) {
            try {
                Date d = formatter.parse(s);
                long mili = d.getTime();
                startDateD.add(mili);
            } catch (ParseException e) {
                System.out.println("ERROR: Can't parse date from string!");
                e.printStackTrace();
            }
        }
        System.out.println("StartDateD built! Length: " + startDateD.size());

        //Setting dates, and displaying events that fall within dates. Starting with today
        Date stTimeD = new Date(); //Start time
        stTimeD = getStartOfDay(stTimeD);
        long stTime = stTimeD.getTime();

        Date enTimeD = new Date(); //End time
        enTimeD = getEndOfDay(enTimeD);
        long enTime = enTimeD.getTime();

        //For loop to go through each day, and set the appropriate events to display
        String[] values;
        for( int y=0;y<7; y++ ) {
            values = getValues(stTime,enTime,eventList,startDateD);
            displayItems(findMeStrings[y],values);

            //Debug print out:
            System.out.println("Y = " + y + " ||||||START TIME: " + stTimeD + " " + stTime);
            System.out.println("Y = " + y + " ||||||END TIME: " + enTimeD + " " + enTime);
            //End debug print out

            stTimeD = increaseDay(stTimeD);
            enTimeD = increaseDay(enTimeD);
            stTime = stTimeD.getTime();
            enTime = enTimeD.getTime();
        }

        /*
        String[] values = getValues(stTime,enTime,eventList,startDateD);
        displayItems("todayItems",values);

        stTimeD = increaseDay(stTimeD);
        enTimeD = increaseDay(enTimeD);
        stTime = stTimeD.getTime();
        enTime = enTimeD.getTime();

        values = getValues(stTime,enTime,eventList,startDateD);
        displayItems("tomorrowItems",values);

        stTimeD = increaseDay(stTimeD);
        enTimeD = increaseDay(enTimeD);
        stTime = stTimeD.getTime();
        enTime = enTimeD.getTime();

        values = getValues(stTime,enTime,eventList,startDateD);
        displayItems("day3Items",values);
        */

        ////////////////////////////////////////////////////////////////////////////////////////////
        //write loop here: Loop through all 7 days, using increaseDay(stTimeD) and increaseDay(enTimeD)
        //then convert with getEndOfDay and getStartOfDay
        //and long stTime = stTimeD.getTime();
        //
        //Find a way to handle changing R.id.todayItems to tomorrowItems and so on
        //
        //Move code into dedicated functions?
        //Move time assignments down, to consolidate
        //
        //Make events clickable, to give more detail
        //Automatically give extra detail on the Today/Tomorrow sections, but not on the rest of the days
        //
        ///////////////////////////////////////////////////////////////////////////////////////////
        /*
        Date stTimeD = new Date();
        stTimeD = getStartOfDay(stTimeD);
        long stTime = stTimeD.getTime();

        Date enTimeD = new Date();
        enTimeD = getEndOfDay(enTimeD);
        long enTime = enTimeD.getTime();
         */

        /*
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
        //This could be a a function, set to take variable findView it takes as input
        //it should go in R.id.today items making it R.id.findView
        //also takes values as variable to display the items required by the view
        //No. need to create new adapters each time,
        //System.out.println(values);
        //get place to store values
        ListView todayItems = (ListView) findViewById( R.id.todayItems );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        todayItems.setAdapter(adapter);
        */
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
