package prepared;

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
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static Scanner scanner = new Scanner(System.in);

    static {
        try{
            InputStream inputStream = ClassLoader.getSystemResourceAsStream("db.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            driver = properties.getProperty("driver");

            connection = DriverManager.getConnection(url,user,password);
//            System.out.println(url);
//            System.out.println(user);
//            System.out.println(password);
//            System.out.println(driver);
//            System.out.println(connection);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void init(){


    }

    public static void insertData() throws SQLException {
        String sql = "insert into jun value(null,?,?,?,default,default)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,scanner.nextLine());
        preparedStatement.setString(2,scanner.nextLine());
        preparedStatement.setInt(3,scanner.nextInt());
        int isInsert = preparedStatement.executeUpdate();

        if(isInsert != 0) {
            System.out.println("Manage to insert!");
        }
        JDBCUtils.close(preparedStatement, connection);
        scanner.close();
    }
    public static void deleteByName() throws SQLException {
        String sql = "delete from jun where name=?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,scanner.nextLine());
        int isDelete = preparedStatement.executeUpdate();

        if(isDelete != 0) {
            System.out.println("Manage to delete!");
        }

        JDBCUtils.close(preparedStatement, connection);
        scanner.close();

    }
    public static void updateByName() throws SQLException {
        String sql= "update jun set name=? where name=?";
        preparedStatement = connection.prepareStatement(sql);
        System.out.println("Please input the new name :");
        preparedStatement.setString(1, scanner.nextLine());
        System.out.println("Please input the old name :");
        preparedStatement.setString(2, scanner.nextLine());
        int isUpdate = preparedStatement.executeUpdate();

        if(isUpdate != 0) {
            System.out.println("Manage to update!");
        } else{
            System.out.println("fail to update!");
        }

        JDBCUtils.close(preparedStatement, connection);
        scanner.close();
    }
    public static void searchByName() throws SQLException {
        String sql ="select * from jun where name=?";
        preparedStatement = connection.prepareStatement(sql);
        System.out.println("Please input your target book's name");
        preparedStatement.setString(1,scanner.nextLine());
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Book> arrayList = new ArrayList<>();

        while(resultSet.next()) {
            arrayList.add(new Book(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getTimestamp(5),
                    resultSet.getTimestamp(6)));
        }

        if(arrayList.size() == 0) {
            System.out.println("Fail to search target Book!");
        }

        Iterator<Book> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        JDBCUtils.close(preparedStatement, connection);
        scanner.close();
    }
    public static void viewAllData() throws SQLException {
        String sql = "select * from jun";
        preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Book> arrayList = new ArrayList<>();

        while(resultSet.next()) {
            arrayList.add(new Book(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getTimestamp(5),
                    resultSet.getTimestamp(6)));
        }

        if(arrayList.size() == 0) {
            System.out.println("The table don't have any data!");
        }

        Iterator<Book> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        JDBCUtils.close(preparedStatement, connection);
    }
    public static void truncateTable() throws SQLException {
        String sql = "truncate table jun";
        preparedStatement = connection.prepareStatement(sql);
        boolean isTruncate = preparedStatement.execute();

        if (isTruncate == true) {
            System.out.println("Manage to truncate the table");
        } else {
            System.out.println("The function is invalid!");
        }

        JDBCUtils.close(preparedStatement, connection);
    }




    public static void close(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) throws SQLException {
        if(resultSet != null) {
            resultSet.close();
        }

        if(preparedStatement != null) {
            preparedStatement.close();
        }

        if(connection != null) {
            connection.close();
        }
    }

    public static void close(PreparedStatement preparedStatement, Connection connection) throws SQLException {
        if(preparedStatement != null) {
            preparedStatement.close();
        }

        if(connection != null) {
            connection.close();
        }



    }
}
