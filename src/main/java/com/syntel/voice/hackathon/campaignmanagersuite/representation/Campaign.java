package com.syntel.voice.hackathon.campaignmanagersuite.representation;

import java.time.Instant;
import java.util.Date;

public class Campaign {
    private int id;
    private String name;
    private String campaignText;
    private String status;
    private Instant liveDate;

    public Campaign(int id, String name, String campaignText, String status) {
        this.id = id;
        this.name = name;
        this.campaignText = campaignText;
        this.status = status;
        this.liveDate = Instant.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCampaignText() {
        return campaignText;
    }

    public void setCampaignText(String campaignText) {
        this.campaignText = campaignText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getLiveDate() {
        return liveDate;
    }

    public void setLiveDate(Instant liveDate) {
        this.liveDate = liveDate;
    }
}
