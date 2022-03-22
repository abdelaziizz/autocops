package com.mdp.autocops.controller;

import com.mdp.autocops.model.entity.ExportField;
import com.mdp.autocops.model.entity.ServiceEntity;
import com.mdp.autocops.service.framework.ExportFieldService;
import com.mdp.autocops.service.framework.ServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/export-fields")
public class ExportFieldController {

    private final ExportFieldService exportFieldService;
    private final ServiceService serviceService;

    @ResponseBody
    @GetMapping
    public List<ExportField> getAll() {
        return exportFieldService.getAll();
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ExportField getById(@PathVariable long id) {
        return exportFieldService.getById(id);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ExportField delete(@PathVariable long id) {
        return exportFieldService.delete(id);
    }

    @ResponseBody
    @PostMapping
    public ExportField create(@RequestParam long service_id, @RequestParam String field_name) {
        return exportFieldService.create(service_id, field_name);
    }

    @ResponseBody
    @PutMapping("/{id}")
    public ExportField put(@PathVariable long id, @RequestParam long service_id, @RequestParam String field_name) {
        return exportFieldService.put(id, service_id, field_name);
    }

    @ResponseBody
    @GetMapping("/available/{service_id}")
    public List<ExportField> getAvailableByService(@PathVariable long service_id) {
        return exportFieldService.getAllByService(service_id);
    }

    @GetMapping("/page/{serviceId}")
    public String institutionConfigPage(@PathVariable long serviceId, Model model) {
        List<ExportField> fields = exportFieldService.getAllByService(serviceId);
        ServiceEntity service = serviceService.getById(serviceId);
        model.addAttribute("fields", fields);
        model.addAttribute("service", service);
        return "views/exportFields";
    }

}
