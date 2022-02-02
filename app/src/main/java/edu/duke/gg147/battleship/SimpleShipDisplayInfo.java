package edu.duke.gg147.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  T myData, onHit;

  //Constructor to init class
  SimpleShipDisplayInfo(T myData, T onHit){
    this.myData = myData;
    this.onHit = onHit;
  }

  //return onHit if hit, else myData
  @Override
  public T getInfo(Coordinate where, boolean hit) {
    if(hit){
      return onHit;
    }
    return myData;
  }

}
