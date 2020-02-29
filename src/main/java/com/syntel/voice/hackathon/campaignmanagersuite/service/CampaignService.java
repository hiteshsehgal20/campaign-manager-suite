package com.syntel.voice.hackathon.campaignmanagersuite.service;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.syntel.voice.hackathon.campaignmanagersuite.util.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CampaignService extends DialogflowApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignService.class);

    @ForIntent("user_operations")
    public ActionResponse make_name(ActionRequest request) {
        LOGGER.info("operation_options");

        String task = request.getParameter("task").toString();
        List<String> campaignList = getCampaignList(task);
        String response = "Alright, here are " + task + " campaigns." +campaignList.toString();
        ResponseBuilder responseBuilder = getResponseBuilder(request).add(response);
        ActionResponse actionResponse = responseBuilder.build();
        LOGGER.info(actionResponse.toString());
        LOGGER.info("make_name end.");
        return actionResponse;
    }

    private List<String> getCampaignList(String task) {
        if("Scheduled".equalsIgnoreCase(task)|| "inactive".equalsIgnoreCase(task)){
            return DataStore.scheduledCampaigns;
        }
        else{
            return DataStore.activeCampaigns;
        }
    }
}
