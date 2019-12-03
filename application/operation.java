package application;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class operation {
	
    public static VBox addOperation(Stage stage, SocialNetworkManager mgr) {
    	 VBox operation = new VBox();
    	 operation.setSpacing(4);
    	 operation.setAlignment(Pos.CENTER);
    	 operation.setPadding(new Insets(15));
    	 
    	 //operation.setAlignment();
         Button Import = new Button("Import");
         Import.setPrefSize(150, 30);
         FileChooser fileChooser = new FileChooser();
         Import.setOnAction(new EventHandler<ActionEvent>() {
        	    @Override public void handle(ActionEvent e) {
        	    	File selectedFile = fileChooser.showOpenDialog(stage);
        	    	try{
        	    		mgr.constructNetwork(selectedFile);
        	    	}
        	        catch (FileNotFoundException exception) {
        	        	 //e.printStackTrace();

        	        } catch (IOException exception) {
        	            //e.printStackTrace();

    	        	} catch (Exception exception) {
	    	            //e.printStackTrace();

    	        	}
        	    }
        	});
         
         Button Export = new Button("Export");
         Export.setPrefSize(150,30);
         Button RemoveAllUsers = new Button("RemoveAllUsers");
         RemoveAllUsers.setPrefSize(150,30);
         
         
         Button ViewFriend = new Button("View Friendships//");
         ViewFriend.setPrefSize(150,30);
         // action rotate event 
         ViewFriend.setOnAction(new EventHandler<ActionEvent>() { 
         	String S;
             public void handle(ActionEvent e) 
             { 
                 	try{
                 		
                 	
                 	}
                 	 catch (Exception nfe)
                     {
                 		 nfe.printStackTrace();
//                 		 result.setText("invalid input");
                     }
                 } 
         }); 
         
         
         
         Button AddUser = new Button("Add Users");
         AddUser.setPrefSize(150,30);
         Button DeleteUser = new Button("Delete Users");
         DeleteUser.setPrefSize(150,30);
         
         Label title = new Label();
         title.setText("Operations");
         title.setFont(Font.font("Verdana",FontPosture.ITALIC,12));
         title.setAlignment(Pos.CENTER);
         
         //set actions
//         ViewFriend.setOnAction(EventByButton);
         
         operation.getChildren().add(title);
         operation.getChildren().add(Import);
         operation.getChildren().add(Export);
         operation.getChildren().add(RemoveAllUsers);
         operation.getChildren().add(ViewFriend);
         operation.getChildren().add(AddUser);
         operation.getChildren().add(DeleteUser);
         return operation;
    }
    
    
    public static VBox Friendship(Stage stage, SocialNetworkManager mgr, Label result, String username) {

    	VBox Friendship = new VBox();

    	Friendship.setSpacing(4);

    	Friendship.setAlignment(Pos.CENTER);

    	Friendship.setPadding(new Insets(15));

   	 

    	Label title = new Label();

        title.setText("Operations");

        title.setFont(Font.font("Verdana",FontPosture.ITALIC,12));

        title.setAlignment(Pos.CENTER);

        title.setTextFill(Color.WHITE);

        title.setBackground(new Background(new BackgroundFill(Color.GREEN,  

	            CornerRadii.EMPTY, Insets.EMPTY)));

	    

	    Button AddFriend = new Button("Add friend");

	    AddFriend.setPrefSize(150,30);

        AddFriend.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 4px; "

        		+ "-fx-background-color: #F5F5F5; -fx-font-size: 2em; -fx-text-fill: #008080");

        

        // create a event handler 

        AddFriend.setOnAction(new EventHandler<ActionEvent>() { 

            public void handle(ActionEvent e) 

            { 

            	try {

	            	// create a text input dialog 

	                TextInputDialog td = new TextInputDialog(); 

	                // setHeaderText 

	                td.setTitle("Add new friend");

	                td.setHeaderText("Please Enter Name"); 

	                td.setContentText("Name:");

	                Optional<String> choice = td.showAndWait();

	                // if we choose the button OK

	        		if (choice.isPresent()) {

//	        			if (mgr.getperson().contains(td.getEditor().getText())){

//	                		Alert error = new Alert(Alert.AlertType.ERROR, "ERROR: Duplicate person is not allowed");

//	                		Button err = new Button();

//	                		err.setOnAction((ActionEvent ee)->{error.showAndWait();});

//	        		}

	        			// if the user is the friend of the input name or the input name is null, then disable the OK button

	        			if(mgr.getPersonalNetwork(username).contains(td.getEditor().getText()) 

	        					|| (td.getEditor().getText()) == null)

	        				td.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);

	        			mgr.setFriendship(username, td.getEditor().getText()); //add friend ?

	        			result.setText("Friend List : " + mgr.getPersonalNetwork(username));

	        		}

            	}

        		 catch (Exception nfe){

            		 nfe.printStackTrace();

            		 result.setText("invalid input");

                }	

            } 

        });

        

        Button RemoveFriend = new Button("Remove friend");

        RemoveFriend.setPrefSize(150,30);

        RemoveFriend.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 4px; "

        		+ "-fx-background-color: #F5F5F5; -fx-font-size: 2em; -fx-text-fill: #008080");

        RemoveFriend.setOnAction(new EventHandler<ActionEvent>() { 

            public void handle(ActionEvent e) 

            { 

            	try {

	            	// create a text input dialog 

	                TextInputDialog td1 = new TextInputDialog(); 

	                // setHeaderText 

	                td1.setTitle("Remove a friend");

	                td1.setHeaderText("Please Enter Name"); 

	                td1.setContentText("Name:");

	                Optional<String> choice1 = td1.showAndWait();

	        		if (choice1.isPresent()) {

//	        			if (mgr.getperson().contains(td.getEditor().getText())){

//	                		Alert error = new Alert(Alert.AlertType.ERROR, "ERROR: Duplicate person is not allowed");

//	          			Button err = new Button();

//	                		err.setOnAction((ActionEvent ee)->{error.showAndWait();});

//	        		}

	        			if(!mgr.getPersonalNetwork(username).contains(td1.getEditor().getText()) 

	        					|| (td1.getEditor().getText()) == null)

	        				td1.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);

	        			mgr.removeFriendship(username, td1.getEditor().getText());

	        			result.setText("Friend List : " + mgr.getPersonalNetwork(username));

	        		}

            	}

        		 catch (Exception nfe){

            		 nfe.printStackTrace();

            		 result.setText("invalid input");

                }	

            } 

        });

        

        Button RemoveAllFriend = new Button("Remove all friend");

        RemoveAllFriend.setPrefSize(150,30);

        RemoveAllFriend.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 4px; "

        		+ "-fx-background-color: #F5F5F5; -fx-font-size: 2em; -fx-text-fill: #008080");

        RemoveAllFriend.setOnAction(new EventHandler<ActionEvent>() { 

            public void handle(ActionEvent e) 

            { 

            	try {

            		for (String s : mgr.getPersonalNetwork(username))

            			mgr.removeFriendship(username, s);

            		result.setText("Friend List : " + mgr.getPersonalNetwork(username));

            	}

            	catch (Exception nfe){

           		 nfe.printStackTrace();

           		 result.setText("invalid input");

               }	

            }

        });

        

        

        

        Button ViewFriendship = new Button("View friendship");

        ViewFriendship.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 4px; "

        		+ "-fx-background-color: #F5F5F5; -fx-font-size: 2em; -fx-text-fill: #008080");

        

        Button Back = new Button("Back");

        Back.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 4px; "

        		+ "-fx-background-color: #F5F5F5; -fx-font-size: 2em; -fx-text-fill: #008080");

        

        Button Menu = new Button("Menu");

        Menu.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 4px; "

        		+ "-fx-background-color: #F5F5F5; -fx-font-size: 2em; -fx-text-fill: #008080");

        

        Button Recall = new Button("Recall");

        Recall.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 4px; "

        		+ "-fx-background-color: #F5F5F5; -fx-font-size: 2em; -fx-text-fill: #008080");

        

        Friendship.getChildren().addAll(title, AddFriend, RemoveFriend, RemoveAllFriend, ViewFriendship, Back, Menu, Recall);

        

        return Friendship;

    }
    
}
