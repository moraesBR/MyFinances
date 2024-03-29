package senac.myfinances;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.SearchRecentSuggestions;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import senac.myfinances.adapters.FinanceAdapter;
import senac.myfinances.models.Finance;
import senac.myfinances.models.FinanceDB;
import senac.myfinances.providers.FinanceSuggestionProvider;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Finance>> {

    public static final int OPERATION_SEARCH_LOADER = 15;

    public static FinanceDB financeDB;
    private RecyclerView rvIncoming;
    ProgressBar loading;
    LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (financeDB == null) {
            financeDB = new FinanceDB(this);
        }

        loaderManager = getSupportLoaderManager();

        loading = findViewById(R.id.loading);
        rvIncoming = findViewById(R.id.rvIncoming);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);

        rvIncoming.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        rvIncoming.setLayoutManager(layout);


        Button btnNewIncoming = findViewById(R.id.btnNewIncoming);
        btnNewIncoming.setOnClickListener(view -> {
            Intent novaFinance = new Intent(getBaseContext(), IncomingActivity.class);
            startActivity(novaFinance);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        Loader<List<Finance>> loader = loaderManager.getLoader(OPERATION_SEARCH_LOADER);

        if (loader == null) {
            loaderManager.initLoader(OPERATION_SEARCH_LOADER, null, MainActivity.this);
        } else {
            loaderManager.restartLoader(OPERATION_SEARCH_LOADER, null, MainActivity.this);
        }
    }

    @NonNull
    @Override
    public Loader<List<Finance>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<List<Finance>>(this) {
            @Nullable
            @Override
            public List<Finance> loadInBackground() {
                List<Finance> listFinance = null;

                // Get the intent, verify the action and get the query
                Intent intent = getIntent();
                if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                    String query = intent.getStringExtra(SearchManager.QUERY);

                    SearchRecentSuggestions suggestions = new SearchRecentSuggestions(getBaseContext(),
                            FinanceSuggestionProvider.AUTHORITY, FinanceSuggestionProvider.MODE);
                    suggestions.saveRecentQuery(query, null);

                    try {
                        Date dt = new SimpleDateFormat("yyyy/MM/dd").parse(query);
                        listFinance = financeDB.select(new SimpleDateFormat("yyyy-MM-dd").format(dt));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        listFinance = financeDB.read();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return listFinance;
            }

            @Override
            protected void onStartLoading() {
                loading.setVisibility(View.VISIBLE);
                forceLoad();
            }
        }

                ;
    }

    @Override
    public void onLoadFinished
            (@NonNull Loader<List<Finance>> loader, List<Finance> data) {
        loading.setVisibility(View.GONE);

        rvIncoming.setAdapter(new FinanceAdapter(data, this));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Finance>> loader) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
