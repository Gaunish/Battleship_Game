package edu.duke.gg147.battleship;

/** this is a a Placeholder class that just occupies one square
    @param myLocation : Coordinate for location 
**/
public class BasicShip implements Ship<Character>{
  private final Coordinate myLocation;

  //Constructor to init class
  BasicShip(Coordinate loc){
    this.myLocation = loc;
  }
  
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    // TODO Auto-generated method stub
    return where.equals(myLocation);
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
  public Character getDisplayInfoAt(Coordinate where) {
    // TODO Auto-generated method stub
    return 's';
  }
  
}
