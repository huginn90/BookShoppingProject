/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iot.bookshoppingproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Provides UI for the view with List.
 */
public class CameraFragment extends Fragment {

    static final String PICFOLDER = "CameraTest";
    Button mShutter;
    CameraSurface mSurface;
    String mRootPath;
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
                e.printStackTrace();
            }

            //파일을 갤러리에 저장
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.parse("file://" + path);
            intent.setData(uri);

//                sendBroadcast(intent);

            Toast.makeText(getContext(), "사진이 저장 되었습니다" + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
            camera.startPreview();

            String ISBNnumber = BarcodeDetection(file.getAbsolutePath());
            WebScraping webScraping = new WebScraping(ISBNnumber);
            webScraping.ThreadForwebconnect();

        }

    };
    // 포커싱 성공하면 촬영 허가
    Camera.AutoFocusCallback mAutoFocus = new Camera.AutoFocusCallback() {

        public void onAutoFocus(boolean success, Camera camera) {
            mShutter.setEnabled(success);
            mSurface.mCamera.takePicture(null, null, mPicture);

        }
    };

    public String BarcodeDetection(String imagepath)  {
        // 사진찍은 이미지 저장경로
//        String imagePath = imagepath;
        // drawable에 사진이 있는 경우
//                        Bitmap myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.barcode);
        Bitmap myBitmap = BitmapFactory.decodeFile(imagepath);

//        myImageView.setImageBitmap(myBitmap);

        BarcodeDetector detector =
                new BarcodeDetector.Builder(getContext())
                        .setBarcodeFormats(Barcode.QR_CODE | Barcode.EAN_13 | Barcode.EAN_8 | Barcode.CODE_39 | Barcode.CODE_93 | Barcode.CODE_128)
                        .build();
        if (!detector.isOperational()) {
            return "Could not set up the detector!";
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
            return "해석 실패";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup fragment = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.fragment_camera, container, false);

        mSurface = (CameraSurface) fragment.findViewById(R.id.previewFrame);
        mShutter = (Button) fragment.findViewById(R.id.button_capture);
        mShutter.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                mSurface.mCamera.autoFocus(mAutoFocus);
//                mSurface.mCamera.takePicture(null, null, mPicture);
            }
        });

        //저장할 공간 /mnt/sdcard/CameraTest 이렇게 폴더 안에 파일이 생성된다
        mRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + PICFOLDER;
        File fRoot = new File(mRootPath);

        if (fRoot.exists() == false) {
            if (fRoot.mkdir() == false) {
                Toast.makeText(getContext(), "사진을 저장할 수 없습니다.", Toast.LENGTH_LONG).show();
            }
        }
        return fragment;
    }

}

