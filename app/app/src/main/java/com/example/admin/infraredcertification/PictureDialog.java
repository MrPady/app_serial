package com.example.admin.infraredcertification;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/06/02 0002.
 */

public class PictureDialog extends Dialog {
    private Context mContext;
    private PadyPicture padyPicture;
    private ImageView imageView;
    public PictureDialog(Context context, int theme,PadyPicture padyPicture) {
        super(context, theme);
        mContext = context;
        this.padyPicture=padyPicture;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_picture, null);
        imageView=(ImageView)layout.findViewById(R.id.iv_picture);
        String fileName = "/data/data/com.example.laserinfraredtest1/files/"+padyPicture.getTitle();
        Bitmap bitmap= BitmapFactory.decodeFile(fileName);
        imageView.setImageBitmap(bitmap);
        this.setContentView(layout);
    }
}
