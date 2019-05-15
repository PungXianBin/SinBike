package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.braintreepayments.cardform.OnCardFormSubmitListener;
import com.braintreepayments.cardform.utils.CardType;
import com.braintreepayments.cardform.view.CardEditText;
import com.braintreepayments.cardform.view.CardForm;
import com.braintreepayments.cardform.view.SupportedCardTypesView;
import com.example.sinbike.R;

public class CardFormActivity extends AppCompatActivity implements OnCardFormSubmitListener,
        CardEditText.OnCardTypeChangedListener {

    private static final CardType[] SUPPORTED_CARD_TYPES = { CardType.VISA, CardType.MASTERCARD, CardType.DISCOVER,
                CardType.AMEX};

    private SupportedCardTypesView mSupportedCardTypesView;

    protected CardForm mCardForm;
    protected Button topupBtn, cancelBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_form);

        mSupportedCardTypesView = findViewById(R.id.supported_card_types);
        mSupportedCardTypesView.setSupportedCardTypes(SUPPORTED_CARD_TYPES);

        topupBtn = findViewById(R.id.topupBtn);
        cancelBtn = findViewById(R.id.cancelBtn);


        mCardForm = findViewById(R.id.card_form);
        mCardForm.cardRequired(true)
                .maskCardNumber(true)
                .maskCvv(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .setup(this);
        mCardForm.setOnCardFormSubmitListener(this);
        mCardForm.setOnCardTypeChangedListener(this);

        // Warning: this is for development purposes only and should never be done outside of this example app.
        // Failure to set FLAG_SECURE exposes your app to screenshots allowing other apps to steal card information.
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);

        topupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCardForm.isValid()) {
                    Bundle extras = getIntent().getExtras();
                    double amount = extras.getDouble("amount");
                    Intent intent = new Intent(CardFormActivity.this , Payment2FAActivity.class);
                    intent.putExtra("amount", amount);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(CardFormActivity.this, "Please fill in all details", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View args0) {
                Intent intent = new Intent(CardFormActivity.this , WalletPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onCardTypeChanged(CardType cardType) {
        if (cardType == CardType.EMPTY) {
            mSupportedCardTypesView.setSupportedCardTypes(SUPPORTED_CARD_TYPES);
        } else {
            mSupportedCardTypesView.setSelected(cardType);
        }
    }

    @Override
    public void onCardFormSubmit() {
        if (mCardForm.isValid()) {
            Toast.makeText(this, "Valid", Toast.LENGTH_SHORT).show();
        } else {
            mCardForm.validate();
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        if (mCardForm.isCardScanningAvailable()) {
            getMenuInflater().inflate(R.menu.card_io, menu);
        }

        return true;
    }


}
