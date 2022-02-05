package edu.duke.gg147.battleship;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class ComputerPlayer implements Player{
  //Fields of the player
  final String name;
  final Board<Character> theBoard;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
 
  //Fields to map lambda function to ship name
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
  final ArrayList<Placement> placements;
  int row, col;


  //constructor to init the class
  ComputerPlayer(String name, Board<Character> theBoard, PrintStream out, AbstractShipFactory<Character> factory){
    this.theBoard = theBoard;
    this.out = out;
    this.shipFactory = factory;
    this.name = name;

    //init and setup ship list, map
    shipsToPlace = new ArrayList<String>();
    shipCreationFns = new HashMap<>();
    placements = new ArrayList<Placement>();
    setupShipCreationList();
    setupShipCreationMap();
    setupPlacements();

    row = 0;
    col = 0;
  }

  //method to setup mapping of lambda fxn -> name
  protected void setupShipCreationMap(){
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
  }

  //Helper method to add placement to list
  protected void place(String s){
    placements.add(new Placement(s));
  }

  //method to init placements
  protected void setupPlacements(){
    //place submarine
    place("A0V");
    place("A1V");

    //place destroyer
    place("A2V");
    place("A3V");
    place("A4V");

    //place battleship
    place("S0U");
    place("Q0U");
    place("O0U");

    //place carrier
    place("A5U");
    place("A7U");
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

  //Method to play one turn for computer
  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName){
    Coordinate c = new Coordinate(row, col);

    //character for conversion
    char a = 'A';
    
    //Place hit
    Ship<Character> s = enemyBoard.fireAt(c);
    //print the result
      if(s == null){
        out.println("Player "+ name +" missed!");
      }
      else{
        out.println("Player " + name + " hit your " + s.getName() + " at " + Character.toString(row+a) + col + "!");
      }
     
    //Special case
    if(col == 9){
      row += 1;
      col = 0;
      return;
    }
    
    col += 1;
  }


  //method to do only placement inputted by user
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn, int i){
    Placement p = placements.get(i);

     //Make the required ship
    Ship<Character> ship  = createFn.apply(p);

    //add ship to board, view it on out
    theBoard.tryAddShip(ship);
  
  }
  
  //Do the placements for Computer
  public void doPlacementPhase() throws IOException{
    int i = 0;
    for(String ship : shipsToPlace){
      doOnePlacement(ship, shipCreationFns.get(ship), i);
      i++;
    }
  }



}
