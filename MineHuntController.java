package s02;

import s02.MineHuntModel;
import s02.MineHuntView;

public class MineHuntController {
	private MineHuntModel model;
	private MineHuntView view;

	// CONSTRUCTEUR
	// ------------------------------------------------------------------------------------------------
	public MineHuntController(MineHuntModel model, MineHuntView view) {
		this.view = view;
		this.model = model;
	}

	public void initialize() {
		try {
			int hauteur = model.getTerrain().length;
			int largeur = model.getTerrain()[0].length;
			int pourcentMines = model.getPourcentMines();
			model.setTerrain(hauteur, largeur);
			model.setDejaClique(hauteur, largeur);
			model.setFlagged(hauteur, largeur);
			model.setPourcentMines(pourcentMines);
			model.setNbClicks(0);
			model.setNbErreurs(0);
			model.initialiser();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		view.creerTerrain(model.getNbLines(), model.getNbCol());
		
		System.out.println("Initialisation avec un terrain d'une largeur de " + model.getTerrain()[0].length
				+ " * une hauteur de " + model.getTerrain().length + " mines " + "et avec un pourcentage de mines de "
				+ model.getPourcentMines());
	}

	/**
	 * Cette méthode sert à définir un terrain lors de la première initialisation
	 * ensuite, l'utilisateur pourra définir cela lui-même
	 */
	public void firstInit() {
		int hauteur = 10;
		int largeur = 10;
		int pourcentMines = 10;
		model.setTerrain(hauteur, largeur);
		model.setPourcentMines(pourcentMines);
		model.initialiser();
	}
	
}
