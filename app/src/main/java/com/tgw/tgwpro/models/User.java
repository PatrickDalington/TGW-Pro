package com.tgw.tgwpro.models;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;

public class User {

    private String lName;
    private String id;
    private String refId;
    private String First_Name;
    private String Last_Name;
    private String Phone;
    private String Gift_Reward;
    private String Cash_Reward;
    private String searh_name;
    private String Country;
    private long Time_Registered;
    private String status;
    private String typing;

    public User(){}

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getGift_Reward() {
        return Gift_Reward;
    }

    public void setGift_Reward(String gift_Reward) {
        Gift_Reward = gift_Reward;
    }

    public String getCash_Reward() {
        return Cash_Reward;
    }

    public void setCash_Reward(String cash_Reward) {
        Cash_Reward = cash_Reward;
    }

    public String getSearh_name() {
        return searh_name;
    }

    public void setSearh_name(String searh_name) {
        this.searh_name = searh_name;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public long getTime_Registered() {
        return Time_Registered;
    }

    public void setTime_Registered(long time_Registered) {
        Time_Registered = time_Registered;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTyping() {
        return typing;
    }

    public void setTyping(String typing) {
        this.typing = typing;
    }
}
