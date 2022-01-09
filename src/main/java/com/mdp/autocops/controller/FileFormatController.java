//package com.mdp.autocops.controller;
//
//import com.mdp.autocops.model.entity.Format;
//import com.mdp.autocops.service.framework.FormatService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequiredArgsConstructor
//@Log4j2
//@RequestMapping("/formats")
//public class FormatController {
//
//    @Autowired
//    FormatService formatService;
//
//    @ResponseBody
//    @PostMapping
//    public Format create(@RequestParam String type) {
//        return formatService.create(type);
//    }
//
//    @ResponseBody
//    @DeleteMapping("/{id}")
//    public Format delete(@PathVariable long id) {
//        return formatService.delete(id);
//    }
//
//    @ResponseBody
//    @GetMapping
//    public List<Format> getAll() {
//        return formatService.getAll();
//    }
//
//    @ResponseBody
//    @PutMapping("/{id}")
//    public Format update(@PathVariable long id, @RequestParam String type) {
//        return formatService.put(id, type);
//    }
//
//    @ResponseBody
//    @GetMapping("/{id}")
//    public Format getById(@PathVariable long id) {
//        return formatService.getById(id);
//    }
//
//}
