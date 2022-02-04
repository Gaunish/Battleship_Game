package edu.duke.gg147.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  //Individual tests for no collision 
  @Test
  public void test_noCollison() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 10, 'X');
    NoCollisionRuleChecker<Character> check = new NoCollisionRuleChecker<Character>(null);

    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> s = factory.makeSubmarine(new Placement("C5H"));
    Ship<Character> s1 = factory.makeCarrier(new Placement("G9U"));
    Ship<Character> s2 = factory.makeCarrier(new Placement("B5U"));

    assertEquals(null, check.checkMyRule(s, b));
    b.tryAddShip(s);
    assertEquals("That placement is invalid: the ship overlaps another ship.", check.checkMyRule(s, b));
    assertEquals(null, check.checkMyRule(s1, b));
    assertEquals("That placement is invalid: the ship overlaps another ship.", check.checkMyRule(s2, b));
  }

  //Combined tests for no collision 
  @Test
  public void test_RuleChecker() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 10, 'X');
    InBoundsRuleChecker<Character> check = new InBoundsRuleChecker<Character>(null);
    NoCollisionRuleChecker<Character> check1 = new NoCollisionRuleChecker<Character>(check);

    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> s = factory.makeSubmarine(new Placement("C5H"));
    Ship<Character> s1 = factory.makeCarrier(new Placement("G9U"));
    Ship<Character> s2 = factory.makeCarrier(new Placement("B5U"));
    Ship<Character> s3 = factory.makeCarrier(new Placement("C4U")); 

    assertEquals(null, check1.checkPlacement(s, b));
    b.tryAddShip(s);
    assertEquals("That placement is invalid: the ship overlaps another ship.", check1.checkPlacement(s, b));
    assertEquals("That placement is invalid: the ship goes off the right of the board.", check1.checkPlacement(s1, b));
    assertEquals("That placement is invalid: the ship overlaps another ship.", check1.checkPlacement(s2, b));
    assertEquals(null, check1.checkPlacement(s3, b));
  }
}
