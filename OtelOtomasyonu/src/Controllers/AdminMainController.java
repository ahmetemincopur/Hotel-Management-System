package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import BusinessLayer.Icons;
import BusinessLayer.Pages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class AdminMainController{

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
	
    @FXML
    private Button OdaBilgileri;

    @FXML
    private Button PersonelListesi;
    

	Pages page=new Pages();

    @FXML
    void MusteriBilgileri_Action(ActionEvent event) {
     	page.openScene("/FXML/MusteriIslemleri.fxml",primaryAnchor);
    }
    
    @FXML
    void oturumu_kapat_action(ActionEvent event) {
     	page.closeStage(mainAnchor);
     	page.openStage("/FXML/Login.fxml", "/application/application.css");
    }
    
    @FXML
    void kapat_action(ActionEvent event) {
    	page.closeStage(mainAnchor);
    }

    @FXML
    void cikis_button_action(ActionEvent event) {
     	page.openScene("/FXML/Cikisİslemleri.fxml",primaryAnchor);
	}
    

    @FXML
    void OdaBilgileri_Action(ActionEvent event) {
    	page.openScene("/FXML/OdaBilgileri.fxml", primaryAnchor);
    }

    @FXML
    void PersonelListesi_Action(ActionEvent event) {
    	page.openScene("/FXML/PersonelListe.fxml", primaryAnchor);
    }





    
    @FXML
    void initialize() {
   

  	
    	PersonelListesi.setId("main-button-orange");
    	OdaBilgileri.setId("main-button-orange");
    	cikis_button.setId("main-button-green");
    	MusteriBilgileri.setId("main-button-green");
    	
    	mainAnchor.setId("anchor-pane_snow");
    	primaryAnchor.setId("anchor-pane_azure");
    	
    	Icons icon=new Icons();
    	icon.setIcon("checkout.png",35,35,cikis_button);
    	icon.setIcon("close.png",25,25,kapat);
    	icon.setIcon("log-out.png",25,25,oturumu_kapat);
    	icon.setIcon("group.png",35,35,MusteriBilgileri);
    	icon.setIcon("employees.png",35,35,PersonelListesi);
    	icon.setIcon("room.png",35,35,OdaBilgileri);
    }




}
