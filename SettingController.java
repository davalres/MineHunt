package s02;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SettingController implements EventHandler<ActionEvent> {
	private MineHuntModel model;
	private SettingsView view;
	
	public SettingController(MineHuntModel model, SettingsView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("JEAN");
	}
	
	
}
