package s02;

import s02.MineHuntController;
import s02.CellButton;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import s02.SettingsView;

public class MineHuntView extends Application {
	// ATTRIBUTS DE L'OBJET VUE
	// ------------------------------------------------------------------------------------------------
	private MineHuntView mhView;
	private SettingsView sView;
	private SettingController sController;
	private MineHuntModel mhModel;
	private MineHuntController mhController;
	private NewGameController ngController;
	private TerrainController tController;
	private ShowMinesController smController;
	private Scene scene;
	private VBox root;
	private Menu mnuSettings;
	private TextField tfClicks, tfErrors;
	private Button btnShowMines, btnNewGame;
	private static final String IMG_BOMB = "file:src/s02/resources/bomb.png";
	private static final String IMG_FLAG = "file:src/s02/resources/flag.png";
	private GridPane gpTerrain = new GridPane(); // On doit le créer ici car on
													// va la modifier dans une
													// méthode
	private CellButton[][] cellButtons; // Tableau contenant tous nos boutons

	// CONSTRUCTEUR PAR DÉFAUT
	// ------------------------------------------------------------------------------------------------
	public MineHuntView() {
		System.out.println("Passage dans le constructeur par défaut");
	}

	// INITIALIZATION
	// ------------------------------------------------------------------------------------------------
	public void initialize() {
		// Récupération des champs de la vue pour initialiser notre grille
		mhView = new MineHuntView();
		// On crée un model sans attributs, le controleur se chargera de faire
		// le lien
		mhModel = new MineHuntModel();
		gpTerrain = new GridPane();
		tController = new TerrainController(mhModel, this);
		// On crée le lien entre la vue et le model
		mhController = new MineHuntController(mhModel, this);
		// On execute la méthode initializee() de notre controller
		mhController.initialize();
		sView = new SettingsView(mhModel, sController);
		sController = new SettingController(mhModel, sView);
		
		System.out.println("Fin initialisation");
	}

	// max: 28
	public int getHauteur() {
		return 20;
	}

	// max: 67
	public int getLargeur() {
		return 10;
	}

	public int getPourcentMines() {
		return 10;
	}

	// MÉTHODE PRINCIPALE
	// ------------------------------------------------------------------------------------------------
	@Override
	public void start(Stage primaryStage) {
		try {
			// initialisation
			initialize();

			// création du container principal
			root = root();

			// création des autres composants de la vue
			Label lblTitle = lblTitle();
			MenuBar mBar = new MenuBar();
			mnuSettings = new Menu("Settings");
			mnuSettings.setOnAction(sController);
			mBar.getMenus().add(mnuSettings);
			tfClicks = new TextField();
			tfErrors = new TextField();
			GridPane gpNombreDe = gpNombreDe();
			// tController déjà créé dans initialize();
			smController = new ShowMinesController(mhModel, this);
			HBox hbButtons = hbButtons();
			createControllerNewGame();
			// Place tous les composants dans le container principal
			root.getChildren().addAll(mBar, lblTitle, gpNombreDe, gpTerrain, hbButtons);

			scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Mine Hunt");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public void createControllerNewGame() {
		ngController = new NewGameController(mhModel, this, mhController, smController);
		btnNewGame.setOnAction(ngController);
	}

	public void updateNbClicks(int nbClicks) {
		tfClicks.setText(Integer.toString(nbClicks));
	}

	public void updateNbErrors(int nbErrors) {
		tfErrors.setText(Integer.toString(nbErrors));
	}

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
				cb.setStyle("-fx-font-size: 12px; -fx-background-color: grey;"
						+ "-fx-border-radius: null;");
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

	public void setTerrain(GridPane gpTerrain) {
		this.gpTerrain = gpTerrain;

		System.out.println("Attribut gpTerrain mis à jour, il n'est donc plus = à null");
	}

	private VBox root() {
		root = new VBox();
		root.setAlignment(Pos.TOP_CENTER);
		//root.setPadding(new Insets(10, 10, 10, 10));
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

	public static void main(String[] args) {
		launch(args);
	}

	public void colorierEnRouge(CellButton btn) {
		btn.setStyle("-fx-background-color: red;");
		btn.setGraphic(new ImageView(IMG_BOMB));
	}

	public void afficherNbMinesAutour(CellButton btn, int nbMinesAutour) {
		btn.setStyle("-fx-background-color: white;");
		if (nbMinesAutour > 0)
			btn.setText(Integer.toString(nbMinesAutour));
	}

	public void incrementerErrors(int nbErrors) {
		tfErrors.setText(Integer.toString(nbErrors));
	}

	public void incrementerClicks(int nbClicks) {
		tfClicks.setText(Integer.toString(nbClicks));
	}

	public void mettreUnDrapeau(CellButton btn) {
		btn.setStyle("-fx-background-color: blue;");
		btn.setGraphic(new ImageView(IMG_FLAG));
	}

	public void enleverDrapeau(CellButton btn) {
		btn.setStyle("-fx-background-color: grey;");
		btn.setGraphic(null);
	}

	public void ouvrirToutesLesCasesAutour(boolean[][] terrain, int[][] casesAutour) {
		for (int i = 0; i < casesAutour.length; i++) {
			if ((casesAutour[i][0] >= 0 && casesAutour[i][0] < terrain.length)
					&& (casesAutour[i][1] >= 0 && casesAutour[i][1] < terrain[0].length)) {
				// Simuler un clic sur les cases autour
				tController.gererBouton(cellButtons[casesAutour[i][0]][casesAutour[i][1]]);
			}
		}
	}

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

	public void cacherMines(boolean[][] mines, boolean[][] dejaClique) {
		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[0].length; j++) {
				if (mines[i][j] == true && dejaClique[i][j] == false) {
					cellButtons[i][j].setStyle("-fx-background-color: grey");
				}
			}
		}
	}

	public void switchLabelBtnShowMines() {
		if (btnShowMines.getText() == "Show Mines")
			btnShowMines.setText("Hide Mines");
		else
			btnShowMines.setText("Show Mines");
	}

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

	public void reinitLabelBtnShowMines() {
		btnShowMines.setText("Show Mines");
	}

}
