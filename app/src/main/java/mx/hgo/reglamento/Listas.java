package mx.hgo.reglamento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Listas extends AppCompatActivity {
    ListView listas;
    String[] elementos = new String[]{"Persona 1","Persona 2","Persona 3","Persona 4","Persona 5","Persona 6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas);

        listas = findViewById(R.id.List);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_expandable_list_item_1,android.R.id.text1,elementos);
        listas.setAdapter(adapter);

    }
}
