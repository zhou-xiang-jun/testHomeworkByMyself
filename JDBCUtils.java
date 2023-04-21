package com.goole.Practice.Prepare;

import com.goole.Practice.Run.Student;
import org.junit.Test;

import javax.xml.transform.Result;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;

public class JDBCUtils {
    private static String url;
    private static String user;
    private static String password;
    private static String driver;
    private static PreparedStatement preparedStatement;

    // 预处理，获取文件中配置的值
    static {
        try {
            JDBCUtils.class.getClassLoader();
            InputStream inputStream = ClassLoader.getSystemResourceAsStream("db.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            driver = properties.getProperty("driver");

            // System.out.println(url+" "+user+" "+password+" "+driver);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(PreparedStatement preparedStatement, Connection connection) throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.close();
        }

        if (connection != null) {
            connection.close();
        }

    }

    public static void close(PreparedStatement preparedStatement, Connection connection, ResultSet resultSet) throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.close();
        }

        if (connection != null) {
            connection.close();
        }

        if (resultSet != null) {
            resultSet.close();
        }

    }

    public static void insertData() {
        try {
            Connection connection = JDBCUtils.getConnection();
            Scanner scanner = new Scanner(System.in);
            String sql = "insert into infor(id,name,age,gender) value(null,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, scanner.nextLine());
            preparedStatement.setInt(2, scanner.nextInt());
            scanner.nextLine();
            preparedStatement.setString(3, scanner.nextLine());

            int isUpdate = preparedStatement.executeUpdate();
            if (isUpdate > 0) {
                System.out.println("Manage to insert!");
            }

            JDBCUtils.close(preparedStatement, connection);
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int viewAllData() {
        try {
            Connection connection = JDBCUtils.getConnection();
            String sql = "select * from infor";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            ArrayList<Student> arrayList = new ArrayList<>();

            while (resultSet.next()) {
                arrayList.add(new Student(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4)));
            }

            if (arrayList.size() == 0) {
                System.out.println("The table not concludes any data! ");
            }

            Iterator<Student> iterator = arrayList.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

            JDBCUtils.close(preparedStatement, connection, resultSet);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void searchByName() {
        try {
            Connection connection = JDBCUtils.getConnection();
            Scanner scanner = new Scanner(System.in);
            String sql = "select * from infor where name=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, scanner.nextLine());
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Student> arrayList = new ArrayList<>();
            while (resultSet.next()) {
                arrayList.add(new Student(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4)));
            }

            if (arrayList.size() == 0) {
                System.out.println("The target data isn't in the table!");
            }

            Iterator<Student> iterator = arrayList.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

            JDBCUtils.close(preparedStatement, connection);
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int deleteByName() {
        try {
            Connection connection = JDBCUtils.getConnection();
            Scanner scanner = new Scanner(System.in);
            String sql = "delete from infor where name=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, scanner.nextLine());
            int is_Update = preparedStatement.executeUpdate();
            JDBCUtils.close(preparedStatement, connection);

            JDBCUtils.close(preparedStatement, connection);
            scanner.close();
            return is_Update;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void updateData() {
        try {
            Connection connection = JDBCUtils.getConnection();
            Scanner scanner = new Scanner(System.in);
            String sql = "update infor set name=? where name=?";
            preparedStatement = connection.prepareStatement(sql);

            System.out.println("Please input the new name :");
            preparedStatement.setString(1, scanner.nextLine());
            System.out.println("Please input the old name :");
            preparedStatement.setString(2, scanner.nextLine());

            int isUpdate = preparedStatement.executeUpdate();
            if (isUpdate > 0) {
                System.out.println("Manage to update!");
            }

            scanner.close();
            JDBCUtils.close(preparedStatement, connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void truncateTable() throws SQLException {
        Connection connection = JDBCUtils.getConnection();
        String sql = "truncate table infor";
        preparedStatement = connection.prepareStatement(sql);
        boolean isTruncate = preparedStatement.execute();

        if (isTruncate == true) {
            System.out.println("Manage to truncate the table");
        } else {
            System.out.println("The function is invalid!");
        }
        JDBCUtils.close(preparedStatement, connection);
    }


}
