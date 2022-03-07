package com.mdp.autocops.service.impl.processes;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;

@Log4j2
@Service
public class FileAccess {

    public String downloadLocal(String import_path) {
        String data = "";
        try (BufferedReader br = new BufferedReader(new FileReader(import_path))) {
            String line;
            while ((line = br.readLine()) != null) {
                data += line + "\n";
            }
            return data;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
