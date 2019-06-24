package id.co.maminfaruq.datamahasiswa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.maminfaruq.datamahasiswa.R;
import id.co.maminfaruq.datamahasiswa.model.mahasiswa.DataItem;
import id.co.maminfaruq.datamahasiswa.ui.detail.DetailActivity;
import id.co.maminfaruq.datamahasiswa.utils.Constants;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ViewHolder> {

    private final Context context;
    private final List<DataItem> dataItemList;


    public MahasiswaAdapter(Context context, List<DataItem> dataItemList) {
        this.context = context;
        this.dataItemList = dataItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mahasiswa, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        final DataItem dataItem = dataItemList.get(i);

        viewHolder.tvTitle.setText(dataItemList.get(i).getNamaMahasiswa());
        viewHolder.tvContent.setText(dataItemList.get(i).getAsalMahasiswa());
        Glide.with(context).load(Constants.BASE_URL + "uploads/" + dataItemList.get(i).getFotoMahasiswa())
                .into(viewHolder.imgmahasiswa);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DetailActivity.class).putExtra(Constants.KEY_EXTRA_ID_MAHASISWA,dataItem.getIdMahasiswa()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgmahasiswa)
        ImageView imgmahasiswa;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvContent)
        TextView tvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
