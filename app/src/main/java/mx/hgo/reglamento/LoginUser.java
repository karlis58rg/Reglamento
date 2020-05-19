package mx.hgo.reglamento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginUser extends AppCompatActivity {
    EditText txtPassUserLU;
    TextView lblUsuarioLU;
    String user,pass,respPass,cargarInfoUser;
    SharedPreferences share;
    SharedPreferences.Editor editor;
    Button btnEntrarLU;
    int banderaLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        cargarDatos();
        lblUsuarioLU = findViewById(R.id.lblUsuario);
        txtPassUserLU = findViewById(R.id.txtPassUser);
        btnEntrarLU = findViewById(R.id.btnEntrar);

        lblUsuarioLU.setText(cargarInfoUser);

        btnEntrarLU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtPassUserLU.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"SU CONTRASEÑA ES NECESARIA PARA INGRESAR",Toast.LENGTH_SHORT).show();
                }else if(txtPassUserLU.getText().length() < 3){
                    Toast.makeText(getApplicationContext(),"SU CONTRASEÑA NO PUEDE SER MENOR A TRES LETRAS",Toast.LENGTH_SHORT).show();
                }else {
                    getPass();
                }

            }
        });

    }

    /******************GET A LA BD***********************************/
    public void getPass() {
        user = cargarInfoUser;
        pass = txtPassUserLU.getText().toString();
        pass = pass.trim();

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://187.174.102.142/AppTransito/api/Usuarios?nombre="+user+"&pass="+pass)
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
                    String myResponse = response.body().string();
                    myResponse = myResponse.replace('"',' ');
                    myResponse = myResponse.trim();
                    String resp = myResponse;
                    String valorUser = "false";
                    if(resp.equals(valorUser)){
                        Looper.prepare(); // to be able to make toast
                        Toast.makeText(getApplicationContext(), "LO SENTIMOS, CONTRASEÑA INCORRECTA", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }else{
                        banderaLogin = 2;
                        respPass = resp;
                        Looper.prepare(); // to be able to make toast
                        guardarDatosUserLogin();
                        Toast.makeText(getApplicationContext(), "ESTAMOS PROCESANDO SU SOLICITUD, UN MOMENTO POR FAVOR", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginUser.this,Reglamento.class);
                        startActivity(i);
                        finish();
                        Looper.loop();
                    }
                    Log.i("HERE", resp);
                }
            }

        });
    }

    public void cargarDatos(){
        share = getSharedPreferences("main",MODE_PRIVATE);
        cargarInfoUser = share.getString("USER","");
    }

    private void guardarDatosUserLogin(){
        share = getSharedPreferences("main",MODE_PRIVATE);
        editor = share.edit();
        editor.putInt("STATUSUSER",banderaLogin);
        editor.commit();
    }
}
