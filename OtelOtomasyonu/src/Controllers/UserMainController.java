package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UserMainController implements ICont {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainAnchor;

	@FXML
    private AnchorPane primaryAnchor;

    @FXML
    private Button MusteriBilgileri;

	@FXML
    private Button cikis_button;

	@FXML
    private Button kapat;
	
	@FXML
    private Button oturumu_kapat;
	
	public void open2(String fxml) {
		try {

			
			Parent root=FXMLLoader.load(getClass().getResource(fxml));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			Stage mainPage=new Stage();
			mainPage.setScene(scene);
			Stage login=(Stage)mainAnchor.getScene().getWindow();
			login.close();
			mainPage.show();
		} catch (Exception e) {}
		
	}

    @FXML
    void MusteriBilgileri_Action(ActionEvent event) {
     	open("/FXML/MusteriIslemleri.fxml");
    }
    
    @FXML
    void oturumu_kapat_action(ActionEvent event) {
     	open2("/FXML/Login.fxml");
    }
    
    @FXML
    void kapat_action(ActionEvent event) {
    	Stage login=(Stage)mainAnchor.getScene().getWindow();
		login.close();
    }

    @FXML
    void cikis_button_action(ActionEvent event) {
     	open("/FXML/Cikisİslemleri.fxml");
	}
    

    void setIcon(String iconName,int height,int width,Button button) {
    	Image icon=new Image("C:\\Users\\ahmet\\eclipse-workspace\\OtelOtomasyonu\\src\\images\\"+iconName);
    	ImageView iconView=new ImageView(icon);
    	iconView.setFitHeight(height);
    	iconView.setFitWidth(width);
    	button.setGraphic(iconView);
    }
    



    
    @FXML
    void initialize() {
   

  	
    	cikis_button.setId("main-button-green");
    	MusteriBilgileri.setId("main-button-green");
    	
    	mainAnchor.setId("anchor-pane_snow");
    	primaryAnchor.setId("anchor-pane_azure");
    	
    	
    	setIcon("checkout.png",35,35,cikis_button);
    	setIcon("close.png",25,25,kapat);
    	setIcon("log-out.png",25,25,oturumu_kapat);
    	setIcon("group.png",35,35,MusteriBilgileri);
    }

	@Override
	public void open(String fxml) {
     	try {
        	AnchorPane pane1=(AnchorPane)FXMLLoader.load(getClass().getResource(fxml));
        	primaryAnchor.getChildren().setAll(pane1);
        	}catch(Exception e) {}
        	TranslateTransition tt1=new TranslateTransition(Duration.seconds(0.5),primaryAnchor);
        	tt1.setByX(0);
        	tt1.setByY(0);
        	tt1.play();
        	FadeTransition fd1=new FadeTransition(Duration.seconds(0.5),primaryAnchor);
        	fd1.setFromValue(0);
        	fd1.setToValue(1);
        	fd1.play();
		
	}
	


}
