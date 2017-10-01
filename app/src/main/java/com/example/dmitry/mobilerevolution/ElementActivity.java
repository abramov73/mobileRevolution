package com.example.dmitry.mobilerevolution;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Created by user on 01.10.2017.
 */

public class ElementActivity  extends AppCompatActivity {

    private TextView nameOfProduct;
    private ImageView image;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_element);
        nameOfProduct=(TextView) findViewById(R.id.elementName);
        image=(ImageView) findViewById(R.id.elementPhoto);
        description=(TextView) findViewById(R.id.elementDescription);

        Bundle extras = getIntent().getExtras();


        image.setImageBitmap((Bitmap) extras.getParcelable("photoOfProduct"));
        nameOfProduct.setText(extras.getString("nameOfProduct"));
        description.setText(extras.getString("descriptionOfProduct"));

    }

}
