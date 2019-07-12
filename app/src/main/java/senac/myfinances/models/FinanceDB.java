package senac.myfinances.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FinanceDB extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "finance.db";
    private static final int VERSAO = 1;

    public FinanceDB(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE TB_FINANCE ("
                + "id integer primary key autoincrement,"
                + "dia text,"
                + "tipo text,"
                + "valor real"
                + ")";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TB_FINANCE");
        onCreate(sqLiteDatabase);
    }



    public List<Finance> read() throws Exception{
        Cursor cursor;
        String[] campos = {"id", "dia", "tipo", "valor"};
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.query("TB_FINANCE", campos, null, null,
                null, null, null, null);

        List<Finance> finances = new LinkedList<Finance>();

        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                finances.add(new Finance(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getDouble(cursor.getColumnIndex("valor")),
                        SpendType.valueOf(cursor.getString(cursor.getColumnIndex("tipo"))),
                        LocalDate.parse(cursor.getString(cursor.getColumnIndex("dia")))

                ));
            }
        }

        db.close();
        Collections.sort(finances);
        return finances;
    }

    public boolean insert(Finance finance) {
        ContentValues values;
        long resultado;

        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put("dia", finance.getData().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        values.put("tipo", finance.getType().toString());
        values.put("valor", finance.getIncoming());

        resultado = db.insert("TB_FINANCE", null, values);
        db.close();

        if (resultado == -1) {
            Log.e("FinanceDB", "Erro ao inserir Finança");
            return false;
        } else {
            return true;
        }
    }

    public List<Finance> select(String data) throws Exception {
        Cursor cursor;
        String[] campos = {"id", "dia", "tipo", "valor"};
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.query("TB_FINANCE", campos, "date(dia) = strftime('%d/%m/%Y', ?)",
                new String[]{data}, null, null, null, null);

        List<Finance> finances = new LinkedList<>();

        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                finances.add(new Finance(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getDouble(cursor.getColumnIndex("valor")),
                        SpendType.valueOf(cursor.getString(cursor.getColumnIndex("tipo"))),
                        LocalDate.parse(cursor.getString(cursor.getColumnIndex("dia")))
                ));
            }
        }
        db.close();
        return finances;
    }

    public void delete(int id) {
        String where = "id =" + id;
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("TB_FINANCE", where, null);
        db.close();
    }

    public boolean update(Finance finance){
        ContentValues values;

        SQLiteDatabase db = this.getWritableDatabase();

        values = new ContentValues();
        values.put("dia", finance.getData().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        values.put("tipo", finance.getType().toString());
        values.put("valor", finance.getIncoming());

        int resultado = db.update("TB_FINANCE",values,"id = " + finance.getId(),null);
        db.close();

        if (resultado == -1) {
            Log.e("FinanceDB", "Erro ao atualizar Finança");
            return false;
        } else {
            return true;
        }
    }
}