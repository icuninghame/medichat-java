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
    public static int uID;                      // Global variable User ID
    public static String uname;                 // Global variable username
    public static String displayName;           // Global variable for user display name
    public static String uType;                 // Global variable for user type (Patient or Doctor)
    public static Stage window;                 // Global variable for the window (Stage)
    public static boolean isLoggedIn = false;   // Global variable to check if logged in
}
