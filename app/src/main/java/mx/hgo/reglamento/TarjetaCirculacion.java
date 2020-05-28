package mx.hgo.reglamento;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class TarjetaCirculacion extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btnQrScan,btnOpenResultQr,btnContinuarTC;
    private TextView lblResultScaner;
    private Spinner sTipoServicio,sMarca,sSubMarca,sModelo,sColor;
    String openResultQR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta_circulacion);

      /*btnQrScan = findViewById(R.id.btnQr);
        lblResultScaner = findViewById(R.id.lblResult);
        btnOpenResultQr = findViewById(R.id.btnOpenInfo);
        btnQrScan.setOnClickListener(mOnClickListener);
        btnOpenResultQr.setVisibility(View.INVISIBLE);
        btnContinuarTC = findViewById(R.id.btnSiguienteTC);

        sTipoServicio = findViewById(R.id.spinTipoServicio);
        sMarca = findViewById(R.id.spinTipoServicio);
        sSubMarca = findViewById(R.id.spinTipoServicio);
        sModelo = findViewById(R.id.spinTipoServicio);
        sColor = findViewById(R.id.spinTipoServicio);*/

        /*
        ArrayAdapter<CharSequence> adapterTipoServicio = ArrayAdapter.createFromResource(this,R.array.arrayMarca, android.R.layout.simple_spinner_item);
        adapterTipoServicio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTipoServicio.setAdapter(adapterTipoServicio);
        sTipoServicio.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterMarca = ArrayAdapter.createFromResource(this,R.array.arrayMarca, android.R.layout.simple_spinner_item);
        adapterMarca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sMarca.setAdapter(adapterMarca);
        sMarca.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterSubMarca = ArrayAdapter.createFromResource(this,R.array.arraySubMarca, android.R.layout.simple_spinner_item);
        adapterSubMarca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sSubMarca.setAdapter(adapterSubMarca);
        sSubMarca.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterModelo = ArrayAdapter.createFromResource(this,R.array.arrayModelo, android.R.layout.simple_spinner_item);
        adapterModelo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sModelo.setAdapter(adapterModelo);
        sModelo.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterColor = ArrayAdapter.createFromResource(this,R.array.arrayColor, android.R.layout.simple_spinner_item);
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sColor.setAdapter(adapterColor);
        sColor.setOnItemSelectedListener(this);*/

      /*  btnOpenResultQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse(openResultQR);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });*/

       /* btnContinuarTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TarjetaCirculacion.this, LicenciaConducir.class);
                startActivity(i);
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null)
            if(result.getContents() != null){
                btnOpenResultQr.setVisibility(View.VISIBLE);
                lblResultScaner.setText("Información encontrada:\n" + result.getContents());
                openResultQR = result.getContents();
            }else{
                lblResultScaner.setText("EL CODIGO NO CONTIENE INFORMACION");
                btnOpenResultQr.setVisibility(View.INVISIBLE);
            }

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //case R.id.btnQr:
                  //  new IntentIntegrator(TarjetaCirculacion.this).initiateScan();
            }
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
