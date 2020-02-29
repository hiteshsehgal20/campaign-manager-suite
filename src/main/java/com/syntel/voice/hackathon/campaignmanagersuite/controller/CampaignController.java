package com.syntel.voice.hackathon.campaignmanagersuite.controller;

import com.google.actions.api.App;
import com.syntel.voice.hackathon.campaignmanagersuite.representation.Campaign;
import com.syntel.voice.hackathon.campaignmanagersuite.service.CampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
class CampaignController {
    private static final Logger LOG = LoggerFactory.getLogger(CampaignService.class);
    public static final App actionsApp = new CampaignService();
    
    @GetMapping("/")
    String serveAck() {
        return "App is listening but requires valid POST request to respond with Action response.";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = { "application/json" })
    String serveAction(@RequestBody String body, @RequestHeader Map<String, String> headers) {
        try {
            return actionsApp.handleRequest(body, headers).get();
        } catch (InterruptedException | ExecutionException e) {
            return handleError(e);
        }
    }

    private String handleError(Exception e) {
        e.printStackTrace();
        LOG.error("Error in app.handleRequest ", e);
        return "Error handling the intent - " + e.getMessage();
    }
}
