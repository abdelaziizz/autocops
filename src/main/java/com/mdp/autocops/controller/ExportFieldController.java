package com.mdp.autocops.controller;

import com.mdp.autocops.model.entity.ExportField;
import com.mdp.autocops.service.framework.ExportFieldService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/export-fields")
public class ExportFieldController {

    @Autowired
    ExportFieldService exportFieldService;

    @ResponseBody
    @GetMapping
    public List<ExportField> getAll () {
        return exportFieldService.getAll();
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ExportField getById (@PathVariable long id) {
        return exportFieldService.getById(id);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ExportField delete (@PathVariable long id) {
        return exportFieldService.delete(id);
    }

    @ResponseBody
    @PostMapping
    public ExportField create (@RequestParam long service_id, @RequestParam String field_name) {
        return exportFieldService.create(service_id, field_name);
    }

    @ResponseBody
    @PutMapping("/{id}")
    public ExportField put (@PathVariable long id, @RequestParam long service_id, @RequestParam String field_name) {
        return exportFieldService.put(id, service_id, field_name);
    }

    @ResponseBody
    @GetMapping("/available/{service_id}")
    public List<ExportField> getAvailableByService (@PathVariable long service_id) {
        return exportFieldService.getAllByService(service_id);
    }

}
