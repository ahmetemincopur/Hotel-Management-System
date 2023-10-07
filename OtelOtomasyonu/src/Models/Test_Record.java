package Models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;

public class Test_Record {
	public Test_Record(){}
	
	private int idMusteri;
	private String Ad;
	private String Soyad;
	private String TCKN;
	private Date DogumTarihi;

	private Button sil;
	private Button degistir;
	private Button duzenle;
	
	List<String> columnNames = new ArrayList<>();

	public List<String> getColumnNames() {
		columnNames.add("");
		columnNames.add("Ad");
		columnNames.add("Soyad");
		columnNames.add("TCKN");
		columnNames.add("Doğum Tarihi");
		columnNames.add("");
		columnNames.add("");
		columnNames.add("");
		return columnNames;
	}
	
	public Test_Record(int idMusteri,String Ad,String Soyad) {
		this.idMusteri=idMusteri;
		this.Ad=Ad;
		this.Soyad=Soyad;
	}
	
	public Test_Record(int idMusteri,String Ad,String Soyad,String TCKN,Date DogumTarihi,Button sil,Button degistir,Button duzenle) {
		this.idMusteri=idMusteri;
		this.Ad=Ad;
		this.Soyad=Soyad;
		this.TCKN=TCKN;
		this.DogumTarihi=DogumTarihi;
		this.sil=sil;
		this.degistir=degistir;
		this.duzenle=duzenle;
	}
	

	public Button getSil() {
		return sil;
	}

	public void setSil(Button sil) {
		this.sil = sil;
	}

	public Button getDegistir() {
		return degistir;
	}

	public void setDegistir(Button degistir) {
		this.degistir = degistir;
	}

	public Button getDuzenle() {
		return duzenle;
	}

	public void setDuzenle(Button duzenle) {
		this.duzenle = duzenle;
	}

	
	public int getIdMusteri() {
		return idMusteri;
	}
	public void setIdMusteri(int idMusteri) {
		this.idMusteri = idMusteri;
	}
	public String getAd() {
		return Ad;
	}
	public void setAd(String ad) {
		Ad = ad;
	}
	public String getSoyad() {
		return Soyad;
	}
	public void setSoyad(String soyad) {
		Soyad = soyad;
	}
	public String getTCKN() {
		return TCKN;
	}
	public void setTCKN(String tCKN) {
		TCKN = tCKN;
	}
	public Date getDogumTarihi() {
		return DogumTarihi;
	}
	public void setDogumTarihi(Date dogumTarihi) {
		DogumTarihi = dogumTarihi;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	private String telNo;

}
