package it.unimore.awd.ShadesAPI.Classes;

public class Rules {
    private String name;
    private Integer priority;
    private Time startTime;
    private Time endTime;
    private Integer closedPercentage;

    public Rules(){

    }

    public Rules(String name, Integer priority, Integer startTimeHours, Integer startTimeMinutes, Integer endTimeHours ,Integer endTimeMinutes, Integer closedPercentage) {
        super();
        this.name = name;
        this.priority = priority;
        this.startTime=new Time(startTimeHours,startTimeMinutes);
        this.endTime=new Time(endTimeHours,endTimeMinutes);
        this.closedPercentage = closedPercentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Integer getClosedPercentage() {
        return closedPercentage;
    }

    public void setClosedPercentage(Integer closedPercentage) {
        this.closedPercentage = closedPercentage;
    }
}
