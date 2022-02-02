package edu.duke.gg147.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_Simple() {
    SimpleShipDisplayInfo<Character> info = new SimpleShipDisplayInfo<>('s', 'x');
    Coordinate stub = new Coordinate(0,0);
    
    assertEquals('s', info.getInfo(stub, false));
    assertEquals('x', info.getInfo(stub, true));

  }

}
