package BusinessLayer;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Buttons {
    public Optional<ButtonType> showConfirmationDialogWithButtons(String title, String headerText, String contentText, ButtonType buttonTypes) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.getButtonTypes().setAll(buttonTypes);
        return alert.showAndWait();
    }
}