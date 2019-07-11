package senac.myfinances;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioButton;

import java.time.LocalDate;
import java.util.Objects;

import senac.myfinances.models.Finance;


public class IncomingActivity extends AppCompatActivity {

    private AppCompatEditText etIncoming;
    private RadioButton rbGain;
    LocalDate date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btn = findViewById(R.id.btnIncoming);
        etIncoming = findViewById(R.id.etIncoming);
        rbGain = findViewById(R.id.rbGain);
        CalendarView cvDate = findViewById(R.id.cvDate);

        cvDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayofMonth) {
                date = LocalDate.of(year,(month+1),dayofMonth);
                Log.e("Date",LocalDate.of(year,month+1,dayofMonth).toString());
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double value = Double.parseDouble(Objects.requireNonNull(etIncoming.getText()).toString());
                    senac.myfinances.models.SpendType type = rbGain.isChecked() ?
                            senac.myfinances.models.SpendType.IN :
                            senac.myfinances.models.SpendType.OUT;

                    Finance finance = new Finance(0, value, type, date);

                    if (MainActivity.financeDB.insert(finance)){
                        finish();
                    }
                } catch (Exception ex){
                    final int financeActivity = Log.e("FinanceActivity", Objects.requireNonNull(ex.getMessage()));
                }
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        /*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        */
    }

}
