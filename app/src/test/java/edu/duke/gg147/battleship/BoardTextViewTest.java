package edu.duke.gg147.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2, 'X');
    BoardTextView view = new BoardTextView(b1);

    String expectedHeader= "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());

    String expectedRow = "A  |  A\n"+ "B  |  B\n";
    assertEquals(expectedRow, view.makeRows((c)->b1.whatIsAtForSelf(c))); 
    String expected=
                expectedHeader+
                expectedRow+
                expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_two_boards(){
    Board<Character> b = new BattleShipBoard<>(2, 2, 'X');
    Board<Character> b1 = new BattleShipBoard<>(2, 2, 'X');

    StringBuilder out = new StringBuilder();
    String head = "Your ocean";
    out.append("     ");
    out.append(String.format("%-26s", head));
    out.append("Player B's ocean\n");

    String header = "  0|1";
    String header1 = "  0|1\n";
    out.append(String.format("%-23s", header));
    out.append(header1);

    add_ship(b, 0, 0);
    add_ship(b, 0, 1);
    add_ship(b1, 1, 1);
    add_ship(b1, 1, 0);
    
    b1.fireAt(new Coordinate(0, 0));
    b1.fireAt(new Coordinate(1, 1));
    b.fireAt(new Coordinate(0, 1));
    b.fireAt(new Coordinate(1, 0));
    
    String row1 = "A s|* A";
    String row2 = "B  |  B";
    
    out.append(String.format("%-23s", row1));
    out.append("A X|  A\n");
    out.append(String.format("%-23s", row2));
    out.append("B  |s B\n");

    out.append(String.format("%-23s", header));
    out.append(header1);

    BoardTextView v = new BoardTextView(b);
    BoardTextView v1 = new BoardTextView(b1);

    assertEquals(out.toString(), v.displayMyBoardWithEnemyNextToIt(v1, "Your ocean", "Player B's ocean"));
  }
  

  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11,20, 'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10,27, 'X');
    //you should write two assertThrows here
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard)); 
  }

  //helper function to test empty boards
  private void emptyBoardHelper(int w, int h, String expectedHeader, String Body){
    Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
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
  private void BoardHelper(Board<Character> b, String expectedHeader, String Body, boolean own){
    BoardTextView view = new BoardTextView(b);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + Body + expectedHeader;
    if(own == true){
      assertEquals(expected, view.displayMyOwnBoard());
    }
    else{
      assertEquals(expected, view.displayMyEnemyBoard());
    }
  }


  //helper function to add ship
  private void add_ship(Board<Character> b, int col, int row){
   Coordinate c = new Coordinate(col, row);
   Ship<Character> s = new RectangleShip<Character>(c, 's', '*');;
   b.tryAddShip(s);
  }
  
 @Test
 public void test_display_ships(){
   Board<Character> b = new BattleShipBoard<>(4, 3, 'X');
   String header = "  0|1|2|3\n";
   String body;
   String empty = "A  | | |  A\n" + "B  | | |  B\n" + "C  | | |  C\n" ;
  
   add_ship(b, 2, 3);
   body = "A  | | |  A\n" + "B  | | |  B\n" + "C  | | |s C\n" ;
   BoardHelper(b, header, body, true);
   BoardHelper(b, header, empty, false);
   
   add_ship(b, 0, 0);
   body = "A s| | |  A\n" + "B  | | |  B\n" + "C  | | |s C\n" ;
   BoardHelper(b, header, body, true);
   BoardHelper(b, header, empty, false);

   add_ship(b, 1, 2);
   body = "A s| | |  A\n" + "B  | |s|  B\n" + "C  | | |s C\n" ;
   BoardHelper(b, header, body, true);

   add_ship(b, 0, 1);
   body = "A s|s| |  A\n" + "B  | |s|  B\n" + "C  | | |s C\n" ;
   BoardHelper(b, header, body, true);

   //test misses/hits
   b.fireAt(new Coordinate(0, 2));
   body = "A s|s| |  A\n" + "B  | |s|  B\n" + "C  | | |s C\n" ;
   empty = "A  | |X|  A\n" + "B  | | |  B\n" + "C  | | |  C\n" ;
   BoardHelper(b, header, body, true);
   BoardHelper(b, header, empty, false);

   b.fireAt(new Coordinate(0, 0));
   body = "A *|s| |  A\n" + "B  | |s|  B\n" + "C  | | |s C\n" ;
   empty = "A s| |X|  A\n" + "B  | | |  B\n" + "C  | | |  C\n" ;
   BoardHelper(b, header, body, true);
   BoardHelper(b, header, empty, false);

   b.fireAt(new Coordinate(0, 1));
   body = "A *|*| |  A\n" + "B  | |s|  B\n" + "C  | | |s C\n" ;
   empty = "A s|s|X|  A\n" + "B  | | |  B\n" + "C  | | |  C\n" ;
   BoardHelper(b, header, body, true);
   BoardHelper(b, header, empty, false);
  
 }
}
