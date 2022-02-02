package edu.duke.gg147.battleship;

import java.util.HashMap;

/** this is a a Placeholder class that just occupies one square
    @param myLocation : Coordinate for location 
**/
public class BasicShip<T> implements Ship<T>{
  //Tracks location of ship, Null -> not present, true -> hit
  protected HashMap<Coordinate, Boolean> myPieces;
  //display info of ship
  protected ShipDisplayInfo<T> myDisplayInfo;
    
  //Constructor to init all coords in where to false (ship locations) & displayinfo
  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo){
    //init myPieces
    myPieces = new HashMap<Coordinate, Boolean>();
    
    //Iterate over where
    for(Coordinate c: where){
      myPieces.put(c, false);
    }

    //init display
    this.myDisplayInfo = myDisplayInfo;
  }

  //checks whether ship occupies coord where
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    return myPieces.containsKey(where);
  }

  @Override
  public boolean isSunk() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public T  getDisplayInfoAt(Coordinate where) {
    // TODO Auto-generated method stub
    //TODO this is not right.  We need to
    //look up the hit status of this coordinate
    return myDisplayInfo.getInfo(where, false);
  
  }
  
}
