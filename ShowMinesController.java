package s02;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import s02.MineHuntModel;
import s02.MineHuntView;

public class ShowMinesController implements EventHandler<ActionEvent> {
	private MineHuntModel model;
	private MineHuntView view;
	private boolean minesShowed;
	
	public ShowMinesController(MineHuntModel model, MineHuntView view) {
		this.model = model;
		this.view = view;
		minesShowed = false;
	}

	@Override
	/**
	 * Lorsque l'on clique sur "Show Mines"
	 */
	public void handle(ActionEvent MouseEvent) {
		System.out.println("Clic sur show mines");
		// Si les mines ne sont pas montrées
		if (!minesShowed) {
			boolean[][] mines = model.getTerrain();
			boolean[][] dejaClique = model.getDejaClique();
			view.montrerMines(mines, dejaClique);
			view.switchLabelBtnShowMines();
			minesShowed = true;
		} else {
			// cacher les mines sur lesquelles on a pas encore cliqué
			boolean[][] mines = model.getTerrain();
			boolean[][] dejaClique = model.getDejaClique();
			view.cacherMines(mines, dejaClique);
			view.switchLabelBtnShowMines();
			minesShowed = false;
		}
	}
	
	/**
	 * Réinitialise l'attribut "minesShowed" lors d'une nouvelle partie
	 * @param val
	 */
	public void setMineShowed(boolean val) {
		minesShowed = val;
	}
}
