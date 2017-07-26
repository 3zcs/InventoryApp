package me.a3zcs.habittracker.inventoryapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import me.a3zcs.habittracker.inventoryapp.model.Product;
import static me.a3zcs.habittracker.inventoryapp.db.InventoryContract.DB_NAME;
import static me.a3zcs.habittracker.inventoryapp.db.InventoryContract.DB_VERSION;
import static me.a3zcs.habittracker.inventoryapp.db.InventoryContract.SQL_CREATE_ENTRIES;
import static me.a3zcs.habittracker.inventoryapp.db.InventoryContract.SQL_DELETE_ENTRIES;

/**
 * Created by root on 25/07/17.
 */

public class InventoryHelper extends SQLiteOpenHelper {

    public InventoryHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long insert(Product product){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(InventoryContract.InventoryEntry.TABLE_NAME, null, getContentValues(product));
    }

    public Cursor queryItem(String productName){
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = InventoryContract.InventoryEntry.COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = { productName };
        return db.query(InventoryContract.InventoryEntry.TABLE_NAME,
                new String [] {InventoryContract.InventoryEntry._ID,
                        InventoryContract.InventoryEntry.COLUMN_NAME,
                        InventoryContract.InventoryEntry.COLUMN_PRICE,
                        InventoryContract.InventoryEntry.COLUMN_QUANTITY,
                        InventoryContract.InventoryEntry.COLUMN_SUPPLIER,
                        InventoryContract.InventoryEntry.COLUMN_IMAGE},
                selection,selectionArgs,null,null,null);
    }

    public Cursor query(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(InventoryContract.InventoryEntry.TABLE_NAME,
                new String [] {InventoryContract.InventoryEntry._ID,
                        InventoryContract.InventoryEntry.COLUMN_NAME,
                        InventoryContract.InventoryEntry.COLUMN_PRICE,
                        InventoryContract.InventoryEntry.COLUMN_QUANTITY,
                        InventoryContract.InventoryEntry.COLUMN_SUPPLIER,
                        InventoryContract.InventoryEntry.COLUMN_IMAGE},
                null,null,null,null,null);
    }

    public int delete(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = InventoryContract.InventoryEntry._ID + " LIKE ?";
        return db.delete(InventoryContract.InventoryEntry.TABLE_NAME,whereClause,new String[]{id});
    }

    public void update(Product product,String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = InventoryContract.InventoryEntry._ID + " LIKE ?";
        db.update(InventoryContract.InventoryEntry.TABLE_NAME,getContentValues(product),whereClause,new String[]{id});
    }

    public ContentValues getContentValues(Product product){
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLUMN_NAME, product.getName());
        values.put(InventoryContract.InventoryEntry.COLUMN_PRICE, product.getPrice());
        values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, product.getQuantity());
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER, product.getSupplier());
        values.put(InventoryContract.InventoryEntry.COLUMN_IMAGE, product.getImage());
        return values;
    }

}
