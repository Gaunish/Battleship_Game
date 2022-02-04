package edu.duke.gg147.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class TextPlayer {
  //Fields of the player
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  final String name;

  //Fields to map lambda function to ship name
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;


  //constructor to init the class
  TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out, AbstractShipFactory<Character> factory){
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = inputSource;
    this.out = out;
    this.shipFactory = factory;
    this.name = name;

    //init and setup ship list, map
    shipsToPlace = new ArrayList<String>();
    shipCreationFns = new HashMap<>();
    setupShipCreationList();
    setupShipCreationMap();
  }

   //method to setup mapping of lambda fxn -> name
  protected void setupShipCreationMap(){
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
  }
     

   //method to create list of shipname
  protected void setupShipCreationList(){
    //2 submarines
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
     
    //3 destroyers
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));

    //3 Battleships
    shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
    
    //2 carriers
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
    
    
  }

  //Method to play one turn for user
  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName){
    out.println("Player " + name + "'s turn:");

    //display boards
    out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean", enemyName));

    doOneHit(enemyBoard);
  }

  //Method to take coordinate from user and do a hit
  public void doOneHit(Board<Character> enemyBoard){
    //Prompt for Coordinate
    try{
      //get the coords
      Coordinate c = readCoordinate("Player " + this.name + " where do you want to hit on board?", enemyBoard);

      //Place hit
      Ship<Character> s = enemyBoard.fireAt(c);

      //print the result
      if(s == null){
        out.println("You missed!");
      }
      else{
        out.println("You hit a " + s.getName() +"!");
      }
      return;
    }
    //Catch any specific exception
    catch(IllegalArgumentException|IOException e){
      out.println("Invalid Coordinates, try again!");
      doOneHit(enemyBoard);
      return;
    }
  }
    

  //method to take user input for Coordinate, prints prompt before
  public Coordinate readCoordinate(String prompt, Board<Character> enemyBoard) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    Coordinate c = new Coordinate(s);

    if(c.getRow() < 0 || c.getRow() >= enemyBoard.getHeight() || c.getColumn() < 0 || c.getColumn() >= enemyBoard.getWidth()){
      throw new IllegalArgumentException("Invalid cood");
    }

    return c;
  }

  
  //method to take user input for placement, prints prompt before
   public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    return new Placement(s);
  }

  //method to retry doOnePlacement
  private void retryOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn, String output){
    out.println(output);
    out.println("Try Again");
    doOnePlacement(shipName, createFn);
    return;
  }

  //method to do only placement inputted by user
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn){
    //check placement
    //catch illegal placement and try again
    try{
    Placement p = readPlacement("Player " + this.name + " where do you want to place a " + shipName + "?");
    
    //RectangleShip<Character> ship = new RectangleShip<Character>(p.getWhere(), 's', '*');
    //Make the required ship
    Ship<Character> ship  = createFn.apply(p);

    //add ship to board, view it on out
    //Check output, if error happened ask user to try again
    String output = theBoard.tryAddShip(ship);
    //invalid ship placement
    if(output != null){
      retryOnePlacement(shipName, createFn, output);
      return;
    }
    BoardTextView view = new BoardTextView(theBoard);
    out.println(view.displayMyOwnBoard());
    }
    catch(IllegalArgumentException|IOException e){
      String error = "That placement is invalid: it does not have the correct format.";
      retryOnePlacement(shipName, createFn, error);
      return;
    }
  }

  //Do the placements for user
  public void doPlacementPhase() throws IOException{
    //Display empty board
    BoardTextView view = new BoardTextView(theBoard);
    out.println(view.displayMyOwnBoard());

    //Display initial message
    String init_message = "Player " + name + ": you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical).  For example M4H would place a ship horizontally starting at M4 and going to the right. You have\n2 \"Submarines\" ships that are 1x2\n3 \"Destroyers\" that are 1x3\n3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6 \n";

    out.println(init_message);

    //iterate over shiplist and do placement for each
    for(String ship : shipsToPlace){
      doOnePlacement(ship, shipCreationFns.get(ship));
    }
  }

}
