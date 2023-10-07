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

import BusinessLayer.Alerts;
import BusinessLayer.Icons;
import BusinessLayer.Pages;
import Models.PL_Kayitlar;
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
public class PersonelListeController{
	public PersonelListeController() {
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
    private AnchorPane AnchorPane_PersonelListe;
    
    @FXML
    private AnchorPane secondary;
    
    @FXML
    private TableColumn<PL_Kayitlar, String> PersonelListesi_Ad;
    
    @FXML
    private TableColumn<PL_Kayitlar, String> email;

    @FXML
    private TableColumn<PL_Kayitlar, String> kullaniciAdi;

    @FXML
    private TableColumn<PL_Kayitlar, String> sifre;

    @FXML
    private TableColumn<PL_Kayitlar, String> yetki;


    @FXML
    private Button PersonelListesi_yenile;

    @FXML
    void PersonelListesi_yenileButton_Action(ActionEvent event) {
    	tableSet();
    }
    
    @FXML
    private TableColumn<PL_Kayitlar, Date> PersonelListesi_DogumTarihi;

    @FXML
    private Button PersonelListesi_EkleButton;

    @FXML
    private TableColumn<PL_Kayitlar,Button> PersonelListesi_Duzenle;
    
    @FXML
    private TableColumn<PL_Kayitlar,Button> PersonelListesi_Sil;

    @FXML
    private TableColumn<PL_Kayitlar, String> PersonelListesi_Soyad;

    @FXML
    private TableColumn<PL_Kayitlar, String> PersonelListesi_Statu;

    @FXML
    private TableColumn<PL_Kayitlar, String> PersonelListesi_TCKN;

    @FXML
    private TableColumn<PL_Kayitlar, String> PersonelListesi_TelNo;
    
    @FXML
    private TableColumn<PL_Kayitlar, Integer> id;

    @FXML
    private TableView<PL_Kayitlar> PersonelListesi_tableView;
    
	 
	int row;
    Button[] Sil=new Button[50];
	int SilButonSayisi=0;
    Button[] Duzenle=new Button[50];
	int DuzenleButonSayisi=0;
	
	public void transferData() throws IOException {
		int id=PersonelListesi_tableView.getSelectionModel().getSelectedItem().id;
		String ad=PersonelListesi_tableView.getSelectionModel().getSelectedItem().Ad;
		String soyad=PersonelListesi_tableView.getSelectionModel().getSelectedItem().Soyad;
		String tckn=PersonelListesi_tableView.getSelectionModel().getSelectedItem().TCKN;
		String statu=PersonelListesi_tableView.getSelectionModel().getSelectedItem().Statu;
		String telNo=PersonelListesi_tableView.getSelectionModel().getSelectedItem().TelNo;
		LocalDate dogumTarihi=PersonelListesi_tableView.getSelectionModel().getSelectedItem().DogumTarihi.toLocalDate();
		String email=PersonelListesi_tableView.getSelectionModel().getSelectedItem().email;
		String kullaniciAdi=PersonelListesi_tableView.getSelectionModel().getSelectedItem().kullaniciAdi;
		String sifre=PersonelListesi_tableView.getSelectionModel().getSelectedItem().sifre;
		String yetki=PersonelListesi_tableView.getSelectionModel().getSelectedItem().yetki;
		
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/FXML/PersonelDuzenle.fxml"));
		Parent root=loader.load();
		
		Stage mainPage=new Stage();
		mainPage.setScene(new Scene(root));
		mainPage.show();
		
		PersonelDuzenleController controller=loader.getController();
		controller.setData(id,ad, soyad, tckn, statu, telNo, dogumTarihi,email,kullaniciAdi,sifre,yetki);
		
	}
    
    @FXML
    void SilButtonAction(ActionEvent event) throws IOException {
    	for (int i = 0; i < Sil.length; i++) {

    		if(event.getSource()==Sil[i]) {
    			
    			PersonelListesi_Sil.getTableView().getSelectionModel().select(i);
    			alert.confirmation("Hotel System", "Delete operation", "If you really want to delete please confirm.");
	        	Alert alert3=new Alert(AlertType.CONFIRMATION);
	        	alert3.setTitle("Uyarı Mesajı");
	        	alert3.setHeaderText("Silme İşlemi");
	        	alert3.setContentText("Silmek istediğinizden emin misiniz?");
	        	
	           	ButtonType btn1=new ButtonType("Evet",ButtonData.OK_DONE);
	        	ButtonType btn3=new ButtonType("İptal",ButtonData.CANCEL_CLOSE);
	        	
	        	alert3.getButtonTypes().clear();
	        	alert3.getButtonTypes().addAll(btn1,btn3);
	        	Optional<ButtonType>text2=alert3.showAndWait();
	        	
	        	if(btn1==text2.get()) 
	        	{
	    	       	sql="delete from personel where idPersonel="+PersonelListesi_tableView.getSelectionModel().getSelectedItem().id;
	    	    	try 
	    	    	{
	    				ps.executeUpdate(sql);
	    				
	    				alert.information("Hotel System", "Operation succeed.", "Successfully deleted. Table will be refresh.");
	    	        	
	    	        	//silme işleminden sonra tabloyu otomatik yeniler
	    	        	buttonActions();
	    				tableSet();
	    				
	    			} 
	    	    	catch (Exception e) 
	    	    	{
	    	    		alert.error("Hotel System", "Warning message", "Please click the row that you want to update.");
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
    	
    			PersonelListesi_Duzenle.getTableView().getSelectionModel().select(i);
    			
    	    	if(PersonelListesi_tableView.getSelectionModel().isEmpty()==false) {
    	    		//düzenle butonuna basıldığında tablo verilerini aktarır
    	    		transferData();
    	    	}
    	    	else {
    	    		alert.warning("Hotel System", "Warning message", "Please click the row that you want to update.");
    	    	}
    		}
		}
    }

    @FXML
    void PersonelListesi_EkleButton_Action(ActionEvent event) {
    	page.openStage("/FXML/PersonelEkle.fxml", "/application/application.css");
    }
   
    // Tabloya verileri aktarır
    public void tableSet() {
    	SilButonSayisi=0;
    	DuzenleButonSayisi=0;
    	ObservableList<PL_Kayitlar>liste=FXCollections.observableArrayList();
    	sql="select * from personel";
    	try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while (rs.next()) {
				liste.add(new PL_Kayitlar(
						rs.getInt("idPersonel"),
						rs.getString("Ad"),
						rs.getString("Soyad"),
						rs.getString("TCKN"),
						rs.getString("Tel"),
						rs.getString("Email"),
						rs.getDate("DogumTarihi"),
						rs.getString("Statu"),
						rs.getString("KullaniciAdi"),
						rs.getString("Sifre"),
						rs.getString("Yetki"),
						Sil[SilButonSayisi++],
						Duzenle[DuzenleButonSayisi++]
						));
				}
			
			id.setCellValueFactory(new PropertyValueFactory<>("id"));
			PersonelListesi_Ad.setCellValueFactory(new PropertyValueFactory<>("Ad"));
			PersonelListesi_Soyad.setCellValueFactory(new PropertyValueFactory<>("Soyad"));
			PersonelListesi_TCKN.setCellValueFactory(new PropertyValueFactory<>("TCKN"));
			PersonelListesi_TelNo.setCellValueFactory(new PropertyValueFactory<>("TelNo"));
			email.setCellValueFactory(new PropertyValueFactory<>("email"));
			PersonelListesi_DogumTarihi.setCellValueFactory(new PropertyValueFactory<>("DogumTarihi"));
			PersonelListesi_Statu.setCellValueFactory(new PropertyValueFactory<>("Statu"));
			kullaniciAdi.setCellValueFactory(new PropertyValueFactory<>("kullaniciAdi"));
			sifre.setCellValueFactory(new PropertyValueFactory<>("sifre"));
			yetki.setCellValueFactory(new PropertyValueFactory<>("yetki"));
			PersonelListesi_Sil.setCellValueFactory(new PropertyValueFactory<>("Sil"));
			PersonelListesi_Duzenle.setCellValueFactory(new PropertyValueFactory<>("Duzenle"));

			
			PersonelListesi_tableView.setItems(liste);
		} catch (Exception e) {
			e.printStackTrace();
		}


    }
    
    
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
    AdminMainController controller=new AdminMainController();
    Icons icon=new Icons();
    Pages page=new Pages();
    Alerts alert=new Alerts();
    @FXML
    void initialize() {
    	buttonActions();
    	tableSet();
    	
    	PersonelListesi_yenile.setId("button-blue");
    	PersonelListesi_EkleButton.setId("ekle-button");

    	
    	icon.setIcon("add-user.png", 35, 35, PersonelListesi_EkleButton);
    	icon.setIcon("refresh.png", 35, 35, PersonelListesi_yenile);
    }

}
