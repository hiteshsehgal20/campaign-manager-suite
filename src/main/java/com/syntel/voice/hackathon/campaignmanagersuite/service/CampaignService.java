package com.syntel.voice.hackathon.campaignmanagersuite.service;

import com.google.actions.api.*;
import com.google.actions.api.response.ResponseBuilder;
import com.syntel.voice.hackathon.campaignmanagersuite.exception.CampaignException;
import com.syntel.voice.hackathon.campaignmanagersuite.representation.Campaign;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CampaignService extends DialogflowApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignService.class);

    List<Campaign> campaigns = new ArrayList<>();
    List<String> activeStatus = Arrays.asList("active", "live", "published");
    List<String> inActiveStatus = Arrays.asList("inactive", "unpublished");
    List<String> publishTasks = Arrays.asList("publish", "schedule");
    List<String> unpublishTasks = Arrays.asList("unpublish", "deactivate", "delete", "remove");

    @ForIntent("get_active_inactive_campaigns")
    public ActionResponse make_name(ActionRequest request) {
        LOGGER.info("get_active_inactive_campaigns");

        String task = request.getParameter("task").toString(); //add, fetch, get, publish, unpublish, delete
        String campStatus = request.getParameter("camp_status").toString(); //active, inactive, scheduled,

        String response;

        try {
            if (StringUtils.isEmpty(campStatus)) {
                campStatus = "active";
            }

            if (StringUtils.isEmpty(task)) {
                task = "list";
            }

            if (activeStatus.contains(campStatus)) { //list campaigns
                String campaigns = getCampaignsByStatus("active").toString();
                response = "Hi these are your " + campStatus + " campaigns " + campaigns;
                response = response.concat("\n What do you want to do next, you can ");
                response = response.concat("deactivate the campaign");
            }
            else  if (inActiveStatus.contains(campStatus)) {
                String campaigns = getCampaignsByStatus("inactive").toString();
                response = "Hi these are your " + campStatus + " campaigns " + campaigns;
                response = response.concat("\n What do you want to do next, you can ");
                response = response.concat("activate the campaign");
            }
            else if ("dev".equalsIgnoreCase(task)) {
                response =  clearCampaigns() + addTestCampaigns();
            } else {
                throw new CampaignException();
            }
        } catch (CampaignException ce) {
            ce.printStackTrace();
            response = "Sorry, could I not understand your request !";
        }

        ResponseBuilder responseBuilder = getResponseBuilder(request).add(response);
        ActionResponse actionResponse = responseBuilder.build();
        LOGGER.info(actionResponse.toString());
        LOGGER.info("get_active_inactive_campaigns end.");
        return actionResponse;
    }

    @ForIntent("publish_or_unpublish_campaign")
    public ActionResponse askForUserCommand(ActionRequest request) {
        LOGGER.info("publish_or_unpublish_campaign");

        ActionContext context = request.getContext("awaiting_publish_or_unpublish_id");
        LOGGER.info("Got context with name {}", context.getName());
        Map parameters = context.getParameters();

        String task = parameters.get("campaign_task").toString();

        int campaignId = (int) request.getParameter("campaign_id");

        String response;

        try {
            if(publishTasks.contains(task)) {
                campaigns.get(campaignId).setStatus("active");
                response = "Campaign with ID " + campaignId + " has been published";
            } else if(unpublishTasks.contains(task)) {
                campaigns.get(campaignId).setStatus("inactive");
                response = "Campaign with ID " + campaignId + " has been unpublished";
            } else {
                throw new CampaignException();
            }
        } catch (CampaignException ce) {
            ce.printStackTrace();
            response = "Sorry, could I not understand your request !";
        }

        ResponseBuilder responseBuilder = getResponseBuilder(request).add(response);
        ActionResponse actionResponse = responseBuilder.build();
        LOGGER.info(actionResponse.toString());
        LOGGER.info("publish_or_unpublish_campaign end.");
        return actionResponse;
    }

    @ForIntent("create_campaign")
    public ActionResponse createCampaign(ActionRequest request) {
        LOGGER.info("create_campaign");

        String title = request.getParameter("title").toString();
        String description = request.getParameter("description").toString();

        int ID = campaigns.size()+1;
        campaigns.add(new Campaign(ID, title, description, "inactive"));

        String response = "Your campaign has been created with ID " + ID;

        ResponseBuilder responseBuilder = getResponseBuilder(request).add(response);
        ActionResponse actionResponse = responseBuilder.build();
        LOGGER.info(actionResponse.toString());
        LOGGER.info("create_campaign end.");
        return actionResponse;
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

    private String clearCampaigns() {
        campaigns = new ArrayList<>();
        return "Campaigns cleared.";
    }

    private String addTestCampaigns() {

        Campaign campaign1 = new Campaign(1, "Hackathon Test", "This is the test hackathon campaign", "active");
        Campaign campaign2 = new Campaign(2, "Banking Test", "This is the test banking campaign", "inactive");
        Campaign campaign3 = new Campaign(3, "healthcare Test", "This is the test healthcare campaign", "active");
        Campaign campaign4 = new Campaign(4, "manufacturing Test", "This is the test manufacturing campaign", "inactive");

        campaigns.add(campaign1);
        campaigns.add(campaign2);
        campaigns.add(campaign3);
        campaigns.add(campaign4);

        return "Test Campaigns Added.";
    }
}
