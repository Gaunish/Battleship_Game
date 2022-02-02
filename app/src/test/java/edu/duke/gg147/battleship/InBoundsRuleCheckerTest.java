package edu.duke.gg147.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_inBounds() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 10);
    InBoundsRuleChecker<Character> check = new InBoundsRuleChecker<Character>(null);

    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> s = factory.makeSubmarine(new Placement("C5H"));
    Ship<Character> s1 = factory.makeCarrier(new Placement("G9H"));

    assertEquals(true, check.checkMyRule(s, b));
    assertEquals(false, check.checkMyRule(s1, b));
    assertEquals(false, check.checkPlacement(s1, b));
    assertEquals(true, check.checkPlacement(s, b));
  }

}
