package info.mattsaunders.apps.logcal;

import java.util.Date;

/**
 * Build an object to store the event information:
 */
public class eventObj {
    public String title;
    public String description;
    public Date startDate;
    public Date endDate;
    public boolean allDay;
    public eventObj() {
        super();
    }

    public eventObj(String title, String description, Date startDate, Date endDate, boolean allDay) {
        //super(title,description,startDate,endDate,allDay);
        super();
        title = this.title;
        description = this.description;
        startDate = this.startDate;
        endDate = this.endDate;
        allDay = this.allDay;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public boolean checkAllDay() {
        return allDay;
    }


}
