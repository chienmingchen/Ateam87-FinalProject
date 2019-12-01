package application;
import java.io.File;
import java.util.List;

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
    public static VBox addOperation(Stage stage) {
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
        	    	
        	    }
        	});
         
         Button Export = new Button("Export");
         Export.setPrefSize(150,30);
         Button RemoveAllUsers = new Button("RemoveAllUsers");
         RemoveAllUsers.setPrefSize(150,30);
         
         
         Button ViewFriend = new Button("View Friendships");
         ViewFriend.setPrefSize(150,30);
//         // action rotate event 
//         EventHandler<ActionEvent> EventByButton = new EventHandler<ActionEvent>() { 
//         	String S;
//             public void handle(ActionEvent e) 
//             { 
//                 	try{
//                 		
//                 	
//                 	}
//                 	 catch (Exception nfe)
//                     {
//                 		 nfe.printStackTrace();
////                 		 result.setText("invalid input");
//                     }
//                 } 
//         }; 
         
         
         
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
    
}
