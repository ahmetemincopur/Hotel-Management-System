package Models;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class VeriTabani {
	private static Connection conn=null;
	
	public static Connection Connect() {
		try {
			conn=DriverManager.getConnection("jdbc:mysql://localhost/otelotomasyonu","root","admin");
			return conn;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String MD5Sifrele(String icerik)
	{
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			//byte olarak okuması
			byte[] sifrelenmis=md.digest(icerik.getBytes());
			BigInteger no=new BigInteger(1,sifrelenmis);
			
			//Hex hesaplama
			String hashIcerik=no.toString(16);
			while(hashIcerik.length()<32) {
				hashIcerik="0"+hashIcerik;
			}
			return hashIcerik;
			
		} catch (NoSuchAlgorithmException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

}
