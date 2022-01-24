package edu.duke.gg147.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by2() {
    Board b1 = new BattleShipBoard(2, 2);
    BoardTextView view = new BoardTextView(b1);

    String expectedHeader= "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());

    String expectedRow = "A  |  A\n"+ "B  |  B\n";
    assertEquals(expectedRow, view.makeRow()); 
    String expected=
                expectedHeader+
                expectedRow+
                expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_invalid_board_size() {
    Board wideBoard = new BattleShipBoard(11,20);
    Board tallBoard = new BattleShipBoard(10,27);
    //you should write two assertThrows here
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard)); 
  }

  private void emptyBoardHelper(int w, int h, String expectedHeader, String Body){
    Board b1 = new BattleShipBoard(w, h);
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

}