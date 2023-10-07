package Models;

import java.sql.Date;

import javafx.scene.control.Button;

public class MI_Kayitlar {
	public MI_Kayitlar() {VeriTabani.Connect();}
	
	public String getAd() {
		return ad;
	}
	public void setAd(String ad) {
		this.ad = ad;
	}
	public String getSoyad() {
		return soyad;
	}
	public void setSoyad(String soyad) {
		this.soyad = soyad;
	}
	public String getTckn() {
		return tckn;
	}
	public void setTckn(String tckn) {
		this.tckn = tckn;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public Date getDogumTarihi() {
		return dogumTarihi;
	}
	public void setDogumTarihi(Date dogumTarihi) {
		this.dogumTarihi = dogumTarihi;
	}
	public String getDurum() {
		return durum;
	}
	public void setDurum(String durum) {
		this.durum = durum;
	}

	public Date getGirisTarihi() {
		return girisTarihi;
	}
	public void setGirisTarihi(Date girisTarihi) {
		this.girisTarihi = girisTarihi;
	}
	public Date getCikisTarihi() {
		return cikisTarihi;
	}
	public void setCikisTarihi(Date cikisTarihi) {
		this.cikisTarihi = cikisTarihi;
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

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	private int id;
	private String ad;
	private String soyad;
	private String tckn;
	private String telNo;
	private Date dogumTarihi;
	private String durum;

	public String getOda() {
		return oda;
	}
	public void setOda(String oda) {
		this.oda = oda;
	}
	
	private String fiyat;
	public String getFiyat() {
		return fiyat;
	}

	public void setFiyat(String fiyat) {
		this.fiyat = fiyat;
	}

	private String oda;
	private Date girisTarihi;
	private Date cikisTarihi;
	private Button cikisYap;
	public Button getCikisYap() {
		return cikisYap;
	}

	public void setCikisYap(Button cikisYap) {
		this.cikisYap = cikisYap;
	}

	private Button sil;
	private Button degistir;
	private Button duzenle;
	
	public MI_Kayitlar(int id,String ad,String soyad,String tckn,String telNo,
			Date dogumTarihi,Date girisTarihi,Date cikisTarihi,String durum,
			String oda,String fiyat,
			Button cikisYap) {
		this.id=id;
		this.ad=ad;
		this.soyad=soyad;
		this.tckn=tckn;
		this.telNo=telNo;
		this.dogumTarihi=dogumTarihi;
		this.oda=oda;
		this.durum=durum;
		this.fiyat=fiyat;
		this.girisTarihi=girisTarihi;
		this.cikisTarihi=cikisTarihi;
		this.cikisYap=cikisYap;
		
		
	}
	
	
	public MI_Kayitlar(int id,String ad,String soyad,String tckn,String telNo,
			Date dogumTarihi,Date girisTarihi,Date cikisTarihi,String durum,
			String oda,
			Button sil,Button duzenle,Button degistir) {
		this.id=id;
		this.ad=ad;
		this.soyad=soyad;
		this.tckn=tckn;
		this.telNo=telNo;
		this.dogumTarihi=dogumTarihi;
		this.oda=oda;
		this.durum=durum;
		this.girisTarihi=girisTarihi;
		this.cikisTarihi=cikisTarihi;
		this.sil=sil;
		this.duzenle=duzenle;
		this.degistir=degistir;
		
		
	}

	public MI_Kayitlar(String ad, String soyad) {
		this.ad=ad+" "+soyad;
	}

}
