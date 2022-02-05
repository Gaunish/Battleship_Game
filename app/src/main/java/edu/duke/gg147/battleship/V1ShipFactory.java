package edu.duke.gg147.battleship;

public class V1ShipFactory implements AbstractShipFactory<Character> {

  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    return createShip(where, 1, 2, 's', "Submarine");
  }

  @Override
  public Ship<Character> makeBattleship(Placement where) {
    return createTShip(where, 'b', "Battleship");
  }

  @Override
  public Ship<Character> makeCarrier(Placement where) {
    return createZShip(where, 'c', "Carrier");
  }

  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    return createShip(where, 1, 3, 'd', "Destroyer");
  }

  //Function to abstract creation of different type of ships
  //throws IllegalArgumentException for invalid orientation
  protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name){
    if(where.getOrient() != 'V' && where.getOrient() != 'H'){
    //invalid orientation
    throw new IllegalArgumentException("Invalid Placement, found : " + where.getOrient());
    }
    
    //vertical orientation
    if(where.getOrient() == 'V'){
      return new RectangleShip<Character>(name, where, w, h, letter, '*');
    }

    //Horizontal orientation
    return new RectangleShip<Character>(name, where, h, w, letter, '*');
  }

  //Function to abstract creation of Tship
  //throws IllegalArgumentException for invalid orientation
  protected Ship<Character> createTShip(Placement where, char letter, String name){
    if(where.getOrient() != 'U' && where.getOrient() != 'L' && where.getOrient() != 'D' && where.getOrient() != 'R'){
    //invalid orientation
    throw new IllegalArgumentException("Invalid Placement, found : " + where.getOrient());
    }
    
    //Create TShip
    return new TShip<Character>(name, where.getWhere(), where.getOrient(), letter, '*');
  }

  //Function to abstract creation of Zship
  //throws IllegalArgumentException for invalid orientation
  protected Ship<Character> createZShip(Placement where, char letter, String name){
    if(where.getOrient() != 'U' && where.getOrient() != 'L' && where.getOrient() != 'D' && where.getOrient() != 'R'){
    //invalid orientation
    throw new IllegalArgumentException("Invalid Placement, found : " + where.getOrient());
    }
    
    //Create ZShip
    return new ZShip<Character>(name, where.getWhere(), where.getOrient(), letter, '*');
  }

}
