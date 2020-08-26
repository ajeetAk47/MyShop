package com.codingprotocols.myshop.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codingprotocols.myshop.R;
import com.codingprotocols.myshop.models.CartItemModel;

import org.w3c.dom.Text;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
    }

    @Override
    public int getItemViewType(int position) {
       switch (cartItemModelList.get(position).getType()){
           case 0:
               return CartItemModel.CART_ITEM;
           case 1:
               return CartItemModel.TOTAL_AMOUNT;
           default:
               return -1;
       }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

//        Switch(viewType){
//            case CartItemModel.CART_ITEM:
                    View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_layout,viewGroup,false);
                    return new CartItemViewHolder(view);
//            case CartItemModel.TOTAL_AMOUNT:
//
//            default:
//                return null;
//
//        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int resource=cartItemModelList.get(position).getProductImage();
        String title=cartItemModelList.get(position).getProductTitle();
        // todo : pending ...

    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    static class CartItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView productImage;
        private TextView productTitle;
        private TextView productPrice;
        private TextView productOldPrice;
        private TextView productQuantity;
        private TextView productRemove;


        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image);
            productTitle=itemView.findViewById(R.id.product_title);
            productPrice=itemView.findViewById(R.id.product_new_price);
            productOldPrice=itemView.findViewById(R.id.product_old_price);
            productQuantity=itemView.findViewById(R.id.product_quantity);
            productRemove=itemView.findViewById(R.id.removeProductTextView);

        }

        private void setItemDetails(int resources,String title,String newPrice,String oldPrice, String quantity){
            productImage.setImageResource(resources);
            productTitle.setText(title);
            productPrice.setText(newPrice);
            productOldPrice.setText(oldPrice);
            productQuantity.setText(quantity);
        }
    }
}
