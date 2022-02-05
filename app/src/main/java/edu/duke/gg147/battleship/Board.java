package edu.duke.gg147.battleship;

/**
 * This interface represents Board in our Battleship game. It is
 * generic in typename T, which is the type of information the view needs to
 * display the Board.
 */
public interface Board<T> {
  /**Method to get width of the board
   * @param no params
   * @return int, width of board
   **/
  public int getWidth();
  /**Method t oget height of the board
   * @param no params
   * @return int, height of the board
   **/
  public int getHeight();
  /**Method tries to add ship to board
   * @param Ship<T>. ship to be added
   * @return String, null if succesful,else, error message
   **/
  public String tryAddShip(Ship<T> toAdd);
  /** Method to check what is present at coord for own player
   * @param Coordinate where on the board
   * @return T, shipInfo if ship is present, else null
   **/
  public T whatIsAtForSelf(Coordinate where);
  /** Method to fire at specific coordinate, store misses
   * @param coordinate c, location of firing
   * @return Ship object Ship<T>, if ship is present, else null
   **/
  public Ship<T> fireAt(Coordinate c);
  /** Method to check what is present at coord for enemy player
   * @param Coordinate where on the board
   * @return T, shipInfo if ship is present, return missInfo if miss, else null
   **/
  public T whatIsAtForEnemy(Coordinate where);
  /** Method to check whether the player has lost
   * @param No params
   * @return True if player has lost, else false
   **/
  public boolean hasLost();
  /** Method to select ship present at coordinate
   * @param where, Coordinate to check
   * @return ship occuping coord, if no ship - returns null
   **/
  public Ship<T> selectShip(Coordinate where);

  /** Method to move ship
   * @param, ship - old ship, newShip - ship at new position, placement inputted
   * @return null if successful, else error message
   **/
  public String tryMoveShip(Ship<T> ship, Ship<T> newShip, Placement p);

  /** Method to Sonar scan
   * @param where - location to do scan around
   * @return output string
   **/
  public String sonarScan(Coordinate where);
}
