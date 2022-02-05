package edu.duke.gg147.battleship;

import java.util.HashSet;

//Class represent T shaped battleship
public class TShip<T> extends BasicShip<T> {
  private final String name;

  //Function to create hashset of ship coordinates
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, Character orient){
    
    //stores to coordinates
    HashSet<Coordinate> coods = new HashSet<Coordinate>();

    int up_row = upperLeft.getRow();
    int up_col = upperLeft.getColumn();    
    
    //Up orientation
    if(orient == 'U'){
      coods.add(new Coordinate(up_row, up_col + 1));
      coods.add(new Coordinate(up_row + 1, up_col));
      coods.add(new Coordinate(up_row + 1, up_col + 1));
      coods.add(new Coordinate(up_row + 1, up_col + 2));
    }
    //Down orientation
    else if(orient == 'D'){
      coods.add(new Coordinate(up_row + 1, up_col + 1));
      coods.add(new Coordinate(up_row, up_col));
      coods.add(new Coordinate(up_row, up_col + 1));
      coods.add(new Coordinate(up_row, up_col + 2));
    }
    //Left Orientation
    else if(orient == 'R'){
      coods.add(new Coordinate(up_row, up_col));
      coods.add(new Coordinate(up_row + 1, up_col));
      coods.add(new Coordinate(up_row + 1, up_col + 1));
      coods.add(new Coordinate(up_row + 2, up_col));
    }
    else{
      coods.add(new Coordinate(up_row, up_col + 1));
      coods.add(new Coordinate(up_row + 1, up_col));
      coods.add(new Coordinate(up_row + 1, up_col + 1));
      coods.add(new Coordinate(up_row + 2, up_col + 1));
    }
    return coods;
  }

  //Constructor to init RectangleShip, BasicShip
  //Uses static method to create hashset of coords before passing
  public TShip(String name, Coordinate upperLeft, Character orient, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, orient), myDisplayInfo, enemyDisplayInfo, upperLeft, orient);
    this.name = name;
  }

  //Helper constructor for abstraction
  public TShip(String name, Coordinate upperLeft, Character orient, T data, T onHit) {
    this(name, upperLeft, orient, new SimpleShipDisplayInfo<T>(data, onHit),
        new SimpleShipDisplayInfo<T>(null, data));
  }

  /*
  //Helper constructor
  public TShip(Coordinate upperLeft, T data, T onHit) {
    this("testship", upperLeft, 'U', data, onHit);
    }*/

  @Override
  //getter function for name
  public String getName(){
    return name;
  }

}
