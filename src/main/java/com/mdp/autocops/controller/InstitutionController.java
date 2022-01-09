package com.mdp.autocops.controller;

import com.mdp.autocops.model.entity.Institution;
import com.mdp.autocops.service.framework.InstitutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/institutions")
public class InstitutionController {

    @Autowired
    InstitutionService institutionService;

    @ResponseBody
    @GetMapping
    public List<Institution> getAll() {
        return institutionService.getAll();
    }

    @ResponseBody
    @GetMapping("/{id}")
    public Institution getById(@PathVariable long id) {
        return institutionService.getById(id);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public Institution delete(@PathVariable long id) {
        return institutionService.delete(id);
    }

    @ResponseBody
    @PutMapping("/{id}")
    public Institution put(@PathVariable long id, @RequestBody Institution name) {
        return institutionService.put(id, name);
    }

    @ResponseBody
    @PostMapping
    public Institution create(@RequestBody Institution institution) {
        return institutionService.create(institution.getInst_id(), institution.getInst_name());
    }

}
