package id.co.maminfaruq.datamahasiswa.ui.main;

import java.util.List;

import id.co.maminfaruq.datamahasiswa.model.mahasiswa.DataItem;

public interface MainContract {

    interface View{
        void showMahasiswa(List<DataItem> listMahasiswa);
    }

    interface Presenter{
        void getMahasiswa();
    }
}
