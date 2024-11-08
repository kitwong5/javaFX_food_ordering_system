/********************************************************/
/* COSC1295 Assignment 2 Burrito Order System           */
/* Student Name: Kit Yi Wong                			*/
/* Student ID: s3970390									*/
/* 														*/
/* Date: 17/05/2024										*/
/* 														*/
/* Version: 1.0											*/
/*********************************************************/

package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import application.DB.CreateTable;
import application.DB.CreateItemTable;

public class Main extends Application {
	private static Stage primaryStage; 

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }
    
    @SuppressWarnings("static-access")
    @Override
	public void start(Stage primaryStage) {
		try {
			// Set the primaryStage
			setPrimaryStage(primaryStage); 
			// Load login page
			Parent root = FXMLLoader.load(getClass().getResource("/application/view/fxml/Login.fxml"));
			Scene sence = new Scene(root);
			primaryStage.setScene(sence);
			primaryStage.show();
		
			// Create require table if no exist
			CreateTable user_table = new CreateTable();
			user_table.CTable();
			CreateItemTable item_table = new CreateItemTable();
			item_table.CItemTab();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
