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

    public Event(String title, String description, String startDate, String endDate, boolean allDay) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.allDay = allDay;
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
