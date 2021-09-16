package com.redhat.fuse.boosters.rest.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DBInitializer {

    @Value("${app.external-db}")
    Boolean externalDb;

    @Value("${spring.datasource.url}")
    String dbURL;

    @Value("${spring.datasource.username}")
    String user;

    @Value("${spring.datasource.password}")
    String password;

    @PostConstruct
    public void init(){
         if(externalDb){
             try {
                 Connection conn = DriverManager.getConnection(dbURL, user, password);
                 Statement stmt = conn.createStatement();
                 String sql = "drop table if exists orders; create table orders (id INTEGER IDENTITY PRIMARY KEY, item varchar(100),amount integer, description varchar(100), processed boolean);";
                 stmt.executeUpdate(sql);
                 System.out.println("Created table in given database...");
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
    }

}
