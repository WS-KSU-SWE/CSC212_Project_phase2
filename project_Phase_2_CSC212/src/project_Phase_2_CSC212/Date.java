package project_Phase_2_CSC212;

public class Date {

	private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    
    public int getIntDate() {
    	return (year*100 + month)*100 + day; // ex 2025/5/4 --> 20250504 < 20250506 --> 2025/5/6
    }
    
    
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String toString() {
    	return day + "/" + month + "/" + year;
    }
    
}
