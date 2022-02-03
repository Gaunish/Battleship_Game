package edu.duke.gg147.battleship;


/** Placement class is used to represent 
    (coordinate, orientation) of battleship
    For eg: A0V - Cood : A0, placment : V
    The class is case-insensitive

    @param where - coordinate
    @param orientation - orientation
**/
public class Placement {
  private final Coordinate where;
  private final char orientation;

  //Constructor to init class
  //given cood, orient
  Placement(Coordinate cood, char orient){
    this.orientation = Character.toUpperCase(orient);
    this.where = cood;
  }

  /**Constructor to init class using string
  eg : A0V - (where : A0, orientation : V)
   @throws IllegalArgumentException if desc is not valid 
  **/
  Placement(String desc){
    if(desc.length() != 3){
      throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.");
    }

    this.where = new Coordinate(desc.substring(0, 2));
      
    //get the alphabet and check its validity
    char caps = Character.toUpperCase(desc.charAt(2));
    if(caps < 'A' || caps > 'Z'){
      throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.");
    }
    this.orientation = caps;
  }
  
  //getter method for orientation
  public char getOrient(){
    return this.orientation;
  }

  //getter method for where
  public Coordinate getWhere(){
    return this.where;
  }
  
  //method to check whether this is equal to o
   @Override
   public boolean equals(Object o) {
     //check if o has exact same class as this  
     if (o.getClass().equals(getClass())) {
       Placement p = (Placement) o;
       return orientation == p.orientation && where.equals(p.where);
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
    return "("+ where +", " + orientation +")";
  }

}
