package edu.duke.gg147.battleship;

public abstract class PlacementRuleChecker<T> {
  private final PlacementRuleChecker<T> next;

  //constructor to init class
  public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
  }

  //Subclasses implement their own rule
  protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);

  //Public method to chain all of the rules together
  public String checkPlacement (Ship<T> theShip, Board<T> theBoard){
    //if we fail our own rule: stop the placement is not legal
    //returns resulring string
    String output = checkMyRule(theShip, theBoard);
    if(output != null){
      return output;
    }

     //other wise, ask the rest of the chain.
    if (next != null) {
      return next.checkPlacement(theShip, theBoard); 
    }
    //if there are no more rules, then the placement is legal
    return null;
  }
}
