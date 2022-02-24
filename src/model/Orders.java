package model;

import java.io.Serializable;

public class Orders implements Serializable {
    private String username;
    private String firstMeal;
    private String secondMeal;
    private String price;
    private String date;
    private int hour;

    public Orders(String username, String firstMeal, String secondMeal, String price, String date, int hour){
        this.username = username;
        this.firstMeal = firstMeal;
        this.secondMeal = secondMeal;
        this.price = price;
        this.date = date;
        this.hour = hour;
    }

    @Override
    public String toString(){
        return username + " " + firstMeal + " " + secondMeal + " " + price + " " + date + " " + hour + "\n";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstMeal() {
        return firstMeal;
    }

    public void setFirstMeal(String firstMeal) {
        this.firstMeal = firstMeal;
    }

    public String getSecondMeal() {
        return secondMeal;
    }

    public void setSecondMeal(String secondMeal) {
        this.secondMeal = secondMeal;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
