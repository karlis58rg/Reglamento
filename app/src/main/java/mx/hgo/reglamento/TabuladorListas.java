package mx.hgo.reglamento;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TabuladorListas extends AppCompatActivity {



    ImageView btnBuscar;
    EditText txtBuscar;
    ListView listado;
    String buscador, respuestaJson;
    public CustomAdapter cl;
    private ArrayList<String> Clave;
    private ArrayList<String> Descripcion;
    private ArrayList<String> Articulos;
    private ArrayList<String> IdFraccion;
    private ArrayList<String> SalMinimos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabulador_listas);

        listado = findViewById(R.id.Lista);

        btnBuscar = findViewById(R.id.imgLupa);
        txtBuscar = findViewById(R.id.txtBuscador);



        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTabulador();
            }
        });
    }

    public void getTabulador() {
        buscador = txtBuscar.getText().toString();
        buscador = buscador.trim();

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://187.174.102.142/AppTransito/api/Tabulador?varDesc=" + buscador)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "ERROR AL OBTENER LA INFORMACIÓN, POR FAVOR VERIFIQUE SU CONEXIÓN A INTERNET", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    TabuladorListas.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                respuestaJson = "null";
                                if (myResponse.equals(respuestaJson)) {
                                    Toast.makeText(getApplicationContext(), "NO SE CUENTA CON INFORMACIÓN", Toast.LENGTH_SHORT).show();
                                } else {

                                    JSONArray ja = null;

                                    try{ ja = new JSONArray("" + myResponse + "");


                                    }catch (JSONException e) {
                                        e.printStackTrace();

                                    }

                                    Clave= new ArrayList<>();
                                    Descripcion= new ArrayList<>();
                                    Articulos= new ArrayList<>();
                                    IdFraccion= new ArrayList<>();
                                    SalMinimos= new ArrayList<>();

                                    for ( int i =0;i < ja.length();i++) {

                                        try{

                                            Clave.add(ja.getJSONObject(i).getString("Clave"));
                                            Descripcion.add(ja.getJSONObject(i).getString("Descripcion"));
                                            Articulos.add(ja.getJSONObject(i).getString("Articulos"));
                                            IdFraccion.add(ja.getJSONObject(i).getString("IdFraccion"));
                                            SalMinimos.add(ja.getJSONObject(i).getString("SalMinimos"));

                                        }catch ( JSONException e){
                                            e.printStackTrace();
                                        }


                                    }



                                    if (myResponse != null){
                                        cl=new CustomAdapter(TabuladorListas.this,Clave,Descripcion,Articulos,IdFraccion,SalMinimos);
                                        cl.notifyDataSetChanged();
                                        listado.setAdapter(cl);
                                    }else   {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(TabuladorListas.this);
                                        builder.setMessage("error").setNegativeButton("PROCESAR DE NUEVO",null).create().show();
                                    }




                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

        });

    }


}

