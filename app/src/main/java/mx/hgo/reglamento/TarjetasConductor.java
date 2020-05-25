package mx.hgo.reglamento;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TarjetasConductor extends AppCompatActivity {
    private ImageView btnLicenciaTC,btnTarjetaTC;
    private LinearLayout btnReglamentoTC,btnLugaresPagoTC,btnContactoTC,btnTabuladorTC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas_conductor);

        btnLicenciaTC = findViewById(R.id.imgTarjetaCirculacon);
        btnTarjetaTC = findViewById(R.id.imgLicenciaConducir);

        btnReglamentoTC = findViewById(R.id.lyInicio);
        btnLugaresPagoTC = findViewById(R.id.lyCategoria);
        btnContactoTC = findViewById(R.id.lyContacto);

        btnLicenciaTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnTarjetaTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnReglamentoTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TarjetasConductor.this, ViewPDFController.class);
                startActivity(i);
            }
        });

        btnLugaresPagoTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TarjetasConductor.this, LugaresDePago.class);
                startActivity(i);

            }
        });

        btnContactoTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TarjetasConductor.this, Contactos.class);
                startActivity(i);

            }
        });
    }
}
