package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import Models.VeriTabani;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class OdaBilgileriDuzenleController {
	public OdaBilgileriDuzenleController() {
		conn=VeriTabani.Connect();
	}
    Connection conn=null;
    PreparedStatement ps=null;
    ResultSet rs=null;
    String sql=null;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane_OdaDuzenle;
    @FXML
    private TextField fiyat;
    @FXML
    private Button guncelle;
    @FXML
    private TextArea kapasite;
    @FXML
    private TextField kat;
    @FXML
    private TextField oda;
    
    private int id;
    // getirilen datalar değişkenlere atandı
    public void setData(int id,String kat,String oda,String kapasite,String fiyat) {
    	this.id=id;
    	this.kat.setText(kat);
    	this.oda.setText(oda);
    	this.kapasite.setText(kapasite);
    	this.fiyat.setText(fiyat);
    }

    @FXML
    void guncelle_action(ActionEvent event) {
       	sql="update oda set Kat=?,OdaNumarasi=?,Kapasite=?,Fiyat=? where idOda="+id;
    	try {
			ps=conn.prepareStatement(sql);
			ps.setString(1,kat.getText().trim());
			ps.setString(2,oda.getText().trim());
			ps.setString(3,kapasite.getText().trim());
			ps.setString(4,fiyat.getText().trim());
			ps.executeUpdate();
			
        	Alert alert=new Alert(AlertType.INFORMATION);
        	alert.setTitle("Otel Sistemi");
        	alert.setHeaderText("İşlem Başarılı.");
        	alert.setContentText("Başarılı bir şekilde güncellenmiştir. Yenilikleri görmek için tabloyu yenilemeyi unutmayın.");
        	alert.showAndWait();
        	
        	// Güncellendikten sonra sayfayı kapatır
			Stage login=(Stage)AnchorPane_OdaDuzenle.getScene().getWindow();
			login.close();

		} catch (Exception e) {
        	Alert alert=new Alert(AlertType.ERROR);
        	alert.setTitle("Otel Sistemi");
        	alert.setHeaderText("Hata Mesajı");
        	alert.setContentText("Boş alan bırakmayınız.");
        	alert.showAndWait();
		}
    }
    MusteriEkleController controller=new MusteriEkleController();
    @FXML
    void initialize() {
    	//butona arka plan rengi eklendi
    	guncelle.setStyle("-fx-background-color: #DBFAA1;");
    	
    	//Musteri ekleden kısıtlamalar alınıp uygulandı
    	controller.sadeceRakam(kat, 1, 2);
    	controller.sadeceRakam(fiyat, 3,5);
    	controller.sadeceRakam(oda, 3, 3);
    }

}
