package edu.duke.gg147.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of
 * a Board (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the 
 * enemy's board.
 */
public class BoardTextView {
  // board to be displayed
  private final Board<Character> toDisplay;

  /** displays the given board 
   *  @param toDisplay : board
   * @throws IllegalArgumentException if the board is larger than 10x26.
  **/
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException("Board must be no larger than 10x26, but input :  " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }

  /* function to display own board in text mode */
  public String displayMyOwnBoard() {
    return displayAnyBoard((c)->toDisplay.whatIsAtForSelf(c));
  }

  /* function to display enemy board in text mode */
  public String displayMyEnemyBoard() {
    return displayAnyBoard((c)->toDisplay.whatIsAtForEnemy(c));
  }

  //method to Display own/enemy board
  protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn){
    StringBuilder ans = new StringBuilder("");
    ans.append(makeHeader());
    ans.append(makeRows(getSquareFn));
    ans.append(makeHeader());
    return ans.toString();
  }

  /**Private function to make row in makeRows
   **/
  private void makeRow(StringBuilder ans, char letter, int col, Function<Coordinate, Character> getSquareFn){
      //append first letter, eg: A  |
      ans.append(letter);
      ans.append(" ");

      //append seperators
      //append ship displayInfo, if ship occupies the position
      //eg A  | |s| |  A
      String sep = ""; 
      for(int row = 0; row < toDisplay.getWidth(); row++){
        ans.append(sep);

        //get what to display at cood via whatIsAt function
        //if null, append " "
        Character c = getSquareFn.apply(new Coordinate(col, row));
        if(c == null){
          ans.append(" ");
        }
        else{
          ans.append(c);
        }
        
        sep = "|";
      }
      

      //append last letter, eg: |  A
      ans.append(" ");
      ans.append(letter);
      ans.append("\n");
  }
  
  /** function returns the rows between headers eg: A  | | | |   A\n
     @returns the String format of row
  **/
  public String makeRows(Function<Coordinate, Character> getSquareFn){
    StringBuilder ans = new StringBuilder("");
    char letter = 'A';

    //print row by row
    for(int col = 0; col < toDisplay.getHeight(); col++){
      makeRow(ans, letter, col, getSquareFn);
      letter++;
    }
    return ans.toString();
 
  }
  /*function returns the header eg: 0|1|2|3|4\n
    @return the String format of header 
  */
  public String makeHeader(){
    StringBuilder ans = new StringBuilder("  ");
    String sep = "";
    for(int row = 0; row < toDisplay.getWidth(); row++){
      ans.append(sep);
      ans.append(row);
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }
}
