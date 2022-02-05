package edu.duke.gg147.battleship;

import java.io.IOException;

/**
Interface to abstract out TextPlayer, ComputerPlayer
 **/
public interface Player {
  /**Method to compelete placement phase of placing ships
   * @param none
   * @returns none
   **/
  public void doPlacementPhase() throws IOException;
  /** method to let player play one turn
   *   @param enemy board, its view and his/her name
   *  @returns void
   **/
  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName);
}
