package BusinessLayer;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts{
	
	public void error(String title,String header,String content) {
		Alert alert=new Alert(AlertType.ERROR);
	  	alert.setTitle(title);
	  	alert.setHeaderText(header);
	  	alert.setContentText(content);
	  	alert.showAndWait();
	}

	public void confirmation(String title,String header,String content) {
		Alert alert=new Alert(AlertType.CONFIRMATION);
	  	alert.setTitle(title);
	  	alert.setHeaderText(header);
	  	alert.setContentText(content);
	  	alert.showAndWait();
	}

	public void information(String title,String header,String content) {
		Alert alert=new Alert(AlertType.INFORMATION);
	  	alert.setTitle(title);
	  	alert.setHeaderText(header);
	  	alert.setContentText(content);
	  	alert.showAndWait();
	}

	public void warning(String title,String header,String content) {
		Alert alert=new Alert(AlertType.WARNING);
	  	alert.setTitle(title);
	  	alert.setHeaderText(header);
	  	alert.setContentText(content);
	  	alert.showAndWait();
	
}
	}
