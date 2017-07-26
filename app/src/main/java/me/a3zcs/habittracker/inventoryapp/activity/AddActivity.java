package me.a3zcs.habittracker.inventoryapp.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.a3zcs.habittracker.inventoryapp.R;
import me.a3zcs.habittracker.inventoryapp.databinding.ActivityAddBinding;
import me.a3zcs.habittracker.inventoryapp.db.InventoryHelper;
import me.a3zcs.habittracker.inventoryapp.model.Product;

public class AddActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE = 1;
    ActivityAddBinding binding;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add);
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    InventoryHelper helper = new InventoryHelper(AddActivity.this);
                    if (helper.insert(new Product(
                            binding.editTextName.getText().toString(),
                            Float.valueOf(binding.editTextPrice.getText().toString()),
                            Integer.valueOf(binding.editTextQuantity.getText().toString()),
                            mCurrentPhotoPath,
                            binding.editTextSupplier.getText().toString())) != -1) {
                        Toast.makeText(AddActivity.this, AddActivity.this.getString(R.string.added), Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(AddActivity.this, AddActivity.this.getString(R.string.not_stored), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
    }

    private void takePicture() {
        Intent pic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pic.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try{
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this,
                        "me.a3zcs.habittracker.inventoryapp.activity",
                        photoFile);
                pic.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(pic, REQUEST_IMAGE);
            }
        }
    }

    private boolean validate() {
        return setEditTextError(binding.editTextName) &&
                setEditTextError(binding.editTextPrice) &&
                setEditTextError(binding.editTextQuantity) &&
                Integer.valueOf(binding.editTextQuantity.getText().toString()) > 0 &&
                setEditTextError(binding.editTextSupplier) &&
                !binding.buttonImage.getText().toString().equals(getString(R.string.image));
    }

    private boolean setEditTextError(EditText editText) {
        if (TextUtils.isEmpty(editText.getText())) {
            editText.setError(getString(R.string.empty));
            return false;
        } else return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK){
            binding.buttonImage.setText(getString(R.string.done));
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


}
