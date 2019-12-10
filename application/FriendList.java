package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FriendList {
	
	public static List<String> getFriends(SocialNetworkManager mgr, TextField input) {
		List<String> list = mgr.getPersonalNetwork(input.getText());
		return list;
	}
		
	
	public static List<String> getFriends(SocialNetworkManager mgr, String input) {
		List<String> list = mgr.getPersonalNetwork(input);
		return list;
	}

//	, String title, Button importButton, Button Export, Button RemoveAllUsers, Button ViewFriend, Button AddUser, Button DeleteUser
//	userOperation[0]=title;

    public static VBox setAsUserOperation(VBox operation, Label title, Button importButton, 
    		Button Export, Button AddFriendship, Button RemoveAllUsers, Button ViewFriend, Button AddUser, 
    		Button DeleteUser, Button Undo, Button Redo) {
    	operation.getChildren().clear();
        operation.getChildren().add(title);
        operation.getChildren().add(importButton);
        operation.getChildren().add(Export);
        operation.getChildren().add(AddFriendship);
        operation.getChildren().add(RemoveAllUsers);
        operation.getChildren().add(ViewFriend);
        operation.getChildren().add(AddUser);
        operation.getChildren().add(DeleteUser);
        operation.getChildren().add(Undo);
        operation.getChildren().add(Redo);
    			
    	return operation;
    }
	

    public static VBox setAsFriendOperation(VBox operation, Label title, Button AddFriend, 
    		Button RemoveFriend, Button RemoveSelectedFriend, Button RemoveAllFriend, Button ViewFriendship, 
    		 Button Back, Button Menu, Button Recall,Button Undo, Button Redo) {
    	operation.getChildren().clear();
        operation.getChildren().add(title);
        operation.getChildren().add(AddFriend);
        operation.getChildren().add(RemoveFriend);
        operation.getChildren().add(RemoveSelectedFriend);
        operation.getChildren().add(RemoveAllFriend);
        operation.getChildren().add(ViewFriendship);
        operation.getChildren().add(Back);
        operation.getChildren().add(Menu);
        operation.getChildren().add(Recall);
        operation.getChildren().add(Undo);
        operation.getChildren().add(Redo);
    	return operation;
    }
	}