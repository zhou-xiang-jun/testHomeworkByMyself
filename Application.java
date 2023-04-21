package com.goole.Practice.Run;

import com.goole.Practice.Prepare.JDBCUtils;

import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {
        functionServer();
    }

    public static void functionServer() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to use the Function Server for StuInfor!");
        System.out.println("Please select your function:");
        System.out.println("1.Insert data");
        System.out.println("2.Delete data by name");
        System.out.println("3.Revise data by name");
        System.out.println("4.Search data by name");
        System.out.println("5.View all data");
        System.out.println("6.Truncate this table");


        switch (scanner.nextInt()) {
            case 1:
                JDBCUtils.insertData();
                break;
            case 2:
                JDBCUtils.deleteByName();
                break;
            case 3:
                JDBCUtils.updateData();
                break;
            case 4:
                JDBCUtils.searchByName();
                break;
            case 5:
                JDBCUtils.viewAllData();
                break;
            case 6:
                JDBCUtils.truncateTable();
                break;
            default:
                System.out.println("Please input the correct number!");
                break;
        }
        scanner.close();
    }
}
