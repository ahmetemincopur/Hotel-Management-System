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

public class OdaBilgileriEkleController {
	public OdaBilgileriEkleController(){
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
    private AnchorPane AnchorPane_OdaEkle;
    @FXML
    private Button odaEkle_ekle;
    @FXML
    private TextArea kapasite;
    @FXML
    private TextField kat;
    @FXML
    private TextField oda;
    @FXML
    private TextField fiyat;
    
    @FXML
    void odaEkle_ekle_Action(ActionEvent event) {
       	sql="insert into oda(Kat,OdaNumarasi,Kapasite,Fiyat) values(?,?,?,?)";
    	try {
			ps=conn.prepareStatement(sql);
			ps.setString(1,kat.getText().trim());
			ps.setString(2,oda.getText().trim());
			ps.setString(3,kapasite.getText().trim());
			ps.setString(4,fiyat.getText().trim());
			ps.executeUpdate();
			
        	Alert alert=new Alert(AlertType.INFORMATION);
        	alert.setTitle("Otel Sistemi");
        	alert.setHeaderText("İşlem başarılı");
        	alert.setContentText("Ekleme işlemi başarıyla gerçekleştirildi. Yenilikleri görmek için tabloyu yenilemeyi unutmayın.");
        	alert.showAndWait();
        	
			Stage login=(Stage)AnchorPane_OdaEkle.getScene().getWindow();
			login.close();

		} catch (Exception e) {
        	Alert alert=new Alert(AlertType.ERROR);
        	alert.setTitle("Otel Sistemi");
        	alert.setHeaderText("Hata Mesajı");
        	alert.setContentText("Boş yer bırakılamaz.");
        	alert.showAndWait();
		}
    }
    MusteriEkleController controller=new MusteriEkleController();
    @FXML
    void initialize() {
//Musteri ekledeki kısıtlamalar alınıp uygulandı
controller.sadeceRakam(kat, 1, 2);
controller.sadeceRakam(fiyat, 3,5);
controller.sadeceRakam(oda, 3, 3);

// Ekle butonuna arkaplan rengi eklendi
odaEkle_ekle.setStyle("-fx-background-color: #00FF94;");
    }
    
    
    
    

}
