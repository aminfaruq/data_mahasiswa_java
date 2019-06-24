package id.co.maminfaruq.datamahasiswa.network;

import id.co.maminfaruq.datamahasiswa.model.detail.DetailResponse;
import id.co.maminfaruq.datamahasiswa.model.mahasiswa.ResponseMahasiswa;
import id.co.maminfaruq.datamahasiswa.model.update.ResponseUpdate;
import id.co.maminfaruq.datamahasiswa.model.upload.ResponseUpload;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    // Mengambil data kategori
    @GET("getmahasiswa.php")
    Call<ResponseMahasiswa> getMahasiswa();

    @Multipart
    @POST("uploadmahasiswa.php")
    Call<ResponseUpload> uploadMahasiswa(
            @Part("nama_mahasiswa") RequestBody namaMahasiswa,
            @Part("asal_mahasiswa") RequestBody asalMahasiswa,
            @Part MultipartBody.Part image
    );

    @FormUrlEncoded
    @POST("deletemahasiswa.php")
    Call<ResponseMahasiswa> deleteMahasiswa(
            @Field("id_mahasiswa") int idMakanan,
            @Field("foto_mahasiswa") String namaFotoMahasiswa
    );

    @GET("getdetailmahasiswa.php")
    Call<DetailResponse> getDetailMahasiswa(@Query("id_mahasiswa") int idMahasiswa);

    // Update makanan
    @Multipart
    @POST("updatemahasiswa.php")
    Call<ResponseUpdate> updateMahasiswa(
            @Part("id_mahasiswa") int idMahasiswa,
            @Part("nama_mahasiswa") RequestBody namaMahasiswa,
            @Part("asal_mahasiswa") RequestBody asalMahasiswa,
            @Part("foto_mahasiswa") RequestBody fotoMahasiswa,
            @Part MultipartBody.Part image
    );
}
