package s02;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SettingsView {
	private MineHuntModel model;
	private SettingController controller;
	private TextField tfHauteur;
	private TextField tfLargeur;
	private TextField tfPourcentMines;
	private Stage subStage;
	
	public SettingsView(MineHuntModel model, SettingController controller) {
		this.model = model;
		this.controller = controller;
	}
	
	public void CreateSettingsView()
    {
        subStage = new Stage();
        subStage.setTitle("Settings");
                
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10,10,10,10));
        ColumnConstraints cc = new ColumnConstraints();
        cc.setMinWidth(100);
        Scene scene = new Scene(root);
        Label lblHauteur = new Label("Hauteur ");
        Label lblLargeur = new Label("Largeur ");
        Label lblPourcentMines = new Label("Pourcentage de mines ");
        
        tfHauteur = new TextField();
        tfLargeur = new TextField();
        tfPourcentMines = new TextField();
        Button btnOk = new Button("OK");
        btnOk.setMinWidth(200);
        btnOk.setOnAction((e)->{controller.btnOk();});
        root.getColumnConstraints().addAll(cc);
        root.add(lblHauteur, 0,0);
        root.add(lblLargeur, 0,1);
        root.add(lblPourcentMines, 0,2);
        root.add(tfHauteur, 1, 0);
        root.add(tfLargeur, 1, 1);
        root.add(tfPourcentMines, 1, 2);
        root.add(btnOk, 1, 3);
        subStage.setScene(scene);
        subStage.show();
    }

	public void setController(SettingController controller) {
		this.controller = controller;
	}
	
	/**
	 * Retourne le contenu du champ texte "hauteur"
	 * @return
	 */
	public int getHauteur() {
		return Integer.parseInt((tfHauteur.getText()));
	}
	
	/**
	 * Retourne le contenu du champ texte "largeur"
	 * @return
	 */
	public int getLargeur() {
		return Integer.parseInt((tfLargeur.getText()));
	}
	
	/**
	 * Retourne le contenu du champ texte "pourcentage de mines"
	 * @return
	 */
	public int getPourcentMines() {
		return Integer.parseInt((tfPourcentMines.getText()));
	}

	/**
	 * Ferme la fenêtre
	 */
	public void close() {
		subStage.close();
	}
}

