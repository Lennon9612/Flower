package com.catchmind.FlowerClassificationApplication;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.catchmind.FlowerClassificationApplication.UnsplashApi.PhotoPickerActivity;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ImageClassifier classifier;
    private final int REQ_PERMISSION = 100;
    private final int REQ_CAMERA = 101;
    private final int REQ_GALLERY = 102;

    private Executor executor = Executors.newSingleThreadExecutor();


    ImageView cameraView;
    Button btnCamera, btnGallery, btnUrl, btnUnsplash, btnFind;
    String userURL;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setWidget();
        setListener();
        tedPermission();

        try {
            classifier = new ImageClassifier(MainActivity.this);
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadURL() {
        Glide.with(this).load(userURL).error(R.drawable.image_not_found).into(cameraView);
    }

    public void alertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("불러올 URL 입력");
        alert.setMessage("이미지 URL을 입력해주세요");

        final EditText imageUrl = new EditText(this);
        alert.setView(imageUrl);

        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                userURL = imageUrl.getText().toString();
                loadURL();
            }
        });

        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        alert.show();
    }


    private void setWidget() {
        btnCamera = (Button) findViewById(R.id.btnCamera);
        btnGallery = (Button) findViewById(R.id.btnGallery);
        btnUrl = (Button) findViewById(R.id.btnUrl);
        btnUnsplash = (Button) findViewById(R.id.btnUnsplash);
        btnFind = (Button) findViewById(R.id.btnFind);
        cameraView = (ImageView) findViewById(R.id.cameraView);
    }

    private void setListener() {
        btnCamera.setOnClickListener(clickListener);
        btnGallery.setOnClickListener(clickListener);
        btnUrl.setOnClickListener(clickListener);
        btnFind.setOnClickListener(clickListener);
        btnUnsplash.setOnClickListener(clickListener);
    }

    private void tedPermission(){
        PermissionListener permissionListener = new PermissionListener(){
            @Override
            public void onPermissionGranted() {
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    Uri fileUri = null;
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btnCamera:
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ContentValues values = new ContentValues(1);
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                        fileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }

                    startActivityForResult(intent, REQ_CAMERA);
                    break;

                case R.id.btnGallery:
                    intent = new Intent(intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.putExtra(intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_GALLERY);
                    break;

                case R.id.btnUrl:
                    alertDialog();
                    break;

                case R.id.btnUnsplash:
                    intent = new Intent(MainActivity.this, PhotoPickerActivity.class);
                    startActivity(intent);
                    break;

                case R.id.btnFind:
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap bitmap = ((BitmapDrawable)cameraView.getDrawable()).getBitmap();
                    Bitmap resize = Bitmap.createScaledBitmap(bitmap, 224, 224 , false);
                    String result = classifier.classifyFrame(resize);
                    resize.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent = new Intent(MainActivity.this, SubActivity.class);
                    intent.putExtra("image", byteArray);
                    intent.putExtra("flowerName",result);
                    startActivity(intent);
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_GALLERY:
                if (resultCode == RESULT_OK) {
                    fileUri = data.getData();
                    Glide.with(this).load(fileUri).error(R.drawable.image_not_found).into(cameraView);

                }
                break;
            case REQ_CAMERA:
                if (requestCode == REQ_CAMERA && resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        fileUri = data.getData();
                    }
                    if (fileUri != null) {
                        cameraView.setImageURI(fileUri);
                    } else {
                        Toast.makeText(this, "사진 파일이 없습니다.", Toast.LENGTH_LONG);
                    }
                }
                break;

        }



    }

}
