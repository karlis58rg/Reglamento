package mx.hgo.reglamento;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TarjetasConductor extends AppCompatActivity {
    private ImageView btnLicenciaC,btnTarjetaC;
    private LinearLayout btnReglamentoTC,btnLugaresPagoTC,btnContactoTC,btnTabuladorTC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas_conductor);

        btnLicenciaC = findViewById(R.id.imgTarjetaCirculacon);
        btnTarjetaC = findViewById(R.id.imgLicenciaConducir);

        btnReglamentoTC = findViewById(R.id.lyInicio);
        btnLugaresPagoTC = findViewById(R.id.lyCategoria);
        btnContactoTC = findViewById(R.id.lyContacto);

        btnLicenciaC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnTarjetaC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TarjetasConductor.this, TarjetaCirculacion.class);
                startActivity(i);

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
