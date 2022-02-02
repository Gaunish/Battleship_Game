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
  private <T> void checkTryAdd(BattleShipBoard<T> b, Ship<T> toAdd, boolean expected){
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
  private <T> void check_ops(BattleShipBoard<T> b, Coordinate c, Ship<T> s, T val, T[][] expected, boolean out){
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
    check_ops(b, c, s, 's', expected, true);

    Coordinate c1 = new Coordinate(25, 9);
    Ship<Character> s1 = new RectangleShip<Character>(c1, 's', '*');
    check_ops(b, c1, s1, 's', expected, true);

    Coordinate c2 = new Coordinate(0, 0);
    Ship<Character> s2 = new RectangleShip<Character>(c2, 's', '*');
    check_ops(b, c2, s2, 's', expected, true);

    Coordinate c3 = new Coordinate(21, 9);
    Ship<Character> s3 = new RectangleShip<Character>(c3, 's', '*');
    check_ops(b, c3, s3, 's', expected, true);

  }
}
