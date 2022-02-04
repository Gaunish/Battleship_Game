package edu.duke.gg147.battleship;

import java.io.IOException;

public interface Player {
  public void doPlacementPhase() throws IOException;
  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName);
}
