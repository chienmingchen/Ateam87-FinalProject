/**
 * 
 */
package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
        // Vbox contains
        VBox vbox = new VBox();
        
        // create a label for prompting message
        Label prompt = new Label("Enter the person you want to see his/her network");
        
        // create a textfield for inputing name
        TextField input = new TextField();
        
        // Create rotate button for the right panel
        Button rotateButton = new Button("Check");
//        Button btn = new Button("Check2");
        
        // create a label for showing rotation degree
        Label result = new Label("Friend List : ");
        
        //create a list for viewing friends
        ObservableList<String> obl = FXCollections.observableList(new ArrayList<String>());
        obl.add("Empty Friend List");
        ListView<String> lv = new ListView<String>(obl);
        //create a scroll bar for friend list
        ScrollPane pane = new ScrollPane();
        
    
        // action rotate event 
        EventHandler<ActionEvent> EventByButton = new EventHandler<ActionEvent>() { 
        	String S;
            public void handle(ActionEvent e) 
            { 
            	System.out.println("AAAA");
                	try{
                		List<String> updated = FriendList.getFriends(mgr, input);
                		//update friends information
                		if(updated == null) {
                			obl.clear();
                    		obl.add("Cannot Find: " + input.getText());
                		}else {
                			obl.clear();
                    		obl.addAll(updated);
                		}                		result.setText("Friend List : " + mgr.getPersonalNetwork(input.getText()));
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
            { System.out.println("BBBB");
            	try{
            		//for(int i=0; i<mgr.getPersonalNetwork(input.getText()).size(); i++)
            			//S += mgr.getPersonalNetwork(input.getText());
            		List<String> updated = FriendList.getFriends(mgr, input);
            		System.out.println(mgr.getPersonalNetwork(input.getText()));
            		System.out.println(updated);
            		//update friends information
            		if(updated == null) {
            			obl.clear();
                		obl.add("Cannot Find: " + input.getText());
            		}else {
            			obl.clear();
                		obl.addAll(updated);
            		}
            		
            		 //set listview
            		result.setText("Friend List : " + mgr.getPersonalNetwork(input.getText()));
            	}
            	 catch (Exception nfe)
                {
            		 nfe.printStackTrace();
            		 result.setText("invalid input");
                }
            } 
        }; 
        
       
        //event of clicking a friend in the list
        EventHandler<MouseEvent> EventByMouse = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	//for test purpose
                System.out.println("clicked on " + lv.getSelectionModel().getSelectedItem());
            }
        };
        
        
        // Set the action of the textfield
        input.setOnAction(EventByEnter);
        // Set the action of the Click button
        rotateButton.setOnAction(EventByButton);
        //Set the action of clicking by mouse
        lv.setOnMouseClicked(EventByMouse);
        
        // fill vbox with different components
        vbox.getChildren().add(prompt);
        vbox.getChildren().add(input);
        vbox.getChildren().add(rotateButton);
        vbox.getChildren().add(result);
        

		// Main layout is Border Pane example (top,left,center,right,bottom)
        BorderPane root = new BorderPane();
        root.setRight(operation.addOperation(primaryStage));
        
        // Set Layout
        root.setTop(vbox);
		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		root.setLeft(lv);
		
		//set scroll bar for friend list
		lv.setItems(obl);
        pane.prefWidthProperty().bind(lv.widthProperty());
        pane.prefHeightProperty().bind(lv.heightProperty());
        pane.setContent(lv);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        Group group = new Group();
        group.getChildren().add(pane);
		
		// Add the stuff and set the primary stage
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(mainScene);
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
