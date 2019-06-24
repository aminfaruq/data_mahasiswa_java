package id.co.maminfaruq.datamahasiswa.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.maminfaruq.datamahasiswa.R;
import id.co.maminfaruq.datamahasiswa.adapter.MahasiswaAdapter;
import id.co.maminfaruq.datamahasiswa.model.mahasiswa.DataItem;
import id.co.maminfaruq.datamahasiswa.ui.upload.UploadActivity;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.fab_main)
    FloatingActionButton fabMain;

    private MainPresenter presenter = new MainPresenter(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter.getMahasiswa();
    }


    @Override
    public void showMahasiswa(List<DataItem> listMahasiswa) {
        rvMain.setAdapter(new MahasiswaAdapter(this, listMahasiswa));
        rvMain.setLayoutManager(new LinearLayoutManager(this));

    }

    @OnClick(R.id.fab_main)
    public void onViewClicked() {
        startActivity(new Intent(this, UploadActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getMahasiswa();
    }
}
