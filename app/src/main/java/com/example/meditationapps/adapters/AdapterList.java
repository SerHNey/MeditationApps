package com.example.meditationapps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meditationapps.R;
import com.example.meditationapps.clasess.citata;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterList extends ArrayAdapter<citata> {

    private List<citata> listcitat;

    public AdapterList(Context context, List<citata> citats) {
        super(context, 0, citats);
        this.listcitat = citats;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.template_list, parent, false);
        }

        citata citat = listcitat.get(position);

        // Получение View-элементов из макета my_object_layout.xml
        TextView title = convertView.findViewById(R.id.tv_title);
        TextView description = convertView.findViewById(R.id.tv_desc);
        ImageView image = convertView.findViewById(R.id.image);

        // Заполнение View-элементов данными из myObject
        title.setText(citat.title);
        description.setText(citat.description);
        Picasso.get().load(citat.image).into(image);

        return convertView;
    }
}

