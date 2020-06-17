package mx.hgo.reglamento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import mx.com.netpay.sdk.SmartApiFactory;
import mx.com.netpay.sdk.exceptions.SmartApiException;
import mx.com.netpay.sdk.models.*;

public class Infraccion extends AppCompatActivity {
    Button btnSiguienteInfrac,btnReglamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infraccion);

       // btnSiguienteInfrac = findViewById(R.id.btnTerminaInfraccion);
       // btnReglamento = findViewById(R.id.btnVerReglamento);

        btnSiguienteInfrac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Infraccion.this,PagosImpresiones.class);
                startActivity(i);

            }
        });

        btnReglamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Infraccion.this,Reglamento.class);
                startActivity(i);
            }
        });
    }
}
