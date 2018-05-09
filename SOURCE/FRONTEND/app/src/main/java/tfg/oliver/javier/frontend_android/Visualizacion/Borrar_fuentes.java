package tfg.oliver.javier.frontend_android.Visualizacion;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tfg.oliver.javier.frontend_android.Control.Fuente;
import tfg.oliver.javier.frontend_android.Control.FuentesBBDD;
import tfg.oliver.javier.frontend_android.Control.Sesion;
import tfg.oliver.javier.frontend_android.R;

public class Borrar_fuentes extends AppCompatActivity {
    private Fuente font;

    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(Borrar_fuentes.this, Menu_principal.class);
        startActivity(in);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_fonts);

        Spinner fuentes = findViewById(R.id.selBorrar);
        final ImageView fuenteIm = findViewById(R.id.imageView5);
        Button borrar = findViewById(R.id.button7);
        FuentesBBDD fontSys = new FuentesBBDD();
        fontSys.setFuentes();
        ArrayList<CharSequence> items = new ArrayList<>();




        for(Fuente i: fontSys.getFuentesP()){
            items.add(i.getNombre());
        }
        for(Fuente j: fontSys.getFuentesG()){
            items.add(j.getNombre());
        }


        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(Borrar_fuentes.this,R.layout.lista_fuentes,items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fuentes.setAdapter(adapter);

        fuentes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            TextView fontSel = findViewById(R.id.fontSelBorrar);
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String texto = parent.getItemAtPosition(position).toString() +":";
                fontSel.setText(texto);

                font = new Fuente(parent.getItemAtPosition(position).toString());
                font.setFuente();
                fuenteIm.setImageBitmap(font.getFuente());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogo = new Dialog(Borrar_fuentes.this, R.style.Theme_AppCompat_Dialog);
                dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //obligamos al usuario a pulsar los botones para cerrarlo
                dialogo.setCancelable(true);
                //establecemos el contenido de nuestro dialog
                dialogo.setContentView(R.layout.confirmar);

                Button si = dialogo.findViewById(R.id.button17);
                Button no = dialogo.findViewById(R.id.button16);

                si.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean res = font.borrarFuente();
                        if(res){
                            fuenteIm.setImageBitmap(null);
                            TextView fontSel = findViewById(R.id.fontSelBorrar);
                            fontSel.setText("");

                            FuentesBBDD fontSys = new FuentesBBDD();
                            fontSys.setFuentes();
                            ArrayList<CharSequence> items = new ArrayList<>();

                            for(Fuente i: fontSys.getFuentesP()){
                                items.add(i.getNombre());
                            }
                            for(Fuente j: fontSys.getFuentesG()){
                                items.add(j.getNombre());
                            }
                            Spinner fuentes = findViewById(R.id.selBorrar);
                            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(Borrar_fuentes.this,R.layout.lista_fuentes,items);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            fuentes.setAdapter(adapter);

                            Toast.makeText(Borrar_fuentes.this, R.string.borrar, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Borrar_fuentes.this, "Error con el servidor", Toast.LENGTH_SHORT).show();
                        }
                        dialogo.dismiss();
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();
                    }
                });
                dialogo.show();
            }
        });
    }
}
