module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;
    requires junit;
    requires org.junit.platform.launcher;
    requires org.junit.jupiter.engine;
    requires org.junit.platform.engine;
    requires org.junit.platform.commons;
    opens com.project.testapplication to javafx.fxml, org.hibernate.orm.core;
    exports com.project.testapplication;
    opens com.project.testapplication.controller to javafx.fxml;
    exports com.project.testapplication.controller;
    exports com.project.testapplication.service;
    opens com.project.testapplication.service to javafx.fxml;
    exports com.project.testapplication.testClass;
    opens com.project.testapplication.testClass to javafx.fxml, org.junit.platform.commons;
    exports com.project.testapplication.entity;
    opens com.project.testapplication.entity to javafx.fxml, org.hibernate.orm.core;
    exports com.project.testapplication.utils;
    opens com.project.testapplication.utils to javafx.fxml, org.hibernate.orm.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires org.junit.jupiter.params;
    requires java.persistence;
}