module org.example.orm_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires java.desktop;
    requires java.xml;
    requires net.sf.jasperreports.core;
    requires jbcrypt;



    opens org.example.orm_project.util to javafx.base;
    opens org.example.orm_project.dto.tm to javafx.base;
    opens org.example.orm_project.entity to org.hibernate.orm.core, javafx.base;
    opens org.example.orm_project to javafx.fxml;
    opens org.example.orm_project.controller to javafx.fxml;
    exports org.example.orm_project;
}