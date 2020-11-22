/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp3415.telehealth.db;
import comp3415.telehealth.views.loginLayout;

import javax.swing.JFrame;
/**
 *
 * @author Gautam
 */
public class logout {                                                       // class to log out of session
    public static void logOut(JFrame context, loginLayout loginDisplay){
        logInfo.isLoggedIn = false;                                         //set log in to false
        loginDisplay.setVisible(true);
        context.setVisible(false);
}
}
