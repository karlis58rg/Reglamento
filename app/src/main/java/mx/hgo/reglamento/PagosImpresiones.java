package mx.hgo.reglamento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PagosImpresiones extends AppCompatActivity {
    Button btnPay,btnReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos_impresiones);
        btnReport = findViewById(R.id.btnReporte);
        btnPay = findViewById(R.id.btnPagar);

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PagosImpresiones.this,Reportes.class);
                startActivity(i);
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PagosImpresiones.this,Pagos.class);
                startActivity(i);
            }
        });
    }
}
