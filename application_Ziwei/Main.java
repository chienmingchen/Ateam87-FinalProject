/**
 * 
 */
package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

/**
 * @author james
 * @param <SocialNetworkManager>
 *
 */
public class Main extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;
	
	//GUI needs to interact with SocialNetworkManager
	private SocialNetworkManager mgr = new SocialNetworkManager();

	private static final int WINDOW_WIDTH = 550;
	private static final int WINDOW_HEIGHT = 300;
	private static final String APP_TITLE = "SocialNetworkManager";
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();
		
		//Init the social network
		mgr.constructNetwork("File Path");
		
		// Create a vertical box in center panel for getting friend list 
        // Hbox contains
        VBox vbox = new VBox();
        
        // create a label for prompting message
        Label prompt = new Label("Enter the person you want to see his/her network");
        
        // create a textfield for inputing name
        TextField input = new TextField();
        
        // Create rotate button for the right panel
        Button rotateButton = new Button("Check");
        
        // create a label for showing rotation degree
        Label result = new Label("Friend List : ");
        
        // action rotate event 
        EventHandler<ActionEvent> EventByButton = new EventHandler<ActionEvent>() { 
        	String S;
            public void handle(ActionEvent e) 
            { 
                	try{
                		result.setText("Friend List : " + mgr.getPersonalNetwork(input.getText()));
                	}
                	 catch (Exception nfe)
                    {
                		 nfe.printStackTrace();
                		 result.setText("invalid input");
                    }
                } 
        }; 
        
        
        EventHandler<ActionEvent> EventByEnter = new EventHandler<ActionEvent>() { 
        	String S;
            public void handle(ActionEvent e) 
            { 
            	try{
            		//for(int i=0; i<mgr.getPersonalNetwork(input.getText()).size(); i++)
            			//S += mgr.getPersonalNetwork(input.getText());
            		result.setText("Friend List : " + mgr.getPersonalNetwork(input.getText()));
            	}
            	 catch (Exception nfe)
                {
            		 nfe.printStackTrace();
            		 result.setText("invalid input");
                }
            } 
        }; 
        
        // Set the action of the textfield
        input.setOnAction(EventByEnter);
        // Set the action of the Click button
        rotateButton.setOnAction(EventByButton);
        
        // fill vbox with different components
        vbox.getChildren().add(prompt);
        vbox.getChildren().add(input);
        vbox.getChildren().add(rotateButton);
        vbox.getChildren().add(result);
        
       
        
        
		// Main layout is Border Pane example (top,left,center,right,bottom)
        BorderPane root = new BorderPane();
        root.setRight(operation.addOperation(primaryStage));
        // Set Layout
        root.setCenter(vbox);
		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		// Add the stuff and set the primary stage
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(true); 
        primaryStage.show();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		   launch(args);
	}
	
}
