package s02;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class TerrainController {
	private MineHuntModel model;
	private MineHuntView view;

	public TerrainController(MineHuntModel model, MineHuntView view) {
		this.view = view;
		this.model = model;
	}

	/**
	 * Lorsque l'on clique sur un bouton du champs de mines
	 * 
	 * @param event
	 */
	public void gererClick(MouseEvent event) {
		try {
			CellButton btn = (CellButton) event.getSource();
			// Click gauche
			if (event.getButton() == MouseButton.PRIMARY) {
				gererBouton(btn);
				if (model.estTermine()) {
					int nbErrors = model.getNbErrors();
					view.terminerPartie(nbErrors);
				}
				// Click droit
			} else if (event.getButton() == MouseButton.SECONDARY) {
				int ligIndex = btn.getLigIndex();
				int colIndex = btn.getColIndex();
				if (model.getUneCaseDejaClique(ligIndex, colIndex) == false) {
					if (model.getUneCaseFlagged(ligIndex, colIndex) == false) {
						model.setUneCaseFlagged(ligIndex, colIndex, true);
						view.mettreUnDrapeau(btn);
						System.out.println(model.getUneCaseFlagged(ligIndex, colIndex));
					} else {
						model.setUneCaseFlagged(ligIndex, colIndex, false);
						view.enleverDrapeau(btn);
					}
					System.out.println("Click droit");
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gère le comportement de la case cliquée
	 * 
	 * @param btn
	 */
	public void gererBouton(CellButton btn) {
		int ligIndex = btn.getLigIndex();
		int colIndex = btn.getColIndex();
		// si la case n'est pas flagged
		if (model.getUneCaseFlagged(ligIndex, colIndex) == false) {
			if (model.getUneCaseDejaClique(ligIndex, colIndex) == true) {}
				// si la case n'a pas été cliquée
			else if (model.estUneMine(ligIndex, colIndex)) {
				model.setUneCaseDejaClique(ligIndex, colIndex, true);
				model.setNbErreurs(model.getNbErrors() + 1);
				System.out.println("clic sur une bombe");
				view.colorierEnRouge(btn);
				view.updateNbErrors((model.getNbErrors()));
			} else {
				model.setUneCaseDejaClique(ligIndex, colIndex, true);
				model.setNbClicks(model.getNbClicks() + 1);
				view.afficherNbMinesAutour(btn, model.nbMinesAutour(ligIndex, colIndex));
				view.updateNbClicks((model.getNbClicks()));
				// Ouvre les cases autour si pas de mines autour
				if (model.nbMinesAutour(ligIndex, colIndex) == 0) {
					view.ouvrirToutesLesCasesAutour(model.getTerrain(), model.casesAutour(ligIndex, colIndex));
				}
			}
		} else {
			System.out.println("Clic gauche sur une case avec drapeau, aucun effet");
		}

	}

}
