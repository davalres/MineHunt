package s02;

import javafx.scene.control.Button;

public class CellButton extends Button {
  private int ligIndex;
  private int colIndex;
  private boolean dejaClique;
  private boolean drapeau;
  
  public CellButton(int ligIndex, int colIndex) {
    super();
    this.ligIndex = ligIndex;
    this.colIndex = colIndex;
    boolean drapeau = false;
    boolean dejaClique = false;
  }

  public int getLigIndex() {
    return ligIndex;
  }
  public int getColIndex() {
    return colIndex;
  }
  public boolean getDejaClique() {
    return dejaClique;
  } 
  public boolean getDrapeau() {
    return drapeau;
  }
  
  public void setDejaClique(boolean dejaClique) {
    this.dejaClique = dejaClique;
  }
  public void setDrapeau(boolean drapeau) {
    this.drapeau = drapeau;
  }
}
