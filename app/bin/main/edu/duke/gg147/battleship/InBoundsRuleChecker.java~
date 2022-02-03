package edu.duke.gg147.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  //constructor to init class
  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }
  
  //Implement own rule in dynamic dispatched function
  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    //Get coordinates of ship
    Iterable<Coordinate> coords = theShip.getCoordinates();

    for(Coordinate c: coords){
      //check bounds
      if(c.getRow() < 0 || c.getRow() >= theBoard.getWidth() || c.getColumn() < 0 || c.getColumn() >= theBoard.getHeight()){
        return false;
      }
    }

    return true;
  }

}
