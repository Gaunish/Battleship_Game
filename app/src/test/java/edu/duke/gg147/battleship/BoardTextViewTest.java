package edu.duke.gg147.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2);
    BoardTextView view = new BoardTextView(b1);

    String expectedHeader= "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());

    String expectedRow = "A  |  A\n"+ "B  |  B\n";
    assertEquals(expectedRow, view.makeRows()); 
    String expected=
                expectedHeader+
                expectedRow+
                expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11,20);
    Board<Character> tallBoard = new BattleShipBoard<Character>(10,27);
    //you should write two assertThrows here
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard)); 
  }

  //helper function to test empty boards
  private void emptyBoardHelper(int w, int h, String expectedHeader, String Body){
    Board<Character> b1 = new BattleShipBoard<Character>(w, h);
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + Body + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

 @Test
 public void test_display_empty_3by2(){
   String header = "  0|1|2\n";
   String body = "A  | |  A\n"+ "B  | |  B\n";
   emptyBoardHelper(3, 2, header, body);
 }

 @Test
 public void test_display_empty_3by5(){
   String header = "  0|1|2\n";
   String body = "A  | |  A\n"+ "B  | |  B\n"+ "C  | |  C\n"+ "D  | |  D\n"+ "E  | |  E\n";
   emptyBoardHelper(3, 5, header, body);
 }

  //helper function to test boards with ships
  private void BoardHelper(Board<Character> b, String expectedHeader, String Body){
    BoardTextView view = new BoardTextView(b);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + Body + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }


  //helper function to add ship
  private void add_ship(Board<Character> b, int col, int row){
   Coordinate c = new Coordinate(col, row);
   Ship<Character> s = new RectangleShip<Character>(c, 's', '*');;
   b.tryAddShip(s);
  }
  
 @Test
 public void test_display_ships(){
   Board<Character> b = new BattleShipBoard<>(4, 3);
   String header = "  0|1|2|3\n";
   String body;

   add_ship(b, 2, 3);
   body = "A  | | |  A\n" + "B  | | |  B\n" + "C  | | |s C\n" ;
   BoardHelper(b, header, body);

   add_ship(b, 0, 0);
   body = "A s| | |  A\n" + "B  | | |  B\n" + "C  | | |s C\n" ;
   BoardHelper(b, header, body);

   add_ship(b, 1, 2);
   body = "A s| | |  A\n" + "B  | |s|  B\n" + "C  | | |s C\n" ;
   BoardHelper(b, header, body);

   add_ship(b, 0, 1);
   body = "A s|s| |  A\n" + "B  | |s|  B\n" + "C  | | |s C\n" ;
   BoardHelper(b, header, body);

   
 }
}
