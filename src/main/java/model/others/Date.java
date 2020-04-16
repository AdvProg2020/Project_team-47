package model.others;

public class Date {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public Date() {
    }

    public String getDateInfoForSending(){return null;}

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public static Date getDateWithString(String date){return null;}
    public static boolean isDateFormatValid(String date){return true;}
    public static boolean validityOfStartingAndFinishDate(Date startTime,Date finishTime){return true;}
    public static Date getCurrentDate(){return null;}
}
