package edu.duke.gg147.battleship;

//Class checks whether coordinate of placement is in bounds of board
public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  //constructor to init class
  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }
  
  //Implement own rule in dynamic dispatched function
  //return error message if any error, else null
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard){
    //Get coordinates of ship    
    Iterable<Coordinate> coords = theShip.getCoordinates();

    for(Coordinate c: coords){
      //check bounds
      
      //Top side
      if(c.getRow() < 0){
        return "That placement is invalid: the ship goes off the top of the board.";
      }

      //Bottom side
      if(c.getRow() >= theBoard.getHeight()){
        return "That placement is invalid: the ship goes off the bottom of the board.";
      }

      //Left side
      if(c.getColumn() < 0){
        return "That placement is invalid: the ship goes off the left of the board.";
      }

      //Right side
      if(c.getColumn() >= theBoard.getWidth()){
        return "That placement is invalid: the ship goes off the right of the board.";
      }     
   
    }

    return null;
  }

}
