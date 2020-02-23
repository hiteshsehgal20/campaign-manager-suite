package com.syntel.voice.hackathon.campaignmanagersuite.representation;

public class Campaign {
    private String campaignId;
    private String campaignName;

    public Campaign(String campaignId, String campaignName) {
        this.campaignId = campaignId;
        this.campaignName = campaignName;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }
}
