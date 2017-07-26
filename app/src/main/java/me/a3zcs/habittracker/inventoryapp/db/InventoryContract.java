package me.a3zcs.habittracker.inventoryapp.db;

import android.provider.BaseColumns;

import static me.a3zcs.habittracker.inventoryapp.db.InventoryContract.InventoryEntry.COLUMN_IMAGE;
import static me.a3zcs.habittracker.inventoryapp.db.InventoryContract.InventoryEntry.COLUMN_NAME;
import static me.a3zcs.habittracker.inventoryapp.db.InventoryContract.InventoryEntry.COLUMN_PRICE;
import static me.a3zcs.habittracker.inventoryapp.db.InventoryContract.InventoryEntry.COLUMN_QUANTITY;
import static me.a3zcs.habittracker.inventoryapp.db.InventoryContract.InventoryEntry.COLUMN_SUPPLIER;

/**
 * Created by root on 25/07/17.
 */

public class InventoryContract {

    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + InventoryEntry.TABLE_NAME + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY NOT NULL ," +
                    COLUMN_NAME + " TEXT NOT NULL ," +
                    COLUMN_PRICE + " INTEGER NOT NULL ," +
                    COLUMN_QUANTITY + " FLOAT NOT NULL , " +
                    COLUMN_IMAGE + " TEXT NOT NULL, " +
                    COLUMN_SUPPLIER + " TEXT NOT NULL" +
                    ")";

    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + InventoryEntry.TABLE_NAME;

    static final int DB_VERSION = 1;
    static final String DB_NAME = "inventory.db";

    public static class InventoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "inventory";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_SUPPLIER = "supplier";
        }
}
