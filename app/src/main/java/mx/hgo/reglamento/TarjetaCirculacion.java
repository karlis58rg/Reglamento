package mx.hgo.reglamento;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TarjetaCirculacion extends AppCompatActivity{

    ImageView btnQrScan,btnLicencia,btnBuscarTarjeta,btnBuscarNoSerie,btnBuscarNoPlaca;
    TextView lblResultScaner;
    String ResultQR,servicio,origen,observaciones,resOrigen,resObservaciones,resServicio,respuestaJson;
    String Tag = "TarjetaCirculación";
    EditText txtNoTarjetaTC,txtNoSerieTC,txtNoPlacaTC;
    EditText txtNombreTC,txtApaternoTC,txtAmaternoTC,txtNombreR,txtApaternoR,txtAmaternoR;
    EditText txtNoMotorTC,txtMarca,txtSubmarca,txtModelo,txtColor,txtMunicipio,txtLocalidad,txtObservaciones;
    String noTarjetaTC,noSerieTC,noPlacaTC;
    String nombreTC,aPaternoTC,aMaternoTC,nombreR,aPaternoR,aMaternoR;
    String noMotorTC,marca,subMarca,modelo,color,municipio,localidad,observacionesTC;
    Spinner spinTipoServicio;
    RadioGroup radioNacionalidadTC;
    RadioButton rNacional,rExtranjero;
    Button  btnGuardarTC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta_circulacion);

        btnBuscarTarjeta = findViewById(R.id.imgBuscarNoTarjeta);
        btnBuscarNoSerie = findViewById(R.id.imgBuscarNoTarjeta);
        btnBuscarNoPlaca = findViewById(R.id.imgBuscarNoTarjeta);
        btnQrScan = findViewById(R.id.imgQrTarjetaCirculacion);
        lblResultScaner = findViewById(R.id.linkQrTC);
        btnLicencia = findViewById(R.id.imgLicenciaTC);

        txtNoTarjetaTC = findViewById(R.id.txtNoTarjeta);
        txtNoSerieTC = findViewById(R.id.txtNoSerie);
        txtNoPlacaTC = findViewById(R.id.txtPlaca);

        txtNombreTC = findViewById(R.id.txtNombreTC);
        txtApaternoTC = findViewById(R.id.txtApaternoTC);
        txtAmaternoTC = findViewById(R.id.txtAmaternoTC);
        txtNombreR = findViewById(R.id.txtNombreR);
        txtApaternoR = findViewById(R.id.txtApaternoR);
        txtAmaternoR = findViewById(R.id.txtAmaternoR);

        txtNoMotorTC = findViewById(R.id.txtNoMotor);
        txtMarca = findViewById(R.id.txtMarca);
        txtSubmarca = findViewById(R.id.txtSubMarca);
        txtModelo = findViewById(R.id.txtModelo);
        txtColor = findViewById(R.id.txtColor);
        txtMunicipio = findViewById(R.id.txtMunicipio);
        txtLocalidad = findViewById(R.id.txtLocalidad);
        radioNacionalidadTC = findViewById(R.id.radioNacionalidad);
        txtObservaciones = findViewById(R.id.txtObservacionesTC);


        btnBuscarTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNoTarjetaTC.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"EL NÚMERO DE TARJETA ES NECESARIO",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"UN MOMENTO POR FAVOR",Toast.LENGTH_SHORT).show();
                    getNoTarjeta();
                }
            }
        });



        btnQrScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(TarjetaCirculacion.this).initiateScan();
            }
        });

        btnLicencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TarjetaCirculacion.this, LicenciaConducir.class);
                startActivity(i);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null)
            if(result.getContents() != null){
                lblResultScaner.setText(result.getContents());
                ResultQR = lblResultScaner.toString();
                Toast.makeText(this, "QR CON INFORMACIÓN", Toast.LENGTH_SHORT).show();
                Log.i(Tag, ResultQR);
            }else{
                ResultQR = "QR SIN INFORMACIÓN";
                Toast.makeText(this, ResultQR, Toast.LENGTH_SHORT).show();
            }

    }

    /********************************************************************************************************************/
    /******************GET A LA BD***********************************/
    public void getNoTarjeta() {
        noTarjetaTC = txtNoTarjetaTC.getText().toString();
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://187.174.102.142/AppTransito/api/TarjetaCirculacion?noTarjeta="+noTarjetaTC)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Looper.prepare();
                Toast.makeText(getApplicationContext(),"ERROR AL OBTENER LA INFORMACIÓN, POR FAVOR VERIFIQUE SU CONEXIÓN A INTERNET",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    TarjetaCirculacion.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                respuestaJson = "null";
                                if(myResponse.equals(respuestaJson)){
                                    Toast.makeText(getApplicationContext(),"NO SE CUENTA CON INFORMACIÓN",Toast.LENGTH_SHORT).show();
                                }else{
                                    JSONObject jObj = null;
                                    jObj = new JSONObject(""+myResponse+"");
                                    noSerieTC = jObj.getString("NoSerie");
                                    noPlacaTC = jObj.getString("Placa");
                                    nombreTC = jObj.getString("NombreP");
                                    aPaternoTC = jObj.getString("ApellidoPP");
                                    aMaternoTC = jObj.getString("ApellidoMP");
                                    nombreR = jObj.getString("NombreU");
                                    aPaternoR = jObj.getString("ApellidoPU");
                                    aMaternoR = jObj.getString("ApellidoMU");
                                    noMotorTC = jObj.getString("NoMotor");
                                    marca = jObj.getString("Marca");
                                    subMarca = jObj.getString("SubMarca");
                                    modelo = jObj.getString("Modelo");
                                    color = jObj.getString("Color");
                                    municipio = jObj.getString("Municipio");
                                    localidad = jObj.getString("Localidad");
                                    resOrigen = jObj.getString("Origen");
                                    resServicio = jObj.getString("TipoServicio");
                                    resObservaciones = jObj.getString("Observaciones");
                                    txtNoSerieTC.setText(noSerieTC);
                                    txtNoPlacaTC.setText(noPlacaTC);
                                    txtNombreTC.setText(nombreTC);
                                    txtApaternoTC.setText(aPaternoTC);
                                    txtAmaternoTC.setText(aMaternoTC);
                                    txtNombreR.setText(nombreR);
                                    txtApaternoR.setText(aPaternoR);
                                    txtAmaternoR.setText(aMaternoR);
                                    txtNoMotorTC.setText(noMotorTC);
                                    txtMarca.setText(marca);
                                    txtSubmarca.setText(subMarca);
                                    txtModelo.setText(modelo);
                                    txtColor.setText(color);
                                    txtMunicipio.setText(municipio);
                                    txtLocalidad.setText(localidad);
                                    origen = "Nacional";
                                    servicio = "Privado";
                                    observaciones = "null";
                                    if(resOrigen.equals(origen)){
                                        rNacional = (RadioButton)radioNacionalidadTC.getChildAt(0);
                                        rNacional.setChecked(true);
                                    }else {
                                        rExtranjero = (RadioButton)radioNacionalidadTC.getChildAt(1);
                                        rExtranjero.setChecked(true);
                                    }
                                    /*if(resServicio.equals(servicio)){
                                        spinTipoServicio.setSelection(((ArrayAdapter<String>)spinTipoServicio.getAdapter()).getPosition("Privado"));
                                    }else{
                                        spinTipoServicio.setSelection(((ArrayAdapter<String>)spinTipoServicio.getAdapter()).getPosition("Publico"));
                                    }*/
                                    if(resObservaciones.equals(observaciones)){
                                        txtObservaciones.setText("SIN OBSERVACIONES");
                                    }else{
                                        resObservaciones = jObj.getString("Observaciones");
                                        txtObservaciones.setText(resObservaciones);
                                    }
                                    Log.i("HERE", ""+jObj);
                                }

                            }catch(JSONException e){
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }

        });
    }

    public void getNoSerie() {
        noSerieTC = txtNoSerieTC.getText().toString();
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://187.174.102.142/AppTransito/api/TarjetaCirculacion?noSerie="+noSerieTC)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Looper.prepare();
                Toast.makeText(getApplicationContext(),"ERROR AL OBTENER LA INFORMACIÓN, POR FAVOR VERIFIQUE SU CONEXIÓN A INTERNET",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    TarjetaCirculacion.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                respuestaJson = "null";
                                if(myResponse.equals(respuestaJson)){
                                    Toast.makeText(getApplicationContext(),"NO SE CUENTA CON INFORMACIÓN",Toast.LENGTH_SHORT).show();
                                }else{
                                    JSONObject jObj = null;
                                    jObj = new JSONObject(""+myResponse+"");
                                    noTarjetaTC = jObj.getString("NoTarjeta");
                                    noPlacaTC = jObj.getString("Placa");
                                    nombreTC = jObj.getString("NombreP");
                                    aPaternoTC = jObj.getString("ApellidoPP");
                                    aMaternoTC = jObj.getString("ApellidoMP");
                                    nombreR = jObj.getString("NombreU");
                                    aPaternoR = jObj.getString("ApellidoPU");
                                    aMaternoR = jObj.getString("ApellidoMU");
                                    noMotorTC = jObj.getString("NoMotor");
                                    marca = jObj.getString("Marca");
                                    subMarca = jObj.getString("SubMarca");
                                    modelo = jObj.getString("Modelo");
                                    color = jObj.getString("Color");
                                    municipio = jObj.getString("Municipio");
                                    localidad = jObj.getString("Localidad");
                                    resOrigen = jObj.getString("Origen");
                                    resServicio = jObj.getString("TipoServicio");
                                    resObservaciones = jObj.getString("Observaciones");
                                    txtNoTarjetaTC.setText(noTarjetaTC);
                                    txtNoPlacaTC.setText(noPlacaTC);
                                    txtNombreTC.setText(nombreTC);
                                    txtApaternoTC.setText(aPaternoTC);
                                    txtAmaternoTC.setText(aMaternoTC);
                                    txtNombreR.setText(nombreR);
                                    txtApaternoR.setText(aPaternoR);
                                    txtAmaternoR.setText(aMaternoR);
                                    txtNoMotorTC.setText(noMotorTC);
                                    txtMarca.setText(marca);
                                    txtSubmarca.setText(subMarca);
                                    txtModelo.setText(modelo);
                                    txtColor.setText(color);
                                    txtMunicipio.setText(municipio);
                                    txtLocalidad.setText(localidad);
                                    origen = "Nacional";
                                    servicio = "Privado";
                                    observaciones = "null";
                                    if(resOrigen.equals(origen)){
                                        rNacional = (RadioButton)radioNacionalidadTC.getChildAt(0);
                                        rNacional.setChecked(true);
                                    }else {
                                        rExtranjero = (RadioButton)radioNacionalidadTC.getChildAt(1);
                                        rExtranjero.setChecked(true);
                                    }
                                    /*if(resServicio.equals(servicio)){
                                        spinTipoServicio.setSelection(((ArrayAdapter<String>)spinTipoServicio.getAdapter()).getPosition("Privado"));
                                    }else{
                                        spinTipoServicio.setSelection(((ArrayAdapter<String>)spinTipoServicio.getAdapter()).getPosition("Publico"));
                                    }*/
                                    if(resObservaciones.equals(observaciones)){
                                        txtObservaciones.setText("SIN OBSERVACIONES");
                                    }else{
                                        resObservaciones = jObj.getString("Observaciones");
                                        txtObservaciones.setText(resObservaciones);
                                    }
                                    Log.i("HERE", ""+jObj);
                                }

                            }catch(JSONException e){
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }

        });
    }

}
