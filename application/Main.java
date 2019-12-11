/**
 * 
 */
package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

import com.sun.javafx.logging.Logger;
import com.sun.javafx.logging.PlatformLogger.Level;

import application.Main.UserNode;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

/**
 * @author james
 * @param <SocialNetworkManager>
 *
 */
public class Main extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;
 
	// GUI needs to interact with SocialNetworkManager
	private static SocialNetworkManager mgr = new SocialNetworkManager();

	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 600;
	private static final String APP_TITLE = "SocialNetworkManager";
	// item that user select
	private static String selectedUser;

	public static SocialNetworkManager getSocialNetworkManager() {
		return mgr;
	}

//	private Stage mainStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
//		this.mainStage = primaryStage;		
//		 primaryStage.setOnCloseRequest(confirmCloseEventHandler);
		 
		// save args example
		args = this.getParameters().getRaw();
		
		class history{
			boolean delete;
			boolean vertexOperation;
			String name1;
			String name2;
			List<String> friends;
			public history(boolean delete, boolean vertexOperation, String name1,String name2, List<String> friends){
				this.delete = delete;
				this.vertexOperation = vertexOperation;
				this.name1=name1;
				this.name2=name2;
				this.friends = friends;
			}
		}
		Stack<List<String>> users = new Stack<List<String>>();
		Stack<List<String>> reUsers = new Stack<List<String>>();
		Stack<List<history>> undoHistory = new Stack<List<history>>();
		Stack<List<history>> redoHistory = new Stack<List<history>>();
		// Init the social network
		// mgr.constructNetwork("File Path");

		// Create a vertical box in center panel for getting friend list
		// Vbox contains
		VBox vbox = new VBox();

		// create a label for prompting message
		Label prompt = new Label("Enter the person you want to see his/her network");

		// create a textfield for inputing name
		TextField input = new TextField();

		// Create rotate button for the right panel
		Button rotateButton = new Button("Check");

		// create a label for showing rotation degree
		Label result = new Label("Friend List : ");

		// create a list for viewing friends
		ObservableList<String> obl = FXCollections.observableList(new ArrayList<String>());
//		obl.add("Empty Friend List");
		ListView<String> lv = new ListView<String>(obl);
		lv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// create a scroll bar for friend list
		ScrollPane pane = new ScrollPane();

		// The GridPane for showing central user network
		GridPane centralUserNtwk = new GridPane();
		centralUserNtwk.setPrefHeight(0.5*WINDOW_WIDTH);
	   	centralUserNtwk.setPrefWidth(0.75*WINDOW_HEIGHT);
	   	//Specify the gridpane's size and number of rows and columns
	   	int cols = 11;
	   	int rows = 11;
	   	double gpWidth = 0.5*WINDOW_WIDTH;
	   	double gpHeight = 0.6*WINDOW_HEIGHT;
		for(int i=0;i<cols;i++) {
			//hard code
			centralUserNtwk.getColumnConstraints().add(new ColumnConstraints(gpWidth/cols));
			centralUserNtwk.getRowConstraints().add(new RowConstraints(gpHeight/rows));
		}
		
		// title for opereation vbox
		Label title = new Label();
		title.setText("Operations");
		title.setFont(Font.font("Verdana", FontPosture.ITALIC, 12));
		title.setAlignment(Pos.CENTER);

		// Buttons for user operations
		Button Import = new Button("Import");
		Button Export = new Button("Export");
		Button AddFriendship = new Button("Add Friendship");
		Button RemoveAllUsers = new Button("RemoveAllUsers");
		Button ViewFriend = new Button("View Friendships");
		Button AddUser = new Button("Add Users");
		Button DeleteUser = new Button("Delete Users");
		Button MutualFriends = new Button("Mutual Friends");
		Button ShortestPath = new Button("Shortest path between two");
		Button Undo = new Button("Undo");
		Button Redo = new Button("Redo");
		
		Import.setPrefSize(150, 30);
		Export.setPrefSize(150, 30);
		AddFriendship.setPrefSize(150, 30);
		RemoveAllUsers.setPrefSize(150, 30);
		ViewFriend.setPrefSize(150, 30);
		AddUser.setPrefSize(150, 30);
		DeleteUser.setPrefSize(150, 30);
		MutualFriends.setPrefSize(150, 30);
		ShortestPath.setPrefSize(150, 30);
		Undo.setPrefSize(150, 30);
		Redo.setPrefSize(150, 30);
        Undo.setDisable(true);
        Redo.setDisable(true);
		RemoveAllUsers.setDisable(true);
		ViewFriend.setDisable(true);
		AddUser.setDisable(false);
		DeleteUser.setDisable(true);
		
		// Buttons for friend operations
		Button AddFriend = new Button("Add Friend");
		Button RemoveAllFriend = new Button("Remove All Friends");
		Button RemoveSelectedFriend = new Button("Remove Selected Friends");
		Button RemoveFriend = new Button("Remove Friend");
		Button ViewFriendship = new Button("View Friendship(Friend Page)");
		Button Back = new Button("Back");
		Button Menu = new Button("Menu");
		Button Recall = new Button("Recall");
		AddFriend.setPrefSize(150, 30);
		RemoveFriend.setPrefSize(150, 30);
		RemoveSelectedFriend.setPrefSize(150, 30);
		RemoveAllFriend.setPrefSize(150, 30);
		ViewFriendship.setPrefSize(150, 30);
		Back.setPrefSize(150, 30);
		Menu.setPrefSize(150, 30);
		Recall.setPrefSize(150, 30);
		
		//temporary button accessibility for a2, edited in 12/2
		Back.setDisable(true);
		Recall.setDisable(true);
		

		// create a vbox for operations
		VBox operation = new VBox();
		operation.setSpacing(4);
		operation.setAlignment(Pos.CENTER);
		operation.setPadding(new Insets(15));

		// operation.setAlignment();
//        Button Import = new Button("Import");
//        Import.setPrefSize(150, 30);
		FileChooser fileChooser = new FileChooser();
		
		DeleteUser.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// for test purpose
				if(lv.getSelectionModel().getSelectedItems() != null) {
					List<String> historyList = new ArrayList<String>();
					Set<String> historySet = mgr.getAllUsers();
					for (String item : historySet) {
						historyList.add(item);
					}
					users.add(historyList);
					List<String> selectedUser = lv.getSelectionModel().getSelectedItems();
					String a = "";
					
					List<history> thisHistory = new ArrayList<history>();
					for(int i = selectedUser.size()-1;i>=0;i--) {
					String userToDelete = selectedUser.get(i);
					a+=userToDelete+", ";
					List<String> friends = mgr.getPersonalNetwork(userToDelete);
					//System.out.println(userToDelete);
					
					history newHistory = new history(true,true,userToDelete,null,friends);
					
					thisHistory.add(newHistory);
					mgr.removePerson(userToDelete);
					
					}
					undoHistory.add(thisHistory);
					
					Undo.setDisable(false);
					List<String> updated = new ArrayList<String>();
					Set<String> updatedSet = mgr.getAllUsers();
					for (String item : updatedSet) {
						updated.add(item);
					}
					obl.clear();

					if(updated.size()!=0) {
						result.setText("" + a + "  deleted.");
					obl.addAll(updated);					
					}
					else {
						result.setText("" + a + "  deleted. List is Empty");
						Import.setDisable(false);
						Export.setDisable(false);
						AddUser.setDisable(false);						
						RemoveAllUsers.setDisable(true);
					    ViewFriend.setDisable(true);
					}
					DeleteUser.setDisable(true);


			}}



		});
		Undo.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e) {
				List<String> historyList = new ArrayList<String>();
				Set<String> historySet = mgr.getAllUsers();
				for (String item : historySet) {
					historyList.add(item);
				}
				reUsers.add(historyList);
				List<history> lastAction = new ArrayList<history>();
				lastAction = undoHistory.pop();
				for(int i = 0; i < lastAction.size();i++) {
					history eachAction = lastAction.get(i);
					
					if(eachAction.delete==true) {
						//delete vertex
						if(eachAction.vertexOperation==true) {
							mgr.addPerson(eachAction.name1);
							if(eachAction.friends!=null) {
							for(String friend:eachAction.friends) {
								mgr.setFriendship(eachAction.name1,friend);
							}}
						}
						//delete edge
						else {
							mgr.setFriendship(eachAction.name1, eachAction.name2);
						}
					}
					else {
						//delete vertex
						if(eachAction.vertexOperation==true) {
							mgr.removePerson(eachAction.name1);
						}
						//delete edge
						else {
							mgr.removeFriendship(eachAction.name1, eachAction.name2);
						}
					}
				}
				List<String> updated = users.pop();
				obl.clear();
                
				if(updated.size()!=0) {
					result.setText("Undo Action");
				obl.addAll(updated);					
				}
				else {
					result.setText("Undo Action, empty");
					Import.setDisable(false);
					Export.setDisable(false);
					AddUser.setDisable(false);						
					RemoveAllUsers.setDisable(true);
				    ViewFriend.setDisable(true);
				}
				DeleteUser.setDisable(true);

				redoHistory.add(lastAction);
				Redo.setDisable(false);
				if(undoHistory.empty()==true) {
					Undo.setDisable(true);
				}
			 	//clear the GridPane first whenever new network is imported
				centralUserNtwk.getChildren().clear();
				//then plot the network of the central user
				centralUserNtwk.getChildren().add(plotCentralUserNtwk(centralUserNtwk,mgr));
			}
		});
		Redo.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e) {
				List<String> historyList = new ArrayList<String>();
				Set<String> historySet = mgr.getAllUsers();
				for (String item : historySet) {
					historyList.add(item);
				}
				users.add(historyList);
				List<history> lastAction = new ArrayList<history>();
				lastAction = redoHistory.pop();
				for(int i = 0; i < lastAction.size();i++) {
					history eachAction = lastAction.get(i);
					
					if(eachAction.delete==true) {
						//delete vertex
						if(eachAction.vertexOperation==true) {
							mgr.removePerson(eachAction.name1);
						}
						//delete edge
						else {
							mgr.removeFriendship(eachAction.name1, eachAction.name2);
						}
					}
					else {
						//delete vertex
						if(eachAction.vertexOperation==true) {
							mgr.addPerson(eachAction.name1);
							if(eachAction.friends!=null) {
							for(String friend:eachAction.friends) {
								mgr.setFriendship(eachAction.name1,friend);
							}
							}
						}
						//delete edge
						else {
							mgr.setFriendship(eachAction.name1, eachAction.name2);
						}
					}
				}
				List<String> updated = reUsers.pop();
				obl.clear();
                
				if(updated.size()!=0) {
					result.setText("Undo Action");
				obl.addAll(updated);					
				}
				else {
					result.setText("Undo Action, empty");
					Import.setDisable(false);
					Export.setDisable(false);
					AddUser.setDisable(false);						
					RemoveAllUsers.setDisable(true);
				    ViewFriend.setDisable(true);
				}
				DeleteUser.setDisable(true);

				undoHistory.add(lastAction);
				Undo.setDisable(false);
				if(redoHistory.empty()==true) {
					Redo.setDisable(true);
				}
			 	//clear the GridPane first whenever new network is imported
				centralUserNtwk.getChildren().clear();
				//then plot the network of the central user
				centralUserNtwk.getChildren().add(plotCentralUserNtwk(centralUserNtwk,mgr));
			}
		});
		Import.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				File selectedFile = fileChooser.showOpenDialog(primaryStage);
				try {
					mgr.constructNetwork(selectedFile);
					List<String> updated = new ArrayList<String>();
					Set<String> updatedSet = mgr.getAllUsers();
					for (String item : updatedSet) {
						updated.add(item);
					}

					System.out.println("Update" + updated);
					// update users information
					obl.clear();
					obl.addAll(updated);

					// set listview
					result.setText("File Import Success");
					
					//update button accessibility
					RemoveAllUsers.setDisable(false);
//					ViewFriend.setDisable(false);
					AddUser.setDisable(false);
//					DeleteUser.setDisable(false);
				 	//clear the GridPane first whenever new network is imported
					centralUserNtwk.getChildren().clear();
					//then plot the network of the central user
					centralUserNtwk.getChildren().add(plotCentralUserNtwk(centralUserNtwk,mgr));
					
				} catch (FileNotFoundException exception) {
					Alert alert1 = new Alert(AlertType.WARNING);
					alert1.setTitle("Warning Dialog");
					alert1.setHeaderText("Warning messager");
					alert1.setContentText("Input file can not be found!");
					alert1.showAndWait();
					// e.printStackTrace();

				}catch (IOException exception) {
					// e.printStackTrace();
					Alert alert2 = new Alert(AlertType.WARNING);
					alert2.setTitle("Warning Dialog");
					alert2.setHeaderText("Warning messager");
					alert2.setContentText("Input file can not be read!");
					alert2.showAndWait();
				} catch (Exception exception) {
					// e.printStackTrace();

				}
			}
		});

//        Button Export = new Button("Export");
//        Export.setPrefSize(150,30);      
		
		Export.setOnAction(new EventHandler<ActionEvent>() {
			String S;

			public void handle(ActionEvent e) {
				
		        final String sampleText = "Log Test \n";
		        
	            FileChooser fileChooser = new FileChooser();
	            
	            //Set extension filter for text files
	            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
	            fileChooser.getExtensionFilters().add(extFilter);
	 
	            //Show save file dialog
	            File file = fileChooser.showSaveDialog(primaryStage);
	 
	            if (file != null) {
	            	try {
	            		mgr.saveLog(file);
	            	}
	            	catch (IOException io) {
						io.printStackTrace();
	            	}
	            }
			}
		});
		


		RemoveAllUsers.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {
				try {
					List<String> historyList = new ArrayList<String>();
					Set<String> historySet = mgr.getAllUsers();
					for (String item : historySet) {
						historyList.add(item);
					}
					users.add(historyList);
                    Set<String> a = mgr.getAllUsers();
                    String temp [] = new String[a.size()];
                    int i=0;
                    for(String element:a) {
                    	temp[i]=element;
                    	i++;
                    }
                    List<history> thisHistory = new ArrayList<history>();
                    for(int j=0; j<temp.length;j++) {
                    	List<String> friends = mgr.getPersonalNetwork(temp[j]);
    					//System.out.println(userToDelete);
    					
    					history newHistory = new history(true,true,temp[j],null,friends);
    					thisHistory.add(newHistory);
                    	mgr.removePerson(temp[j]);
                    }
                    undoHistory.add(thisHistory);
					Undo.setDisable(false);
                    //test functionality
//                    List<String> updated = new ArrayList<String>();
//					Set<String> updatedSet = mgr.getAllUsers();
//					for (String item : updatedSet) {
//						updated.add(item);
//					}
//					System.out.println(updatedSet);
					obl.clear();
//					obl.add("All users removed, Empty Friend List");
					
					//update button accessibility
					ViewFriend.setDisable(true);
					AddUser.setDisable(false);
					DeleteUser.setDisable(true);
					RemoveAllUsers.setDisable(true);
					centralUserNtwk.getChildren().clear();
				} catch (Exception nfe) {
					nfe.printStackTrace();
//                		 result.setText("invalid input");
				}
			}
		});

		// action rotate event
		ViewFriend.setOnAction(new EventHandler<ActionEvent>() {
			String S;

			public void handle(ActionEvent e) {
				try {
					if (mgr.getCentralPerson() != null) {
						List<String> updated = FriendList.getFriends(mgr, selectedUser);
						mgr.centralize(selectedUser); // set central user
						// update friends information
						if (updated == null) {
							obl.clear();
							obl.add("Cannot Find: " + input.getText());
						} else {
							obl.clear();
							obl.addAll(updated);
						}
						undoHistory.clear();
						redoHistory.clear();
						Undo.setDisable(true);
						Redo.setDisable(true);
						FriendList.setAsFriendOperation(operation, title, AddFriend, RemoveFriend, RemoveSelectedFriend, RemoveAllFriend,
								ViewFriend, Back, Menu, Recall,Undo,Redo);
						// set listview
						result.setText("Friend Of : " + selectedUser + "Central User: " + mgr.getCentralPerson());
					
						//update button accessibility
						ViewFriend.setDisable(true);
//						DeleteUser.setDisable(true);
//						Import.setDisable(false);
//						Export.setDisable(false);
//						RemoveAllUsers.setDisable(false);
//						AddUser.setDisable(false);
						//button in friend page
						RemoveFriend.setDisable(false);
						RemoveSelectedFriend.setDisable(true);
						RemoveAllFriend.setDisable(false);
						AddFriend.setDisable(false);
						centralUserNtwk.getChildren().clear();
						centralUserNtwk.getChildren().add(plotCentralUserNtwk(centralUserNtwk,mgr));
					}

				} catch (Exception nfe) {
					nfe.printStackTrace();
//                		 result.setText("invalid input");
				}
			}
		});

		AddUser.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e)

			{

				try {

					// create a text input dialog

					TextInputDialog td1 = new TextInputDialog();

					// setHeaderText

					td1.setTitle("Add a user");

					td1.setHeaderText("Please Enter Name");

					td1.setContentText("Name:");

					Optional<String> choice1 = td1.showAndWait();

					if (choice1.isPresent()) {

//	        			if (mgr.getperson().contains(td.getEditor().getText())){

//	                		Alert error = new Alert(Alert.AlertType.ERROR, "ERROR: Duplicate person is not allowed");

//	          			Button err = new Button();

//	                		err.setOnAction((ActionEvent ee)->{error.showAndWait();});

//	        		}

						if ((td1.getEditor().getText()) != null) {
							if(mgr.getAllUsers().contains(td1.getEditor().getText())) {
								final Stage dialog = new Stage();
//				                dialog.initModality(Modality.APPLICATION_MODAL);
				                dialog.initModality(Modality.NONE);
				                dialog.initOwner(primaryStage);
				                VBox dialogVbox = new VBox(20);
				                dialogVbox.getChildren().add(new Text("Alert! User Already Exists."));
				                dialogVbox.setAlignment(Pos.CENTER);
				                Scene dialogScene = new Scene(dialogVbox, 300, 200);
				                dialog.setScene(dialogScene);
				                dialog.setTitle("Alert Message");
				                dialog.showAndWait();

							}else {
								List<String> historyList = new ArrayList<String>();
								Set<String> historySet = mgr.getAllUsers();
								for (String item : historySet) {
									historyList.add(item);
								}
								users.add(historyList);
								List<history> thisHistory = new ArrayList<history>();
								history newHistory = new history(false,true,td1.getEditor().getText(),null,null);
								thisHistory.add(newHistory);
								undoHistory.add(thisHistory);
								mgr.addPerson(td1.getEditor().getText());
							}							
						}

                            Undo.setDisable(false);
							// update friends information
							List<String> updated = new ArrayList<String>();							
							Set<String> updatedSet = mgr.getAllUsers();
							for (String item : updatedSet) {
									updated.add(item);
							}
							obl.clear();
							obl.addAll(updated);
							
//							FriendList.setAsFriendOperation(operation, title, AddFriend, RemoveFriend, RemoveAllFriend,
//									ViewFriend, Back, Menu, Recall);
						}

						result.setText("Friend Of : " + mgr.getCentralPerson());
					}
				catch (Exception nfe) {

					nfe.printStackTrace();

					result.setText("invalid input");

				}

			}

		});



		// set actions
//        ViewFriend.setOnAction(EventByButton);

		operation.getChildren().add(title);
		operation.getChildren().add(Import);
		operation.getChildren().add(Export);
		operation.getChildren().add(AddFriendship);
		operation.getChildren().add(RemoveAllUsers);
		operation.getChildren().add(ViewFriend);
		operation.getChildren().add(AddUser);
		operation.getChildren().add(DeleteUser);
		operation.getChildren().add(MutualFriends);
		operation.getChildren().add(ShortestPath);
        operation.getChildren().add(Undo);
        operation.getChildren().add(Redo);

		// create a event handler

		AddFriendship.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e)

			{

				try {

					 //Create the custom dialog.
			        Dialog<Pair<String, String>> dialog = new Dialog<>();
			        dialog.setTitle("Add new friendship(two users)");
			        dialog.setHeaderText("Please Enter Names");
			        
					ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
					dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

					// Create the name1 and name2 labels and fields.
					GridPane grid = new GridPane();
					grid.setHgap(10);
					grid.setVgap(10);
					grid.setPadding(new Insets(20, 150, 10, 10));

					TextField name1 = new TextField();
					name1.setPromptText("Name1");
					TextField name2 = new TextField();
					name2.setPromptText("Name2");

					grid.add(new Label("Name1:"), 0, 0);
					grid.add(name1, 1, 0);
					grid.add(new Label("Name2:"), 0, 1);
					grid.add(name2, 1, 1);

					// Enable/Disable login button depending on whether a name1 was entered.
					Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
					loginButton.setDisable(true);

					// Do some validation (using the Java 8 lambda syntax).
					name1.textProperty().addListener((observable, oldValue, newValue) -> {
					    loginButton.setDisable(newValue.trim().isEmpty());
					});
					
					name2.textProperty().addListener((observable, oldValue, newValue) -> {
					    loginButton.setDisable(newValue.trim().isEmpty());
					});

					dialog.getDialogPane().setContent(grid);

					// Request focus on the name1 field by default.
					//Platform.runLater(() -> name1.requestFocus());

					// Convert the result to a name1-name2-pair when the login button is clicked.
					dialog.setResultConverter(dialogButton -> {
					    if (dialogButton == loginButtonType) {
					        return new Pair<>(name1.getText(), name2.getText());
					    }
					    return null;
					});

					Optional<Pair<String, String>> choice = dialog.showAndWait();

					choice.ifPresent(name1name2 -> {
						mgr.setFriendship(name1.getText(), name2.getText());
				    	 List<String> updated = new ArrayList<String>();       
					       Set<String> updatedSet = mgr.getAllUsers();
					       for (String item : updatedSet) {
					         updated.add(item);
					       }
					       obl.clear();
					       obl.addAll(updated);
						result.setText("Friendship between " + name1.getText()+ " and " +name2.getText()+" is added.");
					});
//

				}

				catch (Exception nfe) {

					nfe.printStackTrace();

					result.setText("invalid input");

				}

			}

		});

		MutualFriends.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e)

			{

				try {

					 //Create the custom dialog.
					Dialog<Pair<String, String>> dialog = new Dialog<>();
					dialog.setTitle("Mutual friends");
					dialog.setHeaderText("Please Enter Names");

					// Set the icon (must be included in the project).
					//dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

					// Set the button types.
					ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
					dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

					// Create the name1 and name2 labels and fields.
					GridPane grid = new GridPane();
					grid.setHgap(10);
					grid.setVgap(10);
					grid.setPadding(new Insets(20, 150, 10, 10));

					TextField name1 = new TextField();
					name1.setPromptText("Name1");
					TextField name2 = new TextField();
					name2.setPromptText("Name2");

					grid.add(new Label("Name1:"), 0, 0);
					grid.add(name1, 1, 0);
					grid.add(new Label("Name2:"), 0, 1);
					grid.add(name2, 1, 1);

					// Enable/Disable login button depending on whether a name1 was entered.
					Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
					loginButton.setDisable(true);

					// Do some validation (using the Java 8 lambda syntax).
					name1.textProperty().addListener((observable, oldValue, newValue) -> {
					    loginButton.setDisable(newValue.trim().isEmpty());
					});

					dialog.getDialogPane().setContent(grid);

					// Request focus on the name1 field by default.
					//Platform.runLater(() -> name1.requestFocus());

					// Convert the result to a name1-name2-pair when the login button is clicked.
					dialog.setResultConverter(dialogButton -> {
					    if (dialogButton == loginButtonType) {
					        return new Pair<>(name1.getText(), name2.getText());
					    }
					    return null;
					});

					Optional<Pair<String, String>> choice = dialog.showAndWait();

					choice.ifPresent(name1name2 -> {
						List<String> updated = mgr.mutualFriends(name1.getText(), name2.getText());
//				    	 List<String> updated = new ArrayList<String>();       
//					       Set<String> updatedSet = mgr.getAllUsers();
//					       for (String item : updatedSet) {
//					         updated.add(item);
//					       }
					       obl.clear();
					       obl.addAll(updated);
						result.setText("Friendship between " + name1.getText()+ " and " +name2.getText()+" is added.");
					});
//
						//result.setText("Friendship between " + name1.getText()+ " and " +name2.getText()+" is added.");
					}

				

				catch (Exception nfe) {

					nfe.printStackTrace();

					result.setText("invalid input");

				}

			}

		});
		
		ShortestPath.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e)

			{

				try {

					 //Create the custom dialog.
			        Dialog<Pair<String, String>> dialog = new Dialog<>();
			        dialog.setTitle("Shortest friendship path");
			        dialog.setHeaderText("Please Enter Names");
			        
					ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
					dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

					// Create the name1 and name2 labels and fields.
					GridPane grid = new GridPane();
					grid.setHgap(10);
					grid.setVgap(10);
					grid.setPadding(new Insets(20, 150, 10, 10));

					TextField name1 = new TextField();
					name1.setPromptText("Name1");
					TextField name2 = new TextField();
					name2.setPromptText("Name2");

					grid.add(new Label("Name1:"), 0, 0);
					grid.add(name1, 1, 0);
					grid.add(new Label("Name2:"), 0, 1);
					grid.add(name2, 1, 1);

					// Enable/Disable login button depending on whether a name1 was entered.
					Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
					loginButton.setDisable(true);

					// Do some validation (using the Java 8 lambda syntax).
					name1.textProperty().addListener((observable, oldValue, newValue) -> {
					    loginButton.setDisable(newValue.trim().isEmpty());
					});

					dialog.getDialogPane().setContent(grid);

					// Request focus on the name1 field by default.
					//Platform.runLater(() -> name1.requestFocus());

					// Convert the result to a name1-name2-pair when the login button is clicked.
					dialog.setResultConverter(dialogButton -> {
					    if (dialogButton == loginButtonType) {
					        return new Pair<>(name1.getText(), name2.getText());
					    }
					    return null;
					});

					Optional<Pair<String, String>> choice = dialog.showAndWait();

					choice.ifPresent(name1name2 -> {
			        	List<String> updated = mgr.shortestPath(name1.getText(), name2.getText());
//				    	 List<String> updated = new ArrayList<String>();       
//					       Set<String> updatedSet = mgr.getAllUsers();
//					       for (String item : updatedSet) {
//					         updated.add(item);
//					       }
					       obl.clear();
					       obl.addAll(updated);
//
						//result.setText("Friendship between " + name1.getText()+ " and " +name2.getText()+" is added.");
					});

				}

				catch (Exception nfe) {

					nfe.printStackTrace();

					result.setText("invalid input");

				}

			}

		});
		
		
		AddFriend.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e)

			{

				try {

					// create a text input dialog

					TextInputDialog td = new TextInputDialog();

					// setHeaderText

					td.setTitle("Add a friend");

					td.setHeaderText("Please Enter Name");

					td.setContentText("Name:");
					td.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
					td.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
						td.getDialogPane().lookupButton(ButtonType.OK).setDisable
						(newValue.trim().isEmpty()||mgr.getPersonalNetwork(mgr.getCentralPerson()).contains(newValue));
			        });

					Optional<String> choice2 = td.showAndWait();

					if (choice2.isPresent()) {

//	        			if (mgr.getperson().contains(td.getEditor().getText())){

//	                		Alert error = new Alert(Alert.AlertType.ERROR, "ERROR: Duplicate person is not allowed");

//	          			Button err = new Button();

//	                		err.setOnAction((ActionEvent ee)->{error.showAndWait();});

//	        		}


						mgr.setFriendship(mgr.getCentralPerson(), td.getEditor().getText());

						if (mgr.getCentralPerson() != null) {
							List<String> updated = FriendList.getFriends(mgr, mgr.getCentralPerson());
							// update friends information
							if (updated == null) {
								obl.clear();
								obl.add("Cannot Find: " + mgr.getCentralPerson());
							} else {
								obl.clear();
								obl.addAll(updated);
							}
							FriendList.setAsFriendOperation(operation, title, AddFriend, RemoveFriend, RemoveSelectedFriend, RemoveAllFriend,
									ViewFriend, Back, Menu, Recall,Undo,Redo);
						}

						result.setText("Friend Of : " + mgr.getCentralPerson());
					}

				}

				catch (Exception nfe) {

					nfe.printStackTrace();

					result.setText("invalid input");

				}

			}

		});
		
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

						if (!mgr.getPersonalNetwork(mgr.getCentralPerson()).contains(td1.getEditor().getText())

								|| (td1.getEditor().getText()) == null)

							td1.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);

						mgr.removeFriendship(mgr.getCentralPerson(), td1.getEditor().getText());

						if (mgr.getCentralPerson() != null) {
							List<String> updated = FriendList.getFriends(mgr, mgr.getCentralPerson());
							// update friends information
							if (updated == null) {
								obl.clear();
								obl.add("Cannot Find: " + mgr.getCentralPerson());
							} else {
								obl.clear();
								obl.addAll(updated);
							}
							FriendList.setAsFriendOperation(operation, title, AddFriend, RemoveFriend, RemoveSelectedFriend, RemoveAllFriend,
									ViewFriend, Back, Menu, Recall,Undo,Redo);
						}

						result.setText("Friend Of : " + mgr.getCentralPerson());
					}

				}

				catch (Exception nfe) {

					nfe.printStackTrace();

					result.setText("invalid input");

				}

			}

		});

		RemoveSelectedFriend.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e)

			{

				try {

					

					

					

//	        			if (mgr.getperson().contains(td.getEditor().getText())){

//	                		Alert error = new Alert(Alert.AlertType.ERROR, "ERROR: Duplicate person is not allowed");

//	          			Button err = new Button();

//	                		err.setOnAction((ActionEvent ee)->{error.showAndWait();});

//	        		}

					

						mgr.removeFriendship(mgr.getCentralPerson(), lv.getSelectionModel().getSelectedItem());

						if (mgr.getCentralPerson() != null) {
							List<String> updated = FriendList.getFriends(mgr, mgr.getCentralPerson());
							// update friends information
							if (updated == null) {
								obl.clear();
								obl.add("Cannot Find: " + mgr.getCentralPerson());
							} else {
								obl.clear();
								obl.addAll(updated);
							}
							FriendList.setAsFriendOperation(operation, title, AddFriend, RemoveFriend, RemoveSelectedFriend, RemoveAllFriend,
									ViewFriend, Back, Menu, Recall,Undo,Redo);
						

						result.setText("Friend Of : " + mgr.getCentralPerson());
					}

				}

				catch (Exception nfe) {

					nfe.printStackTrace();

					result.setText("invalid input");

				}

			}

		});

		RemoveAllFriend.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e)

			{

				try {

					 String delete = mgr.getCentralPerson();
	                    List<String> deleteList = mgr.getPersonalNetwork(delete);
	                    //System.out.print(deleteList);
	                    for (int i = deleteList.size()-1; i >=0;  i--) {
	                    	mgr.removeFriendship(delete, deleteList.get(i));
	                    }
	                    List<String> deleteList1 = mgr.getPersonalNetwork(delete);
	                    //System.out.print(deleteList1);
	                    obl.clear();
						result.setText("Friend List : " + mgr.getPersonalNetwork(mgr.getCentralPerson()));

				}

				catch (Exception nfe) {

					nfe.printStackTrace();

					result.setText("invalid input");

				}

			}

		});


		// action rotate event
		Menu.setOnAction(new EventHandler<ActionEvent>() {
			String S;

			public void handle(ActionEvent e) {
				try {
					List<String> updated = new ArrayList<String>();
					Set<String> updatedSet = mgr.getAllUsers();
					for (String item : updatedSet) {
						updated.add(item);
					}
					undoHistory.clear();
					redoHistory.clear();
					Undo.setDisable(true);
					Redo.setDisable(true);
					// update users information
					obl.clear();
					obl.addAll(updated);
					result.setText("You Are Now At User Page (Menu)");
					FriendList.setAsUserOperation(operation, title, Import, Export, AddFriendship, RemoveAllUsers, ViewFriend, AddUser,
							DeleteUser,Undo,Redo);

					//update button accessibility
					ViewFriend.setDisable(true);
					DeleteUser.setDisable(true);
					Import.setDisable(false);
					Export.setDisable(false);
					AddFriendship.setDisable(false);
					RemoveAllUsers.setDisable(false);
					AddUser.setDisable(false);
				} catch (Exception nfe) {
					nfe.printStackTrace();
//                		 result.setText("invalid input");
				}
			}
		});

		// check action rotate event
		EventHandler<ActionEvent> EventByButton = new EventHandler<ActionEvent>() {
			String S;

			public void handle(ActionEvent e) {

				try {
					List<String> updated = FriendList.getFriends(mgr, input);
					// update friends information
					if (updated == null) {
						obl.clear();
						// set listview
						result.setText("Cannot Find: " + input.getText());
					} else {
						obl.clear();
						obl.addAll(updated);
						mgr.centralize(input.getText());
						result.setText("Friend List : " + mgr.getPersonalNetwork(input.getText()));
					}
					
				} catch (Exception nfe) {
					nfe.printStackTrace();
					result.setText("invalid input");
				}
			}
		};

		//textfield action event
		EventHandler<ActionEvent> EventByEnter = new EventHandler<ActionEvent>() {
			String S;

			public void handle(ActionEvent e) {
				try {
					// for(int i=0; i<mgr.getPersonalNetwork(input.getText()).size(); i++)
					// S += mgr.getPersonalNetwork(input.getText());
					List<String> updated = FriendList.getFriends(mgr, input);
					System.out.println("update" + updated);
					// update friends information
					if (updated == null) {
						obl.clear();
						// set listview
						result.setText("Cannot Find: " + input.getText());
					} else {
						obl.clear();
						obl.addAll(updated);
						mgr.centralize(input.getText());
						// set listview
						result.setText("Friend List : " + mgr.getPersonalNetwork(input.getText()));
					}

					
				} catch (Exception nfe) {
					nfe.printStackTrace();
					result.setText("invalid input");
				}
			}
		};

		// event of clicking a friend in the list
		EventHandler<MouseEvent> EventByMouse = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// for test purpose
				if(lv.getSelectionModel().getSelectedItems() != null) {
					if(lv.getSelectionModel().getSelectedItems().size()>1) {
						result.setText("Multiple users selected.");
						ViewFriend.setDisable(true);
						Import.setDisable(true);
						Export.setDisable(true);
						RemoveAllUsers.setDisable(true);
						AddUser.setDisable(true);
						//button in friend page
						RemoveFriend.setDisable(false);
						RemoveSelectedFriend.setDisable(false);
						RemoveAllFriend.setDisable(true);
						AddFriend.setDisable(true);
					}
					else if(lv.getSelectionModel().getSelectedItems().size()==0) {
						ViewFriend.setDisable(true);
						DeleteUser.setDisable(true);
						Import.setDisable(false);
						Export.setDisable(false);
						RemoveAllUsers.setDisable(true);
						AddUser.setDisable(false);
						//button in friend page
						RemoveFriend.setDisable(false);
						RemoveSelectedFriend.setDisable(false);
						RemoveAllFriend.setDisable(true);
						AddFriend.setDisable(true);
					}
					else {
					selectedUser = lv.getSelectionModel().getSelectedItem();
					result.setText("" + selectedUser + " is selected.");
					System.out.println("clicked on " + lv.getSelectionModel().getSelectedItem());
					
					//update button accessibility
					//button in user page
					ViewFriend.setDisable(false);
					DeleteUser.setDisable(false);
					Import.setDisable(true);
					Export.setDisable(true);
					AddFriendship.setDisable(true);
					RemoveAllUsers.setDisable(true);
					AddUser.setDisable(true);
					//button in friend page
					RemoveFriend.setDisable(true);
					RemoveSelectedFriend.setDisable(false);
					RemoveAllFriend.setDisable(true);
					AddFriend.setDisable(true);
				}}

			}
		};

		// event of clicking a friend in the list
		EventHandler<MouseEvent> EventByMouse2 = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				if(!obl.isEmpty()) {
					lv.getSelectionModel().clearSelection();
					selectedUser = null;
						//update button accessibility
						//button in user page
						ViewFriend.setDisable(true);
						DeleteUser.setDisable(true);
						Import.setDisable(false);
						Export.setDisable(false);
						AddFriendship.setDisable(false);
						RemoveAllUsers.setDisable(false);
						AddUser.setDisable(false);
//						//button in friend page
//						RemoveFriend.setDisable(false);
//						RemoveAllFriend.setDisable(true);
//						AddFriend.setDisable(true);
//					
				}
				
				

			}
		};
		
		// Set the action of the textfield
		input.setOnAction(EventByEnter);
		// Set the action of the Click button
		rotateButton.setOnAction(EventByButton);
		// Set the action of clicking by mouse
		lv.setOnMouseClicked(EventByMouse);

		// fill vbox with different components
		vbox.getChildren().add(prompt);
		vbox.getChildren().add(input);
		vbox.getChildren().add(rotateButton);
		vbox.getChildren().add(result);

		// Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane root = new BorderPane();
		root.setRight(operation);

		// Set Layout
		root.setTop(vbox);
		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		root.setLeft(lv);
		root.setCenter(centralUserNtwk);
		root.setOnMouseClicked(EventByMouse2);
		
		// set scroll bar for friend list
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
		primaryStage.setMinWidth(700);
		primaryStage.setMinHeight(700);
	        primaryStage.show();


	        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            @Override
	            public void handle(WindowEvent event) {
	                Alert closeConfirmation = new Alert(
	                        Alert.AlertType.CONFIRMATION,
	                        "Are you sure you want to exit?"
	                );
	                Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
	                        ButtonType.OK
	                );
	                exitButton.setText("Exit Without Saving");
	                closeConfirmation.setHeaderText("Confirm Exit");
	                closeConfirmation.initModality(Modality.APPLICATION_MODAL);
	                
	                Button exitSaveButton = (Button) closeConfirmation.getDialogPane().lookupButton(
	                        ButtonType.CANCEL
	                );
	                exitSaveButton.setText("Save and Exit");
	                exitSaveButton.setOnAction(new EventHandler<ActionEvent>() {
	        			String S;

	        			public void handle(ActionEvent e) {
	        				
	        		        final String sampleText = "Log Test \n"; //TODO change as actual log
	        		        
	        	            FileChooser fileChooser = new FileChooser();
	        	            
	        	            //Set extension filter for text files
	        	            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
	        	            fileChooser.getExtensionFilters().add(extFilter);
	        	 
	        	            //Show save file dialog
	        	            File file = fileChooser.showSaveDialog(primaryStage);
	        	 
	        	            if (file != null) {
	        	        		try {	
	        	        	    	mgr.saveLog(file);
	        	        	    } catch (IOException ex) {
	        	        	    	System.out.println("write log IOException");
	        	        	    }
	        	            }
	        			}
	        		}
	                
	        );


	                Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
	                if (ButtonType.OK.equals(closeResponse.get()) || ButtonType.CANCEL.equals(closeResponse.get())) {
	                }
	                else {
	                    event.consume();
	                }

	                 
	            }
	        });

		

	}
	
//private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
//	System.out.println("CALLED");
//    Alert closeConfirmation = new Alert(
//            Alert.AlertType.CONFIRMATION,
//            "Are you sure you want to exit?"
//    );
//    Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
//            ButtonType.OK
//    );
//    exitButton.setText("Exit");
//    closeConfirmation.setHeaderText("Confirm Exit");
//    closeConfirmation.initModality(Modality.APPLICATION_MODAL);
//    closeConfirmation.initOwner(mainStage);
//
//    // normally, you would just use the default alert positioning,
//    // but for this simple sample the main stage is small,
//    // so explicitly position the alert so that the main window can still be seen.
//    closeConfirmation.setX(mainStage.getX());
//    closeConfirmation.setY(mainStage.getY() + mainStage.getHeight());
//
//    Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
//    if (!ButtonType.OK.equals(closeResponse.get())) {
//        event.consume();
//    }
//};


	
	/**
	 * Create file "log.txt" in the root folder
	 * Contains the log of all the operations occurred during
	 * the running of the social network app
	 * @throws IOException 
	 */
	@Override
	public void stop() throws IOException {
   	 	File file = new File("log.txt");
   	 	file.createNewFile();
		try {	
	    	mgr.saveLog(file);
	    } catch (IOException ex) {
	    	System.out.println("write log IOException");
	    }
	}
	
	
	//text circle - represents user nodes
	public class UserNode extends StackPane{
	    private final Circle circle;
	    private final Text text;

	    UserNode(String text, double r)
	    {            
	        this.text = new Text(text);
	        circle = new Circle (r);
	        if(r == 70.0) {
	        	circle.setFill(Color.ORANGE);
	        }else {
	        	circle.setFill(Color.AQUA);
	        }
	        
	        getChildren().addAll(circle, this.text);
	    }
	}
	
	//modify the Gridpane passed in based on a social network manager
	//The radius of the friend network (the distance between the central user to each friend) is predefined as 4
	//Plots the friend network for central user defined by the SocialNetworkManager mgr
	//Circle node radius is predefined as 30
	public GridPane plotCentralUserNtwk(GridPane centralUserNtwk,SocialNetworkManager mgr) {
		//the radius of the friends circle
		//NOT THE NODE RADIUS
		int friendCircleR = 4;
		int nodeSize = 30;
		int cols = 11;
		int rows = 11;
		//stores a list of friend nodes of the central user
		List<UserNode> friendNodes = new ArrayList<UserNode>();
		//stores the central user specified by the manager
		String centralUser = mgr.getCentralPerson();
		//get the central user's friends
		List<String> friends = mgr.getPersonalNetwork(centralUser);
		
		//stores the coordinates of each friend on the GridPane
		List<Integer> X = new ArrayList<Integer>();
		List<Integer> Y = new ArrayList<Integer>();
		

		//friend list size
		int n=0;
		//If the central user has no friends
		if(friends==null) {
			//If there is a central users
			if(centralUser!=null && !centralUser.equals(" ")) //TODO
				centralUserNtwk.add(new UserNode(centralUser,50), cols/2, rows/2);
			return centralUserNtwk;
		}
		
		//Otherwise there are friends
		n=friends.size();
		//the X and Y coordiantes of the central user
		//half of the number of the columns
		//which is the same with half of the number of rows
		//since there are same number of rows and columns
		int centerCoord = cols/2;

		
		//Calculate the coordiantes of each friends based on the rules:
		//each friend will lies on a circle whose radius is friendCircleR
		//and all friends will be spaced evenly
		for(int i=0;i<friends.size();i++) {
			int x = (int) (centerCoord+friendCircleR*Math.cos(2*Math.PI*i/n));
			int y = (int) (centerCoord+friendCircleR*Math.sin(2*Math.PI*i/n));
			X.add(x);
			Y.add(y);
		}
		
		
		//for each friend
		for(int i=0;i<friends.size();i++) {
			//create a texted node representing this friend
			friendNodes.add(new UserNode(friends.get(i),30));
			//stores a reference to the newly created node
			UserNode thisUser = friendNodes.get(i); //TODO
			//Give each friend node an eventhandler
			//so when the mouse clicks on one friend
			//that friend becomes the new central user
			thisUser.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
				
				thisUser.circle.setFill(Color.AZURE);
				String newCentralUser = thisUser.text.getText();
				mgr.centralize(newCentralUser);
				System.out.println("The new central user is "+mgr.getCentralPerson());
				centralUserNtwk.getChildren().clear();
				try {
					centralUserNtwk.getChildren().add(plotCentralUserNtwk(centralUserNtwk,mgr));
				}catch (Exception ex){
					//intentionally blank
				}
			});
			//add that friend to the appropriate position of the network
			centralUserNtwk.add(thisUser, X.get(i),Y.get(i));
		}
		//add the central user to the center of the gridpane
		centralUserNtwk.add(new UserNode(centralUser, 70), cols/2, rows/2);
		//We DON'T need an event handler for the central user
		return centralUserNtwk;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
