package id.co.maminfaruq.datamahasiswa.ui.detail;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.maminfaruq.datamahasiswa.R;
import id.co.maminfaruq.datamahasiswa.model.mahasiswa.DataItem;
import id.co.maminfaruq.datamahasiswa.utils.Constants;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    public static String EXTRA_OBJ = "OBJ";
    @BindView(R.id.img_picture)
    ImageView imgPicture;
    @BindView(R.id.fab_choose_picture)
    FloatingActionButton fabChoosePicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_desc)
    EditText edtDesc;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_delete)
    Button btnDelete;

    private DetailPresenter presenter = new DetailPresenter(this);


    private Uri filePath = null;
    private String idMahasiswa;
    private DataItem mDataItem;
    private String fotoMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        // Melakukan pengecekan dan permission untuk bisa mengakses gallery
        PermissionGalery();

        // Menangkap id makanan yang dikirimkan dari activity sebelumnya
        idMahasiswa = getIntent().getStringExtra(Constants.KEY_EXTRA_ID_MAHASISWA);
        presenter.getDetailMahasiswa(idMahasiswa);

    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    private void PermissionGalery() {
        // Mencek apakah user sudah memberikan permission untuk mengakses external storage
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.STORAGE_PERMISSION_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                showMessage("Permission granted now you can read the storage");
                Log.i("Permission on", "onRequestPermissionsResult: " + String.valueOf(grantResults));
            } else {
                //Displaying another toast if permission is not granted
                showMessage("Oops you just denied the permission");
                Log.i("Permission off", "onRequestPermissionsResult: " + String.valueOf(grantResults));
            }
        }
    }


    @Override
    public void showDetailMahasiswa(DataItem dataItem) {

        mDataItem = dataItem;

        fotoMahasiswa = dataItem.getFotoMahasiswa();

        edtName.setText(dataItem.getNamaMahasiswa());
        edtDesc.setText(dataItem.getAsalMahasiswa());

        Glide.with(this).load(Constants.BASE_URL + "uploads/" + dataItem.getFotoMahasiswa()).into(imgPicture);

    }

    @Override
    public void successDelete() {
        finish();
    }

    @Override
    public void successUpdate() {
        finish();

    }



    private void ShowFileChooser() {
        // Membuat object intent untuk dapat memilih data
        Intent intentGallery = new Intent(Intent.ACTION_PICK);
        intentGallery.setType("image/*");
        intentGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentGallery, "Select Pictures"),
                1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 &&
                resultCode == RESULT_OK &&
                data != null &&
                data.getData() != null
                ) {
            // mengambil data foto dan memasukkan ke dalam variable filePath
            filePath = data.getData();

            try {
                // Mengambil data gambar lalu di convert ke bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // Tampilkan gambar yang baru dipilih ke layar
                imgPicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.fab_choose_picture, R.id.btn_update, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_choose_picture:
                PermissionGalery();
                // Mengambil gambar dari storage
                ShowFileChooser();
                break;
            case R.id.btn_update:
                presenter.updateMahasiswa(this, filePath,
                        edtName.getText().toString(),
                        edtDesc.getText().toString(),
                        idMahasiswa,
                        fotoMahasiswa);
                break;
            case R.id.btn_delete:
                presenter.deleteMahasiswa(idMahasiswa, fotoMahasiswa);
                break;
        }
    }
}
