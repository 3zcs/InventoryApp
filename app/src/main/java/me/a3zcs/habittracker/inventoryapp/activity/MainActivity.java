package me.a3zcs.habittracker.inventoryapp.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.a3zcs.habittracker.inventoryapp.adapter.ProductAdapter;
import me.a3zcs.habittracker.inventoryapp.R;
import me.a3zcs.habittracker.inventoryapp.db.InventoryContract;
import me.a3zcs.habittracker.inventoryapp.db.InventoryHelper;
import me.a3zcs.habittracker.inventoryapp.model.Product;

import static android.R.attr.data;
import static android.os.Build.VERSION_CODES.N;

public class MainActivity extends AppCompatActivity {

    public static final int code = 99;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    LinearLayoutManager linearLayoutManager;
    List<Product> productList = new ArrayList<>();
    ProductAdapter adapter;
    LoaderManager loaderManager;
    InventoryHelper helper;
    TextView noItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.product_list);
        fab = (FloatingActionButton) findViewById(R.id.add_button);
        noItems = (TextView) findViewById(R.id.TextViewNoItem);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ProductAdapter(productList, this);
        recyclerView.setAdapter(adapter);
        helper = new InventoryHelper(this);
        loaderManager = getSupportLoaderManager();
        new MyLoader(new InventoryHelper(this).query()).execute();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddActivity.class), 99);
            }
        });
    }

    public void handleList(List<Product> productList) {
        this.productList = productList;
        adapter.updateList(productList);
        adapter.notifyDataSetChanged();

        noItems.setVisibility(productList != null && !productList.isEmpty() ? View.GONE : View.VISIBLE);
        recyclerView.setVisibility(productList != null && !productList.isEmpty() ? View.VISIBLE : View.GONE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == code && resultCode == RESULT_OK)
            new MyLoader(new InventoryHelper(this).query()).execute();
    }

    private class MyLoader extends AsyncTask<Void, Void, List<Product>> {
        private List<Product> products = new ArrayList<>();
        Cursor cursor;

        private MyLoader(Cursor cursor) {
            this.cursor = cursor;
        }


        @Override
        protected List<Product> doInBackground(Void... voids) {
            if (cursor.moveToFirst())
                do {
                    products.add(new Product(
                            cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry._ID)),
                            cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_NAME)),
                            Float.valueOf(cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRICE))),
                            Integer.valueOf(cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_QUANTITY))),
                            cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_IMAGE)),
                            cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_SUPPLIER))));
                }
                while (cursor.moveToNext());
            return products;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            handleList(products);
        }
    }
}
