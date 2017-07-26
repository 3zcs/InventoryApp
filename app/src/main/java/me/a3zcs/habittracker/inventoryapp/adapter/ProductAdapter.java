package me.a3zcs.habittracker.inventoryapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import me.a3zcs.habittracker.inventoryapp.R;
import me.a3zcs.habittracker.inventoryapp.activity.DetailsActivity;
import me.a3zcs.habittracker.inventoryapp.activity.MainActivity;
import me.a3zcs.habittracker.inventoryapp.db.InventoryHelper;
import me.a3zcs.habittracker.inventoryapp.model.Product;

import static me.a3zcs.habittracker.inventoryapp.activity.MainActivity.code;

/**
 * Created by root on 25/07/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
            holder.bind(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void updateList(List<Product> productList) {
        this.productList = productList;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name,price,quantity;
        private Button buy;
        private Product item;
        ProductViewHolder(View itemView) {
            super(itemView);
            buy = itemView.findViewById(R.id.button_buy);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            quantity = itemView.findViewById(R.id.product_quantity);
            itemView.setOnClickListener(this);
        }

        void bind(final Product product){
            item = product;
            name.setText(product.getName());
            price.setText(String.valueOf(product.getPrice()+ "$"));
            quantity.setText(String.valueOf(product.getQuantity()));
            setButtonEnabled(product.getQuantity());
            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    product.setQuantity(product.getQuantity()-1);
                    quantity.setText(String.valueOf(product.getQuantity()));
                    new InventoryHelper(context).update(product,product.getId());
                    setButtonEnabled(product.getQuantity());
                }
            });
        }

        private void setButtonEnabled(int quantity) {
            buy.setEnabled(quantity != 0);
        }


        @Override
        public void onClick(View view) {
            ((MainActivity)context).startActivityForResult(new Intent(context, DetailsActivity.class).putExtra("Product",item),code);
        }
    }
}
