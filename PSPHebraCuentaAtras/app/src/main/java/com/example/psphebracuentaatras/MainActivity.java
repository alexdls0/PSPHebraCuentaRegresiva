package com.example.psphebracuentaatras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btLanzar;
    private TextView tvCuentaRegresiva;
    private static final String TAG = "psphebra.HEBRA";
    private volatile boolean stop = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLanzar = findViewById(R.id.btLanzar);
        tvCuentaRegresiva = findViewById(R.id.tvCuentaRegresiva);

        btLanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stop){
                    stop = false;
                    CuentaRegresiva cuentaRegresiva = new CuentaRegresiva(20);
                    new Thread(cuentaRegresiva).start();
                }
            }
        });
    }

    class CuentaRegresiva implements Runnable{
        int seconds;

        CuentaRegresiva(int seconds){
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for(int i = 0; i <= seconds ; i++){
                if(stop)
                    return;
                try {
                    Thread.sleep(1000);
                    final int finalI = i;
                    tvCuentaRegresiva.post(new Runnable() {
                        @Override
                        public void run() {
                            tvCuentaRegresiva.setText(""+(seconds - finalI));
                        }
                    });
                    //Log.d(TAG, "Cuenta atrás: " + i);
                    if(i==seconds) {
                        stop = true;
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intent);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
