package com.mdp.autocops.controller;

import com.mdp.autocops.model.entity.InstitutionsConfigMapping;
import com.mdp.autocops.service.framework.InstitutionConfigMappingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/inst-configs-mappings")
public class InstitutionConfigMappingController {

    @Autowired
    InstitutionConfigMappingService institutionConfigMappingService;

    @ResponseBody
    @GetMapping
    public List<InstitutionsConfigMapping> getAll() {
        return institutionConfigMappingService.getAll();
    }

    @ResponseBody
    @GetMapping("/{id}")
    public InstitutionsConfigMapping getById(@PathVariable long id) {
        return institutionConfigMappingService.getById(id);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public InstitutionsConfigMapping delete(@PathVariable long id) {
        return institutionConfigMappingService.delete(id);
    }

    @ResponseBody
    @PostMapping
    public InstitutionsConfigMapping create(@RequestParam long configId, @RequestParam int imp_field, @RequestParam long typeId, @RequestParam long formatId, @RequestParam String exp_field) {
        return institutionConfigMappingService.create(configId, imp_field, typeId, formatId, exp_field);
    }

    @ResponseBody
    @PutMapping("/{id}")
    public InstitutionsConfigMapping put(@PathVariable long id, @RequestParam long configId, @RequestParam int imp_field,
                                         @RequestParam long typeId, @RequestParam long formatId, @RequestParam String exp_field) {
        return institutionConfigMappingService.put(id, configId, imp_field,typeId, formatId, exp_field);
    }

}
