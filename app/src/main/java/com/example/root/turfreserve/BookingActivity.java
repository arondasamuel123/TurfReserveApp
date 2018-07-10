package com.example.root.turfreserve;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class BookingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText txt_t;
    EditText txt_date;
    Button tpicker;
    Button dpicker;
    EditText org;
    Button book;
    EditText players;
    EditText et_cost;
    private int day;
    private int month;
    private int year;
    private int mHour;
    private int mMinute;
    DatabaseReference databaseEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        databaseEvents = FirebaseDatabase.getInstance().getReference("events");

        book = findViewById(R.id.btn_book);
        players = findViewById(R.id.player);
        et_cost = findViewById(R.id.cost);

        org = findViewById(R.id.organizer);
        dpicker = findViewById(R.id.btn_date);
        tpicker = findViewById(R.id.btn_time);
        txt_date = findViewById(R.id.txtDate);
        txt_t = findViewById(R.id.txtTime);
        final Spinner spinner = findViewById(R.id.spinner);
        if(spinner!=null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.turf_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        if(spinner!=null) {
            spinner.setAdapter(adapter);
        }



        dpicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        txt_date.setText(year + "-" + (month + 1) + "-" +day);

                    }
                },year,month,day);
                datePickerDialog.show();


            }
        });

        tpicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        txt_t.setText(hourOfDay + ":" + minute);
                    }
                },mHour,mMinute,false);
                timePickerDialog.show();

            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String organizer = org.getText().toString().trim();
                String date = txt_date.getText().toString().trim();
                String time = txt_t.getText().toString().trim();
                String location = spinner.getSelectedItem().toString();

                int play = Integer.parseInt(players.getText().toString());
                int cost =Integer.parseInt(et_cost.getText().toString());

                addEvent(organizer, date, time, location,play, cost);
                calculate(play, cost);
            }
        });


    }

    private void addEvent(String organizer, String date, String time, String location, int play, int cost) {
           String id = databaseEvents.push().getKey();

            Event event = new Event(id, organizer,date,time, location,play,cost);
            databaseEvents.child(id).setValue(event);
            //Create intent instead of toast  and use Mpesa Activtiy
        Toast.makeText(this, "Event Added", Toast.LENGTH_SHORT).show();

    }

    private void calculate(int play, int cost) {
        // This calculatiing the cost of the booking
             int total = cost/play;
            Toast.makeText(this, "Each player will pay:"+total, Toast.LENGTH_LONG).show();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
