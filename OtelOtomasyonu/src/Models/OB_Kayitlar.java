package Models;

import javafx.scene.control.Button;

public class OB_Kayitlar {
	public OB_Kayitlar() {VeriTabani.Connect();}
	
	public String getKat() {
		return kat;
	}
	public void setKat(String kat) {
		this.kat = kat;
	}
	public String getOdaNumarasi() {
		return odaNumarasi;
	}
	public void setOdaNumarasi(String odaNumarasi) {
		this.odaNumarasi = odaNumarasi;
	}
	public String getDurum() {
		return durum;
	}
	public void setDurum(String durum) {
		this.durum = durum;
	}
	public String getKapasite() {
		return kapasite;
	}
	public void setKapasite(String kapasite) {
		this.kapasite = kapasite;
	}
	public String getMisafir() {
		return misafir;
	}
	public void setMisafir(String misafir) {
		this.misafir = misafir;
	}

	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String kat;
	public String odaNumarasi;
	private String durum;
	private String kapasite;
	private String misafir;
	private String fiyat;
	public Button getSil() {
		return sil;
	}

	public void setSil(Button sil) {
		this.sil = sil;
	}

	public Button getDuzenle() {
		return duzenle;
	}

	public void setDuzenle(Button duzenle) {
		this.duzenle = duzenle;
	}

	private Button sil;
	private Button duzenle;
	public String getFiyat() {
		return fiyat;
	}

	public void setFiyat(String fiyat) {
		this.fiyat = fiyat;
	}
	
	public OB_Kayitlar(String odaNumarasi) {
		this.odaNumarasi=odaNumarasi;

	}
	
	
	public OB_Kayitlar(int id) {
		this.id=id;

	}
	String ad;
	
	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	String Soyad;

	public String getSoyad() {
		return Soyad;
	}

	public void setSoyad(String soyad) {
		Soyad = soyad;
	}

	public OB_Kayitlar(int id,String kat,String odaNumarasi,String kapasite,String fiyat,Button sil,Button duzenle) {
		this.id=id;
		this.kat=kat;
		this.odaNumarasi=odaNumarasi;
		this.kapasite=kapasite;
		this.fiyat=fiyat;
		this.sil=sil;
		this.duzenle=duzenle;
	}
	
	public OB_Kayitlar(int id,String kat,String odaNumarasi,String kapasite,String misafir,String Soyad,String fiyat,Button sil,Button duzenle) {
		this.id=id;
		this.kat=kat;
		this.odaNumarasi=odaNumarasi;
		this.kapasite=kapasite;
		this.misafir=misafir+" "+Soyad;
		this.fiyat=fiyat;
		this.sil=sil;
		this.duzenle=duzenle;
	}
	
	public OB_Kayitlar(String ad,String soyad,String durum) {
		this.misafir=misafir+" "+Soyad;
		this.durum=durum;

	}

	public OB_Kayitlar(int id,String kat,String odaNumarasi,String kapasite,String fiyat,Button sil,Button duzenle,String misafir,String Soyad,String durum) {
		this.id=id;
		this.kat=kat;
		this.odaNumarasi=odaNumarasi;
		this.durum=durum;
		this.kapasite=kapasite;
		this.misafir=misafir+" "+Soyad;
		this.fiyat=fiyat;
		this.sil=sil;
		this.duzenle=duzenle;
	}
	
	

}
