package activity;

import java.io.IOException;


import javax.swing.JFrame;

import gui.Gui;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
	    Operations op = new Operations();
		op.getDataFromFile();
		Gui gui = new Gui(op);
		gui.setVisible(true);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
