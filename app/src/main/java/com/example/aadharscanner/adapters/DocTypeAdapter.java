package com.example.aadharscanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.aadharscanner.R;
import com.example.aadharscanner.database.KycDocData;

import java.util.List;

public class DocTypeAdapter extends ArrayAdapter<KycDocData> {

    public DocTypeAdapter(@NonNull Context context, @NonNull List<KycDocData> docTypeDataList) {
        super(context, 0, docTypeDataList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Nullable
    private View initView(int position, @Nullable View convertView,
                          ViewGroup parent) {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_textview, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.spi);
        KycDocData currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName.setText(currentItem.getDocName());
        }
        return convertView;
    }
}
