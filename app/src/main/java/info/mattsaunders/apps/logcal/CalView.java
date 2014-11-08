package info.mattsaunders.apps.logcal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalView extends Activity {

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");

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

    public Date stringToDate(String s){
        Date d = null;
        try {
            d = formatter.parse(s);
        } catch (ParseException e) {
            System.out.println("ERROR: Can't parse date from string!");
            e.printStackTrace();
        }
        return d;
    }

    public ArrayList<Event> buildEventList (long stTime,long enTime, ArrayList<String> eventList, ArrayList<String> descr, ArrayList<String> startDate, ArrayList<String> endDate, ArrayList<Integer> allDay,List<Long> startDateD) {
        int i = 0;
        List<Integer> index = new ArrayList<Integer>();
        for (long dateTime : startDateD) {
            if (stTime <= dateTime && enTime >= dateTime) {
                index.add(i);
            }
            i++;
        }
        ArrayList<Event> eventObjList = new ArrayList<Event>();
        i = 0;
        for (int ind : index) {
            //System.out.println(eventList.get(ind));
            Event tempEvent = new Event(eventList.get(ind),descr.get(ind),startDate.get(ind),endDate.get(ind),allDay.get(ind));
            eventObjList.add(tempEvent);
            i++;
        }
        return eventObjList;
    }

    public void displayEvent(String findView,ArrayList<Event> eventObjList) {
        int resID = getResources().getIdentifier(findView,
                "id", getPackageName());
        ListView todayItems = (ListView) findViewById(resID);

        EventDisplayAdapter adapter = new EventDisplayAdapter(this, eventObjList);

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
        ArrayList<String> endDate = Utility.endDates;
        ArrayList<String> descr = Utility.descriptions;
        ArrayList<Integer> allDayBool = Utility.allDayBool;

        //Build list of dates as milliseconds so we can compare with current/desired times, and see if we should display items
        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        List<Long> startDateD = new ArrayList<Long>();
        for (String s : startDate) {
            Date d = stringToDate(s);
            long milli = d.getTime();
            startDateD.add(milli);
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
        ArrayList<Event> eventObjectList;
        ArrayList<Event> tempEventObjectList = new ArrayList<Event>();
        for( int y=0;y<7; y++ ) {
            eventObjectList = buildEventList(stTime, enTime, eventList, descr, startDate, endDate, allDayBool,startDateD);
            // If there are items in the temp event storage, add them to main storage, and then clear temp storage
            if (tempEventObjectList.size() > 0) {
                for (Event oldEObj: tempEventObjectList) {
                    eventObjectList.add(oldEObj);
                }
                tempEventObjectList.clear();
            }

            for (Event eObj : eventObjectList){
                //Check to see if there are any all day events in the event object list
                //if there are, add them to the temp list, and set them to be removed from the object list
                if (eObj.allDay && !eObj.beenMoved) {
                    tempEventObjectList.add(eObj);
                    eObj.beenMoved = true;
                    System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||THIS OBJECT WILL BE MOVED");
                    //eventObjectList.remove(eObj);
                }

                System.out.println("PRINTING FROM OBJECT LIST ||||||||DEBUG|||||||:");
                try {
                    System.out.println(eObj.getTitle());
                    System.out.println(eObj.getStartDate());
                    System.out.println(eObj.getEndDate());
                    System.out.println(eObj.getDescription());
                    System.out.println(eObj.checkAllDay());
                } catch (Exception e) {
                    System.out.println("FAILED::::: PRINTING FROM OBJECT LIST");
                    e.printStackTrace();
                }
            }

            for (Event tempObj : tempEventObjectList) {
                eventObjectList.remove(tempObj);
            }

            displayEvent(findMeStrings[y],eventObjectList);

            //Debug print out:
            System.out.println("Y = " + y + " ||||||START TIME: " + stTimeD + " " + stTime);
            System.out.println("Y = " + y + " ||||||END TIME: " + enTimeD + " " + enTime);
            //End debug print out

            stTimeD = increaseDay(stTimeD);
            enTimeD = increaseDay(enTimeD);
            stTime = stTimeD.getTime();
            enTime = enTimeD.getTime();
        }

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
