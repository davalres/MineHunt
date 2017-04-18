package s02;

import s02.MineHuntController;
import s02.CellButton;
import s02.SettingsView;
import javafx.geometry.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;


public class MineHuntView extends Application {
	private SettingsView sView;
	private SettingController sController;
	private MineHuntModel mhModel;
	private MineHuntController mhController;
	private NewGameController ngController;
	private TerrainController tController;
	private ShowMinesController smController;
	private Scene scene;
	private VBox root;
	private Menu mnuFile;
	private TextField tfClicks, tfErrors;
	private Button btnShowMines, btnNewGame;
	private static final String IMG_BOMB = "file:src/s02/resources/bomb.png";
	private static final String IMG_FLAG = "file:src/s02/resources/flag.png";
	private GridPane gpTerrain = new GridPane();
	private CellButton[][] cellButtons;

	public static void main(String[] args) {
		launch(args);
	}
	
	public void init() {
		mhModel = new MineHuntModel();
		gpTerrain = new GridPane();
		tController = new TerrainController(mhModel, this);
		mhController = new MineHuntController(mhModel, this);
		mhController.firstInit(); // pour générer un terrain quand on lance le
									// programme
		mhController.initialize();
		sView = new SettingsView(mhModel, sController);
		sController = new SettingController(mhModel, sView, this);
		sView.setController(sController);
		System.out.println("Fin initialisation");
	}

	// MÉTHODE PRINCIPALE
	// -------------------------------------------------
	@Override
	public void start(Stage primaryStage) {
		try {
			// création du container principal
			root = root();
			Label lblTitle = lblTitle();
			MenuBar mBar = new MenuBar();
			mnuFile = new Menu("File");
			MenuItem itmSettings = new MenuItem("Settings");
			itmSettings.setOnAction(sController);
			mnuFile.getItems().add(itmSettings);
			mBar.getMenus().add(mnuFile);
			tfClicks = new TextField();
			tfErrors = new TextField();
			GridPane gpNombreDe = gpNombreDe();
			smController = new ShowMinesController(mhModel, this);
			HBox hbButtons = hbButtons();
			createControllerNewGame();
			root.getChildren().addAll(mBar, lblTitle, gpNombreDe, gpTerrain, hbButtons);

			scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(true);
			primaryStage.setTitle("Mine Hunt");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Crée le controleur du bouton New Game
	 */
	public void createControllerNewGame() {
		ngController = new NewGameController(mhModel, this, mhController, smController);
		btnNewGame.setOnAction(ngController);
	}

	/**
	 * Met à jour le TextField nbClicks
	 * @param nbClicks
	 */
	public void updateNbClicks(int nbClicks) {
		tfClicks.setText(Integer.toString(nbClicks));
	}

	/**
	 * Met à jour le TextField nbErrors
	 * @param nbErrors
	 */
	public void updateNbErrors(int nbErrors) {
		tfErrors.setText(Integer.toString(nbErrors));
	}

	/**
	 * Place tous les CellButtons en fonction de la hauteur et
	 * et de la largeur en paramètre
	 * @param ligIndex
	 * @param colIndex
	 */
	public void creerTerrain(int ligIndex, int colIndex) {
		// Tableau contenant les boutons
		cellButtons = new CellButton[ligIndex][colIndex];
		// Controleur contrôlant l'appui sur les boutons
		gpTerrain.setHgap(2);
		gpTerrain.setVgap(2);
		gpTerrain.setPadding(new Insets(10, 10, 10, 10));
		// Placement de tous les boutons
		// hauteur -> ligIndex
		// largeur -> colIndex
		for (int i = 0; i < ligIndex; i++) {
			for (int j = 0; j < colIndex; j++) {
				CellButton cb = new CellButton(i, j);
				cb.setMinSize(25, 25);
				cb.setMaxSize(25, 25);
				cb.setStyle("-fx-font-size: 12px; -fx-background-color: grey;" + "-fx-border-radius: null;");
				cb.setPadding(new Insets(1, 1, 1, 1));
				cb.setOnMouseClicked(event -> {
					tController.gererClick(event);
				});

				// Ajout du bouton dans le tableau
				cellButtons[cb.getLigIndex()][cb.getColIndex()] = cb;
				// Ajout dans la gridPane (Affichage graphique)
				gpTerrain.add(cb, j, i); // Cette méthode fonctionne à l'inverse
											// de ma logique
			}
		}
	}

	private VBox root() {
		root = new VBox();
		root.setAlignment(Pos.TOP_CENTER);
		// root.setPadding(new Insets(10, 10, 10, 10));
		return root;
	}

	private Label lblTitle() {
		Label lblTitle = new Label("~ Mine Hunt ~");
		lblTitle.setFont(Font.font("Cambria", 50));
		return lblTitle;
	}

	private GridPane gpNombreDe() {
		// GridPane Numbers of clicks / errors
		GridPane gpNombreDe = new GridPane();
		ColumnConstraints column1 = new ColumnConstraints();
		ColumnConstraints column2 = new ColumnConstraints();
		column1.setPercentWidth(50);
		column2.setPercentWidth(50);
		gpNombreDe.setHgap(10);
		gpNombreDe.setPadding(new Insets(10, 10, 10, 10));
		HBox hbClicks = new HBox();
		hbClicks.setAlignment(Pos.CENTER);
		Label lblClicks = new Label("Nb CLicks ");
		tfClicks.setEditable(false);
		tfClicks.setText("0");
		tfClicks.setMaxSize(50, 25);
		tfClicks.setStyle("-fx-background-color: green;");
		hbClicks.getChildren().addAll(lblClicks, tfClicks);

		HBox hbErrors = new HBox();
		hbErrors.setAlignment(Pos.CENTER);
		Label lblErrors = new Label("Nb errors ");
		tfErrors = new TextField();
		tfErrors.setEditable(false);

		tfErrors.setText("0");
		tfErrors.setMaxSize(50, 10);
		tfErrors.setStyle("-fx-background-color: red;");
		hbErrors.getChildren().addAll(lblErrors, tfErrors);

		gpNombreDe.getColumnConstraints().addAll(column1, column2);
		gpNombreDe.add(hbClicks, 0, 0);
		gpNombreDe.add(hbErrors, 1, 0);

		return gpNombreDe;
	}

	private HBox hbButtons() {
		HBox hbButtons = new HBox();
		hbButtons.setAlignment(Pos.BOTTOM_RIGHT);
		btnShowMines = new Button("Show Mines");
		btnShowMines.setStyle("-fx-padding: 5px;");
		btnShowMines.setOnAction(smController);
		btnNewGame = new Button("New Game");

		hbButtons.getChildren().addAll(btnShowMines, btnNewGame);
		return hbButtons;
	}
	
	/**
	 * Met le backgrount du CellButton en rouge (bombe)
	 * @param btn
	 */
	public void colorierEnRouge(CellButton btn) {
		btn.setStyle("-fx-background-color: red;");
		btn.setGraphic(new ImageView(IMG_BOMB));
	}

	/**
	 * Affiche le nombre de mines autour sur le bouton
	 * @param btn
	 * @param nbMinesAutour
	 */
	public void afficherNbMinesAutour(CellButton btn, int nbMinesAutour) {
		btn.setStyle("-fx-background-color: white;");
		if (nbMinesAutour > 0)
			btn.setText(Integer.toString(nbMinesAutour));
	}

	/**
	 * Place un drapeau sur le bouton
	 * @param btn
	 */
	public void mettreUnDrapeau(CellButton btn) {
		btn.setStyle("-fx-background-color: blue;");
		btn.setGraphic(new ImageView(IMG_FLAG));
	}

	/**
	 * Enlève un drapeau sur le bouton
	 * @param btn
	 */
	public void enleverDrapeau(CellButton btn) {
		btn.setStyle("-fx-background-color: grey;");
		btn.setGraphic(null);
	}

	/**
	 * Ouvre toutes les cases présentent dans la tableau casesAutour
	 * @param terrain
	 * @param casesAutour
	 */
	public void ouvrirToutesLesCasesAutour(boolean[][] terrain, int[][] casesAutour) {
		for (int i = 0; i < casesAutour.length; i++) {
			if ((casesAutour[i][0] >= 0 && casesAutour[i][0] < terrain.length)
					&& (casesAutour[i][1] >= 0 && casesAutour[i][1] < terrain[0].length)) {
				// Simuler un clic sur les cases autour
				tController.gererBouton(cellButtons[casesAutour[i][0]][casesAutour[i][1]]);
			}
		}
	}

	/**
	 * Montre toutes les mines du terrain
	 * @param mines
	 * @param dejaClique
	 */
	public void montrerMines(boolean[][] mines, boolean[][] dejaClique) {
		// tableau mines et cellButtons ont la même forme
		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[0].length; j++) {
				if (mines[i][j] == true && dejaClique[i][j] == false) {
					cellButtons[i][j].setStyle("-fx-background-color: darkred");
				}
			}
		}
	}

	/**
	 * Cache toutes les mines du terrain
	 * @param mines
	 * @param dejaClique
	 */
	public void cacherMines(boolean[][] mines, boolean[][] dejaClique) {
		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[0].length; j++) {
				if (mines[i][j] == true && dejaClique[i][j] == false) {
					cellButtons[i][j].setStyle("-fx-background-color: grey");
				}
			}
		}
	}

	/**
	 * Change le text du bouton ShowMines
	 */
	public void switchLabelBtnShowMines() {
		if (btnShowMines.getText() == "Show Mines")
			btnShowMines.setText("Hide Mines");
		else
			btnShowMines.setText("Show Mines");
	}

	/**
	 * Termine la partie et affiche le nombre d'erreurs
	 * @param nbErrors
	 */
	public void terminerPartie(int nbErrors) {
		Alert dialog;
		if (nbErrors == 0) {
			dialog = new Alert(AlertType.INFORMATION);
			dialog.setContentText("Congratulations !\nCurrent game ended successfully (no error)");
		} else {
			dialog = new Alert(AlertType.WARNING);
			dialog.setContentText("Current game ended with " + nbErrors + " errors");
		}
		dialog.setTitle("Mine Hunt - GameOver");
		dialog.setHeaderText("MineHunt");
		dialog.showAndWait();
	}

	/**
	 * Met le texte du bouton ShowMine dans son état initial
	 */
	public void reinitLabelBtnShowMines() {
		btnShowMines.setText("Show Mines");
	}

	/**
	 * Supprime les éléments du gridpane Terrain
	 */
	public void resetTerrain() {
		gpTerrain.getChildren().clear();
	}

}
