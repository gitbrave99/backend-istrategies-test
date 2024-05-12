package com.fullstack.strate.fullstack.utils;


import org.springframework.stereotype.Service;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.util.ResourceUtils;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductReporterGenerator {
    public byte[] exportToPdf(List<?> list, String reportName) throws JRException, FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReport(list,reportName));
    }



/*    public byte[] exportToXls(List<ProductoDTO> list) throws JRException, FileNotFoundException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(getReport(list)));
        exporter.setExporterOutput(output);
        exporter.exportReport();
        output.close();
        return byteArray.toByteArray();
    }*/

    private JasperPrint getReport(List<?> list, String reportName) throws FileNotFoundException, JRException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("petsData", new JRBeanCollectionDataSource(list));
        JasperPrint report = JasperFillManager.fillReport(JasperCompileManager.compileReport(
                ResourceUtils.getFile("classpath:"+reportName+".jrxml")
                        .getAbsolutePath()), params, new JREmptyDataSource());
        return report;
    }
}
