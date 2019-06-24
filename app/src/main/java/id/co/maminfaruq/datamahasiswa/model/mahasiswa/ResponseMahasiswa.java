package id.co.maminfaruq.datamahasiswa.model.mahasiswa;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseMahasiswa{

	@SerializedName("result")
	private int result;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("message")
	private String message;

	public void setResult(int result){
		this.result = result;
	}

	public int getResult(){
		return result;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
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
			"ResponseMahasiswa{" + 
			"result = '" + result + '\'' + 
			",data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}