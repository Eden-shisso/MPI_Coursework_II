package com.example.broadcastmenu;


import android.media.ThumbnailUtils;
import android.provider.MediaStore;
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
import android.widget.VideoView;

public class OpenVideosActivity extends AppCompatActivity implements OnClickListener {

    private Button selectVideos, shareVideos;
    private VideoView videoView;



    private Uri videoUri = null;

    private final int select_video = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_videos);

        values();
        OnclickListeners();

    }


    private void values() {
        selectVideos = (Button) findViewById(R.id.select_video);
        shareVideos = (Button) findViewById(R.id.share_video);

        videoView = (VideoView) findViewById(R.id.share_videoview);


    }


    private void OnclickListeners() {
        selectVideos.setOnClickListener(this);
        shareVideos.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.select_video)
        {
            Intent in = new Intent(Intent.ACTION_PICK);
            in.setType("video/*");
            in.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(in, select_video);
        }

        else if(view.getId()==R.id.share_video)
        {

            shareImage(videoUri);

        }
    }

    protected void onActivityResult(int requestcode, int resultcode,
                                    Intent imagereturnintent) {
        super.onActivityResult(requestcode, resultcode, imagereturnintent);

        if (requestcode == select_video)
        {
            if (resultcode == RESULT_OK) {
                try {

                    videoUri = imagereturnintent.getData();
                    String videoFile = videoUri.toString();

                    //Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoFile, MediaStore.Images.Thumbnails.MINI_KIND);

                    if (videoUri != null) {
                        videoView.setVideoURI(videoUri);
                        shareVideos.setVisibility(View.VISIBLE);

                    } else {
                        shareVideos.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),
                                "Error while decoding video.",
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

    //Share Image Code
    private void shareImage(Uri imagePath) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        sharingIntent.setType("video/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imagePath);
        startActivity(Intent.createChooser(sharingIntent, "Share Image Using"));
    }

}
