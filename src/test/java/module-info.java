module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;
    requires junit;
    requires org.junit.platform.launcher;
    requires org.junit.jupiter.engine;
    requires org.junit.platform.engine;
    requires org.junit.platform.commons;
    opens com.example.demo to javafx.fxml, org.hibernate.orm.core;
    exports com.example.demo;
    opens com.example.demo.controller to javafx.fxml;
    exports com.example.demo.controller;
    exports com.example.demo.service;
    opens com.example.demo.service to javafx.fxml;
    exports com.example.demo.testClass;
    opens com.example.demo.testClass to javafx.fxml, org.junit.platform.commons;
    exports com.example.demo.entity;
    opens com.example.demo.entity to javafx.fxml, org.hibernate.orm.core;
    exports com.example.demo.utils;
    opens com.example.demo.utils to javafx.fxml, org.hibernate.orm.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires org.junit.jupiter.params;
    requires java.persistence;
}