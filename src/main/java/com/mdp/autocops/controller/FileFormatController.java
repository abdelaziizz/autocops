package com.mdp.autocops.controller;

import com.mdp.autocops.model.entity.FileFormat;
import com.mdp.autocops.service.framework.FileFormatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/formats")
public class FileFormatController {

    private final FileFormatService formatService;

    @ResponseBody
    @PostMapping
    public FileFormat create(@RequestParam String type) {
        return formatService.create(type);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public FileFormat delete(@PathVariable long id) {
        return formatService.delete(id);
    }

    @ResponseBody
    @GetMapping
    public List<FileFormat> getAll() {
        return formatService.getAll();
    }

    @ResponseBody
    @PutMapping("/{id}")
    public FileFormat update(@PathVariable long id, @RequestParam String type) {
        return formatService.put(id, type);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public FileFormat getById(@PathVariable long id) {
        return formatService.getById(id);
    }

}
