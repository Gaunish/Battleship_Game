package edu.duke.gg147.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T>{
  private final int width;
  private final int height;
  private final PlacementRuleChecker<T> placementChecker;
  //stores ships on board
  private final ArrayList<Ship<T>> myShips;
  //stores misses of hits on ship
  private final HashSet<Coordinate> enemyMisses;
  //Info to display for a miss
  final T missInfo;

  //List for move, covert misses, hits of prev ship
  private final ArrayList<Coordinate> misses;
  private final ArrayList<Coordinate> ships;
  private final HashMap<Coordinate, T> hits;
  
  /**
   * Constructs a BattleShipBoard with the specified width and height
   * @param w : width
   * @param h : height   
   * @throws IllegalArgumentException if the width or height are less than or equal to zero.
   */
  BattleShipBoard(int w, int h, PlacementRuleChecker<T> p, T miss){
    if(w <= 0){
      throw new IllegalArgumentException("BattleShipBoard's width must be > 0 but input : " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be > 0 but input : " + h);
    }
    
    this.width = w;
    this.height = h;

    //Init placement checker
    PlacementRuleChecker<T> r = new NoCollisionRuleChecker<T>(p);
    this.placementChecker = r;

    //init ships list, misses set, miss info
    myShips = new ArrayList<>();
    enemyMisses = new HashSet<Coordinate>();
    missInfo = miss;

    //init lists used for move ship
    misses = new ArrayList<>();
    ships = new ArrayList<>(); 
    hits = new HashMap<>();
  }

  //Helper constructor
  public BattleShipBoard(int w, int h, T miss) {
    this(w, h, new InBoundsRuleChecker<T>(null), miss);
  }

  //getter method for width
  public int getWidth(){
    return width;
  }

  //getter method for height
  public int getHeight(){
    return height;
  }

  /** tries to add ship
     @return True if successful
  **/
  public String tryAddShip(Ship<T> toAdd){
    //check if ship placement is valid
    //returns output string if invalid
    String out = placementChecker.checkPlacement(toAdd, this);
    if(out != null){
      return out;
    }
    
    myShips.add(toAdd);
    return null;
  }

  //Method to select Ship present at coordinate
  // returns the Ship, if found
  // else NULL
  public Ship<T> selectShip(Coordinate where){
    // Traverse ships
    for(Ship<T> s : myShips){
      if(s.occupiesCoordinates(where) == true){
        //ship found
        return s;
      }
    }
    return null;
  }

  //method to update misses list
  private void update_misses(Ship<T> ship){
    for(Coordinate c : ship.getCoordinates()){
      if(whatIsAtForEnemy(c) == missInfo){
        misses.add(c);
      }
    }
  }

  //method to update hits list
  private void update_hits(Ship<T> ship){
    for(Coordinate c : ship.getCoordinates()){
      if(ship.wasHitAt(c)){
        hits.put(c, ship.getDisplayInfoAt(c, false));
      }
    }
  }

  //Method to make hits on newShips relative to old ship
  //Also updates Hits List 
  private void makeHits(Ship<T> ship, Ship<T> newShip, Placement p){
    //get coord, orientation of new ship
    Coordinate c = newShip.getUpperLeft();
    int row = c.getRow();
    int col = c.getColumn();
    Character orient = newShip.getOrient();

    //get coordinate, orientation of old ship
    Coordinate c_old = ship.getUpperLeft();
    int row_old = c_old.getRow();
    int col_old = c_old.getColumn();
    Character orient_old = ship.getOrient();

    //Compute coordinate difference between new ship and old ship
    int row_diff = row - row_old;
    int col_diff = col - col_old;
    
    //Case for Rectangular ships
    if(orient == 'H' || orient == 'V' ){
      //traverse old ship coords
      for(Coordinate pos : ship.getCoordinates()){
        if(ship.wasHitAt(pos)){

          //If same orientation
          if(orient_old == orient){
            //Calculate position 
            int row_n = pos.getRow() + row_diff;
            int col_n = pos.getColumn() + col_diff;
            ships.add(new Coordinate(row_n, col_n));

            newShip.recordHitAt(new Coordinate(row_n, col_n));
          }
          else{
            //Get difference between current coordinate and oldship coords
            int c_row_diff = pos.getRow() - row_old;
            int c_col_diff = pos.getColumn() - col_old;

            //calculate position
            int row_n = pos.getRow() + row_diff + c_col_diff - c_row_diff;
            int col_n = pos.getColumn() + col_diff + c_row_diff - c_col_diff;
            ships.add(new Coordinate(row_n, col_n));

            newShip.recordHitAt(new Coordinate(row_n, col_n));
          }
            
        }
      }
      return;
    }

    //Other case -TShip, Zship
    int no_hit = 0;
    //Get no of coordinates hit
    for(Coordinate pos : ship.getCoordinates()){
        if(ship.wasHitAt(pos)){
          no_hit += 1;
        }
    }

    //Update same no of hits in new ship
    int i = 0;
    for(Coordinate pos : newShip.getCoordinates()){
        if(i >= no_hit){
          break;
        }
        newShip.recordHitAt(pos);
        ships.add(pos);
        i++;
    }
    
  }
  
  //Method to add newship and remove old ship
  public String tryMoveShip(Ship<T> ship, Ship<T> newShip, Placement p){
    //Check if ship to be moved to new posn is valid
    String out = placementChecker.checkPlacement(newShip, this);
    //error message
    if(out != null){
      return out;
    }

    //Update hits, misses table
    update_misses(newShip);
    update_hits(ship);
    
    //make hits in newShip relative to ship
    makeHits(ship, newShip, p);

    //Change the ship's location
    myShips.remove(ship);
    myShips.add(newShip);

   
    return null;
  }

  //method to check whether player has lost
  public boolean hasLost(){
    for(Ship<T> s: myShips){
      if(s.isSunk() == false){
        return false;
      }
    }
    return true;
  }

  //method to check what is present for self
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  //method to check if ship is present at the coordinate
  //if present, returns apt display info
  protected T whatIsAt(Coordinate where, boolean isSelf){
    //Override for move method
    //to keep enemy board constant
    if(isSelf == false){
      if(misses.contains(where)){
          return missInfo;
      }
      if(hits.containsKey(where)){
          return hits.get(where);
      }
      if(ships.contains(where)){
        return null;
      }
    }   
   
    //Normal cases
    for (Ship<T> s: myShips) {
      if (s.occupiesCoordinates(where)){
        return s.getDisplayInfoAt(where, isSelf);
      }
    }

    //for enemy display missInfo for a miss
    if(isSelf == false && enemyMisses.contains(where) == true){
        return missInfo;
    }
   
    return null;
 
  }

  //method to check what is present for enemy
  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }

  //Method to fire at enemy ships
  public Ship<T> fireAt(Coordinate c){
    //traverse ships
    //if any ship occupy c, record hit and return it

    //Remove for hits, misses, ship hits if fired there
    if(ships.contains(c)){
      ships.remove(c);
    }
   
    if(hits.containsKey(c)){
      hits.remove(c);
    }
    else if(misses.contains(c)){
      misses.remove(c);
    }
    
    for(Ship<T> s : myShips){
      if (s.occupiesCoordinates(c)){
        s.recordHitAt(c);
        return s;
      }
   }
    enemyMisses.add(c);
    //no ship at c
    return null;
  }

 //Method to do sonar scan around coordinate c
 public String sonarScan(Coordinate where){
    //No of coords occupied by each shi[
    int s = 0;
    int c = 0;
    int d = 0;
    int b = 0;

    //int to keep track of coords
    int start = 0;
    int row_len = 1;
    int row = where.getRow();
    int column = where.getColumn();

    //track upper half
    for(int i = -3; i <= 0; i++){
      for(int j = 0; j < row_len; j++){
        //calculate positions
        int row_c = row + i;
        int col = column + start + j;

        //if invalid, skip
        if(row_c < 0 || row_c >= height || col < 0 || col >= width){
          continue;
        }
        //check which ship occupy coords
        Coordinate check = new Coordinate(row_c, col);
        for(Ship<T> ship : myShips){
           if (ship.occupiesCoordinates(check)){
             if(ship.getName() == "Submarine"){
               s += 1;
             }
             else if(ship.getName() == "Carrier"){
               c += 1;
             }
             if(ship.getName() == "Destroyer"){
               d += 1;
             }
             else if(ship.getName() == "Battleship"){
               b += 1;
             }
           }
        }
      }
      start -= 1;
      row_len += 2;
    }

    //track lower half
    row_len = 5;
    start = -2;
    for(int i = 1; i < 4; i++){
      for(int j = 0; j < row_len; j++){
        //calculate coordinate
        int row_c = row + i;
        int col = column + start + j;

        //if invalid skip
        if(row_c < 0 || row_c >= height || col < 0 || col >= width){
          continue;
        }

        //check the coordinate
        Coordinate check = new Coordinate(row_c, col);
        for(Ship<T> ship : myShips){
           if (ship.occupiesCoordinates(check)){
             if(ship.getName() == "Submarine"){
               s += 1;
             }
             else if(ship.getName() == "Carrier"){
               c += 1;
             }
             if(ship.getName() == "Destroyer"){
               d += 1;
             }
             else if(ship.getName() == "Battleship"){
               b += 1;
             }
           }
        }
      }
      start += 1;
      row_len -= 2;
    }

    //Output string
    String out = "";
    out += "Submarines occupy " + String.valueOf(s) + " squares\n";
    out += "Destroyers occupy " + String.valueOf(d) + " squares\n";
    out += "Battleships occupy " + String.valueOf(b) + " squares\n";
    out += "Carriers occupy " +  String.valueOf(c) + " squares\n";
    
    return out;
  }
  
}
