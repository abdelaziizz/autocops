package com.mdp.autocops.controller;

import com.mdp.autocops.model.entity.ServiceEntity;
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
@RequestMapping("/services")
public class ServiceController {

    private final ServiceService serviceService;

    @ResponseBody
    @PostMapping
    public ServiceEntity create(@RequestBody ServiceEntity serviceEntity) {
        return serviceService.create(serviceEntity.getService_name(), serviceEntity.getDescription(), serviceEntity.isActive());
    }

    @ResponseBody
    @GetMapping
    public List<ServiceEntity> getAll() {
        return serviceService.getAll();
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ServiceEntity getById(@PathVariable long id) {
        return serviceService.getById(id);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ServiceEntity delete(@PathVariable long id) {
        return serviceService.delete(id);
    }

    @ResponseBody
    @PutMapping("/{id}")
    public ServiceEntity put(@PathVariable long id, @RequestBody ServiceEntity serviceEntity) {
        return serviceService.put(id, serviceEntity.getService_name(), serviceEntity.getDescription(), serviceEntity.isActive());
    }

    @GetMapping("/page")
    public String servicesView(Model model) {
        List<ServiceEntity> services = serviceService.getAll();
        model.addAttribute("services", services);
        return "views/services";
    }

}
