package com.yuanlrc.campus_market.entity;


public enum KillStates {

    IN_VALID(-1, "无效"),
    SUCCESS(0, "成功"),
    PAY(1,"已付款");

    private int code;
    private String name;

    KillStates(){}

    KillStates(int code, String name){
        this.code = code;
        this.name = name;
    }


    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    
}
