package s02;

import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import s02.MineHuntModel;
import s02.MineHuntView;

public class MineHuntController {
	private MineHuntModel model;
	private MineHuntView view;
	private TerrainController tController;

	// CONSTRUCTEUR
	// ------------------------------------------------------------------------------------------------
	public MineHuntController(MineHuntModel model, MineHuntView view) {
		this.view = view;
		this.model = model;
	}

	// Initialise le modèle
	public void initialize() {
		try {
			// Récupération des éléments de la vue
			int hauteur = view.getHauteur();
			int largeur = view.getLargeur();
			int pourcentMines = view.getPourcentMines();

			model.setTerrain(hauteur, largeur);
			model.setDejaClique(hauteur, largeur);
			model.setFlagged(hauteur, largeur);
			model.setPourcentMines(pourcentMines);
			model.setNbClicks(0);
			model.setNbErreurs(0);
			model.initialiser(); // Crée le champs de mines

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		// NbLines -> hauteur
		// NbCol -> largeur
		// Pour placer les boutons, on a juste besoin de connaître
		// le nombre de lignes et de colonnes
		view.creerTerrain(model.getNbLines(), model.getNbCol());
		
		System.out.println("Initialisation avec un terrain d'une largeur de " + model.getTerrain()[0].length
				+ " * une hauteur de " + model.getTerrain().length + " mines " + "et avec un pourcentage de mines de "
				+ model.getPourcentMines());
	}
	
}
