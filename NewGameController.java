package s02;

import s02.MineHuntController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class NewGameController implements EventHandler<ActionEvent> {
	private MineHuntModel model;
	private MineHuntView view;
	private MineHuntController mhController;
	private ShowMinesController smController;

	public NewGameController(MineHuntModel model, MineHuntView view, MineHuntController mhController,
			ShowMinesController smController) {
		this.model = model;
		this.view = view;
		this.mhController = mhController;
		this.smController = smController;
	}

	@Override
	/**
	 * Méthode appelée lorsque "New Game" est pressé
	 */
	public void handle(ActionEvent event) {
		view.resetTerrain();
		mhController.initialize();
		view.updateNbClicks(model.getNbClicks());
		view.updateNbErrors(model.getNbErrors());
		smController.setMineShowed(false);
		view.reinitLabelBtnShowMines();
	}
}
