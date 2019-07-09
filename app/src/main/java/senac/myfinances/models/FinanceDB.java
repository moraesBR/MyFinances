package senac.myfinances.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.time.format.DateTimeFormatter;

public class FinanceDB extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "finance.db";
    private static int VERSION = 1;

    public FinanceDB(Context context) {
        super(context, NOME_BANCO, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE TB_FINANCE (" +
                "id integer primary key autoincrement," +
                "dia text, tipo text, valor real );";
        sqLiteDatabase.execSQL(sql);
    }

    public boolean insert(Finance finance){
        ContentValues values;
        long resultado;

        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put("dia",finance.getData().format(DateTimeFormatter.ofPattern("YYYY-MM-dd kk:mm")));
        values.put("tipo",finance.getType().toString());
        values.put("valor",finance.getIncoming());

        resultado = db.insert("TB_FINANCE",null,values);
        db.close();

        if (resultado == -1) {
            Log.e("FinanceDB","Erro ao inserir Finança");
            return false;
        }
        else
            return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TB_FINANCE");
        onCreate(sqLiteDatabase);
    }
}