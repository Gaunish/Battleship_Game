package edu.duke.gg147.battleship;

import java.util.ArrayList;

public class BattleShipBoard<T> implements Board<T>{
  private final int width;
  private final int height;
  private final PlacementRuleChecker<T> placementChecker;
  final ArrayList<Ship<T>> myShips;
  /**
   * Constructs a BattleShipBoard with the specified width and height
   * @param w : width
   * @param h : height   
   * @throws IllegalArgumentException if the width or height are less than or equal to zero.
   */
  BattleShipBoard(int w, int h, PlacementRuleChecker<T> p){
    if(w <= 0){
      throw new IllegalArgumentException("BattleShipBoard's width must be > 0 but input : " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be > 0 but input : " + h);
    }
    
    this.width = w;
    this.height = h;
    PlacementRuleChecker<T> r = new NoCollisionRuleChecker<T>(p);
    this.placementChecker = r;
    myShips = new ArrayList<>();
  }

  public BattleShipBoard(int w, int h) {
    this(w, h, new InBoundsRuleChecker<T>(null));
  }

  //getter method for width
  public int getWidth(){
    return width;
  }

  //getter method for height
  public int getHeight(){
    return height;
  }

  /** tries to add ship
     @return True if successful
  **/
  public boolean tryAddShip(Ship<T> toAdd){
    //check if ship placement is valid
    if(placementChecker.checkPlacement(toAdd, this) == false){
      return false;
    }
    myShips.add(toAdd);
    return true;
  }

  //method to check if ship is present at the coordinate
  //if True, returns ship display info
  public T whatIsAt(Coordinate where) {
    for (Ship<T> s: myShips) {
      if (s.occupiesCoordinates(where)){
        return s.getDisplayInfoAt(where);
      }
    }
    return null;
  }
  
}
