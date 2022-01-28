package edu.duke.gg147.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_Placement() {
    Coordinate c1 = new Coordinate("A9");
    Placement p1 = new Placement(c1, 'V');
    char x = 'V';
    assertEquals(x, p1.getOrient());
    assertEquals(c1, p1.getWhere());

  }
  
  @Test
  public void test_Equals() {
    Coordinate c1 = new Coordinate("A9");
    Placement p1 = new Placement(c1, 'V');
    Coordinate c2 = new Coordinate("a9");
    Placement p2 = new Placement(c1, 'v');
    Coordinate c3 = new Coordinate("B8");
    Placement p3 = new Placement(c3, 'H');

    assertEquals(p1, p2);
    assertNotEquals(p1, p3);
    assertNotEquals(p1, "(A3, V)");
  }

  @Test
  public void test_Hash(){
    Coordinate c1 = new Coordinate("A9");
    Placement p1 = new Placement(c1, 'V');
    Coordinate c2 = new Coordinate("a9");
    Placement p2 = new Placement(c1, 'v');
    Coordinate c3 = new Coordinate("B8");
    Placement p3 = new Placement(c3, 'H');

    assertEquals(p1.hashCode(), p2.hashCode());
    assertNotEquals(p1.hashCode(), p3.hashCode());
    assertNotEquals(p2.hashCode(), p3.hashCode());
  }

  @Test
  public void test_String(){
    Coordinate c1 = new Coordinate("A9");
    Placement p1 = new Placement(c1, 'V');
   
    Coordinate c3 = new Coordinate("B8");
    Placement p3 = new Placement(c3, 'H');

    assertEquals("((0, 9), V)", p1.toString());
    assertEquals("((1, 8), H)", p3.toString());
  }

   @Test
  void test_string_constructor_valid_cases() {
    Coordinate c1 = new Coordinate("B3");
    Placement p1 = new Placement("b3v");
    assertEquals(c1, p1.getWhere());
    assertEquals('V', p1.getOrient());
    
    Coordinate c2 = new Coordinate("D5");
    Placement p2 = new Placement("D5Z");
    assertEquals(c2, p2.getWhere());
    assertEquals('Z', p2.getOrient());
  }
  @Test
  public void test_string_constructor_error_cases() {
    assertThrows(IllegalArgumentException.class, () -> new Placement("00A"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("AAA"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A0@"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("[0]"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A1V/"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A10"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A10A"));
  }
}
