package edu.duke.gg147.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

public class TextPlayer {
  //Fields of the player
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  final String name;

  //constructor to init the class
  TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out, AbstractShipFactory<Character> factory){
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = inputSource;
    this.out = out;
    this.shipFactory = factory;
    this.name = name;
  }
  
  //method to take user input for placement, prints prompt before
   public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    return new Placement(s);
  }

  //method to do only placement inputted by user
  public void doOnePlacement() throws IOException {
    Placement p = readPlacement("Player " + name + " where do you want to place a Destroyer?");
    //RectangleShip<Character> ship = new RectangleShip<Character>(p.getWhere(), 's', '*');
    //Make the required ship
    Ship<Character> ship  = shipFactory.makeDestroyer(p);

    //add ship to board, view it on out
    theBoard.tryAddShip(ship);
    BoardTextView view = new BoardTextView(theBoard);
    out.println(view.displayMyOwnBoard());
  }

  //Do the placements for user
  public void doPlacementPhase() throws IOException{
    //Display empty board
    BoardTextView view = new BoardTextView(theBoard);
    out.println(view.displayMyOwnBoard());

    //Display initial message
    String init_message = "Player " + name + ": you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical).  For example M4H would place a ship horizontally starting at M4 and going to the right. You have\n2 \"Submarines\" ships that are 1x2\n3 \"Destroyers\" that are 1x3\n3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6 \n";

    out.println(init_message);

    doOnePlacement();
  }

}
