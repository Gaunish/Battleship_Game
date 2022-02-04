
/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.gg147.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

public class App {
  //field to rep 2 players
  final TextPlayer player1, player2;

  //constructor to init the class
  public App(TextPlayer p1, TextPlayer p2) {
    this.player1 = p1;
    this.player2 = p2;
  }

  //creates the two player and call constructor
  //shared buffer, printstream
  public static void main(String[] args) throws IOException{
    //Create boards
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');

    //setup input, factory
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    V1ShipFactory factory = new V1ShipFactory();

    //setup players
    TextPlayer p1 = new TextPlayer("A", b1, in, System.out, factory);
    TextPlayer p2 = new TextPlayer("B", b2, in, System.out, factory);

    //init the class
    App game = new App(p1, p2);

    //Init first phase of game : do placements
    game.doPlacementPhase();

    //Init Second phase of game : attack ships
    game.doAttackingPhase(b1, b2);
    return;
  }

  //Method to play first phase of game -> doing placements
  public void doPlacementPhase() throws IOException{
    player1.doPlacementPhase();
    player2.doPlacementPhase();
  
  }

  //Method to play second phase of game -> player attack each other board till one wins
  public void doAttackingPhase(Board<Character> b1, Board<Character> b2) throws IOException{
    //Loop continues till one of the player wins
    while(b1.hasLost() == false && b2.hasLost() == false){
      //Player A turn
      player1.playOneTurn(b2, new BoardTextView(b2), "Player B's ocean");

      //Player A won
      if(b2.hasLost() == true){
        System.out.println("Player A won!");
        return;
      }

      //Player B turn
      player2.playOneTurn(b1, new BoardTextView(b1), "Player A's ocean");

      //Player B won
      if(b1.hasLost() == true){
        System.out.println("Player B won!");
        return;
      }
    
    }
  }

}
