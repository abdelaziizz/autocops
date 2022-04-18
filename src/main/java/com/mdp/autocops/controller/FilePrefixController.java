package com.mdp.autocops.controller;

import com.mdp.autocops.model.entity.FilePrefix;
import com.mdp.autocops.service.framework.FilePrefixService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/file-prefix")
public class FilePrefixController {

    private final FilePrefixService filePrefixService;

    @ResponseBody
    @PostMapping
    public FilePrefix create (@RequestParam String prefix) {
        return filePrefixService.create(prefix);
    }

    @ResponseBody
    @PutMapping("/{prefixId}")
    public FilePrefix update (@PathVariable long prefixId,  @RequestParam String prefix) {
        return filePrefixService.update(prefixId, prefix);
    }

    @ResponseBody
    @DeleteMapping("/{prefixId}")
    public FilePrefix delete (@PathVariable long prefixId) {
        return filePrefixService.delete(prefixId);
    }

    @ResponseBody
    @GetMapping
    public List<FilePrefix> getAll() {
        return filePrefixService.getAll();
    }

}
