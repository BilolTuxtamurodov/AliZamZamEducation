package com.company.AliZamZamEducation.words;

import com.company.AliZamZamEducation.entity.ProfileEntity;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;

public class Words {
    private static int rowCount = 1;
    public static void writeToExel(List<ProfileEntity> user_dtos) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Bir");
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 3500);
        sheet.setColumnWidth(2, 3555);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 6000);
        sheet.setColumnWidth(5, 6000);
        sheet.setColumnWidth(6, 7000);
        sheet.setColumnWidth(7, 4000);
        Row firstRow = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CENTER);

        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);

        style.setFont(font);
        Cell cell;
        cell = firstRow.createCell(0);
        cell.setCellValue("№");
        cell.setCellStyle(style);

        cell = firstRow.createCell(1);
        cell.setCellValue("Ismi");
        cell.setCellStyle(style);

        cell = firstRow.createCell(2);
        cell.setCellValue("Familiyasi");
        cell.setCellStyle(style);

        cell = firstRow.createCell(3);
        cell.setCellValue("Telefon Raqami");
        cell.setCellStyle(style);

        cell = firstRow.createCell(4);
        cell.setCellValue("Level");
        cell.setCellStyle(style);

        cell = firstRow.createCell(5);
        cell.setCellValue("Kursi");
        cell.setCellStyle(style);

        cell = firstRow.createCell(6);
        cell.setCellValue("Bo'lim");
        cell.setCellStyle(style);

        cell = firstRow.createCell(7);
        cell.setCellValue("Qo'shilgan vaqti");
        cell.setCellStyle(style);

        for (ProfileEntity profileDTO : user_dtos) {
            Row row = sheet.createRow(rowCount);
            cell = row.createCell(0);
            cell.setCellValue(rowCount);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(1);
            cell.setCellValue(profileDTO.getName());

            cell = row.createCell(2);
            cell.setCellValue(profileDTO.getSurname());

            cell = row.createCell(3);
            cell.setCellValue(profileDTO.getPhone());

            if (profileDTO.getKurs() != null) {
                cell = row.createCell(4);
                cell.setCellValue(profileDTO.getKurs());
            } else {
                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue("-");
            }
            if (profileDTO.getLevel() != null) {
                cell = row.createCell(5);
                cell.setCellValue(profileDTO.getLevel());
            } else {
                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);
                cell.setCellValue("-");
            }

            if (profileDTO.getBolim() != null) {
                cell = row.createCell(6);
                cell.setCellValue(profileDTO.getBolim());
            } else {
                cell = row.createCell(6);
                cell.setCellStyle(cellStyle);
                cell.setCellValue("-");
            }

            cell = row.createCell(7);
            cell.setCellValue(profileDTO.getCreatedDate().toLocalDate().toString());
            rowCount++;
        }
        try {
            FileOutputStream outputStream = new FileOutputStream("univer.xlsx");
            workbook.write(outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
