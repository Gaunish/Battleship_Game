package edu.duke.gg147.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ZShipTest {
  @Test
  public void test_makeCoods() {
    ZShip<Character> u = new ZShip<Character>("Yo", new Coordinate(1, 2), 'U', 's', '*');
    ZShip<Character> l = new ZShip<Character>("Yo", new Coordinate(1, 2), 'L', 's', '*');
    ZShip<Character> d = new ZShip<Character>("Yo", new Coordinate(1, 2), 'D', 's', '*');
    ZShip<Character> r = new ZShip<Character>("Yo", new Coordinate(1, 2), 'R', 's', '*');

    //Test up orientation
    test_coods(u, 1, 2);
    test_coods(u, 2, 2);
    test_coods(u, 3, 2);
    test_coods(u, 4, 2);
    test_coods(u, 3, 3);
    test_coods(u, 4, 3);
    test_coods(u, 5, 3);
    assertEquals(true, u.isSunk());
    assertEquals("Yo", u.getName());
    
    //Test down orient
    test_coods(d, 1, 2);
    test_coods(d, 2, 2);
    test_coods(d, 3, 2);
    test_coods(d, 2, 3);
    test_coods(d, 3, 3);
    test_coods(d, 4, 3);
    test_coods(d, 5, 3);
    assertEquals(true, d.isSunk());
    
    //Test left orient
    test_coods(l, 1, 4);
    test_coods(l, 1, 5);
    test_coods(l, 1, 6);
    test_coods(l, 2, 2);
    test_coods(l, 2, 3);
    test_coods(l, 2, 4);
    test_coods(l, 2, 5);
    assertEquals(true, l.isSunk());
    
    //Test right orient
    test_coods(r, 1, 3);
    test_coods(r, 1, 4);
    test_coods(r, 1, 5);
    test_coods(r, 1, 6);
    test_coods(r, 2, 2);
    test_coods(r, 2, 3);
    test_coods(r, 2, 4);
    assertEquals(true, r.isSunk());
  }

  //private function to test 4 methods of BasicShip
  //RecordHit, wasHit, OccupiesCoordinate, getDisplayInfo
  private void test_coods(ZShip<Character> t, int row, int col){
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


