package BusinessLayer;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Icons {
	
    public void setIcon(String iconName,int height,int width,Button button) {
    	Image icon=new Image("C:\\Users\\ahmet\\eclipse-workspace\\OtelOtomasyonu\\src\\images\\"+iconName);
    	ImageView iconView=new ImageView(icon);
    	iconView.setFitHeight(height);
    	iconView.setFitWidth(width);
    	button.setGraphic(iconView);
    }

}
