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

    public static int compare(Date o1, Date o2) {
        if (o2.year > o1.year) {
            return -1;
        } else if (o1.year > o2.year) {
            return 1;
        } else if (o2.month > o1.month) {
            return -1;
        } else if (o1.month > o2.month) {
            return 1;
        } else if (o2.day > o1.day) {
            return -1;
        } else if (o1.day > o2.day) {
            return 1;
        } else if (o2.hour > o1.hour) {
            return -1;
        } else if (o1.hour > o2.hour) {
            return 1;
        } else if (o2.minute > o1.minute) {
            return -1;
        } else if (o1.minute > o2.minute) {
            return 1;
        } else if (o2.second > o1.second) {
            return -1;
        } else if (o1.second > o2.second) {
            return 1;
        }
        return 1;
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
