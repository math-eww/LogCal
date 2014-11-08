package info.mattsaunders.apps.logcal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Build an object to store the event information:
 */
public class Event {
    public String title;
    public String description;
    public String startDate;
    public String endDate;
    public boolean allDay;
    //public Event() {        super();    }

    public Event(String title, String description, String startDate, String endDate, boolean allDay) {
        //super(title,description,startDate,endDate,allDay);
        //super();
        /*
        title = this.title;
        description = this.description;
        startDate = this.startDate;
        endDate = this.endDate;
        allDay = this.allDay;
        */
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.allDay = allDay;
        //check if it's all day:
        //if ()

    }

    private Date stringToDate(String s){
        Date d = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        try {
            d = formatter.parse(s);
        } catch (ParseException e) {
            System.out.println("ERROR: Can't parse date from string!");
            e.printStackTrace();
        }
        return d;
    }


    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getStartDate() {
        return startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public boolean checkAllDay() {
        return allDay;
    }



}
