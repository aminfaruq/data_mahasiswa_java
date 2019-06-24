package id.co.maminfaruq.datamahasiswa.model.detail;

import com.google.gson.annotations.SerializedName;

import id.co.maminfaruq.datamahasiswa.model.mahasiswa.DataItem;

public class DetailResponse{

	@SerializedName("result")
	private int result;

	@SerializedName("data")
	private DataItem data;

	@SerializedName("message")
	private String message;

	public void setResult(int result){
		this.result = result;
	}

	public int getResult(){
		return result;
	}

	public void setData(DataItem data){
		this.data = data;
	}

	public DataItem getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"DetailResponse{" + 
			"result = '" + result + '\'' + 
			",data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}