package com.example.aadharscanner.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePicker extends DialogFragment {
    long star, end;
    public static TextView selectedDate;

    public DatePicker(TextView selectedDate) {
        this.selectedDate = selectedDate;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        SimpleDateFormat f1 = new SimpleDateFormat("dd-MM-yyyy");  /*yyyy-MM-dd*/
        try {
            Date d = f1.parse(DateFactory.getInstance().changeDateValue(DateFactory.getInstance().getTodayDate()));
            Date newDate = new Date(d.getTime() - 604800000L); // 7 * 24 * 60 * 60 * 1000
            long milliseconds = newDate.getTime();
            star = milliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat f2 = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date d = f2.parse(DateFactory.getInstance().changeDateValue(DateFactory.getInstance().getTodayDate()));
            long milliseconds = d.getTime();
            end = milliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
       // datePickerDialog.getDatePicker().setMinDate(star);
       // datePickerDialog.getDatePicker().setMaxDate(end);
        return datePickerDialog;
    }

    @NonNull
    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(@NonNull android.widget.DatePicker view, int year, int month, int day) {
                    Toast.makeText(getActivity(), "selected date is " + view.getDayOfMonth()+
                            "-" + (view.getMonth() + 1) +
                            "-" + view.getYear(), Toast.LENGTH_SHORT).show();
                    String date = "" + view.getDayOfMonth() + "-" + (view.getMonth() + 1) + "-" + view.getYear();
                    AppUtils.getInstance().showLog("dateChanged" + DateFactory.getInstance().changeDateValueFordatePicker(date), DatePicker.class);
                    selectedDate.setText(DateFactory.getInstance().changeDateValueFordatePicker(date));
                }
            };

}
