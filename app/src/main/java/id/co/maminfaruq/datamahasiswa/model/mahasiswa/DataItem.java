package id.co.maminfaruq.datamahasiswa.model.mahasiswa;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataItem implements Parcelable {

	@SerializedName("asal_mahasiswa")
	private String asalMahasiswa;

	@SerializedName("nama_mahasiswa")
	private String namaMahasiswa;

	@SerializedName("url_mahasiswa")
	private String urlMahasiswa;

	@SerializedName("id_mahasiswa")
	private String idMahasiswa;

	@SerializedName("foto_mahasiswa")
	private String fotoMahasiswa;

	public void setAsalMahasiswa(String asalMahasiswa){
		this.asalMahasiswa = asalMahasiswa;
	}

	public String getAsalMahasiswa(){
		return asalMahasiswa;
	}

	public void setNamaMahasiswa(String namaMahasiswa){
		this.namaMahasiswa = namaMahasiswa;
	}

	public String getNamaMahasiswa(){
		return namaMahasiswa;
	}

	public void setUrlMahasiswa(String urlMahasiswa){
		this.urlMahasiswa = urlMahasiswa;
	}

	public String getUrlMahasiswa(){
		return urlMahasiswa;
	}

	public void setIdMahasiswa(String idMahasiswa){
		this.idMahasiswa = idMahasiswa;
	}

	public String getIdMahasiswa(){
		return idMahasiswa;
	}

	public void setFotoMahasiswa(String fotoMahasiswa){
		this.fotoMahasiswa = fotoMahasiswa;
	}

	public String getFotoMahasiswa(){
		return fotoMahasiswa;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.asalMahasiswa);
		dest.writeString(this.namaMahasiswa);
		dest.writeString(this.urlMahasiswa);
		dest.writeString(this.idMahasiswa);
		dest.writeString(this.fotoMahasiswa);
	}

	public DataItem() {
	}

	protected DataItem(Parcel in) {
		this.asalMahasiswa = in.readString();
		this.namaMahasiswa = in.readString();
		this.urlMahasiswa = in.readString();
		this.idMahasiswa = in.readString();
		this.fotoMahasiswa = in.readString();
	}

	public static final Parcelable.Creator<DataItem> CREATOR = new Parcelable.Creator<DataItem>() {
		@Override
		public DataItem createFromParcel(Parcel source) {
			return new DataItem(source);
		}

		@Override
		public DataItem[] newArray(int size) {
			return new DataItem[size];
		}
	};
}