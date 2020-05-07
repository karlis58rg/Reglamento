package mx.hgo.reglamento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Reportes extends AppCompatActivity {

    Button btnEnviarEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        btnEnviarEmail = findViewById(R.id.btnEnviarPorEmail);

        btnEnviarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Reportes.this, ViewPDFController.class);
                startActivity(i);
            }
        });
    }
}
