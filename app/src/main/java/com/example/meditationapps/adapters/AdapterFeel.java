package com.example.meditationapps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.meditationapps.R;
import com.example.meditationapps.clasess.Chuvstvo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterFeel extends RecyclerView.Adapter<AdapterFeel.ViewHolder> {
    private List<Chuvstvo> chuvustv; // Список объектов для отображения

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textview;
        public ImageView imageview;

        public ViewHolder(View itemView) {
            super(itemView);
            textview = (TextView) itemView.findViewById(R.id.textView);
            imageview = (ImageView) itemView.findViewById(R.id.imagev);
        }
    }
    // Получение количества элементов списка
    @Override
    public int getItemCount() {
        return chuvustv.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chuvstvo myObject = chuvustv.get(position);
        holder.textview.setText(myObject.title);
        Picasso.get().load(myObject.image).into(holder.imageview);
    }

    // Конструктор адаптера
    public AdapterFeel(List<Chuvstvo> chustvo) {
        this.chuvustv = chustvo;
    }

    // Создание нового элемента списка (вызывается LayoutManager'ом)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_recycle, parent, false);
        return new ViewHolder(view);
    }



}
