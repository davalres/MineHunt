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

	// CONSTRUCTEUR
	// ------------------------------------------------------------------------------------------------
	public NewGameController(MineHuntModel model, MineHuntView view, MineHuntController mhController,
			ShowMinesController smController) {
		this.model = model;
		this.view = view;
		this.mhController = mhController;
		this.smController = smController;
	}

	// méthode appelée lorsque NewGame est pressé
	@Override
	public void handle(ActionEvent event) {
		mhController.initialize();
		view.updateNbClicks(model.getNbClicks());
		view.updateNbErrors(model.getNbErrors());
		if (event.getSource() instanceof Button) // Pour éviter un bug lors de
													// l'init
			smController.setMineShowed(false);
		view.reinitLabelBtnShowMines();
		
	}
}
