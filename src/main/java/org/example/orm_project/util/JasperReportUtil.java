package org.example.orm_project.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.example.orm_project.config.FactoryConfiguration;

import java.util.HashMap;

public class JasperReportUtil {

    public static void showTherapistReport() {
        showReport("/org/example/orm_project/report/therapist.jrxml");
    }

    public static void showPaymentReport() {
        showReport("/org/example/orm_project/report/payment.jrxml");
    }

    private static void showReport(String jrxmlPath) {
        try {
            var url = JasperReportUtil.class.getResource(jrxmlPath);

            if (url == null) {
                System.err.println("Report not found: " + jrxmlPath);
                return;
            }

            JasperReport report = JasperCompileManager.compileReport(url.openStream());

            var connection = FactoryConfiguration.getInstance()
                    .getSession()
                    .doReturningWork(conn -> conn);

            JasperPrint print = JasperFillManager.fillReport(
                    report, new HashMap<>(), connection);

            JasperViewer.viewReport(print, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}