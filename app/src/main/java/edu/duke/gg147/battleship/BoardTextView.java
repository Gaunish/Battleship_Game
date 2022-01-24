package edu.duke.gg147.battleship;

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
  private final Board toDisplay;

  /** displays the given board 
   *  @param toDisplay : board
   * @throws IllegalArgumentException if the board is larger than 10x26.
  **/
  public BoardTextView(Board toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException("Board must be no larger than 10x26, but input :  " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }

  /* function to display board in text mode */
  public String displayMyOwnBoard() {
    StringBuilder ans = new StringBuilder("");
    ans.append(makeHeader());
    ans.append(makeRow());
    ans.append(makeHeader());
    return ans.toString();
  }
  
  /* function returns the rows between headers eg: A  | | | |   A\n
     @return the String format of row
   */
  public String makeRow(){
    StringBuilder ans = new StringBuilder("");
    String sep = " |";
    char letter = 'A';
    //print row by row
    for(int i = 0; i < toDisplay.getHeight(); i++){
      ans.append(letter);
      ans.append(" ");

      //print seperators |
      for(int j = 1; j < toDisplay.getWidth(); j++){
        ans.append(sep);
      }
      
      ans.append("  ");
      ans.append(letter);
      ans.append("\n");
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
    for(int i = 0; i < toDisplay.getWidth(); i++){
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }
}
