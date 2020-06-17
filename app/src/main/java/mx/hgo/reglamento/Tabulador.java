package mx.hgo.reglamento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tabulador extends AppCompatActivity {
    ImageView btnBuscar;
    EditText txtBuscar;
    ListView listado;
    String buscador,respuestaJson,descripciones,articulos,idfracciones;
    int claves,salminimos;
    ArrayAdapter<String> adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabulador);
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
                .url("http://187.174.102.142/AppTransito/api/Tabulador?varDesc="+buscador)
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
                    Tabulador.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                respuestaJson = "null";
                                if (myResponse.equals(respuestaJson)) {
                                    Toast.makeText(getApplicationContext(), "NO SE CUENTA CON INFORMACIÓN", Toast.LENGTH_SHORT).show();
                                } else {
                                    JSONArray ja = null;
                                    ja = new JSONArray("" + myResponse + "");

                                    Log.i("sizejson", "" + ja);
                                    CargarListaView(ja);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

        });
    }

    public void CargarListaView(JSONArray ja){
        ArrayList<String> lista=new ArrayList<>();

        for(int i=0;i< ja.length();i++){

            try{
                lista.add(ja.getString(i));
            }catch(JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lista);
        listado.setAdapter(adaptador);
    }


}
