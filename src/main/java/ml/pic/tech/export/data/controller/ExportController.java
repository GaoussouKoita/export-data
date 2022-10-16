package ml.pic.tech.export.data.controller;

import ml.pic.tech.export.data.service.ExportCSV;
import ml.pic.tech.export.data.service.ExportExcel;
import ml.pic.tech.export.data.service.ExportPDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/export")
public class ExportController {
    @Autowired
    private ExportPDF pdfSservice;
    @Autowired
    private ExportCSV csvService;
    @Autowired
    private ExportExcel excelService;

    @GetMapping("/pdf")
    public void exportPdf(HttpServletResponse response) throws IOException {
        pdfSservice.exportToPdf(response);
    }

    @GetMapping("/csv")
    public void exportCsv(HttpServletResponse response) throws IOException {
        csvService.exportToCSV(response);
    }

    @GetMapping("/xlsx")
    public void exportExcel(HttpServletResponse response) throws IOException {
        excelService.exportToExcel(response);
    }

}
