package com.example.sinbike.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.example.sinbike.POJO.Bicycle;
import com.example.sinbike.R;
import com.example.sinbike.Repositories.common.Resource;
import com.example.sinbike.ViewModels.BicycleViewModel;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RentalBarcodeActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    TextView qrCodeResult;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    String intentData = "";
    BicycleViewModel bicycleViewModel;
    List<Bicycle> bicycleList = new ArrayList<>();
    ArrayList<String> lst = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);
        initViews();
        initViewModel();

        bicycleViewModel.getAllBicycles().observe(this, (Resource<List<Bicycle>> listResource) -> {
            bicycleList = listResource.data();
            for (int y = 0; y < bicycleList.size(); y++) {
                lst.add(bicycleList.get(y).id);
            }
        });
    }

    private void initViews() {
        qrCodeResult = findViewById(R.id.qrCodeResult);
        surfaceView = findViewById(R.id.surfaceView);
    }

    private void initialiseDetectorsAndSources() {

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(RentalBarcodeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(RentalBarcodeActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                //Toast.makeText(getApplicationContext(), "To prevent memory leaks qrcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    intentData = barcodes.valueAt(0).displayValue; // Retrieving text from QR Code
                    for (int i = 0; i < lst.size(); i++) {
                            if(intentData.trim().equals(lst.get(i).trim())) {
                                Bundle s = getIntent().getExtras();
                                String coordinate = s.getString("coordinate");
                            Intent data = new Intent(getApplicationContext(), PopActivity.class);
                            data.putExtra("barcode9", intentData);
                            data.putExtra("coordinate", String.valueOf(coordinate));
                            startActivity(data);
                            finish();
                            break;
                        }/*else {
                                Toast.makeText(RentalBarcodeActivity.this, "Invalid bicycle qrcode. Please scan again!", Toast.LENGTH_SHORT).show();
                               Intent data1 = new Intent(getApplicationContext(), RentalActivity.class);
                               startActivity(data1);
                               finish();
                               break;
                            }*/
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    public void initViewModel(){
        this.bicycleViewModel = ViewModelProviders.of(this).get(BicycleViewModel.class);
        this.bicycleViewModel.setLifecycleOwner(this);
    }

}