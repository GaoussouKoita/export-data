package ml.pic.tech.export.data.service;

import ml.pic.tech.export.data.domaine.Role;
import ml.pic.tech.export.data.domaine.Utilisateur;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ExportExcel {

    @Autowired
    private UtilisateurService utilisateurService;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExportExcel() {
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Utilisateur");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Prenom", style);
        createCell(row, 1, "Nom", style);
        createCell(row, 2, "Adresse", style);
        createCell(row, 3, "Telephone", style);
        createCell(row, 4, "Login", style);
        createCell(row, 5, "Roles", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        }else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        }else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Utilisateur user : utilisateurService.utilisateurList()) {
            String roles = "";
            for (Role r : user.getRoles()) {
                roles += r.getRoleName() + " ";
            }
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getPrenom(), style);
            createCell(row, columnCount++, user.getNom(), style);
            createCell(row, columnCount++, user.getAdresse(), style);
            createCell(row, columnCount++, user.getTelephone(), style);
            createCell(row, columnCount++, user.getLogin(), style);
            createCell(row, columnCount++, roles, style);

        }
    }

    public void exportToExcel(HttpServletResponse response) throws IOException, IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Utilisateurs_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
