package sample;

public class AlarmClock {
    private int hourAlarm;
    private int minuteAlarm;
    private String dayOfWeekAlarm;
    private String setTime;

    public AlarmClock(int hourAlarm, int minuteAlarm, String dayOfWeekAlarm) {
        this.hourAlarm = hourAlarm;
        this.minuteAlarm = minuteAlarm;
        this.dayOfWeekAlarm = dayOfWeekAlarm;
        setTimeFormat();
    }

    public int getHourAlarm() {
        return hourAlarm;
    }

    public int getMinuteAlarm() {
        return minuteAlarm;
    }

    public String getDayOfWeekAlarm() {
        return dayOfWeekAlarm;
    }

    public void setTimeFormat(){
        if (hourAlarm < 10) {
            if (minuteAlarm < 10) {
                setTime = ("0" + hourAlarm + ":0" + minuteAlarm).toString();
            } else {
                setTime = ("0" + hourAlarm + ":" + minuteAlarm).toString();
            }
        } else {
            if (minuteAlarm < 10) {
                setTime = (hourAlarm + ":0" + minuteAlarm).toString();
            } else {
                setTime = (hourAlarm + ":" + minuteAlarm).toString();
            }
        }
    }

    public String getTimeFormat(){
        return setTime;
    }
}
