package com.example.admin.infraredcertification;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PictureActivity extends Activity {
    private ListView listView;
    private ArrayList<PadyPicture> padyPictures=new ArrayList<>();
    private PadyDatabaseHelper padyDatabaseHelper;
    private PictureAdapter pictureAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_picture);
        padyDatabaseHelper=new PadyDatabaseHelper(this,"pictures.db",null,1);
        SQLiteDatabase db = padyDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.query("Picture", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex("time"));
                Log.i("pictureTitle",title+"++++++++++++++");
                boolean isPicture =(cursor.getInt(cursor.getColumnIndex("isPicture"))==1);
                padyPictures.add(new PadyPicture(title,isPicture));
            } while (cursor.moveToNext());
        }
        cursor.close();
        listView= (ListView) findViewById(R.id.lv_pic);
        pictureAdapter=new PictureAdapter(this,R.layout.item_picture,padyPictures);
        listView.setAdapter(pictureAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(padyPictures.get(position).isPicture()){
                    PictureDialog pictureDialog=new PictureDialog(PictureActivity.this,R.style.Dialog_Fullscreen,padyPictures.get(position));
                    pictureDialog.show();
                }else{
//                    Uri uri = Uri.parse("/data/data/com.example.laserinfraredtest1/files/"+padyPictures.get(position).getTitle());
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setDataAndType(uri, "video/*");
//                    startActivity(intent);
                    VideoDialog videoDialog=new VideoDialog(PictureActivity.this,R.style.Dialog_Fullscreen,padyPictures.get(position));
                    videoDialog.show();
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteDialog deleteDialog=new DeleteDialog(PictureActivity.this,position);
                deleteDialog.show();
                return true;
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.title_bar, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    class DeleteDialog extends Dialog{
        int id;
        private TextView tvInfo,tvYes,tvNo;
        public DeleteDialog(Context context,int id){
            super(context, R.style.FullHeightDialog);
            this.id=id;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            LayoutInflater inflater = (LayoutInflater) PictureActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.dialog_delete, null);
            tvYes=(TextView)layout.findViewById(R.id.tv_yes);
            tvNo=(TextView)layout.findViewById(R.id.tv_no);
            tvInfo=(TextView)layout.findViewById(R.id.tv_info);
            tvInfo.setText("确认要删除 "+padyPictures.get(id).getTitle()+"吗？");
            tvYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PictureActivity.this.deleteFile(padyPictures.get(id).getTitle());
                    SQLiteDatabase db = padyDatabaseHelper.getWritableDatabase();
                    db.delete("Picture", "time=?", new String[]{padyPictures.get(id).getTitle()});
                    padyPictures.remove(id);
                    pictureAdapter.notifyDataSetChanged();
                    dismiss();
                }
            });
            tvNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            this.setContentView(layout);
        }
    }

    @Override
    protected void onResume() {
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }
}
