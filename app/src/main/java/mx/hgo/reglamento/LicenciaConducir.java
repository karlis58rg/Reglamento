package mx.hgo.reglamento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LicenciaConducir extends AppCompatActivity {
    Button btnContinuarLC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licencia_conducir);

        btnContinuarLC = findViewById(R.id.btnSiguienteLC);

        btnContinuarLC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LicenciaConducir.this, Infraccion.class);
                startActivity(i);
            }
        });
    }
}
