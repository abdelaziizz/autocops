package com.mdp.autocops.controller;

import com.mdp.autocops.model.entity.FieldFormat;
import com.mdp.autocops.service.framework.FieldFormatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/field-formats")
public class FieldFormatController {

    private final FieldFormatService fieldFormatService;

    @ResponseBody
    @GetMapping
    public List<FieldFormat> getAll() {
        return fieldFormatService.getAll();
    }

    @ResponseBody
    @GetMapping("/{id}")
    public FieldFormat getById(@PathVariable long id) {
        return fieldFormatService.getById(id);
    }

    @ResponseBody
    @PostMapping
    public FieldFormat create(@RequestParam long typeId, @RequestParam String format) {
        return fieldFormatService.create(typeId, format);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public FieldFormat delete(@PathVariable long id) {
        return fieldFormatService.delete(id);
    }

    @ResponseBody
    @PutMapping("/{id}")
    public FieldFormat put(@PathVariable long id, @RequestParam long typeId, @RequestParam String newFormat) {
        return fieldFormatService.put(id, typeId, newFormat);
    }

    @ResponseBody
    @GetMapping("/type/{id}")
    public List<FieldFormat> getByType(@PathVariable long id) {
        return fieldFormatService.getAllByType(id);
    }
}
