package com.example.plugins.tutorial.rest;

import javax.xml.bind.annotation.*;
@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class MyIssueGetModel {

    @XmlElement(name = "value")
    private String message;

    public MyIssueGetModel() {
    }

    public MyIssueGetModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}