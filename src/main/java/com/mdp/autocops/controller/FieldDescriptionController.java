package com.mdp.autocops.controller;

import com.mdp.autocops.model.entity.FieldDescription;
import com.mdp.autocops.model.entity.ImportField;
import com.mdp.autocops.model.entity.ServiceEntity;
import com.mdp.autocops.service.framework.FieldDescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/field-descriptions")
public class FieldDescriptionController {

    private final FieldDescriptionService service;

    @ResponseBody
    @GetMapping
    public List<FieldDescription> getAll () {
        return service.getAll();
    }

    @ResponseBody
    @GetMapping("/{id}")
    public FieldDescription getById (@PathVariable long id) {
        return service.getById(id);
    }

    @ResponseBody
    @GetMapping("/name/{name}")
    public FieldDescription getByName (@PathVariable String name) {
        return service.getByName(name);
    }

    @ResponseBody
    @PostMapping
    public FieldDescription create (@RequestParam String field_name, @RequestParam String description) {
        return service.create(field_name, description);
    }

    @ResponseBody
    @PutMapping("/{id}")
    public FieldDescription put (@PathVariable long id, @RequestParam String field_name, @RequestParam String description) {
        try {
            return service.put(id, field_name, description);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public FieldDescription delete (@PathVariable long id) {
        return service.delete(id);
    }

    @GetMapping("/page")
    public String fieldDescriptionPage(Model model) {
        List<FieldDescription> fieldDescriptions = service.getAll();
        model.addAttribute("fieldDescriptions", fieldDescriptions);
        return "views/fieldDescriptions";
    }

}
