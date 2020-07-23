package com.catchmind.FlowerClassificationApplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.catchmind.FlowerClassificationApplication.UnsplashApi.PhotoPickerActivity;


public class SubActivity extends AppCompatActivity {

    ImageView imgResult;
    Button btnAnother, btnImgUnsplash;
    TextView txtResult;
    String flowerName;




    // 첫번째 방법
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity_home);

        Intent intent = new Intent (this, LoadingActivity.class);
        startActivity(intent);

        btnAnother = (Button) findViewById(R.id.btnAnother);
        btnImgUnsplash = (Button) findViewById(R.id.btnImgUnsplash);
        txtResult = (TextView) findViewById(R.id.txtResult);
        imgResult = (ImageView) findViewById(R.id.imgResult);


        btnAnother.setOnClickListener(clickListener);
        btnImgUnsplash.setOnClickListener(clickListener);

        Bundle extras = getIntent().getExtras();

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        flowerName = getIntent().getStringExtra("flowerName");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length); //이 액티비티를 부른 인텐트를 받는다
        imgResult.setImageBitmap(bitmap);
        txtResult.setText(flowerName);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btnAnother: {
                    intent = new Intent(SubActivity.this, MainActivity.class);
                }
                startActivity(intent);
                break;

                case R.id.btnImgUnsplash: {
                    intent = new Intent(SubActivity.this, PhotoPickerActivity.class);
                    flowerName = flowerName.split(":")[0];
                    intent.putExtra("flowerName", flowerName);
                }
                startActivity(intent);
                break;
            }
        }
    };
}


