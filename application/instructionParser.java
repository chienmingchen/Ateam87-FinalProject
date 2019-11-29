package ateamProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class instructionParser {
	SocialNetWork mySN;
	public void parse(String filepath) throws FileNotFoundException,Exception {
		Scanner parserScanner = new Scanner(new File(filepath));
		while(parserScanner.hasNextLine()) {
			String currentLine = parserScanner.nextLine();
			String[] splittedCmd = currentLine.split("");
			String function = splittedCmd[0];
			String user1 = splittedCmd[1];
			String user2 = splittedCmd.length>2?splittedCmd[2]:null;
			if(function.compareTo("a")==0) {
				if(user2!=null)
					mySN.addFriends(user1,user2);
				else
					mySN.addUser(user1);
			}else if(function.compareTo("r")==0) {
				if(user2!=null)
					mySN.removeFriends(user1,user2);
				else
					mySN.removeUser(user1);
			}else if(function.compareTo("s")==0) {
				mySN.centralize("user1");
			}else {
				throw new Exception("Uncompilable commands.");
			}
		}
	}
}
