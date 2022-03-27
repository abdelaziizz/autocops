package com.mdp.autocops.controller;

import com.mdp.autocops.model.entity.ExportField;
import com.mdp.autocops.model.entity.ImportField;
import com.mdp.autocops.model.entity.ServiceEntity;
import com.mdp.autocops.service.framework.ImportFieldService;
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
@RequestMapping("/import-fields")
public class ImportFieldController {

    private final ImportFieldService importFieldService;
    private final ServiceService serviceService;

    @ResponseBody
    @GetMapping
    public List<ImportField> getAll() {
        return importFieldService.getAll();
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ImportField getById(@PathVariable long id) {
        return importFieldService.getById(id);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ImportField delete(@PathVariable long id) {
        return importFieldService.delete(id);
    }

    @ResponseBody
    @PostMapping
    public ImportField create(@RequestParam long service_id, @RequestParam String field_name) {
        return importFieldService.create(service_id, field_name);
    }

    @ResponseBody
    @PutMapping("/{id}")
    public ImportField put(@PathVariable long id, @RequestParam long service_id, @RequestParam String field_name) {
        return importFieldService.put(id, service_id, field_name);
    }

    @ResponseBody
    @GetMapping("/available/{service_id}")
    public List<ImportField> getAvailableByService(@PathVariable long service_id) {
        return importFieldService.getAllByService(service_id);
    }

    @GetMapping("/page/{serviceId}")
    public String institutionConfigPage(@PathVariable long serviceId, Model model) {
        List<ImportField> fields = importFieldService.getAllByService(serviceId);
        ServiceEntity service = serviceService.getById(serviceId);
        model.addAttribute("fields", fields);
        model.addAttribute("service", service);
        return "views/importFields";
    }

}
