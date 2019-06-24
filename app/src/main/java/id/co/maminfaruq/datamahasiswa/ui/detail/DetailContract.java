package id.co.maminfaruq.datamahasiswa.ui.detail;

import android.content.Context;
import android.net.Uri;

import id.co.maminfaruq.datamahasiswa.model.mahasiswa.DataItem;

public interface DetailContract {

    interface View {
        void showDetailMahasiswa(DataItem dataItem);

        void successDelete();

        void successUpdate();

        void showMessage(String msg);
    }

    interface Presenter {
        void getDetailMahasiswa(String idMahasiswa);

        void updateMahasiswa(Context context,
                             Uri filePath,
                             String namaMahasiswa,
                             String asalMahasiswa,
                             String idMahasiswa,
                             String fotoMahasiswa);

        void deleteMahasiswa(String idMahasiswa, String fotoMahasiswa);
    }
}
