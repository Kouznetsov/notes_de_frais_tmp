package com.a_andries.fichedepaie;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Calendar;

public class NDF implements Serializable {
    Calendar dateOfVisit; // timestamp in ms
    Calendar timeOfVisit;
    String practicianName;
    float amountNoon;
    float amountEvening;
    float amountNight;
    int justificativesCount;
    String key = null;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public float getTotalAmount() {
        return amountEvening + amountNight + amountNoon;
    }

    public Calendar getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(Calendar dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    public Calendar getTimeOfVisit() {
        return timeOfVisit;
    }

    public void setTimeOfVisit(Calendar timeOfVisit) {
        this.timeOfVisit = timeOfVisit;
    }

    public String getPracticianName() {
        return practicianName;
    }

    public void setPracticianName(String practicianName) {
        this.practicianName = practicianName;
    }

    public float getAmountNoon() {
        return amountNoon;
    }

    public void setAmountNoon(float amountNoon) {
        this.amountNoon = amountNoon;
    }

    public float getAmountEvening() {
        return amountEvening;
    }

    public void setAmountEvening(float amountEvening) {
        this.amountEvening = amountEvening;
    }

    public float getAmountNight() {
        return amountNight;
    }

    public void setAmountNight(float amountNight) {
        this.amountNight = amountNight;
    }

    public int getJustificativesCount() {
        return justificativesCount;
    }

    public void setJustificativesCount(int justificativesCount) {
        this.justificativesCount = justificativesCount;
    }
}
