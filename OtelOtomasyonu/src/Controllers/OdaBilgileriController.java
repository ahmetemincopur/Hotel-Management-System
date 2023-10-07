package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

import BusinessLayer.Icons;
import Models.OB_Kayitlar;
import Models.VeriTabani;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class OdaBilgileriController {
	public OdaBilgileriController(){
		conn=VeriTabani.Connect();
	}
    Connection conn=null;
    PreparedStatement ps=null;
    ResultSet rs=null;
    String sql=null;
    
  
    
    @FXML
    private ResourceBundle resources;
    
    @FXML
    private AnchorPane secondary;

    @FXML
    private URL location;
    
    @FXML
    private TableColumn<OB_Kayitlar,Integer> id;

    @FXML
    private Button yenile;

    @FXML
    private AnchorPane AnchorPane_odaBilgileri;

    @FXML
    private TableColumn<OB_Kayitlar,String> durum;

    @FXML
    private Button ekle;

    @FXML
    private TableColumn<OB_Kayitlar,String> kapasite;
    
    @FXML
    private TableColumn<OB_Kayitlar,String> fiyat;
    
    @FXML
    private TableColumn<OB_Kayitlar,String> silTable;
    
    @FXML
    private TableColumn<OB_Kayitlar,String> duzenleTable;

    @FXML
    private TableColumn<OB_Kayitlar,String> kat;

    @FXML
    private TableColumn<OB_Kayitlar,String> misafir;

    @FXML
    private TableColumn<OB_Kayitlar,String> odaNumarasi;

    @FXML
    private TableView<OB_Kayitlar> odaTable;
     
	public void open(String fxml) {
		try {
			Parent root1=FXMLLoader.load(getClass().getResource(fxml));
			Stage mainPage=new Stage();
			mainPage.setScene(new Scene(root1));
			mainPage.show();
		} catch (Exception e) {}
		
	}
	// Oda ekle sayfasını açar
    @FXML
    void ekle_action(ActionEvent event) {
    	open("/FXML/OdaEkle.fxml");
    }
    
    // Tabloyu yeniler
    @FXML
    void yenile_action(ActionEvent event) {
    	tableSet();
    }
    
    // Tablodaki verileri Duzenle sayfasına aktarır
	public void transferData() throws IOException {
		int id=odaTable.getSelectionModel().getSelectedItem().getId();
		String kat=odaTable.getSelectionModel().getSelectedItem().getKat();
		String oda=odaTable.getSelectionModel().getSelectedItem().getOdaNumarasi();
		String kapasite=odaTable.getSelectionModel().getSelectedItem().getKapasite();
		String fiyat=odaTable.getSelectionModel().getSelectedItem().getFiyat();
		
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXML/OdaDuzenle.fxml"));
		Parent root=loader.load();
		
		Stage mainPage=new Stage();
		mainPage.setScene(new Scene(root));
		mainPage.show();
		
		// Nesne türetip sayfanın controllerını aktarıp setData() fonksiyonuyla veriyi aktarır
		OdaBilgileriDuzenleController controller=loader.getController();
		controller.setData(id,kat,oda,kapasite,fiyat);
		
	}
    

	int row;
    Button[] Sil=new Button[50];
	int SilButonSayisi=0;
    Button[] Duzenle=new Button[50];
	int DuzenleButonSayisi=0;
    
    public void tableSet() {
    	SilButonSayisi=0;
    	DuzenleButonSayisi=0;
    	ObservableList<OB_Kayitlar>liste=FXCollections.observableArrayList();
    	// iki sorguyu union ile birleştirerek durum kısımlarını Müsait olarak atadım ve ad soyad kısımlarını getirdim
    	sql = "SELECT o.idOda,o.Kat,o.OdaNumarasi,o.Kapasite,o.Fiyat,m.Ad,m.Soyad,mo.durum\r\n"
    			+ "FROM oda AS o\r\n"
    			+ "INNER JOIN musteri_oda AS mo ON o.idOda = mo.idOda\r\n"
    			+ "INNER JOIN musteri AS m ON mo.idMusteri = m.idMusteri\r\n"
    			+ "WHERE m.idMusteri=mo.idMusteri\r\n"
    			+ "UNION\r\n"
    			+ "SELECT o.idOda,o.Kat,o.OdaNumarasi,o.Kapasite,o.Fiyat,'','','Müsait'\r\n"
    			+ "                FROM oda as o \r\n"
    			+ "                LEFT JOIN musteri_oda as mo ON o.idOda = mo.idOda \r\n"
    			+ "				where mo.idOda is null";
    	try {

			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			
				while(rs.next()) {
				liste.add(new OB_Kayitlar(
						rs.getInt("idOda"),
						rs.getString("Kat"),
						rs.getString("OdaNumarasi"),
						rs.getString("Kapasite"),
						rs.getString("Fiyat"),
						Sil[SilButonSayisi++],
						Duzenle[DuzenleButonSayisi++],
						rs.getString("Ad"),
						rs.getString("Soyad"),
						rs.getString("durum")

						));
				}
			
			id.setCellValueFactory(new PropertyValueFactory<>("id"));
			kat.setCellValueFactory(new PropertyValueFactory<>("kat"));
			odaNumarasi.setCellValueFactory(new PropertyValueFactory<>("odaNumarasi"));
			durum.setCellValueFactory(new PropertyValueFactory<>("durum"));
			kapasite.setCellValueFactory(new PropertyValueFactory<>("kapasite"));
			misafir.setCellValueFactory(new PropertyValueFactory<>("misafir"));
			fiyat.setCellValueFactory(new PropertyValueFactory<>("fiyat"));
			silTable.setCellValueFactory(new PropertyValueFactory<>("sil"));
			duzenleTable.setCellValueFactory(new PropertyValueFactory<>("duzenle"));
			
			odaTable.setItems(liste);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
    
    @FXML
    void SilButtonAction(ActionEvent event) throws IOException 
    {
    	for (int i = 0; i < Sil.length; i++) {

    		if(event.getSource()==Sil[i]) {
    			
    			// Butonun basıldığı satırı seçer
    			silTable.getTableView().getSelectionModel().select(i);
    			
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
    		       	sql="delete from oda where idOda="+odaTable.getSelectionModel().getSelectedItem().getId();
    		    	try 
    		    	{
    					ps.executeUpdate(sql);
    					
    		        	Alert alert1=new Alert(AlertType.INFORMATION);
    		        	alert1.setTitle("Otel Sistemi");
    		        	alert1.setHeaderText("İşlem Başarılı");
    		        	alert1.setContentText("Başarılı bir şekilde silinmiştir. Tablo otomatik olarak güncellenicektir.");
    		        	alert1.showAndWait();
    		        	
    					tableSet();
    					
    				} 
    		    	catch (Exception e) 
    		    	{
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
    			
    			duzenleTable.getTableView().getSelectionModel().select(i);
    			
    	    	if(odaTable.getSelectionModel().isEmpty()==false) {
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
    }

    Icons icon=new Icons();
    @FXML
    void initialize() {
    	icon.setIcon("key-room.png", 35, 35, ekle);
    	icon.setIcon("refresh.png", 35, 35, yenile);
    	buttonActions();
    	tableSet();
    	
    	yenile.setId("button-blue");
    	ekle.setId("ekle-button");
    	
    }

}
