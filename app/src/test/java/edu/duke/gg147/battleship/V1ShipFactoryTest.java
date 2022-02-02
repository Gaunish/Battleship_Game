package edu.duke.gg147.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {
  @Test
  public void test_factory() {
    //Create factory
    V1ShipFactory f = new V1ShipFactory();

    Placement v = new Placement(new Coordinate(2, 2), 'V');
    Ship<Character> s = f.makeDestroyer(v);
    checkShip(s, "Destroyer", 'd', new Coordinate(2, 2), new Coordinate(3, 2), new Coordinate(4, 2));

    Placement v1 = new Placement(new Coordinate(3, 2), 'H');
    Ship<Character> s1 = f.makeSubmarine(v1);
    checkShip(s1, "Submarine", 's', new Coordinate(3, 2), new Coordinate(3, 3));

    Placement v2 = new Placement(new Coordinate(3, 2), 'V');
    Ship<Character> s2 = f.makeCarrier(v2);
    checkShip(s2, "Carrier", 'c', new Coordinate(3, 2), new Coordinate(4, 2), new Coordinate(5, 2), new Coordinate(6, 2), new Coordinate(7, 2), new Coordinate(8, 2));

    Placement v3 = new Placement(new Coordinate(3, 2), 'H');
    Ship<Character> s3 = f.makeBattleship(v3);
    checkShip(s3, "Battleship", 'b', new Coordinate(3, 2), new Coordinate(3, 3), new Coordinate(3, 4), new Coordinate(3, 5));

    Placement v4 = new Placement(new Coordinate(3, 2), 'X');
    assertThrows(IllegalArgumentException.class, () -> f.makeSubmarine(v4));


  }

  //helper function to abstract testing different ship aspects
  private void checkShip(Ship<Character> testShip, String expectedName,
                         char expectedLetter, Coordinate... expectedLocs){

    assertEquals(expectedName, testShip.getName());
    assertEquals(expectedLetter, testShip.getDisplayInfoAt(expectedLocs[0]));

    for(int i = 0; i < expectedLocs.length; i++){
      assertEquals(true, testShip.occupiesCoordinates(expectedLocs[i]));
    }
  }


}
