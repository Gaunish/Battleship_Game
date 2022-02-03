package edu.duke.gg147.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
   @Test
   void test_read_placement() throws IOException{
      //input stream to read programmed input (user feint) 
      StringReader sr = new StringReader("B2V\nC8H\na4v\n");

      //output stream to put output in bytes (ps)
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(bytes, true);

      //Init board, app
      Board<Character> b = new BattleShipBoard<Character>(10, 20);
   
      TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);



      //Prompt, expected output
      String prompt = "Player A where do you want to place a Destroyer?";
      Placement[] expected = new Placement[3];
      expected[0] = new Placement(new Coordinate(1, 2), 'V');
      expected[1] = new Placement(new Coordinate(2, 8), 'H');
      expected[2] = new Placement(new Coordinate(0, 4), 'V');

      //check expected output
      for (int i = 0; i < expected.length; i++) {
        Placement p = player.readPlacement(prompt);
        assertEquals(p, expected[i]); //did we get the right Placement back
        assertEquals(prompt + "\n", bytes.toString()); //should have printed prompt and newline
        bytes.reset(); //clear out bytes for next time around
        }
    }

  //helper function to create a text player
  private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h);
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);
  }

  //Tests method placementPhase,doOnePlacement
    @Test
    void test_placement() throws IOException{
      StringReader sr = new StringReader("B2V\n");

      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(bytes, true);

      //Init board, app
      Board<Character> b = new BattleShipBoard<Character>(3, 5);
      TextPlayer player = createTextPlayer(3, 5, "B2V\n", bytes);

      player.doPlacementPhase();
      String out = "";
      
      String header = "  0|1|2\n";
      String board = "A  | |  A\n"+ "B  | |  B\n"+ "C  | |  C\n"+ "D  | |  D\n"+ "E  | |  E\n";

      String prompt = "Player A: you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical).  For example M4H would place a ship horizontally starting at M4 and going to the right. You have\n2 \"Submarines\" ships that are 1x2\n3 \"Destroyers\" that are 1x3\n3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6 \n";

      out += header + board + header + "\n" + prompt;
      
      String out1 = "\nPlayer A where do you want to place a Destroyer?\n";
      String body = "A  | |  A\n"+ "B  | |d B\n"+ "C  | |d C\n"+ "D  | |d D\n"+ "E  | |  E\n";
  
      out += out1 + header + body + header + "\n";
      assertEquals(out, bytes.toString());
      }

}
