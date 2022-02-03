package edu.duke.gg147.battleship;

//Class checks ship placement does not collide with any other ship
public class NoCollisionRuleChecker <T> extends PlacementRuleChecker<T> {
  //constructor to init class
  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }
  
  //Implement own rule in dynamic dispatched function
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) throws IllegalArgumentException{
    //Get coordinates of ship
    Iterable<Coordinate> coords = theShip.getCoordinates();

    for(Coordinate c: coords){
      //check if any ship occupies coordinate c 
      if(theBoard.whatIsAt(c) != null){
        return "That placement is invalid: the ship overlaps another ship.";
      }
    }

    return null;
  }
  
}
