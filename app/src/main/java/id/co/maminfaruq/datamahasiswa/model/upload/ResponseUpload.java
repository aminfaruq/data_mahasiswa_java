package id.co.maminfaruq.datamahasiswa.model.upload;

import com.google.gson.annotations.SerializedName;

public class ResponseUpload{

	@SerializedName("result")
	private int result;

	@SerializedName("asal")
	private String asal;

	@SerializedName("nama")
	private String nama;

	@SerializedName("message")
	private String message;

	@SerializedName("url")
	private String url;

	public void setResult(int result){
		this.result = result;
	}

	public int getResult(){
		return result;
	}

	public void setAsal(String asal){
		this.asal = asal;
	}

	public String getAsal(){
		return asal;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"ResponseUpload{" + 
			"result = '" + result + '\'' + 
			",asal = '" + asal + '\'' + 
			",nama = '" + nama + '\'' + 
			",message = '" + message + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}