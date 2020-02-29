package com.syntel.voice.hackathon.campaignmanagersuite.service;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.syntel.voice.hackathon.campaignmanagersuite.exception.CampaignException;
import com.syntel.voice.hackathon.campaignmanagersuite.representation.Campaign;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CampaignService extends DialogflowApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignService.class);

    List<Campaign> campaigns = new ArrayList<>();
    List<String> getTasks = Arrays.asList("list", "fetch", "get", "grab", "hear", "see");

    @ForIntent("user_operations")
    public ActionResponse make_name(ActionRequest request) {
        LOGGER.info("operation_options");

        String task = request.getParameter("task").toString(); //add, fetch, get, publish, unpublish, delete
        String campStatus = request.getParameter("camp_status").toString(); //active, inactive, scheduled,

        String response;

        try {
            response = executeAction(task, campStatus);
        } catch (CampaignException ce) {
            response = "Exception aai re";
        }

        ResponseBuilder responseBuilder = getResponseBuilder(request).add(response);
        ActionResponse actionResponse = responseBuilder.build();
        LOGGER.info(actionResponse.toString());
        LOGGER.info("make_name end.");
        return actionResponse;
    }

    @ForIntent("prepare_dev_env")
    public ActionResponse prepareDevEnv(ActionRequest request) {
        LOGGER.info("prepareDevEnv");

        String response = clearCampaigns() + addTestCampaigns();
        ResponseBuilder responseBuilder = getResponseBuilder(request).add(response);
        ActionResponse actionResponse = responseBuilder.build();
        LOGGER.info(actionResponse.toString());
        LOGGER.info("prepareDevEnv end.");
        return actionResponse;
    }

    private String executeAction(String task, String campStatus) {
        if (StringUtils.isEmpty(campStatus)) {
            campStatus = "active";
        }
        if (getTasks.contains(task)) { //list campaigns
            String campaigns = getCampaignsByStatus(campStatus).toString();
            String response = "Hi these are your " + campStatus + "campaigns" + campaigns;
            response.concat("\n What do you want to do next, you can");
            if (campStatus.equalsIgnoreCase("active")) {
                response.concat("either publish or deactivate the campaign");
            }
            return response;
        } else if ("publish".equalsIgnoreCase(task)) {
            return "Campaign Published";
        } else if ("dev".equalsIgnoreCase(task)) {
            return clearCampaigns() + addTestCampaigns();
        }

        throw new CampaignException();
    }

    private List<String> getCampaignsByStatus(String status) {
        List<String> camps = new ArrayList<>();
        for (Campaign camp : campaigns) {
            if (camp.getStatus().equalsIgnoreCase(status)) {
                camps.add(camp.getCampaignText());
            }
        }
        return camps;
    }

    private List<String> reduceToString(List<Campaign> campaigns) {
        List<String> camps = new ArrayList<>();
        for (Campaign camp : campaigns) {
            camps.add(camp.getCampaignText());
        }
        return camps;
    }

    private String addCampaign(String campaignName, String campaignText) {
        //int iSize = dataStore.getInactiveCampaigns().size();
        //dataStore.getInactiveCampaigns().add(new Campaign(iSize + 1, campaignName, campaignText));

        return "Campaign Added to inactive campaigns";
        //TODO : Logic to ask if you want to schedule this campaign
    }

    private String clearCampaigns() {
        campaigns = new ArrayList<>();
        return "Campaigns cleared.";
    }

    private String addTestCampaigns() {

        Campaign campaign1 = new Campaign(1, "Hackathon Test", "This is the test hackathon campaign", "active");
        Campaign campaign2 = new Campaign(2, "Banking Test", "This is the test banking campaign", "deactive");
        Campaign campaign3 = new Campaign(2, "healthcare Test", "This is the test healthcare campaign", "active");
        Campaign campaign4 = new Campaign(2, "manufacturing Test", "This is the test manufacturing campaign", "deactive");

        campaigns.add(campaign1);
        campaigns.add(campaign2);
        campaigns.add(campaign3);
        campaigns.add(campaign4);

        return "Test Campaigns Added.";
    }
}
