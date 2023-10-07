package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

import BusinessLayer.Icons;
import Models.MI_Kayitlar;
import Models.VeriTabani;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CikisIslemleriController implements ICont {
	public CikisIslemleriController(){
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
    private AnchorPane RS_Anc;

    @FXML
    private TableColumn<MI_Kayitlar,String> ad;


    @FXML
    private TextField araAd;

    @FXML
    private DatePicker araCikis;

    @FXML
    private DatePicker araGiris;

    @FXML
    private TableColumn<MI_Kayitlar,Date> cikisTarihi;

    @FXML
    private TableColumn<MI_Kayitlar,Button> degistir;

    @FXML
    private TableColumn<MI_Kayitlar,Date> dogumTarihi;

    @FXML
    private TableColumn<MI_Kayitlar,String> durum;
    
    @FXML
    private TableColumn<MI_Kayitlar,String> oda;

    @FXML
    private TableColumn<MI_Kayitlar,Button> duzenle;

    @FXML
    private TableColumn<MI_Kayitlar,Date> girisTarihi;

    @FXML
    private TableColumn<MI_Kayitlar,Integer> id;

    @FXML
    private TableColumn<MI_Kayitlar,Button> sil;

    @FXML
    private TableColumn<MI_Kayitlar,String> soyad;

    @FXML
    private TableView<MI_Kayitlar> tableView;

    @FXML
    private TableColumn<MI_Kayitlar,String> tckn;

    @FXML
    private TableColumn<MI_Kayitlar,String> telNo;
    
    @FXML
    private TableColumn<MI_Kayitlar,String> fiyat;
    
    @FXML
    private TableColumn<MI_Kayitlar,Button> cikis_yap;

    @FXML
    private Button yenile;


	@Override
	public void open(String fxml) {

    		try {
    			Parent root1=FXMLLoader.load(getClass().getResource(fxml));
    			Stage mainPage=new Stage();
    			mainPage.setScene(new Scene(root1));
    			mainPage.show();
    		} catch (Exception e) {}
	}
	 
    

    @FXML
    void yenile_action(ActionEvent event) {
    	buttonActions();
		tableSet();

    }
    
public boolean olur;
public boolean olur2;
Icons icon=new Icons();
    @FXML
    void initialize() {
    	
    	yenile.setId("button-blue");
    	
    	buttonActions();
    	tableSet();
    	
    	
    	
    	icon.setIcon("refresh.png", 35, 35, yenile);
	
	
    araAd.textProperty().addListener((observable, oldValue, newValue) -> {
        tableListele(newValue);
    });
    
	  araGiris.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
  		  if(!newValue.isEmpty()) {
  			  olur=true;
  			  }
  			if(olur==true&&olur2==true) {
  		    	String sql="SELECT m.idMusteri,m.Ad,m.Soyad,m.TCKN,m.telNo,m.DogumTarihi,mo.girisTarih,mo.cikisTarih,mo.durum,o.OdaNumarasi,mo.toplam_fiyat\r\n"
  		    			+ "FROM oda AS o\r\n"
  		    			+ "INNER JOIN musteri_oda AS mo ON o.idOda = mo.idOda\r\n"
  		    			+ "INNER JOIN musteri AS m ON mo.idMusteri = m.idMusteri\r\n"
  		    			+ "where (girisTarih>=?) and (cikisTarih<=?)";
  				tableSet2(sql);
  				araGiris.setStyle("-fx-background-color: green;");
  				araCikis.setStyle("-fx-background-color: green;");
  			}
  			else {
  				araGiris.setStyle("-fx-background-color: blue;");
  			}

  	  });
  	  
		araCikis.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue.isEmpty()) {
	  			  olur2=true;
	  			  }
	    		if(olur==true&&olur2==true) {
	    	    	String sql1="SELECT m.idMusteri,m.Ad,m.Soyad,m.TCKN,m.telNo,m.DogumTarihi,mo.girisTarih,mo.cikisTarih,mo.durum,o.OdaNumarasi,mo.toplam_fiyat\r\n"
	  		    			+ "FROM oda AS o\r\n"
	  		    			+ "INNER JOIN musteri_oda AS mo ON o.idOda = mo.idOda\r\n"
	  		    			+ "INNER JOIN musteri AS m ON mo.idMusteri = m.idMusteri\r\n"
	  		    			+ "where (girisTarih>=?) and (cikisTarih<=?)";
	    	    	tableSet2(sql1);
	    	    	araCikis.setStyle("-fx-background-color: green;");
	    	    	araGiris.setStyle("-fx-background-color: green;");
	  			}
	  			else {
	  				araCikis.setStyle("-fx-background-color: blue;");
	  			}
	    		
	 	    });

    }
    
	int row;
    Button[] cikisYap=new Button[50];
	int cikisYapButonSayisi=0;

	
	AdminMainController controller=new AdminMainController();
	
    public void buttonActions() {

    	
    	for (int i = 0; i <cikisYap.length; i++) {

    		cikisYap[i]=new Button();
    		cikisYap[i].setText("Çıkış Yap");
    		cikisYap[i].setPrefWidth(120);
    		icon.setIcon("checkout.png", 20, 20, cikisYap[i]);
	
			cikisYap[i].setId("cikisYap");
			cikisYap[i].setOnAction(arg0  -> {
				try {
					cikisYapButtonAction(arg0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			});}
    	
    }

    
    @FXML
    void cikisYapButtonAction(ActionEvent event) throws IOException {
    	for (int i = 0; i < cikisYap.length; i++) {
    		
    		if(event.getSource()==cikisYap[i]) {
    			
    			cikis_yap.getTableView().getSelectionModel().select(i);
    			
    	    	if(tableView.getSelectionModel().isEmpty()==false) {
    	        	Alert alert=new Alert(AlertType.CONFIRMATION);
        	    	alert.setTitle("Uyarı Mesajı");
        	    	alert.setHeaderText("Çıkış işlemi");
        	    	alert.setContentText("Çıkış işlemi yapmak istediğinizden emin misiniz?");
        	    	
        	       	ButtonType btn1=new ButtonType("Evet",ButtonData.OK_DONE);
        	    	ButtonType btn3=new ButtonType("İptal",ButtonData.CANCEL_CLOSE);
        	    	
        	    	alert.getButtonTypes().clear();
        	    	alert.getButtonTypes().addAll(btn1,btn3);
        	    	Optional<ButtonType>text2=alert.showAndWait();
        	    	
        	    	if(btn1==text2.get()) 
        	    	{
        	        	sql="update musteri as m INNER JOIN musteri_oda as mo ON m.idMusteri = mo.idMusteri set durum='Çıkış Yapıldı' where mo.idMusteri="+tableView.getSelectionModel().getSelectedItem().getId();
        		    	try 
        		    	{
        		    		ps=conn.prepareStatement(sql);
        					ps.executeUpdate(sql);
        					
        		        	Alert alert1=new Alert(AlertType.INFORMATION);
        		        	alert1.setTitle("Otel Sistemi");
        		        	alert1.setHeaderText("İşlem Başarılı");
        		        	alert1.setContentText("Çıkış İşlemi Başarılı.");
        		        	alert1.showAndWait();
        		        	
        					tableSet();
        					
        				} 
        		    	catch (Exception e) 
        		    	{
        		           	Alert alert1=new Alert(AlertType.ERROR);
        		        	alert1.setTitle("Otel Sistemi");
        		        	alert1.setHeaderText("Hata Mesajı");
        		        	alert1.setContentText("Çıkış Yapılamıyor");
        		        	alert1.showAndWait();
        				}

        	    	}
        	    	else 
        	    	{
        	    	}
    	    	}
    	    	else {
    	        	Alert alert1=new Alert(AlertType.WARNING);
    	        	alert1.setTitle("Otel Sistemi");
    	        	alert1.setHeaderText("Hata Mesajı");
    	        	alert1.setContentText("Çıkış yapmak istediğiniz kişinin bilgilerinin olduğu satırı seçiniz.");
    	        	alert1.showAndWait();
    	    	}
    		}
		}
    }
    

    
    public void tableSet() {
    	
    	cikisYapButonSayisi=0;
    	
    	ObservableList<MI_Kayitlar>liste=FXCollections.observableArrayList();
    	String sql1="SELECT m.idMusteri,m.Ad,m.Soyad,m.TCKN,m.telNo,m.DogumTarihi,mo.girisTarih,mo.cikisTarih,mo.durum,o.OdaNumarasi,mo.toplam_fiyat\r\n"
    			+ "FROM oda AS o\r\n"
    			+ "INNER JOIN musteri_oda AS mo ON o.idOda = mo.idOda\r\n"
    			+ "INNER JOIN musteri AS m ON mo.idMusteri = m.idMusteri\r\n"
    			+ "WHERE m.idMusteri=mo.idMusteri";
    	try {
			ps=conn.prepareStatement(sql1);
			rs=ps.executeQuery();
			while (rs.next()) {
				liste.add(new MI_Kayitlar(
						rs.getInt("idMusteri"),
						rs.getString("Ad"),
						rs.getString("Soyad"),
						rs.getString("TCKN"),
						rs.getString("telNo"),
						rs.getDate("DogumTarihi"),
						rs.getDate("girisTarih"),
						rs.getDate("cikisTarih"),
						rs.getString("durum"),
						rs.getString("OdaNumarasi"),
						rs.getString("toplam_fiyat"),
						cikisYap[cikisYapButonSayisi++]
						));
				}
			
			id.setCellValueFactory(new PropertyValueFactory<>("id"));
			ad.setCellValueFactory(new PropertyValueFactory<>("ad"));
			soyad.setCellValueFactory(new PropertyValueFactory<>("soyad"));
			tckn.setCellValueFactory(new PropertyValueFactory<>("tckn"));
			telNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));
			dogumTarihi.setCellValueFactory(new PropertyValueFactory<>("dogumTarihi"));
			durum.setCellValueFactory(new PropertyValueFactory<>("durum"));
			girisTarihi.setCellValueFactory(new PropertyValueFactory<>("girisTarihi"));
			cikisTarihi.setCellValueFactory(new PropertyValueFactory<>("cikisTarihi"));
			oda.setCellValueFactory(new PropertyValueFactory<>("oda"));
			fiyat.setCellValueFactory(new PropertyValueFactory<>("fiyat"));
			cikis_yap.setCellValueFactory(new PropertyValueFactory<>("cikisYap"));
			
			
			tableView.setItems(liste);
		} catch (Exception e) {
			e.printStackTrace();
		}


    }
    
    public void tableSet2(String sql) {
    	
    	cikisYapButonSayisi=0;

    	
    	ObservableList<MI_Kayitlar>liste=FXCollections.observableArrayList();

    	try {
			ps=conn.prepareStatement(sql);
			
  			ps.setDate(1,Date.valueOf(araGiris.getValue()));
  			ps.setDate(2,Date.valueOf(araCikis.getValue()));
 
  			
			rs=ps.executeQuery();
			while (rs.next()) {
				liste.add(new MI_Kayitlar(
						rs.getInt("idMusteri"),
						rs.getString("Ad"),
						rs.getString("Soyad"),
						rs.getString("TCKN"),
						rs.getString("telNo"),
						rs.getDate("DogumTarihi"),
						rs.getDate("girisTarih"),
						rs.getDate("cikisTarih"),
						rs.getString("durum"),
						rs.getString("OdaNumarasi"),
						rs.getString("toplam_fiyat"),
						cikisYap[cikisYapButonSayisi++]
						));
				}
			
			id.setCellValueFactory(new PropertyValueFactory<>("id"));
			ad.setCellValueFactory(new PropertyValueFactory<>("ad"));
			soyad.setCellValueFactory(new PropertyValueFactory<>("soyad"));
			tckn.setCellValueFactory(new PropertyValueFactory<>("tckn"));
			telNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));
			dogumTarihi.setCellValueFactory(new PropertyValueFactory<>("dogumTarihi"));
			durum.setCellValueFactory(new PropertyValueFactory<>("durum"));
			girisTarihi.setCellValueFactory(new PropertyValueFactory<>("girisTarihi"));
			cikisTarihi.setCellValueFactory(new PropertyValueFactory<>("cikisTarihi"));
			oda.setCellValueFactory(new PropertyValueFactory<>("oda"));
			fiyat.setCellValueFactory(new PropertyValueFactory<>("fiyat"));
			cikis_yap.setCellValueFactory(new PropertyValueFactory<>("cikisYap"));
			
			
			tableView.setItems(liste);
		} catch (Exception e) {
			e.printStackTrace();
		}


    }
    
    public void tableListele(String newValue) {
    	
    	cikisYapButonSayisi=0;
    	
    	ObservableList<MI_Kayitlar>liste=FXCollections.observableArrayList();
    	sql="SELECT m.idMusteri,m.Ad,m.Soyad,m.TCKN,m.telNo,m.DogumTarihi,mo.girisTarih,mo.cikisTarih,mo.durum,o.OdaNumarasi,mo.toplam_fiyat\r\n"
    			+ "FROM oda AS o\r\n"
    			+ "INNER JOIN musteri_oda AS mo ON o.idOda = mo.idOda\r\n"
    			+ "INNER JOIN musteri AS m ON mo.idMusteri = m.idMusteri\r\n"
    			+ "WHERE m.Ad like '%"+newValue+"%'";

    	try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while (rs.next()) {
				liste.add(new MI_Kayitlar(
						rs.getInt("idMusteri"),
						rs.getString("Ad"),
						rs.getString("Soyad"),
						rs.getString("TCKN"),
						rs.getString("telNo"),
						rs.getDate("DogumTarihi"),
						rs.getDate("girisTarih"),
						rs.getDate("cikisTarih"),
						rs.getString("durum"),
						rs.getString("OdaNumarasi"),
						rs.getString("toplam_fiyat"),
						cikisYap[cikisYapButonSayisi++]
						));
				}
			
			id.setCellValueFactory(new PropertyValueFactory<>("id"));
			ad.setCellValueFactory(new PropertyValueFactory<>("ad"));
			soyad.setCellValueFactory(new PropertyValueFactory<>("soyad"));
			tckn.setCellValueFactory(new PropertyValueFactory<>("tckn"));
			telNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));
			dogumTarihi.setCellValueFactory(new PropertyValueFactory<>("dogumTarihi"));
			durum.setCellValueFactory(new PropertyValueFactory<>("durum"));
			girisTarihi.setCellValueFactory(new PropertyValueFactory<>("girisTarihi"));
			cikisTarihi.setCellValueFactory(new PropertyValueFactory<>("cikisTarihi"));
			oda.setCellValueFactory(new PropertyValueFactory<>("oda"));
			fiyat.setCellValueFactory(new PropertyValueFactory<>("fiyat"));
			cikis_yap.setCellValueFactory(new PropertyValueFactory<>("cikisYap"));
			
			
			tableView.setItems(liste);
		} catch (Exception e) {
			e.printStackTrace();
		}


    }
    

    
    

	



}
