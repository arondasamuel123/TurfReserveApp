package com.example.root.turfreserve;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.twigafoods.daraja.Daraja;
import com.twigafoods.daraja.DarajaListener;
import com.twigafoods.daraja.model.AccessToken;
import com.twigafoods.daraja.model.LNMExpress;
import com.twigafoods.daraja.model.LNMResult;


public class MpesaActivity extends AppCompatActivity {
    private String PhoneNumber;
    private EditText etPhone;
    private Daraja daraja;
    public final static String CONSUMER_KEY="PLqyVz7j63sbYJOX1j6yCAT020yt1sOV";
    public final static String CONSUMER_SECRET="AAVdAk6wSGde7yZ5";
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpesa);
        etPhone=(EditText)findViewById(R.id.etPhoneNumber);
        daraja = Daraja.with(CONSUMER_KEY, CONSUMER_SECRET, new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
                Log.i(MpesaActivity.this.getClass().getSimpleName(), accessToken.getAccess_token());
                Toast.makeText(MpesaActivity.this, "TOKEN : " + accessToken.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Log.e(MpesaActivity.this.getClass().getSimpleName(), error);
            }
        });
        btnSend=(Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = etPhone.getText().toString();
                LNMExpress lnmExpress = new LNMExpress(
                        "174379",
                        "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
                        "100",
                        "254708374149",
                        "174379",
                        phoneNumber,
                        "http://mycallbackurl.com/checkout.php",
                        "TurfServe",
                        "Pay to TurfServe!"
                );

                daraja.requestMPESAExpress(lnmExpress,
                        new DarajaListener<LNMResult>() {
                            @Override
                            public void onResult(@NonNull LNMResult lnmResult) {
                                Log.i(MpesaActivity.this.getClass().getSimpleName(), lnmResult.ResponseDescription);
                            }

                            @Override
                            public void onError(String error) {
                                Log.i(MpesaActivity.this.getClass().getSimpleName(), error);
                            }
                        }
                );
            }
        });

    }
}

