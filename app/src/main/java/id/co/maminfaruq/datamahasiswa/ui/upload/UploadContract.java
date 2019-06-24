package id.co.maminfaruq.datamahasiswa.ui.upload;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import id.co.maminfaruq.datamahasiswa.model.mahasiswa.DataItem;

public interface UploadContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showMessage(String msg);
        void successUpload();
    }

    interface Presenter{
        void uploadMakanan(Context context, Uri filePath, String namaMahasiswa, String asalMahasiswa);
    }
}
