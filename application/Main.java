/**
 * 
 */
package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.sun.javafx.logging.Logger;
import com.sun.javafx.logging.PlatformLogger.Level;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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

	private static final int WINDOW_WIDTH = 550;
	private static final int WINDOW_HEIGHT = 300;
	private static final String APP_TITLE = "SocialNetworkManager";
	// item that user select
	private static String selectedUser;

	public static SocialNetworkManager getSocialNetworkManager() {
		return mgr;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();

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
		Import.setPrefSize(150, 30);
		Export.setPrefSize(150, 30);
		AddFriendship.setPrefSize(150, 30);
		RemoveAllUsers.setPrefSize(150, 30);
		ViewFriend.setPrefSize(150, 30);
		AddUser.setPrefSize(150, 30);
		DeleteUser.setPrefSize(150, 30);

		RemoveAllUsers.setDisable(true);
		ViewFriend.setDisable(true);
		AddUser.setDisable(false);
		DeleteUser.setDisable(true);
		
		// Buttons for friend operations
		Button AddFriend = new Button("Add Friend");
		Button RemoveAllFriend = new Button("Remove All Friends");
		Button RemoveFriend = new Button("Remove Friend");
		Button ViewFriendship = new Button("View Friendship(Friend Page)");
		Button Back = new Button("Back");
		Button Menu = new Button("Menu");
		Button Recall = new Button("Recall");
		AddFriend.setPrefSize(150, 30);
		RemoveFriend.setPrefSize(150, 30);
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
					List<String> selectedUser = lv.getSelectionModel().getSelectedItems();
					String a = "";
					for(int i = selectedUser.size()-1;i>=0;i--) {
					String userToDelete = selectedUser.get(i);
					a+=userToDelete+", ";
					//System.out.println(userToDelete);
					mgr.removePerson(userToDelete);
					}
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
						DeleteUser.setDisable(true);
						RemoveAllUsers.setDisable(true);
					    ViewFriend.setDisable(true);
					}



			}}



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

					System.out.println(updated);
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
				} catch (FileNotFoundException exception) {
					// e.printStackTrace();

				} catch (IOException exception) {
					// e.printStackTrace();

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
	                saveTextToFile(sampleText, file);
	            }
			}
		});
		


		RemoveAllUsers.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {
				try {
                    Set<String> a = mgr.getAllUsers();
                    String temp [] = new String[a.size()];
                    int i=0;
                    for(String element:a) {
                    	temp[i]=element;
                    	i++;
                    }
                    
                    for(int j=0; j<temp.length;j++) {
                    	
                    	mgr.removePerson(temp[j]);
                    }
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
					AddUser.setDisable(true);
					DeleteUser.setDisable(true);
					RemoveAllUsers.setDisable(true);
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
						FriendList.setAsFriendOperation(operation, title, AddFriend, RemoveFriend, RemoveAllFriend,
								ViewFriend, Back, Menu, Recall);
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
						RemoveFriend.setDisable(true);
						RemoveAllFriend.setDisable(false);
						AddFriend.setDisable(false);
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
							mgr.addPerson(td1.getEditor().getText());
						}


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


		// create a event handler

		AddFriendship.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e)

			{

				try {

					// create a  dialog

					Dialog<Pair<String, String>> dialog = new Dialog<>();

					// setHeaderText

					dialog.setTitle("Add new friendship(two users)");

					dialog.setHeaderText("Please Enter Names");
					 // Set the button types.
				    ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
				    dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
				    
				    GridPane gridPane = new GridPane();
				    gridPane.setHgap(10);
				    gridPane.setVgap(10);
				    //gridPane.setPadding(new Insets(20, 150, 10, 10));

				    TextField name1 = new TextField();
				    name1.setPromptText("Name 1");
				    TextField name2 = new TextField();
				    name2.setPromptText("Name 2");
				    
				    gridPane.add(new Label("Name 1"), 0, 0);
				    gridPane.add(name1, 0, 1);
				    
				    gridPane.add(new Label("Name 2"), 1, 0);
				    gridPane.add(name2, 1, 1);

				    dialog.getDialogPane().setContent(gridPane);
				    Optional<Pair<String, String>> choice = dialog.showAndWait();
				    
				    if (choice.isPresent()) {
				    	mgr.setFriendship(name1.getText(), name2.getText());
				    	 List<String> updated = new ArrayList<String>();       
					       Set<String> updatedSet = mgr.getAllUsers();
					       for (String item : updatedSet) {
					         updated.add(item);
					       }
					       obl.clear();
					       obl.addAll(updated);
//
						result.setText("Friendship between " + name1.getText()+ " and " +name2.getText()+" is added.");
					}

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

					Optional<String> choice2 = td.showAndWait();

					if (choice2.isPresent()) {

//	        			if (mgr.getperson().contains(td.getEditor().getText())){

//	                		Alert error = new Alert(Alert.AlertType.ERROR, "ERROR: Duplicate person is not allowed");

//	          			Button err = new Button();

//	                		err.setOnAction((ActionEvent ee)->{error.showAndWait();});

//	        		}

						if (!mgr.getPersonalNetwork(mgr.getCentralPerson()).contains(td.getEditor().getText())

								|| (td.getEditor().getText()) == null)

							td.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);

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
							FriendList.setAsFriendOperation(operation, title, AddFriend, RemoveFriend, RemoveAllFriend,
									ViewFriend, Back, Menu, Recall);
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
							FriendList.setAsFriendOperation(operation, title, AddFriend, RemoveFriend, RemoveAllFriend,
									ViewFriend, Back, Menu, Recall);
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

					// update users information
					obl.clear();
					obl.addAll(updated);
					result.setText("You Are Now At User Page (Menu)");
					FriendList.setAsUserOperation(operation, title, Import, Export, RemoveAllUsers, ViewFriend, AddUser,
							DeleteUser);

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
					System.out.println(updated);
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
					RemoveFriend.setDisable(false);
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
//		root.setBottom(ViewFriend);

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

	}
	

    private void saveTextToFile(String name, File log){
        try {
            PrintWriter writer;
            writer = new PrintWriter(log);
            writer.println(name);
            writer.close();
        } catch (IOException ex) {
            
        }
    }
	
	/**
	 * Create file "log.txt" in the root folder
	 * Contains the log of all the operations occurred during
	 * the running of the social network app
	 * @throws IOException 
	 */
	@Override
	public void stop() throws IOException {
		String exampleExport = "a mark sid\r\n" + 
				"a sid sri\r\n" + 
				"a sri mark\r\n" + 
				"a sapan deb\r\n" + 
				"a deb sid\r\n" + 
				"a deb mark\r\n" + 
				"r deb\r\n" + 
				"s mark\r\n" + 
				"\r\n" + 
				"";
		FileWriter log = new FileWriter("log.txt");
		PrintWriter logWriter = new PrintWriter(log);
		logWriter.println(exampleExport);
		logWriter.close();
	}
    

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
