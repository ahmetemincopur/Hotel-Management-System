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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MusteriDuzenleController {
	public MusteriDuzenleController() {
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
    private AnchorPane AnchorPane_Duzenle;

    @FXML
    private TextField ad;

    @FXML
    private DatePicker cikisTarih;

    @FXML
    private DatePicker dogumTarihi;

    @FXML
    private Button duzenle;

    @FXML
    private DatePicker girisTarih;

    @FXML
    private TextField soyad;

    @FXML
    private TextField tckn;

    @FXML
    private TextField telNo;
 
   
//tabloda seçilen verileri burdaki değişkenlere atıyoruz
    private int id;
    public void setData(int id,String ad,String soyad,String TCKN,String telNo,LocalDate dogumTarihi,LocalDate girisTarihi,LocalDate cikisTarihi) {
    	this.id=id;
    	this.ad.setText(ad);
    	this.soyad.setText(soyad);
    	this.tckn.setText(TCKN);
    	this.telNo.setText(telNo);
    	this.dogumTarihi.setValue(dogumTarihi);
    	this.girisTarih.setValue(girisTarihi);
    	this.cikisTarih.setValue(cikisTarihi);
    	
    }
//2
    @FXML
    void initialize() {
    	duzenle.setStyle("-fx-background-color: #DBFAA1;");
    	MusteriEkleController controller=new MusteriEkleController();
    	//textfield ve datepicker kısıtlamaları uygulandı
    	controller.sadeceHarf(ad, 2, 20);
    	controller.sadeceHarf(soyad, 2, 20);
    	controller.sadeceRakam(tckn, 11, 11);
    	controller.sadeceRakam(telNo, 11, 11);
    	controller.datePicker(dogumTarihi);
    	controller.datePicker(girisTarih);
    	controller.datePicker(cikisTarih);
    }

  //butona basıldığında kontrolleri sağlayıp musteri ve musteri_oda tablolarını güncelleyen yapı kuruldu
    @FXML
    void duzenle_Action(ActionEvent event) {	 
    	if(
    			ad.getText().length()<2||
    			soyad.getText().length()<2||
    			dogumTarihi.getValue().toString().isEmpty()==true||
    			cikisTarih.getValue().toString().isEmpty()==true||
    			girisTarih.getValue().toString().isEmpty()==true||
    			tckn.getText().length()!=11||
    			telNo.getText().length()!=11
    	) {
    		Alert alert=new Alert(AlertType.INFORMATION);
        	alert.setTitle("Otel Sistemi");
        	alert.setHeaderText("İşlem Hatası.");
        	alert.setContentText("Kırmızı alanları ve tarihleri doldurmayı unutmayın.");
        	alert.showAndWait();
    	}
    	else {
           	sql="update musteri as m INNER JOIN musteri_oda as mo ON m.idMusteri = mo.idMusteri set Ad=?,Soyad=?,TCKN=?,DogumTarihi=?,telNo=?,girisTarih=?,cikisTarih=? where mo.idMusteri="+id;
        	try {
    			ps=conn.prepareStatement(sql);
    			ps.setString(1,ad.getText().trim());
    			ps.setString(2,soyad.getText().trim());
    			ps.setString(3,tckn.getText().trim());
    			ps.setDate(4,Date.valueOf(dogumTarihi.getValue()));
    			ps.setString(5,telNo.getText().trim());
    			ps.setDate(6,Date.valueOf(girisTarih.getValue()));
    			ps.setDate(7,Date.valueOf(cikisTarih.getValue()));

    			ps.executeUpdate();
    			
            	Alert alert=new Alert(AlertType.INFORMATION);
            	alert.setTitle("Otel Sistemi");
            	alert.setHeaderText("İşlem Başarılı");
            	alert.setContentText("Başarılı bir şekilde güncellenmiştir. Yenilikleri görmek için tabloyu yenilemeyi unutmayın.");
            	alert.showAndWait();
            	
            	//işlem başarılı olduğunda sayfayı kapat
    			Stage login=(Stage)AnchorPane_Duzenle.getScene().getWindow();
    			login.close();

    		} catch (Exception e) {
            	Alert alert=new Alert(AlertType.ERROR);
            	alert.setTitle("Otel Sistemi");
            	alert.setHeaderText("Hata Mesajı");
            	alert.setContentText("Boş Alan Bırakmayınız.");
            	alert.showAndWait();
    		}
    	}

    }
   
}
