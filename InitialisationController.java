package s02;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class InitialisationController implements EventHandler<ActionEvent> {
  
  private MineHuntModel model;
  private MineHuntView view;

  // CONSTRUCTEUR
  // ------------------------------------------------------------------------------------------------
  public InitialisationController(MineHuntModel model, MineHuntView view) {
    this.model = model;
    this.view = view;
  }
  
  public void handle(ActionEvent event) {
    // récupération des valeurs de la vue (on check les erreurs) et modification
    // du model
    try {
      // valeurs récupérées de la vue
      int largeur = view.getLargeur();
      int hauteur = view.getHauteur();
      int pourcentMines = view.getPourcentMines();
     
      // reset le model
      model.setTerrain(largeur, hauteur);
      model.setDejaClique(largeur, hauteur);
      model.setFlagged(largeur, hauteur);
      model.setPourcentMines(pourcentMines);
      model.setNbClicks(0);
      model.setNbErreurs(0);
    } 
    catch(Exception e) {
      e.printStackTrace();
    }
    
    // mise à jour de la vue avec les nouvelle valeurs du model
    view.updateNbClicks(model.getNbClicks());
    view.updateNbErrors(model.getNbErrors());
  }
  
}
