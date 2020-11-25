/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp3415.telehealth.db;

import javafx.stage.Stage;

/**
 *
 * @author Gautam
 */
public class LogInfo {
    public static int UserID;                               // Global variable User ID
    public static String UName;                             // Global variable User name
    public static String UType;                             // Global variable for user type (Patient or Doctor)
    public static boolean isLoggedIn = false;               // Global variable to check if logged in
    public static Stage window;                       // Global variable for the window (Stage)
}
