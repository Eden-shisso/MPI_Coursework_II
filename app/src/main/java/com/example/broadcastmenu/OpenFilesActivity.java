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

public class OpenFilesActivity extends AppCompatActivity implements OnClickListener {

    private Button selectFile, shareFile;
    private ImageView fileView;



    private Uri fileUri = null;

    private final int select_file = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_files);

        values();
        OnclickListeners();

    }


    private void values() {
        selectFile = (Button) findViewById(R.id.select_file);
        shareFile = (Button) findViewById(R.id.share_file);

        fileView = (ImageView) findViewById(R.id.share_imageview);


    }


    private void OnclickListeners() {
        selectFile.setOnClickListener(this);
        shareFile.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.select_file)
        {
            Intent in = new Intent(Intent.ACTION_GET_CONTENT);
            in.setType("application/pdf");
            in.addCategory(Intent.CATEGORY_OPENABLE);
            try {
                startActivityForResult(in, select_file);
            }
            catch (android.content.ActivityNotFoundException e)
            {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        }

        else if(view.getId()==R.id.share_file)
        {

            shareFile(fileUri);

        }
    }

    protected void onActivityResult(int requestcode, int resultcode,
                                    Intent filereturnintent) {
        super.onActivityResult(requestcode, resultcode, filereturnintent);

        if (requestcode == select_file)
        {
            if (resultcode == RESULT_OK) {
                try {

                    fileUri = filereturnintent.getData();

                    Uri bitmap = fileUri;

                    if (bitmap != null) {
                        shareFile.setVisibility(View.VISIBLE);

                    } else {
                        shareFile.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),
                                "Error while decoding file.",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "File not found.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //Share File Code
    private void shareFile(Uri filePath) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        sharingIntent.setType("application/pdf");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, filePath);
        startActivity(Intent.createChooser(sharingIntent, "Share File Using"));
    }


}
