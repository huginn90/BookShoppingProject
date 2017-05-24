package com.iot.bookshoppingproject;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.File;
import java.io.FileOutputStream;


public class CameraActivity extends Activity {

    Button mShutter;
    MyCameraSurface mSurface;
    String mRootPath;
    static final String PICFOLDER = "CameraTest";
    String barcodeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mSurface = (MyCameraSurface)findViewById(R.id.previewFrame);
        mShutter = (Button)findViewById(R.id.button1);
        mShutter.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                // 사진을 촬영
                mSurface.mCamera.autoFocus(mAutoFocus);

                if(barcodeNumber != null) {
                    Intent intentBarcode = new Intent(getApplicationContext(), BarcodeActivity.class);
                    intentBarcode.putExtra("barcodeNumber", barcodeNumber);
                    startActivity(intentBarcode);
                }
                else {
                    Toast.makeText(getApplicationContext(), "다시 촬영", Toast.LENGTH_LONG).show();
                }


            }
        });

        //저장할 공간 /mnt/sdcard/CameraTest 이렇게 폴더 안에 파일이 생성된다
        mRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + PICFOLDER;
        File fRoot = new File(mRootPath);

        if (fRoot.exists() == false) {
            if (fRoot.mkdir() == false) {
                Toast.makeText(this, "사진을 저장할 폴더가 없습니다.", Toast.LENGTH_LONG).show();
//                finish();

                return;
            }
        }
    }
    // 포커싱 성공하면 촬영 허가
    Camera.AutoFocusCallback mAutoFocus = new Camera.AutoFocusCallback() {

        public void onAutoFocus(boolean success, Camera camera) {
            mShutter.setEnabled(success);
            mSurface.mCamera.takePicture(null, null, mPicture);

        }
    };

    // 사진 저장.
    PictureCallback mPicture = new PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            String FileName = "save.jpg";
            String path = mRootPath + "/" + FileName;

            File file = new File(path);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data);
                fos.flush();
                fos.close();
            } catch (Exception e) {

                return;
            }

            //파일을 갤러리에 저장
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.parse("file://" + path);
            intent.setData(uri);
            sendBroadcast(intent);

            Toast.makeText(getApplicationContext(), "사진이 저장 되었습니다"+file.getAbsolutePath(), Toast.LENGTH_LONG).show();
            camera.startPreview();
            barcodeNumber = DecodeBarcode(file.getAbsolutePath());

        }

    };

    public String DecodeBarcode(String imagePath) {
        // 사진찍은 이미지 저장경로
//        String imagePath = "/mnt/sdcard/CameraTest/save.jpg";
        // drawable에 사진이 있는 경우
//                        Bitmap myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.barcode);
        Bitmap myBitmap = BitmapFactory.decodeFile(imagePath);
//        myImageView.setImageBitmap(myBitmap);

        BarcodeDetector detector =
                new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.QR_CODE | Barcode.EAN_13 | Barcode.EAN_8 | Barcode.CODE_39 | Barcode.CODE_93 | Barcode.CODE_128)
                        .build();
        if (!detector.isOperational()) {
//            txtView.setText("Could not set up the detector!");
            return null;
        }

        Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
        SparseArray<Barcode> barcodes = detector.detect(frame);

        try {
            Barcode thisCode = barcodes.valueAt(0);
            return thisCode.rawValue;
//            TextView txtView = (TextView) findViewById(R.id.txtContent);
//            txtView.setText(thisCode.rawValue);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
