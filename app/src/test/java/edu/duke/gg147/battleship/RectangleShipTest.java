package edu.duke.gg147.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  //Test Basic ship functions
  @Test
  public void test_BasicShipMethods() {
    //Init ship
    // create 2x3 rectangle starting at (row = 2, col = 2)   
    RectangleShip<Character> t = new RectangleShip<Character>(new Coordinate(1, 2), 2, 2, 's', '*');

    //checks coordinate in ship
    //adds a hit and test if marked
    test_coods(t, 2, 2);
    test_coods(t, 1, 2);
    test_coods(t, 2, 3);

    assertEquals(false, t.isSunk());
    assertThrows(IllegalArgumentException.class, () -> t.recordHitAt(new Coordinate(1, 1)));
    assertThrows(IllegalArgumentException.class, () -> t.wasHitAt(new Coordinate(3, 2)));

    //single location ship
    RectangleShip<Character> t2 = new RectangleShip<Character>(new Coordinate(4, 9), 1, 1, 's', '*');
    test_coods(t2, 4, 9);

    //assertEquals(true, t.isSunk());
  }

  //private function to test 4 methods of BasicShip
  //RecordHit, wasHit, OccupiesCoordinate, getDisplayInfo
  private void test_coods(RectangleShip<Character> t, int row, int col){
     Coordinate c = new Coordinate(row, col);
     assertEquals(true, t.occupiesCoordinates(c));
     assertEquals('s', t.getDisplayInfoAt(c));
     
     //record hit
     t.recordHitAt(c);
     assertEquals(true, t.wasHitAt(c));
     assertEquals('*', t.getDisplayInfoAt(c));
  }
  
  
  //Test rectangle ship functions
  @Test
  public void test_RectangleShip() {
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
    assertEquals(false, t.occupiesCoordinates(new Coordinate(4, 10)));

  }

  //private function to check whether ship contains coordinate
  private void add_cood(int row, int col, RectangleShip<Character> t){
    Coordinate cood = new Coordinate(row, col);
    assertEquals(true, t.occupiesCoordinates(cood));
  }

  

}
