package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import Models.OB_Kayitlar;
import Models.VeriTabani;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MusteriDegistirController {
	public MusteriDegistirController() {
		conn=VeriTabani.Connect();
	}
    Connection conn=null;
    PreparedStatement ps=null;
    PreparedStatement ps2=null;
    ResultSet rs=null;
    String sql=null;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anc_degistir;

    @FXML
    private Button degistir;

    @FXML
    private ChoiceBox<String> durum;

    @FXML
    private ChoiceBox<String> oda;
    
    private int id;

    public void setData(int id) {
    	this.id=id;
    	
    }
	int id_oda;
    @FXML
    void degistir_action(ActionEvent event) {

    	String sql2="select idOda from oda where OdaNumarasi="+oda.getSelectionModel().getSelectedItem().toString().trim();
    	
    	sql = "UPDATE musteri_oda AS mo "+ 
    			"INNER JOIN oda AS o ON mo.idOda = o.idOda "+
    			"SET mo.durum = ?, mo.idOda = ? WHERE mo.idMusteri="+id;
    	try {
    		ps=conn.prepareStatement(sql);
    		ps2=conn.prepareStatement(sql2);
    		System.out.println(sql2);
    		rs=ps2.executeQuery();
    		
			//ps2.setString(1,oda.getSelectionModel().getSelectedItem().toString().trim());
			rs.next();
			id_oda=rs.getInt("idOda");
			ps.setString(1,durum.getSelectionModel().getSelectedItem().toString().trim());
			ps.setInt(2,id_oda);


			ps.executeUpdate();
			
        	Alert alert=new Alert(AlertType.INFORMATION);
        	alert.setTitle("Otel Sistemi");
        	alert.setHeaderText("İşlem Başarılı");
        	alert.setContentText("Başarılı bir şekilde değiştirilmiştir. Yenilikleri görmek için tabloyu yenilemeyi unutmayın.");
        	alert.showAndWait();
        	
			Stage login=(Stage)anc_degistir.getScene().getWindow();
			login.close();

		} catch (Exception e) {
        	Alert alert=new Alert(AlertType.ERROR);
        	alert.setTitle("Otel Sistemi");
        	alert.setHeaderText("Hata Mesajı");
        	alert.setContentText("Boş Alan Bırakmayınız.");
        	alert.showAndWait();
		}
    }

    @FXML
    void initialize() {
    	degistir.setStyle("-fx-background-color: #F8FFE3;");
    	
      	ObservableList<OB_Kayitlar>liste=FXCollections.observableArrayList();
    	sql="select * from oda";
    	//String sql="select o.OdaNumarasi from oda as o inner join musteri_oda as mo on o.idOda=mo.idOda where mo.idOda="+id;
    	try {
			ps2=conn.prepareStatement(sql);
			rs=ps2.executeQuery();
			while (rs.next()) {
				liste.add(new OB_Kayitlar(
						rs.getString("OdaNumarasi")
						));
				}
			 ObservableList<String> odaNumaralari = FXCollections.observableArrayList();
			 //ObservableList<String> Durumlar = FXCollections.observableArrayList();
		        for (OB_Kayitlar kayit : liste) {
		            odaNumaralari.addAll(kayit.getOdaNumarasi());
		           // Durumlar.addAll(kayit.getDurum());
		        }
			

		oda.setItems(odaNumaralari);
		oda.setValue(odaNumaralari.get(0));
//		durum.setItems(Durumlar);
//		durum.setValue(Durumlar.get(0));
		
		
    	} catch (Exception e) {
        	Alert alert=new Alert(AlertType.ERROR);
        	alert.setTitle("Otel Sistemi");
        	alert.setHeaderText("Hata Mesajı");
        	alert.setContentText("Bilgiler getirilemedi.");
        	alert.showAndWait();
		}
    	
    	  durum.getItems().addAll("Rezerve","Giriş Yapıldı");
    	  durum.setValue("Giriş Yapıldı");
    	
    }
  
}
