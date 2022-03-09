package com.mdp.autocops.controller;

import com.mdp.autocops.model.entity.FileFormat;
import com.mdp.autocops.model.entity.Institution;
import com.mdp.autocops.model.entity.InstitutionConfig;
import com.mdp.autocops.model.entity.ServiceEntity;
import com.mdp.autocops.service.framework.FileFormatService;
import com.mdp.autocops.service.framework.InstitutionConfigService;
import com.mdp.autocops.service.framework.InstitutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/inst-configs")
public class InstitutionConfigController {

    private final InstitutionConfigService institutionConfigService;
    private final InstitutionService institutionService;
    private final FileFormatService fileFormatService;


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
    public InstitutionConfig put(@PathVariable long id, @RequestParam(required = false) Integer reading_line, @RequestParam long import_format, @RequestParam long export_format,
                                 @RequestParam Boolean fail_on_error, @RequestParam Boolean active, @RequestParam long service_id,
                                 @RequestParam String import_path, @RequestParam String export_path, @RequestParam String template_path,
                                 @RequestParam String reading_root, @RequestParam String writing_root,
                                 @RequestParam(required = false) Integer last_lines, @RequestParam(required = false) String import_date, @RequestParam(required = false) String export_date) {
        return institutionConfigService.put(id, reading_line, import_format, export_format, fail_on_error, active, service_id, import_path,
                export_path, template_path, reading_root, writing_root, last_lines, import_date, export_date);
    }


    @ResponseBody
    @PostMapping("/{instId}")
    public InstitutionConfig create(@PathVariable long instId, @RequestParam(required = false) Integer reading_line, @RequestParam long import_format, @RequestParam long export_format,
                                    @RequestParam Boolean fail_on_error, @RequestParam Boolean active, @RequestParam long service_id,
                                    @RequestParam String import_path, @RequestParam String export_path, @RequestParam String template_path,
                                    @RequestParam(required = false) String reading_root, @RequestParam String writing_root,
                                    @RequestParam(required = false) Integer last_lines, @RequestParam(required = false) String import_date, @RequestParam(required = false) String export_date) {
        return institutionConfigService.create(instId, reading_line, import_format, export_format, fail_on_error, active, service_id, import_path,
                export_path, template_path, reading_root, writing_root, last_lines, import_date, export_date);
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

    @GetMapping("/page/{institutionId}")
    public String institutionConfigPage(@PathVariable long institutionId, Model model) {
        List<InstitutionConfig> institutionConfigs = institutionConfigService.getByInst(institutionId);
        Institution institution = institutionService.getById(institutionId);
        model.addAttribute("configs", institutionConfigs);
        model.addAttribute("inst", institution);
        List<ServiceEntity> availableServices = institutionConfigService.getAvailableServices(institution.getInst_id());
        List<FileFormat> fileFormats = fileFormatService.getAll();
        model.addAttribute("availableServices", availableServices);
        model.addAttribute("formats", fileFormats);
        return "views/institutionConfig";
    }
    @GetMapping("/{institutionId}/{configId}")
    public String singleConfigPage(@PathVariable long institutionId, @PathVariable long configId, Model model) {
        Institution institution = institutionService.getById(institutionId);
        InstitutionConfig config = institutionConfigService.getById(configId);
        model.addAttribute("inst", institution);
        model.addAttribute("config", config);
        List<ServiceEntity> availableServices = institutionConfigService.getAvailableServices(institution.getInst_id());
        List<FileFormat> fileFormats = fileFormatService.getAll();
        model.addAttribute("availableServices", availableServices);
        model.addAttribute("formats", fileFormats);
        return "views/singleConfiguration";
    }

}
