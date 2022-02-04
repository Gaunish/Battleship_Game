package edu.duke.gg147.battleship;

/**
 * This interface represents shipView in our Battleship game. It is
 * generic in typename T, which is the type of information to generate required  * view 
 */
public interface ShipDisplayInfo<T> {
  /** Method to get info of a coordinate
   * @param where is the coordinate on the board, hit tells whether the coordina   * te has been hit
   * @return T, onHit info if hit else myData info 
   **/
  public T getInfo(Coordinate where, boolean hit);
}
