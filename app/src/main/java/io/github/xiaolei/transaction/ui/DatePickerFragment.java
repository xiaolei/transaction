package io.github.xiaolei.transaction.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import io.github.xiaolei.transaction.listener.OnDateSelectedListener;

/**
 * TODO: add comment
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    public static final String ARG_INITIAL_DATE = "arg_initial_date";
    private OnDateSelectedListener mOnDateSelectedListener;

    public DatePickerFragment() {

    }

    public static DatePickerFragment showDialog(FragmentManager fragmentManager,
                                                Date initialDate, OnDateSelectedListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        if (initialDate != null) {
            Bundle args = new Bundle();
            args.putLong(ARG_INITIAL_DATE, initialDate.getTime());
            fragment.setArguments(args);
        }

        fragment.setOnDateSelectedListener(listener);
        fragment.show(fragmentManager, DatePickerFragment.class.getSimpleName());

        return fragment;
    }

    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        mOnDateSelectedListener = listener;
    }

    protected void onDateSelectedListener(Date selectedDate) {
        if (mOnDateSelectedListener != null) {
            mOnDateSelectedListener.onDateSelected(selectedDate);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date initialDate = new Date();
        Bundle args = getArguments();
        if (args != null) {
            long value = args.getLong(ARG_INITIAL_DATE, -1);
            if (value > 0) {
                initialDate = new Date(value);
            }
        }

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        c.setTime(initialDate);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        Date selectedDate = c.getTime();
        onDateSelectedListener(selectedDate);
    }
}