package BusinessLayer;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Pages {
	
	public void openStage(String fxmlPath,String cssPath) {
		try {

			Parent root=FXMLLoader.load(getClass().getResource(fxmlPath));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
			Stage mainPage=new Stage();
			mainPage.setScene(scene);
			mainPage.show();
			} catch (Exception e) {}
		}
	
	public void openScene(String fxmlPath, AnchorPane anchorPane) {
     	try {
        	AnchorPane pane1=(AnchorPane)FXMLLoader.load(getClass().getResource(fxmlPath));
        	anchorPane.getChildren().setAll(pane1);
        	}catch(Exception e) {}

		
	}
	
	
	public void closeStage(AnchorPane anchorPane) {
		try {
			Stage stage=(Stage)anchorPane.getScene().getWindow();
			stage.close();
		} catch (Exception e) {}
		
	}
	
	public void translateTransition(AnchorPane anchorpane) {
    	TranslateTransition tt1=new TranslateTransition(Duration.seconds(0.5),anchorpane);
    	tt1.setByX(0);
    	tt1.setByY(0);
    	tt1.play();
	}
	
	public void fadeTransition(AnchorPane anchorpane) {
    	FadeTransition fd1=new FadeTransition(Duration.seconds(0.5),anchorpane);
    	fd1.setFromValue(0);
    	fd1.setToValue(1);
    	fd1.play();
	}

}
