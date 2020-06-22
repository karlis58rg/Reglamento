package mx.hgo.reglamento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import mx.hgo.reglamento.R;



public class CustomAdapter extends ArrayAdapter<String> {
    private ArrayList<String> Clave;
    private ArrayList<String> Descripcion;
    private ArrayList<String> Articulos;
    private ArrayList<String> IdFraccion;
    private ArrayList<String> SalMinimos;
    private Context context;


    public CustomAdapter(@NonNull Context context, ArrayList<String> Clave, ArrayList<String> Descripcion, ArrayList<String> Articulos, ArrayList<String> IdFraccion, ArrayList<String> SalMinimos) {
        super(context, R.layout.activity_tabulador);
        this.context = context;
        this.Clave = Clave;
        this.Descripcion = Descripcion;
        this.Articulos = Articulos;
        this.IdFraccion = IdFraccion;
        this.SalMinimos = SalMinimos;


    }

    @Override
    public int getCount() {
        return Clave.size();

    }

    private class myholder {

        public myholder(View view) {
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        CustomAdapter.myholder holder = null;
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.activity_tabulador, parent, false);

            holder = new myholder(view);
            view.setTag(holder);
        } else {
            holder= (myholder) view.getTag();

        }

        return view;
    }
}
