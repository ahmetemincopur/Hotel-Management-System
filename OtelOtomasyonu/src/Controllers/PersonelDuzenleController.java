package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Models.VeriTabani;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PersonelDuzenleController {
	public PersonelDuzenleController() {
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
    private ChoiceBox<String> yetki;

    @FXML
    private AnchorPane AnchorPane_Duzenle;

    @FXML
    private TextField ad;

    @FXML
    private DatePicker dogumTarihi;

    @FXML
    private Button duzenleButton;

    @FXML
    private TextField soyad;
    
    @FXML
    private TextField kullaniciAdi;
    
    @FXML
    private TextField sifre;
    
    @FXML
    private TextField email;
    
    @FXML
    private TextField statu;

    @FXML
    private TextField tckn;

    @FXML
    private TextField telNo;
    
    private int id;

    public void setData(int id,String ad,String soyad,String TCKN,String statu,String telNo,LocalDate dogumTarihi,String email,String kullaniciAdi,String sifre,String yetki) {
    	this.id=id;
    	this.ad.setText(ad);
    	this.soyad.setText(soyad);
    	this.tckn.setText(TCKN);
    	this.telNo.setText(telNo);
    	this.statu.setText(statu);
    	this.dogumTarihi.setValue(dogumTarihi);
    	this.email.setText(email);
    	this.kullaniciAdi.setText(kullaniciAdi);
    	this.sifre.setText(sifre);
    	this.yetki.setValue(yetki);
    	
    }

    @FXML
    void duzenleButton_Action(ActionEvent event) {
       	sql="update personel set Ad=?,Soyad=?,TCKN=?,Tel=?,DogumTarihi=?,Email=?,Statu=?,KullaniciAdi=?,Sifre=?,Yetki=? where idPersonel="+id;
    	try {
			ps=conn.prepareStatement(sql);
			ps.setString(1,ad.getText().trim());
			ps.setString(2,soyad.getText().trim());
			ps.setString(3,tckn.getText().trim());
			ps.setString(4,telNo.getText().trim());
			ps.setDate(5,Date.valueOf(dogumTarihi.getValue()));
			ps.setString(6,email.getText().trim());
			ps.setString(7,statu.getText().trim());
			ps.setString(8,kullaniciAdi.getText().trim());
			ps.setString(9,sifre.getText().trim());
			ps.setString(10,yetki.getSelectionModel().getSelectedItem().toString().trim());
			ps.executeUpdate();
			
        	Alert alert=new Alert(AlertType.INFORMATION);
        	alert.setTitle("Otel Sistemi");
        	alert.setHeaderText("İşlem Başarılı");
        	alert.setContentText("Başarılı bir şekilde güncellenmiştir. Yenilikleri görmek için tabloyu yenilemeyi unutmayın.");
        	alert.showAndWait();
        	
			Stage personel_duzenle=(Stage)AnchorPane_Duzenle.getScene().getWindow();
			personel_duzenle.close();

		} catch (Exception e) {
        	Alert alert=new Alert(AlertType.ERROR);
        	alert.setTitle("Otel Sistemi");
        	alert.setHeaderText("Hata Mesajı");
        	alert.setContentText("Boş Alan Bırakmayınız.");
        	alert.showAndWait();
		}
    }
    MusteriEkleController controller=new MusteriEkleController();
PersonelEkleController controller2=new PersonelEkleController();
    @FXML
    void initialize() {
    	duzenleButton.setStyle("-fx-background-color: #DBFAA1;");
    	
    	yetki.getItems().addAll("Admin","Personel");
    	controller.sadeceHarf(ad, 2, 20);
    	controller.sadeceHarf(soyad, 2, 20);
    	controller.sadeceHarf(statu, 5, 40);
    	controller2.rakamHarf(sifre);
    	controller.sadeceRakam(tckn, 11, 11);
    	controller.sadeceRakam(telNo, 11, 11);
    	controller2.rakamHarf(kullaniciAdi);
    	controller.datePicker(dogumTarihi);
    	
    	

    }

}
