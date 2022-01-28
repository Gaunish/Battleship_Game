package edu.duke.gg147.battleship;

/** Coordinate class to represent (x,y) in board
    = (row, col)
    @param row - x
    @param col - y
**/
public class Coordinate {
  private final int row, col;

  //constructor to init the class
  // given row, column
  Coordinate(int row, int col){
    this.row = row;
    this.col = col;
  }

  /**constructor to init the class
   given a string, for eg: A5 - (0, 5)
   @throws IllegalArgumentException if desc is not valid 
   **/
  Coordinate(String desc){
    if(desc.length() != 2){
      throw new IllegalArgumentException("Input length is invalid, found length : " + desc.length());
    }
    
    //get the alphabet and check its validity
    char caps = Character.toUpperCase(desc.charAt(0));
    if(caps < 'A' || caps > 'Z'){
      throw new IllegalArgumentException("alphabet/row value is invalid, found :  " + caps);
    }

    this.row = caps - 'A';
    //Convert char at second position into int
    this.col = Integer.parseInt(String.valueOf(desc.charAt(1)));
    
  }

  //getter method for row
  public int getRow(){
    return row;
  }

  //getter method for col
  public int getColumn(){
    return col;
  }

  //method to check whether this is equal to o
   @Override
   public boolean equals(Object o) {
     //check if o has exactle same class as this
     if (o.getClass().equals(getClass())) {
       Coordinate c = (Coordinate) o;
       return row == c.row && col == c.col;
     }
     return false;
   }

  //method to return hash of the object
  //for use in hashmap
  @Override
  public int hashCode(){
    //strings have good hash
    return toString().hashCode();
  }

  //method to return string rep of obj
  @Override
  public String toString() {
    return "("+row+", " + col +")";
  }
}
