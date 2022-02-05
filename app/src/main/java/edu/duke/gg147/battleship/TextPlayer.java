package edu.duke.gg147.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class TextPlayer implements Player{
  //Fields of the player
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  final String name;
  private int move_rem, sonar_rem;

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
    move_rem = 3;
    sonar_rem = 3;
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
    out.println("Player " + name + "'s turn:\n");

    //display boards
    out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean", enemyName));

    //Ask user about options
    takeOption(enemyBoard);

    /*
    //Do apt actions
    if(opt == 'F'){
      doOneHit(enemyBoard);
    }
    else if(opt == 'S'){
      doSonarScan(enemyBoard);
      sonar_rem -= 1;
    }
    else{
      doShipMove_cood();
      move_rem -= 1;
      }*/
    
  }

  //Method to take option
  public void takeOption(Board<Character> enemyBoard){
    String prompt = "Possible actions for Player " + name + ":\n\n";
    prompt += "F Fire at a square\nM Move a ship to another square (" + String.valueOf(move_rem) + " remaining)\nS Sonar scan (" + String.valueOf(sonar_rem) +" remaining)\n\n";
    prompt += "Player " + name + ", what would you like to do?\n\n";

    //take input while not correct 
    while(true){
      char in = readOption(prompt).toUpperCase().charAt(0);
      if(in == 'F' || in == 'M' || in == 'S'){

        //Out of option for M,S
        if(in == 'S' && sonar_rem <= 0){
          out.println("Sonar is out of quantity, select other options\n\n");
          continue;
        }
        if(in == 'M' && move_rem <= 0){
          out.println("Move is out of quantity, select other options\n\n");
          continue;
        }

        //Do apt actions
        if(in == 'F'){
          doOneHit(enemyBoard);
        }
        else if(in == 'S'){
          doSonarScan(enemyBoard);
        }
        else{
          doShipMove_cood(enemyBoard);
        }
   
        break;
      }
      out.println("Invalid Option, try again \n\n");
    }
  }

  //method to take user input for action to do
   public String readOption(String prompt){
    out.println(prompt);
    try{
      String s = inputReader.readLine();
      return s;
    }
    catch(IOException e){
      out.println("Unexpected error");
    }
    return null;
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
      takeOption(enemyBoard);
      return;
    }
  }

  //Method to do a sonar scan on enemy board
  public void doSonarScan(Board<Character> enemyBoard){
    //Prompt for Coordinate
    try{
      //get the coords
      Coordinate c = readCoordinate("Player " + this.name + " where do you want to do a Sonar scan?", enemyBoard);

      //Place hit
      String s = enemyBoard.sonarScan(c);

      //print the result
      out.println(s);
      sonar_rem -= 1;
      
      return;
    }
    //Catch any specific exception
    catch(IllegalArgumentException|IOException e){
      out.println("Invalid Coordinates, try again!");
      takeOption(enemyBoard);
      return;
    }
  }

  private void retryShipMove(String message, Board<Character> enemyBoard){
      out.println(message);
      takeOption(enemyBoard);
      return;
  }
  
  //Method to do ship move from one location to another
  private void doShipMove_cood(Board<Character> enemyBoard){
    //Prompt for Coordinate
    try{
      //get the coords
      Coordinate c = readCoordinate("Player " + this.name + " Enter coordinates of ship you want to move?", theBoard);

      //Get the ship via coordinate
      Ship<Character> ship = theBoard.selectShip(c);

      //no ship at coordinate
      if(ship == null){
        retryShipMove("No ship present at the coordinate, try again", enemyBoard);
        return;
      }

      doShipMove_placement(ship, shipCreationFns.get(ship.getName()), enemyBoard);
   
      return;
    }
    //Catch any specific exception
    catch(IllegalArgumentException|IOException e){
      retryShipMove("Invalid Coordinates, try again!", enemyBoard);
      return;
    }
  }

  //Method to do ship move from one location to another
  private void doShipMove_placement(Ship<Character> ship, Function<Placement, Ship<Character>> createFn, Board<Character> enemyBoard){
    //Prompt for Coordinate
    try{
      //get the coords
      Placement p = readPlacement("Player " + this.name + "Enter new Placement of ship where you want it to be placed:");

      //Make the required ship
      Ship<Character> newShip  = createFn.apply(p);

      //try to move the ship
      String result = theBoard.tryMoveShip(ship, newShip, p);
      //Invalid placement
      if(result != null){
        out.println(result);
        takeOption(enemyBoard);
        return;
      }

      move_rem -= 1;
      return;
    }
    //Catch any specific exception
    catch(IllegalArgumentException|IOException e){
      //out.println("Invalid Placement, try again!\n");
      out.println(e);
      takeOption(enemyBoard);
      return;
    }
  }
  
    
  //method to take user input for Coordinate, prints prompt before
  public Coordinate readCoordinate(String prompt, Board<Character> enemyBoard) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    Coordinate c = new Coordinate(s);

    if(c.getRow() < 0 || c.getRow() >= enemyBoard.getHeight() || c.getColumn() < 0 || c.getColumn() >= enemyBoard.getWidth()){
      throw new IllegalArgumentException("Invalid Coordinate\n");
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
