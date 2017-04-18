package s02;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SettingController implements EventHandler<ActionEvent> {
	private MineHuntModel model;
	private SettingsView view;
	
	public SettingController(MineHuntModel model, SettingsView view, MineHuntView mainView) {
		this.model = model;
		this.view = view;
	}

	@Override
	/**
	 * Lorsque le menu Setting est pressé
	 */
	public void handle(ActionEvent event) {
		System.out.println("JEAN");
		view.CreateSettingsView();
	}

	/**
	 * Lorsque le bouton "Ok" de la vue Seting est pressé
	 */
	public void btnOk() {
		int hauteur = view.getHauteur();
		int largeur = view.getLargeur();
		int pourcentMines = view.getPourcentMines();
		model.setTerrain(hauteur, largeur);
		model.setPourcentMines(pourcentMines);
		view.close();
	}
	
	
}
