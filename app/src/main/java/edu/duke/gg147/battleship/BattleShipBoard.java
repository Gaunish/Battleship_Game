package edu.duke.gg147.battleship;

import java.util.ArrayList;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T>{
  private final int width;
  private final int height;
  private final PlacementRuleChecker<T> placementChecker;
  //stores ships on board
  private final ArrayList<Ship<T>> myShips;
  //stores misses of hits on ship
  private final HashSet<Coordinate> enemyMisses;
  //Info to display for a miss
  final T missInfo;
  
  /**
   * Constructs a BattleShipBoard with the specified width and height
   * @param w : width
   * @param h : height   
   * @throws IllegalArgumentException if the width or height are less than or equal to zero.
   */
  BattleShipBoard(int w, int h, PlacementRuleChecker<T> p, T miss){
    if(w <= 0){
      throw new IllegalArgumentException("BattleShipBoard's width must be > 0 but input : " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be > 0 but input : " + h);
    }
    
    this.width = w;
    this.height = h;

    //Init placement checker
    PlacementRuleChecker<T> r = new NoCollisionRuleChecker<T>(p);
    this.placementChecker = r;

    //init ships list, misses set, miss info
    myShips = new ArrayList<>();
    enemyMisses = new HashSet<Coordinate>();
    missInfo = miss;
  }

  public BattleShipBoard(int w, int h, T miss) {
    this(w, h, new InBoundsRuleChecker<T>(null), miss);
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
  public String tryAddShip(Ship<T> toAdd){
    //check if ship placement is valid
    //returns output string if invalid
    String out = placementChecker.checkPlacement(toAdd, this);
    if(out != null){
      return out;
    }
    
    myShips.add(toAdd);
    return null;
  }

  //method to check what is present for self
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  //method to check if ship is present at the coordinate
  //if present, returns apt display info
  protected T whatIsAt(Coordinate where, boolean isSelf){
    for (Ship<T> s: myShips) {
      if (s.occupiesCoordinates(where)){
        return s.getDisplayInfoAt(where, isSelf);
      }
    }

    //for enemy display missInfo for a miss
    if(isSelf == false && enemyMisses.contains(where) == true){
        return missInfo;
    }
   
    return null;
 
  }

  //method to check what is present for enemy
  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }

  //Method to fire at enemy ships
  public Ship<T> fireAt(Coordinate c){
    //traverse ships
    //if any ship occupy c, record hit and return it
    for(Ship<T> s : myShips){
      if (s.occupiesCoordinates(c)){
        s.recordHitAt(c);
        return s;
      }
   }
    enemyMisses.add(c);
    //no ship at c
    return null;
  }
  
}
