package edu.duke.gg147.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TShipTest {
  @Test
  public void test_makeCoods() {
    TShip<Character> u = new TShip<Character>("Yo", new Coordinate(1, 2), 'U', 's', '*');
    TShip<Character> l = new TShip<Character>("Yo", new Coordinate(1, 2), 'L', 's', '*');
    TShip<Character> d = new TShip<Character>("Yo", new Coordinate(1, 2), 'D', 's', '*');
    TShip<Character> r = new TShip<Character>("Yo", new Coordinate(1, 2), 'R', 's', '*');

    //Test up orientation
    test_coods(u, 1, 3);
    test_coods(u, 2, 2);
    test_coods(u, 2, 3);
    test_coods(u, 2, 4);
    assertEquals(true, u.isSunk());
    assertEquals("Yo", u.getName());
    
    //Test down orient
    test_coods(d, 1, 2);
    test_coods(d, 1, 3);
    test_coods(d, 1, 4);
    test_coods(d, 2, 3);

    //Test left orient
    test_coods(l, 1, 3);
    test_coods(l, 2, 2);
    test_coods(l, 2, 3);
    test_coods(l, 3, 3);

    //Test right orient
    test_coods(r, 1, 2);
    test_coods(r, 2, 2);
    test_coods(r, 2, 3);
    test_coods(r, 3, 2);
  }

  //private function to test 4 methods of BasicShip
  //RecordHit, wasHit, OccupiesCoordinate, getDisplayInfo
  private void test_coods(TShip<Character> t, int row, int col){
     Coordinate c = new Coordinate(row, col);
     assertEquals(true, t.occupiesCoordinates(c));
     assertEquals('s', t.getDisplayInfoAt(c, true));
     assertEquals(null, t.getDisplayInfoAt(c, false));

     //record hit
     t.recordHitAt(c);
     assertEquals(true, t.wasHitAt(c));
     assertEquals('*', t.getDisplayInfoAt(c, true));
     assertEquals('s', t.getDisplayInfoAt(c, false));
  }
  

}
