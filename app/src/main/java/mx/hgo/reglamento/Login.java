package mx.hgo.reglamento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Login extends AppCompatActivity {
    Button btnIniciarL;
    EditText txtUserL,txtPassL;
    String user,pass,respUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnIniciarL = findViewById(R.id.btnIniciar);
        txtUserL = findViewById(R.id.txtUser);
        txtPassL = findViewById(R.id.txtPass);

        btnIniciarL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUserL.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"EL USUARIO ES NECESARIO PARA INGRESAR",Toast.LENGTH_SHORT).show();
                }else if(txtPassL.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"LA CONTRASEÑA ES NECESARIA PARA INGRESAR",Toast.LENGTH_SHORT).show();
                }else if(txtUserL.getText().length() < 3){
                    Toast.makeText(getApplicationContext(),"EL USUARIO NO PUEDE SER MENOR A TRES LETRAS",Toast.LENGTH_SHORT).show();
                }else if(txtPassL.getText().length() < 3){
                    Toast.makeText(getApplicationContext(),"LA CONTRASEÑA NO PUEDE SER MENOR A TRES LETRAS",Toast.LENGTH_SHORT).show();
                }else {
                    getUsuaioL();
                }

            }
        });
    }

    /******************GET A LA BD***********************************/
    public void getUsuaioL() {
        user = txtUserL.getText().toString().toUpperCase();
        pass = txtPassL.getText().toString().toUpperCase();

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://187.174.102.142/AppTransito/api/Usuarios?user="+user+"&pass="+pass)
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
                    Login.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String resp = myResponse;
                            System.out.println(resp);
                            respUser = "true";
                            if(resp.equals(respUser)){
                                Intent i = new Intent(Login.this,Reglamento.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(getApplicationContext(),"USUARIO O CONTRASEÑA INCORRECTOS",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

        });
    }
}
