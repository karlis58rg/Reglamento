package mx.hgo.reglamento;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LicenciaConducir extends AppCompatActivity {
    ImageView btnMenuL,btnQrL,btnBuscarL,btnTarjetaL,btnInfraccionL;
    Button btnGuardarL;
    EditText txtLicencia,txtNombre,txtApaterno,txtAmaterno,txtFNacimiento,txtDireccion,txtSangre,txtValidez,txtClase,txtObservaciones;
    RadioGroup radioNacionalidad;
    RadioButton rMexicano,rExtranjero;
    TextView lblResultScaner;
    private LinearLayout btnReglamento,btnLugaresPago,btnContactos,btnTabulador;
    String ResultQR;
    String Tag = "LICENCIA CONDUCIR";
    String licencia,nombre,apaterno,amaterno,fNacimiento,direccion,sangre,validez,clase,observaciones;
    String resNacionalidad = "";
    String resObservaciones = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licencia_conducir);
        btnMenuL = findViewById(R.id.btnListL);
        btnQrL = findViewById(R.id.imgQrLicenciaConducir);
        btnBuscarL = findViewById(R.id.imgBuscarLicencia);
        btnTarjetaL = findViewById(R.id.imgTarjetaCirculaconL);
        btnGuardarL = findViewById(R.id.imgGuardarL);
        btnInfraccionL = findViewById(R.id.imgTerminalLC);

        txtLicencia = findViewById(R.id.txtNoLicencia);
        txtNombre = findViewById(R.id.txtNombreL);
        txtApaterno = findViewById(R.id.txtApaternoL);
        txtAmaterno = findViewById(R.id.txtApaternoL);
        txtFNacimiento = findViewById(R.id.txtFechaNacimiento);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtSangre = findViewById(R.id.txtTipoSangre);
        txtValidez = findViewById(R.id.txtValidez);
        txtClase = findViewById(R.id.txtClase);
        txtObservaciones = findViewById(R.id.txtObservacionesLC);
        radioNacionalidad = findViewById(R.id.radioNacionalidadL);

        lblResultScaner = findViewById(R.id.linkQrL);

        btnReglamento = findViewById(R.id.lyInicioL);
        btnLugaresPago = findViewById(R.id.lyCategoriaL);
        btnContactos = findViewById(R.id.lyContactoL);
        btnTabulador = findViewById(R.id.lyFavoritosL);

        btnMenuL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LicenciaConducir.this,Reglamento.class);
                startActivity(i);
            }
        });

        btnQrL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(LicenciaConducir.this).initiateScan();
            }
        });

        btnBuscarL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsuaioL();
            }
        });

        btnTarjetaL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LicenciaConducir.this,TarjetaCirculacion.class);
                startActivity(i);
            }
        });

        btnGuardarL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnInfraccionL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnReglamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LicenciaConducir.this, ViewPDFController.class);
                startActivity(i);
            }
        });
        btnLugaresPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LicenciaConducir.this, LugaresDePago.class);
                startActivity(i);
            }
        });
        btnContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LicenciaConducir.this, Contactos.class);
                startActivity(i);
            }
        });
        btnTabulador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    public void getUsuaioL() {
        licencia = txtLicencia.getText().toString();
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://187.174.102.142/AppTransito/api/LicenciaConducir?noLicencia="+licencia)
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
                    LicenciaConducir.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if(myResponse == null){
                                    Toast.makeText(getApplicationContext(),"NO SE CUENTA CON INFORMACIÓN",Toast.LENGTH_SHORT).show();
                                }else{
                                    JSONObject jObj = null;
                                    jObj = new JSONObject(""+myResponse+"");
                                    nombre = jObj.getString("NombreL");
                                    apaterno = jObj.getString("ApellidoPL");
                                    amaterno = jObj.getString("ApellidoML");
                                    fNacimiento = jObj.getString("FechaNacimiento");
                                    direccion = jObj.getString("Direccion");
                                    sangre = jObj.getString("Sangre");
                                    validez = jObj.getString("Validez");
                                    clase = jObj.getString("Clase");
                                    txtNombre.setText(nombre);
                                    txtApaterno.setText(apaterno);
                                    txtAmaterno.setText(amaterno);
                                    txtFNacimiento.setText(fNacimiento);
                                    txtDireccion.setText(direccion);
                                    txtSangre.setText(sangre);
                                    txtValidez.setText(validez);
                                    txtLicencia.setText(licencia);
                                    txtClase.setText(clase);
                                    resNacionalidad = jObj.getString("Nacionalidad");
                                    resObservaciones = jObj.getString("Observaciones");
                                    System.out.println(resNacionalidad);
                                    System.out.println(resObservaciones);
                                    if(resNacionalidad == "Mexicano"){
                                        rMexicano = (RadioButton)radioNacionalidad.getChildAt(0);
                                        rMexicano.setChecked(true);
                                    }else {
                                        rExtranjero = (RadioButton)radioNacionalidad.getChildAt(1);
                                        rExtranjero.setChecked(true);
                                    }
                                    if(resObservaciones == null){
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
