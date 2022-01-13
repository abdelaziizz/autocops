package com.mdp.autocops.controller;

import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.ServiceEntity;
import com.mdp.autocops.service.framework.InstitutionConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/inst-configs")
public class InstitutionConfigController {

    private final InstitutionConfigService institutionConfigService;
    private final MessageSource messageSource;


    @ResponseBody
    @GetMapping
    public List<InstitutionConfig> getAll() {
        return institutionConfigService.getAll();
    }

    @ResponseBody
    @GetMapping("/{id}")
    public InstitutionConfig getById(@PathVariable long id) {
        return institutionConfigService.getById(id);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public InstitutionConfig delete(@PathVariable long id) {
        return institutionConfigService.delete(id);
    }

    @ResponseBody
    @PutMapping("/{id}")
    public InstitutionConfig put(@PathVariable long id,@RequestParam Integer reading_line, @RequestParam long import_format, @RequestParam long export_format,
                                 @RequestParam Boolean fail_on_error, @RequestParam Boolean active, @RequestParam long service_id) {
        return institutionConfigService.put(id, reading_line, import_format, export_format, fail_on_error, active, service_id);
    }


    @ResponseBody
    @PostMapping("/{instId}")
    public InstitutionConfig create(@PathVariable long instId, @RequestParam Integer reading_line, @RequestParam long import_format, @RequestParam long export_format,
                                    @RequestParam Boolean fail_on_error, @RequestParam Boolean active, @RequestParam long service_id) {
        return institutionConfigService.create(instId, reading_line, import_format, export_format, fail_on_error, active, service_id);
    }

    @ResponseBody
    @GetMapping("/inst/{id}")
    public List<InstitutionConfig> getAll(@PathVariable long id) {
        return institutionConfigService.getByInst(id);

    }

    @ResponseBody
    @GetMapping("/availableServices/{id}")
    public List<ServiceEntity> getAvailableServices(@PathVariable long id) {
//        System.out.println(messageSource.getMessage("CONFIG_ERROR", new Object[0],Locale.ENGLISH));
        return institutionConfigService.getAvailableServices(id);
    }

}
