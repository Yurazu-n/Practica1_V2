package org.example.view;

import org.example.control.MyExecutionException;
import org.example.control.WeatherDataBase;
import org.example.model.WeatherStorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class WeatherInterface {
    private Scanner scanner;
    private WeatherStorage weatherStorage;
    private DataBaseTableViewer dataBaseTableViewer;

    public WeatherInterface() {
        this.scanner = new Scanner(System.in);
        this.weatherStorage = new WeatherDataBase();
        this.dataBaseTableViewer = new DataBaseTableViewer();
    }

    public void run(String path) throws MyExecutionException {
        try {
            String url = "jdbc:sqlite:" + path + "/IslandDataBase.db";
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();

            while (true) {
                System.out.println();
                System.out.println("1. Get an Island climatic information");
                System.out.println("2. Exit");
                System.out.println();

                int choice = getUserChoice();

                switch (choice) {
                    case 1:
                        System.out.println("1. Tenerife");
                        System.out.println("2. Gran Canaria");
                        System.out.println("3. Lanzarote");
                        System.out.println("4. Fuerteventura");
                        System.out.println("5. La Palma");
                        System.out.println("6. El Hierro");
                        System.out.println("7. La Gomera");
                        System.out.println("8. Go Back");
                        System.out.println();

                        int islandNumber = getUserChoice();
                        switch (islandNumber) {
                            case 1:
                                System.out.println("Showing the table:");
                                getDataBaseTableViewer().dataTableViewer(statement, "Tenerife");
                                System.out.println();
                                break;
                            case 2:
                                System.out.println("Showing the table:");
                                getDataBaseTableViewer().dataTableViewer(statement, "GranCanaria");
                                System.out.println();
                                break;
                            case 3:
                                System.out.println("Showing the table:");
                                getDataBaseTableViewer().dataTableViewer(statement, "Lanzarote");
                                System.out.println();
                                break;
                            case 4:
                                System.out.println("Showing the table:");
                                getDataBaseTableViewer().dataTableViewer(statement, "Fuerteventura");
                                System.out.println();
                                break;
                            case 5:
                                System.out.println("Showing the table:");
                                getDataBaseTableViewer().dataTableViewer(statement, "LaPalma");
                                System.out.println();
                                break;
                            case 6:
                                System.out.println("Showing the table:");
                                getDataBaseTableViewer().dataTableViewer(statement, "ElHierro");
                                System.out.println();
                                break;
                            case 7:
                                System.out.println("Showing the table:");
                                getDataBaseTableViewer().dataTableViewer(statement, "LaGomera");
                                break;
                            case 8:
                                System.out.println("Going back-");
                                break;
                            default:
                                System.out.println("Not valid option, going back-");
                                break;
                        }
                        break;
                    case 2:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Not valid option, try again");
                }
            }
        } catch (SQLException e) {
            throw new MyExecutionException("Execution Error");
        }
    }

    private int getUserChoice() {
        System.out.print("Write a number according to the options above: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public DataBaseTableViewer getDataBaseTableViewer() {
        return dataBaseTableViewer;
    }
}

