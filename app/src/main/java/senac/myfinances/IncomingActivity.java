package senac.myfinances;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;

import java.time.LocalDate;
import java.util.Calendar;

import senac.myfinances.models.Finance;
import senac.myfinances.models.SpendType;

public class IncomingActivity extends AppCompatActivity {

    private Button btn;
    private CalendarView cvDate;
    private AppCompatEditText etIncoming;
    private RadioButton rbGain;
    LocalDate date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn = findViewById(R.id.btnIncoming);
        etIncoming = findViewById(R.id.etIncoming);
        cvDate = findViewById(R.id.cvDate);
        cvDate.setDate(Calendar.getInstance().getTimeInMillis(),false,true);

        cvDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                date = LocalDate.of(i,i1,i2);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double value = Double.parseDouble(etIncoming.getText().toString());
                    senac.myfinances.models.SpendType type = rbGain.isChecked() ?
                            senac.myfinances.models.SpendType.IN :
                            senac.myfinances.models.SpendType.OUT;

                    Finance finance = new Finance(0, value, type, date);

                    if (MainActivity.financeDB.insert(finance)){
                        finish();
                    }
                } catch (Exception ex){
                    Log.e("FinanceActivity", ex.getMessage());
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
