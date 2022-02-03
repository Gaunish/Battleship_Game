package edu.duke.gg147.battleship;

import java.util.HashMap;

/** this is a a Placeholder class that just occupies one square
    @param myLocation : Coordinate for location 
**/
public abstract class BasicShip<T> implements Ship<T>{
  //Tracks location of ship, Null -> not present, true -> hit
  protected HashMap<Coordinate, Boolean> myPieces;
  //display info of ship
  protected ShipDisplayInfo<T> myDisplayInfo;
  //Method to display enemy board info
  protected ShipDisplayInfo<T> enemyDisplayInfo;

    
  //Constructor to init all coords in where to false (ship locations) & displayinfo
  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo){
    //init myPieces
    myPieces = new HashMap<Coordinate, Boolean>();
    
    //Iterate over where
    for(Coordinate c: where){
      myPieces.put(c, false);
    }

    //init displays
    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = enemyDisplayInfo;
  }

  //Checks if coord in ship
  //throws IllegalArgumentException if not present
  protected void checkCoordinateInThisShip(Coordinate c){
    if(occupiesCoordinates(c) == false){
      throw new IllegalArgumentException("Coordinate: " + c.toString() + " not found in ship");
    }
  }

  //checks whether ship occupies coord where
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    return myPieces.containsKey(where);
  }

  //checks if ship is sunk
  @Override
  public boolean isSunk() {
    return myPieces.containsValue(false) == false;
  }

  //checks if cood in ship then records hit at cood where
  //throws IllegalArgumentException if not present 
  @Override
  public void recordHitAt(Coordinate where) {
     checkCoordinateInThisShip(where);

     myPieces.replace(where, true);
  }

  //checks if cood in ship then check if hit at cood where
  //throws IllegalArgumentException if not present 
  @Override
  public boolean wasHitAt(Coordinate where) {
     checkCoordinateInThisShip(where);

     return myPieces.get(where);
  }

  //get display info of coord where
  //throws IllegalArgumentException if not present 
  @Override
  public T getDisplayInfoAt(Coordinate where, boolean myShip) {
    boolean isHit = wasHitAt(where);

    //Own ship
    if(myShip == true){
     return myDisplayInfo.getInfo(where, isHit);
    }

    //enemy ship    
    return enemyDisplayInfo.getInfo(where, isHit);
  }

  //get coordinates that ship occupies
  public Iterable<Coordinate> getCoordinates(){
    return myPieces.keySet();
  }

}
