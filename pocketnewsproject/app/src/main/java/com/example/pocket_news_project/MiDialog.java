package com.example.pocket_news_project;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.pocket_news_project.conexion.HiloConexion;

public class MiDialog extends DialogFragment implements View.OnClickListener {

    private final Handler handler;
    private final Context applicationContext;

    Spinner categories;
    Spinner countries;
    Spinner languages;

    public MiDialog(Handler handler, Context applicationContext){

        this.handler = handler;
        this.applicationContext = applicationContext;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstance) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog, null);

        categories = view.findViewById(R.id.categories_spinner);
        countries = view.findViewById(R.id.countries_spinner);
        languages = view.findViewById(R.id.languages_spinner);

        ArrayAdapter<CharSequence> adapter_categories = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_array_categories, android.R.layout.simple_spinner_item);

        adapter_categories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categories.setAdapter(adapter_categories);

        ArrayAdapter<CharSequence> adapter_countries = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_array_countries, android.R.layout.simple_spinner_item);

        adapter_countries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        countries.setAdapter(adapter_countries);

       ArrayAdapter<CharSequence> adapter_languages = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_array_languages, android.R.layout.simple_spinner_item);

        adapter_languages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        languages.setAdapter(adapter_languages);


        Button cancelBtn = view.findViewById(R.id.cancelar);
        cancelBtn.setOnClickListener(view1 -> {
            Log.d("cancel dialog", "dismiss()");
            dismiss();
        });

        Button guardarBtn = view.findViewById(R.id.buscar_news_btn);
        guardarBtn.setOnClickListener(this);


        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onClick(View view) {

        Log.d("onClick Buscar", "buscar por filtros");

        String country = countries.getSelectedItem().toString();
        String category = categories.getSelectedItem().toString();
        String language = languages.getSelectedItem().toString();

        LanguageEnum l_enum = LanguageEnum.valueOf(language);
        String l_value = l_enum.toString();

        CountryEnum c_enum = CountryEnum.valueOf(country);
        String c_value = c_enum.toString();


        HiloConexion hiloNews = new HiloConexion(handler, applicationContext, "http://api.mediastack.com/v1/news?access_key=f54a3fd41406f035a3ce65504967147f&sort=popularity&limit=100&languages="+l_value+"&countries="+c_value+"&categories="+category, 0, false);
        hiloNews.start();

        dismiss();

    }
}
