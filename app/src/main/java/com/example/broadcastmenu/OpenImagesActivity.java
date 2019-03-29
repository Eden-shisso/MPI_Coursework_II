package com.example.broadcastmenu;


import android.support.v7.app.AppCompatActivity;


import java.io.FileNotFoundException;



import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.Toast;

public class OpenImagesActivity extends AppCompatActivity implements OnClickListener {

    private Button selectImage, shareImage;
    private ImageView imageView;



    private Uri imageUri = null;

    private final int select_photo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_images);

        values();
        OnclickListeners();

    }


    private void values() {
        selectImage = (Button) findViewById(R.id.select_image);
        shareImage = (Button) findViewById(R.id.share_image);

        imageView = (ImageView) findViewById(R.id.share_imageview);


    }


    private void OnclickListeners() {
        selectImage.setOnClickListener(this);
        shareImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.select_image)
        {
            Intent in = new Intent(Intent.ACTION_PICK);
            in.setType("image/*");
            startActivityForResult(in, select_photo);
        }

        else if(view.getId()==R.id.share_image)
        {

                shareImage(imageUri);

        }
    }

    protected void onActivityResult(int requestcode, int resultcode,
                                    Intent imagereturnintent) {
        super.onActivityResult(requestcode, resultcode, imagereturnintent);

        if (requestcode == select_photo)
        {
                if (resultcode == RESULT_OK) {
                    try {

                        imageUri = imagereturnintent.getData();

                        Bitmap bitmap = Utils.decodeUri(getApplicationContext(),
                                imageUri, 200);// call

                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                            shareImage.setVisibility(View.VISIBLE);

                        } else {
                            shareImage.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),
                                    "Error while decoding image.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (FileNotFoundException e) {

                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "File not found.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

   //Share Image Code
    private void shareImage(Uri imagePath) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        sharingIntent.setType("image/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imagePath);
        startActivity(Intent.createChooser(sharingIntent, "Share Image Using"));
    }


}
