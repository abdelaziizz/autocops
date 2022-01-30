package com.mdp.autocops.controller.processes;

import com.mdp.autocops.service.framework.processes.CardIssuanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/card-issuance")
public class CardIssuanceController {

    private final CardIssuanceService cardIssuanceService;

    @ResponseBody
    @PostMapping("/adcb")
    public String issueCardAdcb() {
        return cardIssuanceService.issueCardADCB();
    }

}
