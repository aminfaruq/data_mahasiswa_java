package id.co.maminfaruq.datamahasiswa.ui.main;

import id.co.maminfaruq.datamahasiswa.model.mahasiswa.ResponseMahasiswa;
import id.co.maminfaruq.datamahasiswa.network.ApiClient;
import id.co.maminfaruq.datamahasiswa.network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View view;
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void getMahasiswa() {
        Call<ResponseMahasiswa> call = mApiInterface.getMahasiswa();
        call.enqueue(new Callback<ResponseMahasiswa>() {
            @Override
            public void onResponse(Call<ResponseMahasiswa> call, Response<ResponseMahasiswa> response) {
                if (response.body() != null) {
                    view.showMahasiswa(response.body().getData());
                }

            }

            @Override
            public void onFailure(Call<ResponseMahasiswa> call, Throwable t) {

            }
        });
    }
}
