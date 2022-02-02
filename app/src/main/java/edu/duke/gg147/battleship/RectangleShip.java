package edu.duke.gg147.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T>{
  
  //Function to create hashset of ship coordinates
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height){
    
    //stores to coordinates
    HashSet<Coordinate> coods = new HashSet<Coordinate>();
    
    int up_row = upperLeft.getRow();
    int up_col = upperLeft.getColumn();

    //traverse rows (vertical)
    for(int i = 0; i < height; i++){
      //traverse columns (horizontal)
      for(int j = 0; j < width; j++){
        Coordinate cood = new Coordinate(up_row + i, up_col + j);
        coods.add(cood);
      }
    }
    
    return coods;
  }

  //Constructor to init RectangleShip, BasicShip
  //Uses static method to create hashset of coords before passing and ship display
  public RectangleShip(Coordinate upperLeft, int width, int height, T data, T onHit) {
    super(makeCoords(upperLeft, width, height), new SimpleShipDisplayInfo<T>(data, onHit));
  }
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this(upperLeft, 1, 1, data, onHit);
  }

}
