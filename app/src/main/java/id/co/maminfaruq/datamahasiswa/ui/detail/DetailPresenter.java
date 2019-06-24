package id.co.maminfaruq.datamahasiswa.ui.detail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;

import id.co.maminfaruq.datamahasiswa.model.detail.DetailResponse;
import id.co.maminfaruq.datamahasiswa.model.mahasiswa.ResponseMahasiswa;
import id.co.maminfaruq.datamahasiswa.model.update.ResponseUpdate;
import id.co.maminfaruq.datamahasiswa.model.upload.ResponseUpload;
import id.co.maminfaruq.datamahasiswa.network.ApiClient;
import id.co.maminfaruq.datamahasiswa.network.ApiInterface;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter implements DetailContract.Presenter {

    private final DetailContract.View view;
    private File imageFile = null;
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

    public DetailPresenter(DetailContract.View view) {
        this.view = view;
    }

    @Override
    public void getDetailMahasiswa(String idMahasiswa) {

        Call<DetailResponse> call = mApiInterface.getDetailMahasiswa(Integer.valueOf(idMahasiswa));
        call.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.showDetailMahasiswa(response.body().getData());
                    } else {
                        view.showMessage(response.body().getMessage());
                    }
                } else {
                    view.showMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                view.showMessage(t.getMessage());
            }
        });

    }

    @Override
    public void updateMahasiswa(Context context, Uri filePath, String namaMahasiswa, String asalMahasiswa, String idMahasiswa, String fotoMahasiswa) {

        // Mencek foto, namaMakanan, dan DescMakanan apakah sudah terisi
        if (namaMahasiswa.isEmpty()) {
            view.showMessage("Nama makanan is empty");
            return;
        }

        if (asalMahasiswa.isEmpty()) {
            view.showMessage("Desc makanan is empty");
            return;
        }
        if (filePath != null){
            // Mengambil alamat file image
            File myFile = new File(filePath.getPath());
            Uri selectedImage = getImageContentUri(context, myFile, filePath);
            String partImage = getPath(context, selectedImage);
            imageFile = new File(partImage);
        }

        // Memasukkan data yang diperlukan ke dalam request body dengan tipe form-data untuk di kirim ke API
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part mPartImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);

        // Membungkus nama makanan
        RequestBody mNamaMahasiswa = RequestBody.create(MediaType.parse("multipart/form-data"), namaMahasiswa);
        RequestBody mAsalMahasiswa = RequestBody.create(MediaType.parse("multipart/form-data"), asalMahasiswa);
        RequestBody mFotoMahasiswa = RequestBody.create(MediaType.parse("multipart/form-data"), fotoMahasiswa);

        Call<ResponseUpdate> call = mApiInterface.updateMahasiswa(
                Integer.valueOf(idMahasiswa),
                mNamaMahasiswa,
                mAsalMahasiswa,
                mFotoMahasiswa,
                mPartImage
        );
        call.enqueue(new Callback<ResponseUpdate>() {
            @Override
            public void onResponse(Call<ResponseUpdate> call, Response<ResponseUpdate> response) {

                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.showMessage(response.body().getMessage());
                       // view.successUpdate();
                    } else {
                        view.showMessage(response.body().getMessage());
                    }
                } else {
                    view.showMessage("Data kurang atau endpoint bermasalah");
                }
            }

            @Override
            public void onFailure(Call<ResponseUpdate> call, Throwable t) {
                view.showMessage(t.getMessage());
                Log.i("cek", "onFailure: " + t.getMessage());
            }
        });


    }

    @Override
    public void deleteMahasiswa(String idMahasiswa, String fotoMahasiswa) {

        if (idMahasiswa.isEmpty()) {
            view.showMessage("ID makanan tidak ada");
            return;
        }
        if (fotoMahasiswa.isEmpty()) {
            view.showMessage("Nama foto makanan tidak ada");
            return;
        }

        Call<ResponseMahasiswa> call = mApiInterface.deleteMahasiswa(Integer.valueOf(idMahasiswa), fotoMahasiswa);
        call.enqueue(new Callback<ResponseMahasiswa>() {
            @Override
            public void onResponse(Call<ResponseMahasiswa> call, Response<ResponseMahasiswa> response) {
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.showMessage(response.body().getMessage());
                        view.successDelete();
                    } else {
                        view.showMessage(response.body().getMessage());
                    }
                } else {
                    view.showMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<ResponseMahasiswa> call, Throwable t) {
                view.showMessage(t.getMessage());
            }
        });

    }


    private String getPath(Context context, Uri filepath) {
        Cursor cursor = context.getContentResolver().query(filepath, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ",
                new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private Uri getImageContentUri(Context context, File imageFile, Uri filePath) {
        String fileAbsolutePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{fileAbsolutePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Apabila file gambar sudah pernah diapakai namun ada kondisi lain yang belum diketahui
            // Apabila file gambar sudah pernah dipakai pengambilan bukan di galery

            Log.i("Isi Selected if", "Masuk cursor ada");
            return filePath;

        } else {
            Log.i("Isi Selected else", "cursor tidak ada");
            if (imageFile.exists()) {
                // Apabila file gambar baru belum pernah di pakai
                Log.i("Isi Selected else", "imagefile exists");
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, fileAbsolutePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                // Apabila file gambar sudah pernah dipakai
                // Apabila file gambar sudah pernah dipakai di galery
                Log.i("Isi Selected else", "imagefile tidak exists");
                return null;
            }
        }
    }

}
