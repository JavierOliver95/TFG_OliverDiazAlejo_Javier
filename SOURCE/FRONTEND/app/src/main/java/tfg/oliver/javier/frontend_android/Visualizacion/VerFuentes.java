package tfg.oliver.javier.frontend_android.Visualizacion;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import tfg.oliver.javier.frontend_android.Control.Fuente;
import tfg.oliver.javier.frontend_android.Control.FuentesBBDD;
import tfg.oliver.javier.frontend_android.R;

public class VerFuentes extends AppCompatActivity {
    private FuentesBBDD fuentes;
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_fuentes);
        final Button generales, propias;
        generales = findViewById(R.id.button18);
        propias = findViewById(R.id.button15);
        final ListView lista = findViewById(R.id.fuentes);

        fuentes = new FuentesBBDD();
        if(!fuentes.setFuentes()){
            Toast.makeText(VerFuentes.this, R.string.falloCarga, Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Bitmap[] array = new Bitmap[fuentes.getFuentesP().length];
            Fuente [] array2 = fuentes.getFuentesP();
            for(int i=0;i<array2.length;i++){
                array[i]=array2[i].getNombreF();
            }
            ListFuentesAdapter adaptador = new ListFuentesAdapter(getBaseContext(),array);

            lista.setAdapter(adaptador);
        }

        generales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generales.setAlpha(1);
                generales.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                propias.setAlpha((float)0.7);
                propias.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                Bitmap[] array = new Bitmap[fuentes.getFuentesG().length];
                Fuente [] array2 = fuentes.getFuentesG();
                for(int i=0;i<array2.length;i++){
                    array[i]=array2[i].getNombreF();
                }
                ListFuentesAdapter adaptador = new ListFuentesAdapter(getBaseContext(),array);

                lista.setAdapter(adaptador);
            }
        });

        propias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generales.setAlpha((float)0.7);
                generales.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                propias.setAlpha(1);
                propias.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                Bitmap[] array = new Bitmap[fuentes.getFuentesP().length];
                Fuente [] array2 = fuentes.getFuentesP();
                for(int i=0;i<array2.length;i++){
                    array[i]=array2[i].getNombreF();
                }
                ListFuentesAdapter adaptador = new ListFuentesAdapter(getBaseContext(),array);

                lista.setAdapter(adaptador);
            }
        });

    }
}
