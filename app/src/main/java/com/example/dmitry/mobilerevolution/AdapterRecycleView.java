package com.example.dmitry.mobilerevolution;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dmitry on 30.09.17.
 */

/**
 * Необходимо форматировать код, в идеале в соответствии с Java CodeStyle и Android CodeStyle
 * https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md
 * http://www.oracle.com/technetwork/java/codeconvtoc-136057.html -- читать его полностью
 * не обязательно, но нужно иметь ввиду.
 * К тому же, можно использовать комбинацию Ctrl + Alt + L для форматирования.
 */
public class AdapterRecycleView extends RecyclerView.Adapter<AdapterRecycleView.ViewHolder>  {


    private LayoutInflater inflater;
    private List<Product> products;
    private FragmentManager fragmentManager;
    public AdapterRecycleView(Context context, List<Product> list)
    {
        this.inflater = LayoutInflater.from(context);
        this.products = list;

    }

    public void setManager(FragmentManager manager){
        this.fragmentManager=manager;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.element_of_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.nameOfProduct.setText(product.getName());
        holder.photoProduct.setTag(R.drawable.bread);
        holder.photoProduct.setImageDrawable(product.getPhoto());
        holder.setDescription(product.getDescription());
        // Лучше в классе ViewHolder добавить метод
        // void init(Product product);
        // и перенести туда эту логику, потому что иначе если будет несколько типов ViewHolder'ов,
        // то здесь будет много кода
    }


    @Override
    public int getItemCount() {
        return products.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final ImageView photoProduct;
        final TextView nameOfProduct;
        final ImageButton buttonAdd;
        final View viewBackground;
        private String description;
        public ViewHolder(View itemView) {
            super(itemView);
            this.photoProduct = itemView.findViewById(R.id.elementImageViewPhoto); // начиная с 26 support library можно убрать явный каст
            this.nameOfProduct =itemView.findViewById(R.id.elementTextViewName);
            this.buttonAdd = itemView.findViewById(R.id.elementImageButtonAdd);
            this.viewBackground = itemView.findViewById(R.id.elementView);
            viewBackground.setOnClickListener(this);
        }

        public void setDescription(String description){
            this.description=description;
        }

        @Override
        public void onClick(View view) {
            if( view.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT) {
                photoProduct.buildDrawingCache();
                Bitmap photo = photoProduct.getDrawingCache();
                Bitmap watermarkimage = photo.copy(photo.getConfig(), true);
                Bundle extras = new Bundle();
                extras.putString("nameOfProduct", nameOfProduct.getText().toString());
                extras.putString("descriptionOfProduct", description);
                extras.putParcelable("photoOfProduct", watermarkimage );
                Intent intent = new Intent(view.getContext(), ElementActivity.class);
                intent.putExtras(extras);
                view.getContext().startActivity(intent);//тут будет вызываться новое активитии с подробной информацией
            } else{
                Fragment f=new ElementFragment();
                photoProduct.buildDrawingCache();
                Bitmap photo = photoProduct.getDrawingCache();
                Bitmap watermarkimage = photo.copy(photo.getConfig(), true);
                Bundle extras = new Bundle();
                extras.putString("nameOfProduct", nameOfProduct.getText().toString());
                extras.putString("descriptionOfProduct", description);
                extras.putParcelable("photoOfProduct", watermarkimage);
                f.setArguments(extras);
                fragmentManager.beginTransaction().replace(R.id.fragment_container1,f).commit();
            }

        }
    }
}
