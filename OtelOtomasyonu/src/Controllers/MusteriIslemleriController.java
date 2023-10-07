package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MusteriIslemleriController implements ICont {
	public MusteriIslemleriController(){
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
    private Button ekle;

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
    void ekle_action(ActionEvent event) {
    	open("/FXML/MusteriEkle.fxml");
    }

    @FXML
    void yenile_action(ActionEvent event) {
    	buttonActions();
		tableSet();

    }
    
public boolean olur;
public boolean olur2;
    @FXML
    void initialize() {
    	buttonActions();
    	tableSet();
    	
    	
    	yenile.setId("button-blue");
    	ekle.setId("ekle-button");
    	
    	icon.setIcon("add-user.png", 35, 35, ekle);
    	icon.setIcon("refresh.png", 35, 35, yenile);
	
    araAd.textProperty().addListener((observable, oldValue, newValue) -> {
        tableListele(newValue);
    });
    
	  araGiris.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
  		  if(!newValue.isEmpty()) {
  			  olur=true;
  			  }
  			if(olur==true&&olur2==true) {
  		    	String sql="SELECT m.idMusteri,m.Ad,m.Soyad,m.TCKN,m.telNo,m.DogumTarihi,mo.girisTarih,mo.cikisTarih,mo.durum,o.OdaNumarasi\r\n"
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
	    	    	String sql1="SELECT m.idMusteri,m.Ad,m.Soyad,m.TCKN,m.telNo,m.DogumTarihi,mo.girisTarih,mo.cikisTarih,mo.durum,o.OdaNumarasi\r\n"
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
    Button[] Sil=new Button[50];
	int SilButonSayisi=0;
    Button[] Duzenle=new Button[50];
	int DuzenleButonSayisi=0;
    Button[] Degistir=new Button[50];
	int DegistirButonSayisi=0;
	
	Icons icon=new Icons();
	AdminMainController controller=new AdminMainController();
	
    public void buttonActions() {
    	for (int i = 0; i < Sil.length; i++) {

			Sil[i]=new Button();
			Sil[i].setText("Sil");
			Sil[i].setPrefWidth(90);
			Sil[i].setId("sil-button");
			icon.setIcon("delete.png", 15, 15, Sil[i]);
			Sil[i].setOnAction(arg0  -> {
				try {
					SilButtonAction(arg0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			});}
    	
    	for (int i = 0; i < Duzenle.length; i++) {

			Duzenle[i]=new Button();
			Duzenle[i].setText("Düzenle");
			Duzenle[i].setPrefWidth(90);
			Duzenle[i].setId("duzenle-button");
			icon.setIcon("edit.png", 15, 15, Duzenle[i]);
			Duzenle[i].setOnAction(arg0  -> {
				try {
					DuzenleButtonAction(arg0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			});}
    	
    	for (int i = 0; i < Degistir.length; i++) {

    		Degistir[i]=new Button();
    		Degistir[i].setText("Degistir");
    		Degistir[i].setPrefWidth(90);
    		Degistir[i].setId("degistir-button");
    		icon.setIcon("arrows.png", 15, 15, Degistir[i]);
			Degistir[i].setOnAction(arg0  -> {
				try {
					DegistirButtonAction(arg0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			});}
    }
    
    @FXML
    void SilButtonAction(ActionEvent event) throws IOException {
    	for (int i = 0; i < Sil.length; i++) {

    		if(event.getSource()==Sil[i]) {
    			
    			sil.getTableView().getSelectionModel().select(i);

	        	Alert alert=new Alert(AlertType.CONFIRMATION);
	        	alert.setTitle("Uyarı Mesajı");
	        	alert.setHeaderText("Silme İşlemi");
	        	alert.setContentText("Silmek istediğinizden emin misiniz?");
	        	
	           	ButtonType btn1=new ButtonType("Evet",ButtonData.OK_DONE);
	        	ButtonType btn3=new ButtonType("İptal",ButtonData.CANCEL_CLOSE);
	        	
	        	alert.getButtonTypes().clear();
	        	alert.getButtonTypes().addAll(btn1,btn3);
	        	Optional<ButtonType>text2=alert.showAndWait();
	        	
	        	if(btn1==text2.get()) 
	        	{
	        		String sql2="delete from musteri_oda where idMusteri="+tableView.getSelectionModel().getSelectedItem().getId();
	    	       	sql="delete from musteri where idMusteri="+tableView.getSelectionModel().getSelectedItem().getId();
	    	    	try 
	    	    	{
	    	    		ps.executeUpdate(sql2);
	    				ps.executeUpdate(sql);
	    				
	    	        	Alert alert1=new Alert(AlertType.INFORMATION);
	    	        	alert1.setTitle("Otel Sistemi");
	    	        	alert1.setHeaderText("İşlem Başarılı");
	    	        	alert1.setContentText("Başarılı bir şekilde silinmiştir. Tablo otomatik olarak güncellenicektir.");
	    	        	alert1.showAndWait();
	    	        	
	    	        	buttonActions();
	    				tableSet();
	    				
	    			} 
	    	    	catch (Exception e) 
	    	    	{
	    	    		System.out.println("wow");
	    	           	Alert alert1=new Alert(AlertType.ERROR);
	    	        	alert1.setTitle("Otel Sistemi");
	    	        	alert1.setHeaderText("Hata Mesajı");
	    	        	alert1.setContentText("Silmek istediğiniz bilgilerin olduğu satırı seçiniz.");
	    	        	alert1.showAndWait();
	    			}

	        	}
	        	else 
	        	{
	        	}

    		}
		}
    }
    
    @FXML
    void DuzenleButtonAction(ActionEvent event) throws IOException {
    	for (int i = 0; i < Duzenle.length; i++) {
    		
    		if(event.getSource()==Duzenle[i]) {
    			
    			duzenle.getTableView().getSelectionModel().select(i);
    			
    	    	if(tableView.getSelectionModel().isEmpty()==false) {
    	    		transferData();
    	    	}
    	    	else {
    	        	Alert alert1=new Alert(AlertType.WARNING);
    	        	alert1.setTitle("Otel Sistemi");
    	        	alert1.setHeaderText("Hata Mesajı");
    	        	alert1.setContentText("Güncellemek istediğiniz bilgilerin olduğu satırı seçiniz.");
    	        	alert1.showAndWait();
    	    	}
    		}
		}
    }
    
    @FXML
    void DegistirButtonAction(ActionEvent event) throws IOException {
    	for (int i = 0; i < Degistir.length; i++) {
    		
    		if(event.getSource()==Degistir[i]) {
    			
    			degistir.getTableView().getSelectionModel().select(i);
    			
    	    	if(tableView.getSelectionModel().isEmpty()==false) {
    	    		transferData2();
    	    	}
    	    	else {
    	        	Alert alert1=new Alert(AlertType.WARNING);
    	        	alert1.setTitle("Otel Sistemi");
    	        	alert1.setHeaderText("Hata Mesajı");
    	        	alert1.setContentText("Güncellemek istediğiniz bilgilerin olduğu satırı seçiniz.");
    	        	alert1.showAndWait();
    	    	}
    		}
		}
    }
    
    public void tableSet() {
    	
    	SilButonSayisi=0;
    	DuzenleButonSayisi=0;
    	DegistirButonSayisi=0;
    	
    	ObservableList<MI_Kayitlar>liste=FXCollections.observableArrayList();
    	String sql1="SELECT m.idMusteri,m.Ad,m.Soyad,m.TCKN,m.telNo,m.DogumTarihi,mo.girisTarih,mo.cikisTarih,mo.durum,o.OdaNumarasi\r\n"
    			+ "FROM oda AS o\r\n"
    			+ "INNER JOIN musteri_oda AS mo ON o.idOda = mo.idOda\r\n"
    			+ "INNER JOIN musteri AS m ON mo.idMusteri = m.idMusteri\r\n"
    			+ "WHERE m.idMusteri=mo.idMusteri and !(durum='Çıkış Yapıldı')";
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
						Sil[SilButonSayisi++],
						Duzenle[DuzenleButonSayisi++],
						Degistir[DegistirButonSayisi++]
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
			sil.setCellValueFactory(new PropertyValueFactory<>("sil"));
			duzenle.setCellValueFactory(new PropertyValueFactory<>("duzenle"));
			degistir.setCellValueFactory(new PropertyValueFactory<>("degistir"));
			
			
			tableView.setItems(liste);
		} catch (Exception e) {
			e.printStackTrace();
		}


    }
    
    public void tableSet2(String sql) {
    	
    	SilButonSayisi=0;
    	DuzenleButonSayisi=0;
    	DegistirButonSayisi=0;
    	
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
						Sil[SilButonSayisi++],
						Duzenle[DuzenleButonSayisi++],
						Degistir[DegistirButonSayisi++]
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
			sil.setCellValueFactory(new PropertyValueFactory<>("sil"));
			duzenle.setCellValueFactory(new PropertyValueFactory<>("duzenle"));
			degistir.setCellValueFactory(new PropertyValueFactory<>("degistir"));
			
			
			tableView.setItems(liste);
		} catch (Exception e) {
			e.printStackTrace();
		}


    }
    
    public void tableListele(String newValue) {
    	
    	SilButonSayisi=0;
    	DuzenleButonSayisi=0;
    	DegistirButonSayisi=0;
    	
    	ObservableList<MI_Kayitlar>liste=FXCollections.observableArrayList();
    	sql="SELECT m.idMusteri,m.Ad,m.Soyad,m.TCKN,m.telNo,m.DogumTarihi,mo.girisTarih,mo.cikisTarih,mo.durum,o.OdaNumarasi\r\n"
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
						Sil[SilButonSayisi++],
						Duzenle[DuzenleButonSayisi++],
						Degistir[DegistirButonSayisi++]
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
			sil.setCellValueFactory(new PropertyValueFactory<>("sil"));
			duzenle.setCellValueFactory(new PropertyValueFactory<>("duzenle"));
			degistir.setCellValueFactory(new PropertyValueFactory<>("degistir"));
			
			
			tableView.setItems(liste);
		} catch (Exception e) {
			e.printStackTrace();
		}


    }
    

    
    
	public void transferData() throws IOException {
		
		
		int id=tableView.getSelectionModel().getSelectedItem().getId();
		String ad=tableView.getSelectionModel().getSelectedItem().getAd();
		String soyad=tableView.getSelectionModel().getSelectedItem().getSoyad();
		String tckn=tableView.getSelectionModel().getSelectedItem().getTckn();
		String telNo=tableView.getSelectionModel().getSelectedItem().getTelNo();
		//String durum=tableView.getSelectionModel().getSelectedItem().getDurum();
		LocalDate dogumTarihi=tableView.getSelectionModel().getSelectedItem().getDogumTarihi().toLocalDate();
		LocalDate girisTarihi=tableView.getSelectionModel().getSelectedItem().getGirisTarihi().toLocalDate();
		LocalDate cikisTarihi=tableView.getSelectionModel().getSelectedItem().getCikisTarihi().toLocalDate();
		//String oda=tableView.getSelectionModel().getSelectedItem().getOda();
		
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXML/MusteriDuzenle.fxml"));
		Parent root=loader.load();
		
		Stage mainPage=new Stage();
		mainPage.setScene(new Scene(root));
		mainPage.show();
		
		MusteriDuzenleController controller=loader.getController();
		controller.setData(id,ad, soyad, tckn, telNo, dogumTarihi,girisTarihi,cikisTarihi);
		
	}
	
	public void transferData2() throws IOException {
		
		
		int id=tableView.getSelectionModel().getSelectedItem().getId();

		
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXML/MusteriDegistir.fxml"));
		Parent root=loader.load();
		
		Stage mainPage=new Stage();
		mainPage.setScene(new Scene(root));
		mainPage.show();
		
		MusteriDegistirController controller=loader.getController();
		controller.setData(id);
		
	}


}
