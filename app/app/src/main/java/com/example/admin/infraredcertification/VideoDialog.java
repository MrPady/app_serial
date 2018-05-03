package com.example.admin.infraredcertification;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by Administrator on 2017/07/26 0026.
 */


public class VideoDialog extends Dialog {
    private Context mContext;
    private PadyPicture padyPicture;
    private VideoView videoView;
    private MediaController mediaController;
    public VideoDialog(Context context, int theme,PadyPicture padyPicture) {
        super(context, theme);
        mContext = context;
        this.padyPicture=padyPicture;
        mediaController=new MediaController(mContext);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_video, null);
        videoView = (VideoView)layout.findViewById(R.id.vv_video);
        mediaController.setAnchorView(videoView);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoPath("/data/data/com.example.laserinfraredtest1/files/"+padyPicture.getTitle());
        videoView.start();
        videoView.requestFocus();
        this.setContentView(layout);
    }
}
