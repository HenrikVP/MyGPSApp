package com.example.mygpsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sql extends AppCompatActivity {
    // Connect to your database.
    // Replace server name, username, and password with your credentials

    private static String host = "10.233.149.11";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "carDb";
    private static String username = "sa";
    private static String password = "Passw0rd";
    //private static String url = "jdbc:jtds:sqlserver://"+host+":"+port+"/"+database;
    private static String url = "jdbc:jtds:sqlserver://" + host + "/" + database;

    private Connection connection = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startConn();
    }

    public void startConn() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username, password);
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Class fail", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Connected no", Toast.LENGTH_SHORT).show();
        }
    }


    public static void selectFromDB() {
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
//Data Source=10.233.149.11;Initial Catalog=CarDB;User ID=sa;Password=********;Connect Timeout=30;Encrypt=True;Trust Server Certificate=True;Application Intent=ReadWrite;Multi Subnet Failover=False
        String connectionUrl =
                "jdbc:sqlserver://10.233.149.11:1433;"
                        + "databaseName=CarDB;"
                +"Encrypt=true;TrustServerCertificate=True;" +
                        "trustStore=mygpsapp;trustStorePassword=Passw0rd;"
                        + "username=sa;"
                        + "password=Passw0rd;"
                        + "loginTimeout=10;";

        //Data Source=10.233.149.11;Initial Catalog=CarDB;User ID=sa;Password=********;Connect Timeout=30;Encrypt=True;Trust Server Certificate=True;Application Intent=ReadWrite;Multi Subnet Failover=False
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM CarTable";
            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                System.out.println(resultSet.getString(2) + " " + resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String connectionUrl =
                "jdbc:sqlserver://10.233.149.11:1433;"
                        + "database=CarDB;"
                        + "user=sa;"
                        + "password=Passw0rd;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=10;";

        //Data Source=10.233.149.11;Initial Catalog=CarDB;User ID=sa;Password=********;Connect Timeout=30;Encrypt=True;Trust Server Certificate=True;Application Intent=ReadWrite;Multi Subnet Failover=False
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM CarTable";
            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                System.out.println(resultSet.getString(2) + " " + resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}