package com.mdp.autocops.controller;


import com.mdp.autocops.model.entity.*;
import com.mdp.autocops.service.framework.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SecurityController {

    private final InstitutionConfigService institutionConfigService;
    private final InstitutionService institutionService;
    private final InstitutionConfigMappingService mappingService;
    private final ServiceService serviceService;
    private final FileFormatService fileFormatService;
    private final FieldTypeService fieldTypeService;
    private final FieldFormatService fieldFormatService;

    @GetMapping("/login")
    public String loginPage() {

        return "views/login";
    }

    @GetMapping("/institutionsPage")
    public String institutionsPage(Model model) {
        List<Institution> institutions = institutionService.getAll();
        model.addAttribute("institutions", institutions);
        return "views/institutions";
    }

    @GetMapping("/institutionsConfig/{institutionId}")
    public String institutionConfigPage(@PathVariable long institutionId, Model model) {
        List<InstitutionConfig> institutionConfigs = institutionConfigService.getByInst(institutionId);
        Institution institution = institutionService.getById(institutionId);
        model.addAttribute("configs",institutionConfigs);
        model.addAttribute("inst",institution);
        List<ServiceEntity> availableServices = institutionConfigService.getAvailableServices(institution.getInst_id());
        List<FileFormat> fileFormats = fileFormatService.getAll();
        model.addAttribute("availableServices", availableServices);
        model.addAttribute("formats", fileFormats);
        return "views/institutionConfig";
    }

    @GetMapping("/configMappings/{institutionId}/{configId}")
    public String configMappings(@PathVariable long institutionId, @PathVariable long configId, Model model) {
        List<InstitutionsConfigMapping> mappings = mappingService.findByInstConfig(configId);
        Institution institution = institutionService.getById(institutionId);
        InstitutionConfig config = institutionConfigService.getById(configId);
        List<FieldType> fieldTypes = fieldTypeService.getAll();
        List<FieldFormat> fieldFormats = fieldFormatService.getAll();
        model.addAttribute("fieldTypes", fieldTypes);
        model.addAttribute("fieldFormats", fieldFormats);
        model.addAttribute("mappings",mappings);
        model.addAttribute("inst",institution);
        model.addAttribute("config", config);
        return "views/configMappings";
    }

    @GetMapping("/servicesView")
    public String servicesView(Model model) {
        List<ServiceEntity> services = serviceService.getAll();
        model.addAttribute("services", services);
        return "views/services";
    }
}
