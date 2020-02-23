package com.syntel.voice.hackathon.campaignmanagersuite.service;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CampaignService extends DialogflowApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignService.class);
    @ForIntent("operation_options")
    public ActionResponse make_name(ActionRequest request) {
        LOGGER.info("operation_options");
        String task = request.getParameter("task").toString();
        String response = "Alright, your task is " + task;
        ResponseBuilder responseBuilder = getResponseBuilder(request).add(response).endConversation();
        ActionResponse actionResponse = responseBuilder.build();
        LOGGER.info(actionResponse.toString());
        LOGGER.info("make_name end.");
        return actionResponse;
    }
}
