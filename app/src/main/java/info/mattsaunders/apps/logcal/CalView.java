package info.mattsaunders.apps.logcal;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import java.util.Calendar;

public class CalView extends Activity {

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
