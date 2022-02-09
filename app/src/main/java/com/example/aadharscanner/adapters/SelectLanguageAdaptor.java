package com.example.aadharscanner.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aadharscanner.Pojo.LanguagePojo;
import com.example.aadharscanner.R;
import com.example.aadharscanner.activites.HomeActivity;
import com.example.aadharscanner.utils.AppUtils;
import com.example.aadharscanner.utils.DialogFactory;
import com.example.aadharscanner.utils.PreferenceFactory;
import com.example.aadharscanner.utils.PreferenceManager;

import java.util.List;
import java.util.Locale;

public class SelectLanguageAdaptor extends RecyclerView.Adapter<SelectLanguageAdaptor.MyViewHolder> {

    Context context;
    List<LanguagePojo> languagedataList;
    Locale myLocale;
    @NonNull
    String currentLanguage = "en", currentLang;


    public SelectLanguageAdaptor(Context context, List<LanguagePojo> languagedataList) {
        this.context = context;
        this.languagedataList = languagedataList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myLanguageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.language_selection_custom_layout, parent, false);
        return new SelectLanguageAdaptor.MyViewHolder(myLanguageView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.localLanguage.setText(languagedataList.get(position).getLocalLanguage());
        holder.englishLanguage.setText(languagedataList.get(position).getEnglishLanguage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFactory.getInstance().showAlertDialog(context, R.drawable.ic_launcher_background, "Message", context.getResources().getString(R.string.do_you_want_to_change_the_language), context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String languageCode = languagedataList.get(position).getLanguagecode();
                        String languageID = languagedataList.get(position).getLanguageid();
                        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefLanguageCode(), languageCode, context);
                        PreferenceFactory.getInstance().saveSharedPrefrecesData(PreferenceManager.getPrefLanguageId(), languageID, context);
                        AppUtils.getInstance().setLocale(languageCode, context.getResources(), context);
                        Intent refresh = new Intent(context, HomeActivity.class);
                        context.startActivity(refresh);
                        AppUtils.getInstance().makeIntent(context, HomeActivity.class, true);
                        // AppUtils.getInstance().makeIntent(context, HomeActivity.class, true);
                    }
                }, context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }, false);

            }
        });
    }

    @Override
    public int getItemCount() {
        return languagedataList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView englishLanguage, localLanguage;

        public MyViewHolder(@NonNull View iteamView) {
            super(iteamView);

            localLanguage = (TextView) iteamView.findViewById(R.id.Local_LanguageTv);
            englishLanguage = (TextView) iteamView.findViewById(R.id.english_languageTv);

        }
    }
}


