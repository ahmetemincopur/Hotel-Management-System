package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MusteriEkleController {
	public MusteriEkleController(){
		conn=VeriTabani.Connect();
	}
	
    Connection conn=null;
    PreparedStatement ps=null;
    ResultSet rs=null;
    PreparedStatement ps2=null;
    ResultSet rs2=null;
    PreparedStatement ps3=null;
    ResultSet rs3=null;
    String sql=null;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane AnchorPane_Ekle;

    @FXML
    private TextField ad;

    @FXML
    private DatePicker cikisTarih;

    @FXML
    private DatePicker dogumTarihi;
    
    @FXML
    private Label fiyat_label;

    @FXML
    private ChoiceBox<String> durum;

    @FXML
    private Button ekleButton;

    @FXML
    private DatePicker girisTarih;

    @FXML
    private ChoiceBox<String> oda;

    @FXML
    private TextField soyad;

    @FXML
    private TextField tckn;

    @FXML
    private TextField telNo;
    
    // textfield veri girişini dinleyerek rakam dışında girilen değerler silindi & kontrollerle rakam sayısı parametrelerle kısıtlandı
    public void sadeceRakam(TextField textField,int enAz,int enFazla) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if(newValue.length()<enAz) {
            	textField.setStyle("-fx-background-color: red;");
            }
         else {
	        textField.setStyle("-fx-background-color: white;");
	    }

            if (newValue.length() > enFazla) {
                textField.setText(oldValue);
            }
        });
    }
    
    
    // Harf dışında girilen değerler silindi & kontrollerle harf sayısı parametrelerle kısıtlandı
    public void sadeceHarf(TextField textField,int enAz,int enFazla) {
    	  textField.textProperty().addListener((observable, oldValue, newValue) -> {
              String input = textField.getText();
              if (!input.matches("[a-zA-Z]")) {
                  textField.setText(newValue.replaceAll("[^a-zA-Z]", ""));
              }
              if(newValue.length()<enAz) {
            	  textField.setStyle("-fx-background-color: red;");
              }
              else {
      	        textField.setStyle("-fx-background-color: white;");
      	    }

              if (newValue.length() > enFazla) {
                  textField.setText(oldValue);
              }
          });
    }
    
    // DatePickerın değer aldığında kenarlarının mavi olması sağlandı
    public void datePicker(DatePicker datePicker) {
    	datePicker.setEditable(false);
    	datePicker.setStyle("-fx-background-color: red;");
    	datePicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
    	    if (!newValue.isEmpty()) {
    	    	datePicker.setStyle("-fx-background-color: blue;");
    	    }
    	});
    }
    
    public LocalDate start;
    public LocalDate end;
    
    public long gun_sayisi;
    private String fiyat;
    
	private String toplam_fiyat;
    
	public boolean olur;
	public boolean olur2;
	
    public void datePicker(DatePicker datePicker,DatePicker datePicker2) {
    	
    	//LocalDate'ten date e veri yazdırmak için tarih formatı kuruldu
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	
    	// 2 Tarih arasında dolu olmayan odaları listeleyen sql sorgusu
    	String sql1="SELECT DISTINCT o.OdaNumarasi\r\n"
    			+ "FROM oda AS o\r\n"
    			+ "INNER JOIN musteri_oda AS mo ON mo.idOda = o.idOda\r\n"
    			+ "WHERE NOT EXISTS (\r\n"
    			+ "    SELECT 1\r\n"
    			+ "    FROM musteri_oda AS mo2\r\n"
    			+ "    WHERE mo2.idOda = mo.idOda\r\n"
    			+ "      AND (\r\n"
    			+ "          (mo2.girisTarih <= ? AND mo2.cikisTarih >= ?)\r\n"
    			+ "          OR (mo2.girisTarih <= ? AND mo2.cikisTarih >= ?)\r\n"
    			+ "          OR (mo2.girisTarih >= ? AND mo2.cikisTarih <= ?)\r\n"
    			+ "      )\r\n"
    			+ ")";
    	
    	// DatePickera yazılabilirliği kapatıldı ve arkaplan renkleri kırmızı yapıldı
    	datePicker.setEditable(false);
    	datePicker.setStyle("-fx-background-color: red;");
	    datePicker2.setEditable(false);
    	datePicker2.setStyle("-fx-background-color: red;");
    	
    	//2 datepickerın daha uyumlu olarak tarihlere göre oda verilerini getirebilmesi için ayrı listenerlarla 
    	//sadece 2 datepickera veri girişi olduğu zaman odaları getiren sorguyu çalıştırır
  	  	datePicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
  		  if(!newValue.isEmpty()) {

  			  olur=true;
  			  start=LocalDate.parse(newValue,formatter);
  			  }
  		  	
  			if(olur==true&&olur2==true) {
  				tableSet2(sql1);
  			}
  			else {
  				datePicker.setStyle("-fx-background-color: blue;");
  			}

  	  });
  	  
		datePicker2.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.isEmpty()) {
	  			  olur2=true;
	  			  end=LocalDate.parse(newValue,formatter);
	  			  }
	    		if(olur==true&&olur2==true) {
	  				tableSet2(sql1);
	
	  			}
	  			else {
	  				datePicker2.setStyle("-fx-background-color: green;");
	  			}
	    		
	 	    });
		
		// Tarihler girildikten sonra müsait olan odaları seçebiliyoruz
		// 2 Tarih arasındaki gün sayısını getiren fonksiyon çağrıldı
		// Seçilen odanın fiyatını alıp tableSet3()'ten elde ettiğimiz gün sayısıyla çarpıp toplam fiyat değeri label'a atandı
		oda.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		   	gunSay(start, end);
		    if (newValue != null) {
		    		
		    	   	String sql3="select fiyat from oda where OdaNumarasi="+newValue;
		    
						tableSet3(sql3);
	  					long double_fiyat=Long.parseLong(fiyat);
	  					long toplam=double_fiyat*gun_sayisi;
	  					
	  					fiyat_label.setText(Double.toString(toplam));
	  					toplam_fiyat=fiyat_label.getText().toString().trim();
		    } else {
		    	fiyat_label.setText("");
		    }
		});
		


    }
    
    int odaID;
 // OdaNumarasına göre id'sini bulan fonksiyon yapısı
    void getOdaID() {
    	
      	ObservableList<OB_Kayitlar>liste=FXCollections.observableArrayList();
        String selectedOdaNumarasi = oda.getSelectionModel().getSelectedItem();

        sql = "SELECT idOda FROM otelotomasyonu.oda WHERE OdaNumarasi = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, selectedOdaNumarasi);
            rs = ps.executeQuery();
            while (rs.next()) {
                liste.add(new OB_Kayitlar(rs.getInt("idOda")));
            }
            int id = liste.get(0).getId();
            this.odaID = id;
		} catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Otel Sistemi");
            alert.setHeaderText("Hata Mesajı");
            alert.setContentText("Oda Numarası Bulunamadı.");
            alert.showAndWait();
		}
    }

    @FXML
    void ekleButton_Action(ActionEvent event) {

		 //kontrollerle textfield ve datepicklerların kontrolleri sağlandı
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
    		//seçilen odanın id'si alındı
    		getOdaID();
    		
    		//seçilen odanın id'sine göre giris ve çıkış tarihlerinde odanın müsait olup olmadığını anlamak için değer verip vermediğine bakıldı
    		String sql =  "SELECT count(*) FROM otelotomasyonu.musteri as m "
    				+ "INNER JOIN otelotomasyonu.musteri_oda as mo ON m.idMusteri = mo.idMusteri "
    				+ "where ((mo.girisTarih between ? and ?) or (mo.cikisTarih between ? and ?)) and mo.idOda="+odaID;
    	  	try {
    			ps=conn.prepareStatement(sql);
    			ps.setDate(1,Date.valueOf(girisTarih.getValue()));
    			ps.setDate(2,Date.valueOf(cikisTarih.getValue()));
    			ps.setDate(3,Date.valueOf(girisTarih.getValue()));
    			ps.setDate(4,Date.valueOf(cikisTarih.getValue()));
    			
    			ps.executeQuery();
    			rs=ps.executeQuery();
    			  if(rs.next()) {
    				  if(rs.getInt(1)>0) {
    				  }
    				  else {
    					  arttir();
  			        }
    			  }
    		} catch (Exception e) {
    			e.printStackTrace();
    			Alert alert=new Alert(AlertType.ERROR);
            	alert.setTitle("Otel Sistemi");
            	alert.setHeaderText("Hata Mesajı");
            	alert.setContentText("yoyoyo.");
            	alert.showAndWait();
    		}
    	}
    }
    
int id_musteri;

    void arttir() {
    	//sadece müşteri tablo verileri girildi
    	  String sql1="insert into musteri(Ad,Soyad,TCKN,DogumTarihi,telNo) values(?,?,?,?,?)";
    	  //en son eklenen müşteri tablo verisinin id'si alındı
    	  String sql2="SELECT *\r\n"
    	  		+ "FROM musteri\r\n"
    	  		+ "ORDER BY idMusteri DESC\r\n"
    	  		+ "LIMIT 1";
    	  //Alınan müşteri id ile oda id'ler diğer verilerle musteri_oda tablosuna eklendi 
    	  String sql3="insert into musteri_oda(idMusteri,idOda,girisTarih,cikisTarih,durum,toplam_fiyat) values(?,?,?,?,?,"+toplam_fiyat+")";
    	  
      	try {
  			ps=conn.prepareStatement(sql1);
  			ps2=conn.prepareStatement(sql2);
  			ps3=conn.prepareStatement(sql3);
  			
  			ps.setString(1,ad.getText().trim());
  			ps.setString(2,soyad.getText().trim());
  			ps.setString(3,tckn.getText().trim());
  			ps.setDate(4,Date.valueOf(dogumTarihi.getValue()));
  			ps.setString(5,telNo.getText().trim());
  			
  			//ilk sorgu bitti
  			ps.executeUpdate();
  			rs=ps2.executeQuery();
  			
  			//2.sorgu
  			rs.next();
  			id_musteri=rs.getInt("idMusteri");
  			
 
  			//3.sorgu
  			ps3.setInt(1,id_musteri);
			ps3.setInt(2,odaID);
  			ps3.setDate(3,Date.valueOf(girisTarih.getValue()));
  			ps3.setDate(4,Date.valueOf(cikisTarih.getValue()));
  			ps3.setString(5,durum.getSelectionModel().getSelectedItem().toString().trim());
  
  			
  			ps3.executeUpdate();
  			
  			//başarılı olduğuna dair bilgilendirme
          	Alert alert=new Alert(AlertType.INFORMATION);
          	alert.setTitle("Otel Sistemi");
          	alert.setHeaderText("İşlem Mesajı");
          	alert.setContentText("Başarıyla eklendi. Sonuçları görmek için tabloyu yenilemeyi unutmayın.");
          	alert.showAndWait();
          	
  			Stage login=(Stage)AnchorPane_Ekle.getScene().getWindow();
  			login.close();

  		} catch (Exception e) {
  			//hata mesajı
          	Alert alert=new Alert(AlertType.ERROR);
          	alert.setTitle("Otel Sistemi");
          	alert.setHeaderText("Hata Mesajı");
          	alert.setContentText("Boş yer bırakmayınız.");
          	alert.showAndWait();
  		}
    }
    @FXML
    void initialize() {
    	//sadece odaları getirdi
    	tableSet();
    	//durum choicebox'ına otomatik olarak rezerve değeri atandı
    	durum.setValue("Rezerve");
    	
    	//kısıtlamalar uygulandı
    	sadeceRakam(tckn,11,11);
    	sadeceRakam(telNo,11,11);
    	sadeceHarf(ad, 2,20);
    	sadeceHarf(soyad, 2,20);
    	datePicker(dogumTarihi);
    	datePicker(girisTarih,cikisTarih);
    	
    	ekleButton.setId("ekle-button");
    }
    
    //Tarihleri girmeden önce bütün odaları getiriyorum
    public void tableSet() {
    	ObservableList<OB_Kayitlar>liste=FXCollections.observableArrayList();
    	sql="SELECT OdaNumarasi FROM oda";
    	try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while (rs.next()) {
				liste.add(new OB_Kayitlar(
						rs.getString("OdaNumarasi")
						));
				}
			
			 ObservableList<String> odaNumaralari = FXCollections.observableArrayList();
		        for (OB_Kayitlar kayit : liste) {
		            odaNumaralari.addAll(kayit.getOdaNumarasi());
		        }
			

		oda.setItems(odaNumaralari);
		oda.setValue(odaNumaralari.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}


    }
    
    //Tarihlere göre odaları getirme
    public void tableSet2(String sql) {
    	ObservableList<OB_Kayitlar>liste=FXCollections.observableArrayList();
    	try {
			ps=conn.prepareStatement(sql);
			
  			ps.setDate(2,Date.valueOf(girisTarih.getValue()));
  			ps.setDate(1,Date.valueOf(cikisTarih.getValue()));
  			ps.setDate(3,Date.valueOf(girisTarih.getValue()));
  			ps.setDate(4,Date.valueOf(cikisTarih.getValue()));
  			ps.setDate(5,Date.valueOf(girisTarih.getValue()));
  			ps.setDate(6,Date.valueOf(cikisTarih.getValue()));
			
			
			rs=ps.executeQuery();
			while (rs.next()) {
				liste.add(new OB_Kayitlar(
						rs.getString("OdaNumarasi")
						));
				}
			
			 ObservableList<String> odaNumaralari = FXCollections.observableArrayList();
		        for (OB_Kayitlar kayit : liste) {
		            odaNumaralari.addAll(kayit.getOdaNumarasi());
		        }
			

		oda.setItems(odaNumaralari);
		oda.setValue(odaNumaralari.get(0));
		} catch (Exception e) {
			Alert alert=new Alert(AlertType.ERROR);
          	alert.setTitle("Otel Sistemi");
          	alert.setHeaderText("Hata Mesajı");
          	alert.setContentText("Tarihleri gözden geçirmeyi unutmayınız.");
          	alert.showAndWait();
		}


    }
    
    //oda ve güne göre fiyat
    public void tableSet3(String sql) {
    	try {
			ps=conn.prepareStatement(sql);
			

			
			rs=ps.executeQuery();
			rs.next();
			fiyat=rs.getString("Fiyat");



    	} catch (Exception e) {
			Alert alert=new Alert(AlertType.ERROR);
          	alert.setTitle("Otel Sistemi");
          	alert.setHeaderText("Hata Mesajı");
          	alert.setContentText("Tarihleri gözden geçirmeyi unutmayınız.");
          	alert.showAndWait();
		}


    }
    
    //2 Tarih arasında kaç gün olduğunu bul
    private void gunSay(LocalDate inDate, LocalDate outDate) {
        if (inDate != null && outDate != null) {
            long gunler = Duration.between(inDate.atStartOfDay(), outDate.atStartOfDay()).toDays();
           
            long gunSayisi = Math.abs(gunler);
            gun_sayisi=gunSayisi;
        }
    }
}
