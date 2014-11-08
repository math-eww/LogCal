package info.mattsaunders.apps.logcal;

/**
 * Build an object to store the event information:
 */
public class Event {
    public String title;
    public String description;
    public String startDate;
    public String endDate;
    public boolean allDay;
    public boolean beenMoved = false; //helper attribute to deal with all day events

    public Event(String title, String description, String startDate, String endDate, int allDay) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        if (allDay > 0) {
            this.allDay = true;
        } else {
            this.allDay = false;
        }
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
