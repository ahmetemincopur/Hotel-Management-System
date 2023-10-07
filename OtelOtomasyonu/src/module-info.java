module OtelOtomasyonu {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	
	opens Controllers to javafx.graphics, javafx.fxml;
	opens application to javafx.graphics, javafx.fxml;
	opens Models to javafx.graphics, javafx.fxml, javafx.base;
}
