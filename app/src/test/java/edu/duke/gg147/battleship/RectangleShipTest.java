package edu.duke.gg147.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_makeCoords() {
    //test 2x3 rectangle starting at (row = 1, col = 2)
    Coordinate cood = new Coordinate(1, 2);
    int width = 2;
    int height = 3;

    RectangleShip<Character> t = new RectangleShip<Character>(cood, width, height, 's', '*');

    //check whether each coordinate is occupied
    add_cood(1, 2, t);
    add_cood(2, 2, t);
    add_cood(1, 3, t);
    add_cood(2, 3, t);
    add_cood(3, 2, t);
    add_cood(3, 3, t);

    //test single piece
    RectangleShip<Character> t2 = new RectangleShip<Character>(new Coordinate(4, 9), 1, 1, 's', '*');
    add_cood(4, 9, t2);

  }

  //private function to check whether ship contains coordinate
  private void add_cood(int row, int col, RectangleShip t){
    Coordinate cood = new Coordinate(row, col);
    assertEquals(true, t.occupiesCoordinates(cood));
  }

}
