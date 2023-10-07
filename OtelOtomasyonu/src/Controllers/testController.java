package Controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Models.Test_Record;
import Models.VeriTabani;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class testController extends Application{
	public testController(){
		conn=VeriTabani.Connect();
	}
    Connection conn=null;
    PreparedStatement ps=null;
    ResultSet rs=null;
    String sql=null;


	public String[] dataTypes(int columnCount,ResultSetMetaData metaData) throws SQLException {
        // Sütun veri tiplerini bir diziye atayın
        String[] columnDataTypes = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnDataTypes[i - 1] = metaData.getColumnTypeName(i);
        }
		return columnDataTypes;}
	public String[]columnNames(int columnCount,ResultSetMetaData metaData) throws SQLException{
        // Sütun isimlerini bir diziye atayın
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }
        return columnNames;
	}
	public void writeDataTypes(String[] columnDataTypes) throws SQLException {
        // Sütun veri tiplerini yazdırın veya kullanın
        for (String columnDataType : columnDataTypes) {
            System.out.println("Sütun Veri Tipi: " + columnDataType);
        }
        
}
	
	public void writeColumnNames(String[] columnNames) throws SQLException {
        // Sütun isimlerini yazdırın veya kullanın
        for (String columnName : columnNames) {
            System.out.println("Sütun Adı: " + columnName);
        }
        }
        
	
    public static String determineDataType(String veri) {
        if (veri.matches("\\d+")) {
            return "int";
        } else if (veri.equalsIgnoreCase("true") || veri.equalsIgnoreCase("false")) {
            return "boolean";
        } else {
            return "String";
        }
    }
    
    public static List<Object> setResults(String[] columnDataTypes) {
        List<Object> results = new ArrayList<>();

        for (String veri : columnDataTypes) {
            Object deger = null;
            String veriTipi = determineDataType(veri);
            System.out.println(veriTipi);

            switch (veriTipi) {
                case "":
                    deger = Integer.parseInt(veri);
                    break;
                case "DATE":
                    deger = Date.valueOf(veri);
                    break;
                case "VARCHAR":
                    deger = veri;
                    break;
                default:
                    System.out.println("Geçersiz veri tipi: " + veriTipi);
            }

            if (deger != null) {
                results.add(deger); // Değerleri listeye ekleyin
            }
        }

        return results; // Sonuçları liste olarak döndürün
    }
	public void writeResults(List<Object> results) {
        // Listeyi kullanarak sonuçları yazdırabilirsiniz
        for (Object deger : results) {
            System.out.println("Değer: " + deger + ", Veri Tipi: " + deger.getClass().getSimpleName());
        }
	}
    
	public ResultSetMetaData getMeta(String sql) {
		try (
    			Statement ps = conn.createStatement();
    
                ResultSet rs = ps.executeQuery(sql)) {

               	ResultSetMetaData metaData = rs.getMetaData();
		return metaData;
	} catch (Exception e) {
   		e.printStackTrace();
   	}
		return null;}
	

	@Override
    public void start(Stage primaryStage) throws SQLException {
       	ObservableList<Test_Record>liste=FXCollections.observableArrayList();
    	String sql="select * from musteri";
        TableView<Test_Record> tableView = new TableView<>();
				
        		ResultSetMetaData metaData=getMeta(sql);
               	int columnCount = metaData.getColumnCount();
               
               	String[] columnDataTypes=dataTypes(columnCount, metaData);
               	writeDataTypes(columnDataTypes);

               	String[] columnNames=columnNames(columnCount, metaData);
               	//writeColumnNames(columnNames);

               	List<Object> results = setResults(columnDataTypes);
               	writeResults(results);
               
               @SuppressWarnings("unchecked")
			TableColumn<Test_Record, String>[] columns = new TableColumn[columnCount];

    			ps=conn.prepareStatement(sql);
    			rs=ps.executeQuery();
   				while (rs.next()) {

   					liste.add(new Test_Record(
   							rs.getInt("idMusteri"),
   							rs.getString("Ad"),
   							rs.getString("Soyad")
   							));
   					}
   				for (int i = 0; i < columnCount; i++) {
   				    columns[i] = new TableColumn<>(columnNames[i]);
   				    columns[i].setCellValueFactory(new PropertyValueFactory<>(columnNames[i]));
   				}
   			
   				tableView.getColumns().addAll(columns);

   				tableView.setItems(liste);
   				
       

        StackPane root = new StackPane(tableView);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("TableView Example");
        primaryStage.setScene(scene);
        primaryStage.show();
	}

    public static void main(String[] args) {
        launch(args);
    }
}
