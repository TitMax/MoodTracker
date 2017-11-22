package com.boussaingault.maxime.moodtracker.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Maxime Boussaingault on 15/11/2017.
 */

public class MoodData {

    private String mood;
    private String color;
    private String comment;
    private String date;

    public MoodData() {}

    public MoodData(String mood, String comment) {
        this.mood = mood;
        this.comment = comment;
    }

    public MoodData(String date, String mood, String comment, String color) {
        this.mood = mood;
        this.color = color;
        this.comment = comment;
        this.date = date;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDaysAgo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] number = {"", "", "", "trois", "quatre", "cinq", "six"};
        long days = 0;
        try {
            Date newDate = sdf.parse(date);
            days = (new Date().getTime() - newDate.getTime()) / 86400000;
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        if(days == 0) return "Aujourd'hui";
        else if(days == 1) return "Hier";
        else if(days == 2) return "Avant-hier";
        else if(days == 7) return "Il y a une semaine";
        else return "Il y a " + number[(int) days] + " jours";
    }

    @Override
    public String toString() {
        return getDaysAgo() + " " + mood + " " + comment + " " + color ;
    }
}
