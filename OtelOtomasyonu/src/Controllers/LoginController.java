package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import BusinessLayer.Alerts;
import BusinessLayer.Pages;
import Models.VeriTabani;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class LoginController{
	public LoginController(){
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
    private Button loginButton;
    
    @FXML
    private Label label;

    @FXML
    private Label label2;

    @FXML
    private PasswordField loginPass_PasswordField;

    @FXML
    private TextField loginUser_TextField;

    @FXML
    private AnchorPane login_Anc;
    

    @FXML
    private ImageView password_icon;

    @FXML
    private ImageView user_icon;
    
    

	
    @FXML
    void loginButton_Action(ActionEvent event) throws SQLException, IOException {
    	Pages pages=new Pages();
    	loginButton.setId("degistir-button");
    	label.setId("lable");
    	label2.setId("lable2");


    	sql="select Yetki from personel where KullaniciAdi=? and Sifre=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, loginUser_TextField.getText().trim());
			ps.setString(2, loginPass_PasswordField.getText().trim());
			rs=ps.executeQuery();

		if(rs.next()) {
			String yetki=rs.getString("Yetki");
			String admin="Admin";
			String personel="Personel";
			if(yetki.equals(admin)) {

				pages.openStage("\"/FXML/AdminMain.fxml\"", "/application/application.css");
				pages.closeStage(login_Anc);
			}
			else if(yetki.equals(personel)) {
				pages.openStage("\"/FXML/UserMain.fxml\"", "/application/application.css");
				pages.closeStage(login_Anc);
			}
			else {
		    	Alerts alert=new Alerts();
		    	alert.error("Hotel System", "Error Message", "Wrong username or password. Please check information");

			}
			
			}

    }

 


    
    @FXML
    void initialize() throws IOException {
       loginButton.setId("button-blue");


    }
    


		
	}

