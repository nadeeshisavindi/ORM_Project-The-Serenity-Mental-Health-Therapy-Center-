package org.example.orm_project.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

public class JasperReportUtil {

    private static final String DB_URL  = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "your_password";

    public static void showPaymentReport() {
        try {

            InputStream reportStream = JasperReportUtil.class
                    .getResourceAsStream("report/therapist.jrxml");

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);


            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);


            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    new HashMap<>(),
                    connection
            );

            connection.close();


            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}