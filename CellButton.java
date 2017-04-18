package s02;

import javafx.scene.control.Button;

public class CellButton extends Button {
  private int ligIndex;
  private int colIndex;
  
  public CellButton(int ligIndex, int colIndex) {
    super();
    this.ligIndex = ligIndex;
    this.colIndex = colIndex;
  }

  /**
   * Retourne l'index de la ligne
   * @return
   */
  public int getLigIndex() {
    return ligIndex;
  }
  
  /**
   * Retourne l'index de la colonne
   * @return
   */
  public int getColIndex() {
    return colIndex;
  }
}
