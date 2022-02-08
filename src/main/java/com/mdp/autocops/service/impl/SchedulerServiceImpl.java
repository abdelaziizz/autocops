package com.mdp.autocops.service.impl;

import com.mdp.autocops.service.framework.SchedulerService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class SchedulerServiceImpl implements SchedulerService {


    @Override
    public String scheduledReader() throws IOException {

        try {
            DataFormatter formatter = new DataFormatter();
            FileInputStream file = new FileInputStream("C:\\Users\\ab.ashraf\\Documents\\TestExcel.xlsx");
            Workbook workbook = new XSSFWorkbook(file);
            Workbook workbook1 = new XSSFWorkbook();

            Sheet sheet1 = workbook1.createSheet("Persons");
            sheet1.setColumnWidth(0, 6000);
            sheet1.setColumnWidth(1, 4000);


            Sheet sheet = workbook.getSheetAt(0);

            Map<Integer, List<String>> data = new HashMap<>();
            int i = 0;
            for (Row row : sheet) {
                Row header = sheet1.createRow(i);
                data.put(i, new ArrayList<String>());
                for (Cell cell : row) {
                    Cell headerCell = header.createCell(0);
                    headerCell.setCellValue(formatter.formatCellValue(cell));
                }
                i++;
            }
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "new.xlsx";

            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return null;
    }


}
