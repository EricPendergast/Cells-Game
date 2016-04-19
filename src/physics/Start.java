package physics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import input.FileRead;
import cells.CellState;
import cells.generators.Tutorial;
import cells.gui.StartMenu;
import states.*;
public class Start {
	public static void main(String[] args) throws IOException{
		Game game = new Game(1200,750,Integer.parseInt(args[0]));
		CellState cells = new CellState(game);
		game.addState(cells);
		game.addState(new StartMenu(cells.getRunner(),game));
		game.addState(new CellState(game, new Tutorial()));
		game.setStateIndex(1);
		game.start();
	}
	
	public static BufferedReader getReader(String fileLoc){
		BufferedReader br = null;
		try {
			URL url = Start.class.getResource(fileLoc);
			System.out.println(url);
			br = new BufferedReader(new FileReader(url.getPath()));
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		return br;
	} 
	public static BufferedWriter getWriter(String fileLoc){
		BufferedWriter bw = null;
		try {
			URL url = FileRead.class.getResource(fileLoc);
			bw = new BufferedWriter(new FileWriter(url.getPath()));
		} catch (IOException e) {e.printStackTrace();}
		
		return bw;
	}
}

