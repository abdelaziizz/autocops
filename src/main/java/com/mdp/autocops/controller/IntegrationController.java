package com.mdp.autocops.controller;

import com.mdp.autocops.service.integration.IntegrationService;
import generatedSources.cxf.ru.bpc.svxp.omnichannels.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/integration")
public class IntegrationController {

    private final IntegrationService integrationService;

    @ResponseBody
    @GetMapping("/fetch")
    public String fetchData (@RequestParam int id, @RequestParam String omniVersion, @RequestParam String lang) throws OmniChannelsException {
        return integrationService.fetch(id, omniVersion, lang);
    }

}
