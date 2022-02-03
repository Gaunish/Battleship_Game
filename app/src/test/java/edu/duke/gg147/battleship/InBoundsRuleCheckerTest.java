package edu.duke.gg147.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_inBounds() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(8, 10, 'X');
    InBoundsRuleChecker<Character> check = new InBoundsRuleChecker<Character>(null);

    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> s = factory.makeSubmarine(new Placement("C5H"));
    assertEquals(null, check.checkMyRule(s, b));


    //Check right side
    Ship<Character> s1 = factory.makeCarrier(new Placement("A8V"));
    assertEquals("That placement is invalid: the ship goes off the right of the board.", check.checkMyRule(s1, b));

    //Check bottom side
    Ship<Character> s2 = factory.makeCarrier(new Placement("K0H"));
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.",check.checkMyRule(s2, b));

    //Check left side
    Ship<Character> s3 = factory.makeCarrier(new Placement(new Coordinate(0, -1), 'H'));
    assertEquals("That placement is invalid: the ship goes off the left of the board.",check.checkMyRule(s3, b));

    //Check top side
    Ship<Character> s4 = factory.makeCarrier(new Placement(new Coordinate(-1, 0), 'V'));
    assertEquals("That placement is invalid: the ship goes off the top of the board.",check.checkMyRule(s4, b));    
    
  }

}
