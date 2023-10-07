package Models;

import java.sql.Date;

import javafx.scene.control.Button;

public class PL_Kayitlar {
	public PL_Kayitlar() {VeriTabani.Connect();}
	
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

	public String getStatu() {
		return Statu;
	}

	public void setStatu(String statu) {
		Statu = statu;
	}

	public String getTelNo() {
		return TelNo;
	}

	public void setTelNo(String telNo) {
		TelNo = telNo;
	}

	public Date getDogumTarihi() {
		return DogumTarihi;
	}

	public void setDogumTarihi(Date dogumTarihi) {
		DogumTarihi = dogumTarihi;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Button getSil() {
		return Sil;
	}

	public void setSil(Button sil) {
		Sil = sil;
	}

	public Button getDuzenle() {
		return Duzenle;
	}

	public void setDuzenle(Button duzenle) {
		Duzenle = duzenle;
	}


	public int id;
	public String Ad;
	public String Soyad;
	public String TCKN;
	public String Statu;
	public String TelNo;
	public Date DogumTarihi;
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKullaniciAdi() {
		return kullaniciAdi;
	}

	public void setKullaniciAdi(String kullaniciAdi) {
		this.kullaniciAdi = kullaniciAdi;
	}

	public String getSifre() {
		return sifre;
	}

	public void setSifre(String sifre) {
		this.sifre = sifre;
	}

	public String getYetki() {
		return yetki;
	}

	public void setYetki(String yetki) {
		this.yetki = yetki;
	}


	public String email;
	public String kullaniciAdi;
	public String sifre;
	public String yetki;

	public Button Sil;
	public Button Duzenle;
	
	public PL_Kayitlar(int id,String Ad,String Soyad,String TCKN,String TelNo,String email,Date DogumTarihi,String Statu,String kullaniciAdi,String sifre,String yetki,Button Sil,Button Duzenle) {
		this.id=id;
		this.Ad=Ad;
		this.Soyad=Soyad;
		this.TCKN=TCKN;
		this.TelNo=TelNo;
		this.email=email;
		this.DogumTarihi=DogumTarihi;
		this.Statu=Statu;
		this.kullaniciAdi=kullaniciAdi;
		this.sifre=sifre;
		this.yetki=yetki;
		this.Sil=Sil;
		this.Duzenle=Duzenle;
	}
	
}
