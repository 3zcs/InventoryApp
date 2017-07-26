package me.a3zcs.habittracker.inventoryapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.squareup.picasso.Picasso;
import java.io.File;
import me.a3zcs.habittracker.inventoryapp.R;
import me.a3zcs.habittracker.inventoryapp.databinding.ActivityDetailsBinding;
import me.a3zcs.habittracker.inventoryapp.db.InventoryHelper;
import me.a3zcs.habittracker.inventoryapp.model.Product;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        Intent i = getIntent();
        product = i.getParcelableExtra("Product");
        bind();
        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantityOperation(1);
            }
        });

        binding.buttonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantityOperation(-1);
            }
        });

        binding.ButtonSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, product.getSupplier());
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, getString(R.string.order)));
            }
        });
    }

    private void bind() {
        binding.TextViewName.setText(product.getName());
        binding.TextViewPrice.setText(String.valueOf(product.getPrice()));
        binding.TextViewQuantity.setText(String.valueOf(product.getQuantity()));
        binding.buttonSubtract.setEnabled(product.getQuantity() > 0);
        binding.ButtonSupplier.setText(product.getSupplier());
        Picasso.with(this)
                .load(new File(product.getImage()))
                .placeholder(R.drawable.placeholder)
                .into(binding.imageView);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                showConfirmation();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.delete))
                .setCancelable(true)
                .setMessage(R.string.confirm_msg)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new InventoryHelper(DetailsActivity.this).delete(product.getId());
                setResult(RESULT_OK);
                dialogInterface.dismiss();
                finish();
            }
        });
        AlertDialog deleteDialog = builder.create();
        deleteDialog.show();
    }

    private void quantityOperation(int i) {
        product.setQuantity(product.getQuantity() + i);
        InventoryHelper helper = new InventoryHelper(DetailsActivity.this);
        helper.update(product, product.getId());
        bind();
        setResult(RESULT_OK);
    }
}