package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class PersonelEkleController  {
	public PersonelEkleController(){
		conn=VeriTabani.Connect();
	}
	
    Connection conn=null;
    PreparedStatement ps=null;
    ResultSet rs=null;
    String sql=null;

    @FXML
    private TextField email;
    @FXML
    private TextField kullaniciAdi;
    @FXML
    private TextField sifre;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private AnchorPane AnchorPane_Ekle;
    @FXML
    private TextField ad;    
    @FXML
    private ChoiceBox<String> yetki;
    @FXML
    private DatePicker dogumTarihi;
    @FXML
    private Button ekleButton;
    @FXML
    private TextField soyad;
    @FXML
    private TextField statu;
    @FXML
    private TextField tckn;
    @FXML
    private TextField telNo;

    @FXML
    void ekleButton_Action(ActionEvent event) {
       	sql="insert into personel(Ad,Soyad,TCKN,Tel,DogumTarihi,Email,Statu,KullaniciAdi,Sifre,Yetki) values(?,?,?,?,?,?,?,?,?,?)";
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
        	alert.setHeaderText("İşlem Mesajı");
        	alert.setContentText("Başarıyla eklendi. Sonuçları görmek için tabloyu yenilemeyi unutmayın.");
        	alert.showAndWait();
        	
			Stage login=(Stage)AnchorPane_Ekle.getScene().getWindow();
			login.close();

		} catch (Exception e) {
        	Alert alert=new Alert(AlertType.ERROR);
        	alert.setTitle("Otel Sistemi");
        	alert.setHeaderText("Hata Mesajı");
        	alert.setContentText("Tarih Giriniz.");
        	alert.showAndWait();
		}
    	
    }
MusteriEkleController controller=new MusteriEkleController();
    @FXML
    void initialize() {
    	yetki.getItems().addAll("Admin","Personel");
    	yetki.setValue("Personel");
    	controller.sadeceHarf(ad, 2, 20);
    	controller.sadeceHarf(soyad, 2, 20);
    	controller.sadeceHarf(statu, 5, 40);
    	controller.sadeceRakam(tckn, 11, 11);
    	controller.sadeceRakam(telNo, 11, 11);
    	rakamHarf(sifre);
    	rakamHarf(kullaniciAdi);
    	controller.datePicker(dogumTarihi);
    	
    	ekleButton.setStyle("-fx-background-color: #00FF94;");
    }
    
    // textfield içinde Rakam ve harfler dışında hiçbir işareti almayan yapı
public void rakamHarf(TextField textField) {
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("[a-zA-Z0-9]*")) {
            textField.setText(newValue.replaceAll("[^a-zA-Z0-9]", ""));
        }
    });
}
}
