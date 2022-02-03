package edu.duke.gg147.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b = new BattleShipBoard<Character>(3, 4);
    assertEquals(3, b.getWidth());
    assertEquals(4, b.getHeight());
  }

  @Test
  public void test_try_add() {
    Board<Character> b = new BattleShipBoard<Character>(8, 10);
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> s = factory.makeSubmarine(new Placement("C5H"));

    //check overlapping
    assertEquals(null, b.tryAddShip(s));
    assertEquals("That placement is invalid: the ship overlaps another ship.", b.tryAddShip(s));

    //Check right side
    Ship<Character> s1 = factory.makeCarrier(new Placement("A8V"));
    assertEquals("That placement is invalid: the ship goes off the right of the board.", b.tryAddShip(s1));

    //Check invalid placements
    assertThrows(IllegalArgumentException.class, () -> b.tryAddShip(factory.makeCarrier(new Placement("A0Q"))));
    assertThrows(IllegalArgumentException.class, () -> b.tryAddShip(factory.makeCarrier(new Placement(""))));

  }
  

  @Test
  public void  test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20));
  }

  //private method to check whatIsAtBoard for each coordinate
  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected){
    for(int i = 0; i < expected.length; i++){
      for(int j = 0; j < expected[0].length; j++){
        Coordinate c = new Coordinate(i, j);
        assertEquals(b.whatIsAt(c), expected[i][j]);
      }
    }
  }

  //private method to check tryAddShip
  private <T> void checkTryAdd(BattleShipBoard<T> b, Ship<T> toAdd, String expected){
    assertEquals(expected, b.tryAddShip(toAdd));
  }

  //private method to init T[][] expected values
  private <T> void init_expected(T[][] expected, int row, int col){
    for(int i = 0; i < row; i++){
      for(int j = 0; j < col; j++){
        expected[i][j] = null;
      }
    }
  }

  //private method to modify T[][] expected at (row, col) to val
  private <T> void add_expected(T[][] expected, Coordinate c, T val){
    int col = c.getColumn();
    int row = c.getRow();
    expected[row][col] = val;
  }

  //private method to check for adding a new ship
  private <T> void check_ops(BattleShipBoard<T> b, Coordinate c, Ship<T> s, T val, T[][] expected, String out){
    checkTryAdd(b, s, out);
    add_expected(expected, c, val);
    checkWhatIsAtBoard(b, expected);
  }

  @Test
  //method to test both whatisAtBoard, TryAddShip
  // with <T> as character
  public void test_add_what(){
    BattleShipBoard<Character> b = new BattleShipBoard<>(10, 26);

    Character[][] expected = new Character[26][10];
    init_expected(expected, 26, 10);
    checkWhatIsAtBoard(b, expected);

    Coordinate c = new Coordinate(21, 5);
    Ship<Character> s = new RectangleShip<Character>(c, 's', '*');
    check_ops(b, c, s, 's', expected, null);

    Coordinate c1 = new Coordinate(25, 9);
    Ship<Character> s1 = new RectangleShip<Character>(c1, 's', '*');
    check_ops(b, c1, s1, 's', expected, null);

    Coordinate c2 = new Coordinate(0, 0);
    Ship<Character> s2 = new RectangleShip<Character>(c2, 's', '*');
    check_ops(b, c2, s2, 's', expected, null);

    Coordinate c3 = new Coordinate(21, 9);
    Ship<Character> s3 = new RectangleShip<Character>(c3, 's', '*');
    check_ops(b, c3, s3, 's', expected, null);

  }
}
