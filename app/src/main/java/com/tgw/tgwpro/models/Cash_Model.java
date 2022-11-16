package com.tgw.tgwpro.models;

public class Cash_Model {

    private String id;
    private String company_name;
    private String task_verification;
    private String amount;
    private String pay_date;
    private boolean isCashable;

    public Cash_Model(){
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getTask_verification() {
        return task_verification;
    }

    public void setTask_verification(String task_verification) {
        this.task_verification = task_verification;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPay_date() {
        return pay_date;
    }

    public void setPay_date(String pay_date) {
        this.pay_date = pay_date;
    }

    public boolean isCashable() {
        return isCashable;
    }

    public void setCashable(boolean cashable) {
        isCashable = cashable;
    }
}
