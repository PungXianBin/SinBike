package com.example.sinbike.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sinbike.Constants;
import com.example.sinbike.POJO.Account;
import com.example.sinbike.POJO.Fine;
import com.example.sinbike.POJO.ParkingLot;
import com.example.sinbike.POJO.Rental;
import com.example.sinbike.POJO.Transaction;
import com.example.sinbike.R;
import com.example.sinbike.Repositories.common.Resource;
import com.example.sinbike.ViewModels.AccountViewModel;
import com.example.sinbike.ViewModels.FineViewModel;
import com.example.sinbike.ViewModels.ParkingLotViewModel;
import com.example.sinbike.ViewModels.PaymentViewModel;
import com.example.sinbike.ViewModels.RentalViewModel;
import com.example.sinbike.ViewModels.TransactionViewModel;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.Timestamp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RentalPaymentBarcodeActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    TextView qrCodeResult;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    String intentData = "";
    private ArrayList<String> lst = new ArrayList<>();
    ParkingLotViewModel parkingLotViewModel;
    List<ParkingLot> parkingLot = new ArrayList<>();
    TransactionViewModel transactionViewModel;
    PaymentViewModel paymentViewModel;
    AccountViewModel accountViewModel;
    Account account;
    RentalViewModel rentalViewModel;
    FineViewModel fineViewModel;

    double accountBalance, difference, totalamount;
    String totalamount1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);
        initViews();
        initViewModel();
        parkingLotViewModel.getAllParkinglot().observe(this, new Observer<Resource<List<ParkingLot>>>() {
            @Override
            public void onChanged(Resource<List<ParkingLot>> listResource) {
                parkingLot = listResource.data();
                for (int y = 0; y < parkingLot.size(); y++) {
                    lst.add(parkingLot.get(y).id);
                }
            }
        });
    }

    private void initViews() {
        qrCodeResult = findViewById(R.id.qrCodeResult);
        surfaceView = findViewById(R.id.surfaceView);
    }

    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

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
                    if (ActivityCompat.checkSelfPermission(RentalPaymentBarcodeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(RentalPaymentBarcodeActivity.this, new
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
                Toast.makeText(getApplicationContext(), "To prevent memory leaks qrcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {

                   // ToneGenerator toneNotification = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100); /* Setting beep sound */
                    //toneNotification.startTone(ToneGenerator.TONE_PROP_BEEP, 150);

                    intentData = barcodes.valueAt(0).displayValue; // Retrieving text from QR Code
                    for (int i = 0; i < lst.size(); i++) {
                        if (intentData.trim().equals(lst.get(i).trim())) {

                            Bundle extras = getIntent().getExtras();
                            totalamount1 = extras.getString("totalamount2");
                            String bicycleID = extras.getString("bicycleID");
                            totalamount = Double.parseDouble(totalamount1);
                            accountBalance = account.getAccountBalance();
                            difference = accountBalance - totalamount;

                            account.setAccountBalance(difference);
                            accountViewModel.update(account);

                            Transaction transaction = new Transaction();
                            transaction.setAmount(totalamount);
                            transaction.setAccountId(account.id);
                            transaction.settransactionDate(Timestamp.now());
                            transaction.setTransactionType(Constants.TRANSACTION_TYPE_RENTAL);
                            transactionViewModel.create(transaction);

                            Rental rental = new Rental();
                            rental.setAccountId(account.id);
                            rental.setAmount(totalamount);
                            rental.setRentalDate(Timestamp.now());
                            rental.setBicycleId(bicycleID);
                            rentalViewModel.createRental(rental);

                          /*  RentalPayment rentalPayment = new RentalPayment();
                            rentalPayment.setAccountId(account.id);
                            rentalPayment.setPaymentDate(Timestamp.now());
                            rentalPayment.setTotalAmount(totalamount);
                            rentalPayment.setRentalId(rental.id);
                            paymentViewModel.createRentalPayment(rentalPayment);*/

                            Intent data = new Intent(getApplicationContext(), RentalPaymentSuccessful.class); //create payment layout
                            data.putExtra("barcode3", intentData);
                            data.putExtra("bicycleID", bicycleID);
                            startActivity(data);
                            finish();
                            break;
                        } else {
                            //Intent data1 = new Intent(getApplicationContext(), RentalPaymentActivity.class);
                            //startActivity(data1);
                            //finish();
                            //Looper.prepare();
                            //Toast.makeText(getApplicationContext(), "Invalid parking lot qrcode. Please scan again!", Toast.LENGTH_SHORT).show();
                            //Looper.loop();
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Bundle extras = getIntent().getExtras();
        String bicycleID = extras.getString("bicycleID");
        double fineAmount = 5;
        Fine fine = new Fine();
        fine.setAccountId(account.id);
        fine.setAmount(fineAmount);
        fine.setFineDate(Timestamp.now());
        fine.setLocation("Pasir Ris");
        fine.setStatus(Constants.FINE_NOTPAID);
        fineViewModel.createFine(fine);

        Intent n = new Intent(RentalPaymentBarcodeActivity.this, RentalActivity.class);
        startActivity(n);
        finish();
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
        this.parkingLotViewModel = ViewModelProviders.of(this).get(ParkingLotViewModel.class);
        this.parkingLotViewModel.setLifecycleOwner(this);
        this.accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        this.account = accountViewModel.getAccount();
        this.transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        this.paymentViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);
        this.rentalViewModel = ViewModelProviders.of(this).get(RentalViewModel.class);
        this.rentalViewModel.setLifecycleOwner(this);
        this.fineViewModel = ViewModelProviders.of(this).get(FineViewModel.class);
    }
}