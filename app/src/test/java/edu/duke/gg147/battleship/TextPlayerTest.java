package edu.duke.gg147.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TextPlayerTest {

  //Method to test readPlacement
   @Test
   void test_read_placement() throws IOException{
      //input stream to read programmed input (user feint) 
      StringReader sr = new StringReader("B2V\nC8H\na4v\n");

      //output stream to put output in bytes (ps)
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
 
      //Init board, app
      Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
   
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

  //Method to test doOneHit
  @Disabled
  @Test
  void test_do_one_hit(){
    //Init board, app
    Board<Character> b = new BattleShipBoard<Character>(4, 4, 'X');
    V1ShipFactory f = new V1ShipFactory();
    Ship<Character> s = f.makeSubmarine(new Placement("A0V"));
    b.tryAddShip(s);
    

    //input stream to read programmed input (user feint) 
    //StringReader sr = new StringReader("F5\nB0\n");

    //output stream to put output in bytes (ps)
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();

    TextPlayer player = createTextPlayer(4, 4, "F5\nB0\nA1\n", bytes);

    /*String out = "";
    out += "Player A where do you want to hit on board?\n";
    out += "You hit a Submarine!\n";
    
    player.doOneHit(b);
    assertEquals(out, bytes.toString());
    bytes.reset();
    

    out = "";
    out += "Player A where do you want to hit on board?\n";
    out += "You missed!\n";
    player.doOneHit(b);
    assertEquals(out, bytes.toString());
    bytes.reset();*/
    
    String out = "";
    out += "Player A where do you want to hit on board?\n";
    out += "Invalid Coordinates, try again!\n";
    out += "Player A where do you want to hit on board?\n";
    out += "You hit a Submarine!\n";
    out += "Player A where do you want to hit on board?\n";
    out += "You missed!\n";
   
   
    player.doOneHit(b);
    player.doOneHit(b);
    assertEquals(out, bytes.toString());
    bytes.reset();
  }
    
  
  //Method to test readCoordinate
  @Test
   void test_read_coords() throws IOException{
      //input stream to read programmed input (user feint) 
      //StringReader sr = new StringReader("D4\nE5\nF6\n");

      //output stream to put output in bytes (ps)
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
 
      //Init board, app
      Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
   
      TextPlayer player = createTextPlayer(10, 20, "D4\nE5\nF6\n", bytes);



      //Prompt, expected output
      String prompt = "Player A where do you want to hit on board?";
      Coordinate[] expected = new Coordinate[3];
      expected[0] = new Coordinate(3, 4);
      expected[1] = new Coordinate(4, 5);
      expected[2] = new Coordinate(5, 6);

      //check expected output
      for (int i = 0; i < expected.length; i++) {
        Coordinate p = player.readCoordinate(prompt, b);
        assertEquals(p, expected[i]); //did we get the right Placement back
        assertEquals(prompt + "\n", bytes.toString()); //should have printed prompt and newline
        bytes.reset(); //clear out bytes for next time around
        }
    }


  

  //helper function to create a text player
  private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);
  }


  /*
  private String test_help(String name, String body, String ship){
    String out = "";
    String header = "  0|1|2|3|4\n";
    String out1 = "Player " + name + " where do you want to place a " + ship + "?\n";  
    out += out1 + header + body + header + "\n";
    return out;
  }

  
  //Tests method placementPhase,doOnePlacement
  @Test
    void test_do_one_placement() throws IOException{
    /* ByteArrayOutputStream bytes = new ByteArrayOutputStream();

      String in = "A0V\n";
      //B0V\nD0V\n";
      //nB1V\nD4V\nA4V\nA5V\nD5V\nA2V\nA3V\
      
      //Init board, app
      TextPlayer player = createTextPlayer(6, 6, "A0V\n", bytes);
      player.doPlacementPhase();

      String out = "";
      
      String header = "  0|1|2|3|4\n";
      String board = "A  | | | |  A\n"+ "B  | | | |  B\n"+ "C  | | | |  C\n"+ "D  | | | |  D\n"+ "E  | | | |  E\n"+"F  | | | |  F\n";

      String prompt = "Player A: you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical).  For example M4H would place a ship horizontally starting at M4 and going to the right. You have\n2 \"Submarines\" ships that are 1x2\n3 \"Destroyers\" that are 1x3\n3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6 \n";

      out += header + board + header + "\n" + prompt + "\n";
     
      String body = "A s|s| | |  A\n"+ "B  | | | |  B\n"+ "C  | | | |  C\n"+ "D  | |  D\n"+ "E  | | | |  E\n"+"F  | | | |  F\n";  
      out += test_help("A", body, "Submarine");

      String body1 = "A s|s| | |  A\n"+ "B s| | | |  B\n"+ "C s| | | |  C\n"+ "D  | | | |  D\n"+ "E  | | | |  E\n"+"F  | | | |  F\n";  
      out += test_help("A", body1, "Submarine");

      String body2 = "A s|s| | |  A\n"+ "B s|d| | |  B\n"+ "C s|d| | |  C\n"+ "D  |d| | |  D\n"+ "E  | | | |  E\n"+"F  | | | |  F\n";  
      out+= test_help("A", body2, "Destroyer");

      String body3 = "A s|s| | |  A\n"+ "B s|d| | |  B\n"+ "C s|d| | |  C\n"+ "D d|d| | |  D\n"+ "E d| | | |  E\n"+"F d| | | |  F\n";  
      out += test_help("A", body3, "Destroyer");

      String bodyn = "A s|s| | |  A\n"+ "B s|d| | |  B\n"+ "C s|d| | |  C\n"+ "D d|d| | |d D\n"+ "E d| | | |d E\n"+"F d| | | |d F\n";  
      out += test_help("A", bodyn, "Destroyer");

      String body6 = "A s|s|c|c|b A\n"+ "B s|d|c| |b B\n"+ "C s|d|c| |b C\n"+ "D d|d| | |d D\n"+ "E d| | | |d E\n"+"F d| | | |d F\n";  
      out += test_help("A", body6, "Battleship");

      String body7 = "A s|s|c|c|b A\n"+ "B s|d|c|b|b B\n"+ "C s|d|c|b|b C\n"+ "D d|d| |b|d D\n"+ "E d| | | |d E\n"+"F d| | | |d F\n";  
      out += test_help("A", body7, "Battleship");

      String body8 = "A s|s|c|c|b A\n"+ "B s|d|c|b|b B\n"+ "C s|d|c|b|b C\n"+ "D d|d| |b|d D\n"+ "E d|b|b|b|d E\n"+"F d| | | |d F\n";  
      out += test_help("A", body8, "Battleship");
      
      String body4 = "A s|s|c|c|  A\n"+ "B s|d| | |  B\n"+ "C s|d| | |  C\n"+ "D d|d| | |d D\n"+ "E d| | | |d E\n"+"F d| | | |d F\n";  
      out += test_help("A", body4, "Carrier");

      String body5 = "A s|s|c|c|  A\n"+ "B s|d|c| |  B\n"+ "C s|d|c| |  C\n"+ "D d|d| | |d D\n"+ "E d| | | |d E\n"+"F d| | | |d F\n";  
      out += test_help("A", body5, "Carrier");

      ByteArrayOutputStream bytes = new ByteArrayOutputStream();

      String in = "A0V\nB0V\nD0V\nB1V\nd4v\na4v\na5v\nd5v\na2v\na3v\n";
      //B0V\nD0V\n";
      //nB1V\nD4V\nA4V\nA5V\nD5V\nA2V\nA3V\
      
      //Init board, app
      TextPlayer player = createTextPlayer(9, 9, "A0V\nB0V\nD0V\nB1V\nd4v\na4v\na5v\nd5v\na2v\na3v\n", bytes);
      player.doPlacementPhase();

      //player.doPlacementPhase();
      assertEquals(out, bytes.toString());
      bytes.reset();
      }
  */
}
