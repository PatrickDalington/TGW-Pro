package com.tgw.tgwpro.models;

public class Promo_Model {



    private String title;
    private long time;
    private String logoUrl;
    private String reward;
    private String rewardType;
    private String availability;
    private String expiring_date;
    private String time_visibility;
    private String promo_type;
    private String key;
    private String promo_task;
    private String description;
    private String about;
    private String how_to_earn;
    private String est_amount;

    public Promo_Model(){

    }


    public String getHow_to_earn() {
        return how_to_earn;
    }

    public void setHow_to_earn(String how_to_earn) {
        this.how_to_earn = how_to_earn;
    }

    public String getEst_amount() {
        return est_amount;
    }

    public void setEst_amount(String est_amount) {
        this.est_amount = est_amount;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPromo_task() {
        return promo_task;
    }

    public void setPromo_task(String promo_task) {
        this.promo_task = promo_task;
    }

    public String getPromo_type() {
        return promo_type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPromo_type(String promo_type) {
        this.promo_type = promo_type;
    }

    public String getExpiring_date() {
        return expiring_date;
    }

    public void setExpiring_date(String expiring_date) {
        this.expiring_date = expiring_date;
    }

    public String getTime_visibility() {
        return time_visibility;
    }

    public void setTime_visibility(String time_visibility) {
        this.time_visibility = time_visibility;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }
}
