package com.mdp.autocops.controller.processes;

import com.mdp.autocops.service.framework.processes.CardActivationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/card-activation")
public class CardActivationController {

    private final CardActivationService cardActivationService;

    @ResponseBody
    @PostMapping("/adib")
    public String processAdib(){
        return cardActivationService.readAndExport();
    }

}
