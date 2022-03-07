package com.mdp.autocops.controller;

import com.mdp.autocops.model.entity.*;
import com.mdp.autocops.service.framework.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/inst-configs-mappings")
public class InstitutionConfigMappingController {


    private final InstitutionConfigService institutionConfigService;
    private final InstitutionService institutionService;
    private final InstitutionConfigMappingService mappingService;
    private final FieldTypeService fieldTypeService;
    private final FieldFormatService fieldFormatService;
    private final ImportFieldService importFieldService;

    @ResponseBody
    @GetMapping
    public List<InstitutionsConfigMapping> getAll() {
        return mappingService.getAll();
    }

    @ResponseBody
    @GetMapping("/{id}")
    public InstitutionsConfigMapping getById(@PathVariable long id) {
        return mappingService.getById(id);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public InstitutionsConfigMapping delete(@PathVariable long id) {
        return mappingService.delete(id);
    }

    @ResponseBody
    @PostMapping
    public InstitutionsConfigMapping create(@RequestParam long configId, @RequestParam String imp_field_index, @RequestParam long typeId,
                                            @RequestParam long formatId, @RequestParam long exp_field, @RequestParam String imp_field,
                                            @RequestParam String start_index, @RequestParam String last_index) {
        int index;
        long imp;
        int last;
        int start;
        if (imp_field_index.equals("")) index = -1;
        else index = Integer.valueOf(imp_field_index);

        if (start_index.equals("")) start = -1;
        else start = Integer.valueOf(start_index);

        if (last_index.equals("")) last = -1;
        else last = Integer.valueOf(last_index);

        if (imp_field.equals("")) imp = -1;
        else imp = Long.valueOf(imp_field);

        return mappingService.create(configId, index, typeId, formatId, exp_field, imp, start, last);
    }

    @ResponseBody
    @PutMapping("/{id}")
    public InstitutionsConfigMapping put(@PathVariable long id, @RequestParam long configId, @RequestParam String imp_field_index,
                                         @RequestParam long typeId, @RequestParam long formatId, @RequestParam long exp_field, @RequestParam String imp_field,
                                         @RequestParam String start_index, @RequestParam String last_index) {
        int index;
        long imp;
        int last;
        int start;
        if (imp_field_index.equals("")) index = -1;
        else index = Integer.valueOf(imp_field_index);

        if (start_index.equals("")) start = -1;
        else start = Integer.valueOf(start_index);

        if (last_index.equals("")) last = -1;
        else last = Integer.valueOf(last_index);

        if (imp_field.equals("")) imp = -1;
        else imp = Long.valueOf(imp_field);

        return mappingService.put(id, configId, index, typeId, formatId, exp_field, imp, start, last);
    }

    @ResponseBody
    @GetMapping("/export-fields/getAvailable/{config_id}")
    public List<ExportField> getAvailableFields(@PathVariable long config_id) {
        return mappingService.getAvailableExport(config_id);
    }

    @GetMapping("/page/{institutionId}/{configId}")
    public String configMappings(@PathVariable long institutionId, @PathVariable long configId, Model model) {
        List<InstitutionsConfigMapping> mappings = mappingService.findByInstConfig(configId);
        Institution institution = institutionService.getById(institutionId);
        InstitutionConfig config = institutionConfigService.getById(configId);
        List<FieldType> fieldTypes = fieldTypeService.getAll();
        List<FieldFormat> fieldFormats = fieldFormatService.getAll();
        List<ExportField> exportFields = mappingService.getAvailableExport(configId);
        List<ImportField> importFields = importFieldService.getAllByService(config.getService().getService_id());
        model.addAttribute("exportFields", exportFields);
        model.addAttribute("importFields", importFields);
        model.addAttribute("fieldTypes", fieldTypes);
        model.addAttribute("fieldFormats", fieldFormats);
        model.addAttribute("mappings", mappings);
        model.addAttribute("inst", institution);
        model.addAttribute("config", config);
        return "views/configMappings";
    }

}
