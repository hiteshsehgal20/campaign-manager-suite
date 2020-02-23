package com.syntel.voice.hackathon.campaignmanagersuite;

import com.google.actions.api.App;
import com.syntel.voice.hackathon.campaignmanagersuite.service.CampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CampaignManagerSuiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampaignManagerSuiteApplication.class, args);
    }

}