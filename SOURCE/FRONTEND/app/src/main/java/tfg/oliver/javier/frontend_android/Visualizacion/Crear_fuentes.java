package tfg.oliver.javier.frontend_android.Visualizacion;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import tfg.oliver.javier.frontend_android.Control.Fuente;
import tfg.oliver.javier.frontend_android.Control.Sesion;
import tfg.oliver.javier.frontend_android.R;

public class Crear_fuentes extends AppCompatActivity {
    private double ver, hor;
    Fuente font;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(Crear_fuentes.this, Menu_principal.class);
        startActivity(in);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creat_font);
        SeekBar parametroVer = findViewById(R.id.seekBar2);
        SeekBar parametroHor = findViewById(R.id.seekBar3);
        final TextView pVer, pHor;
        pVer = findViewById(R.id.textView);
        pHor = findViewById(R.id.textView5);
        Button generar = findViewById(R.id.button8);
        final ImageView fuenteIm = findViewById(R.id.imageView4);
        ImageView cancelar = findViewById(R.id.imageView3), guardar=findViewById(R.id.imageView);

        parametroVer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ver= ((float)progress / 100.0);
                String txt = getText(R.string.genParam1)+": "+ver;
                pVer.setText(txt);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        parametroHor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hor=((float)progress / 100.0);
                String txt = getText(R.string.genParam2)+": "+hor;
                pHor.setText(txt);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                font = new Fuente();
                boolean res = font.generarFuente(hor+"", ver+"");

                if(res){
                    fuenteIm.setImageBitmap(font.getFuente());
                }else{
                    Toast.makeText(Crear_fuentes.this, "Error con el servidor", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),findViewById(R.id.imageView));
                popupMenu.getMenuInflater().inflate(R.menu.menu_guardar,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem item) {

                        final Dialog dialogo = new Dialog(Crear_fuentes.this, R.style.Theme_AppCompat_Dialog);
                        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        //obligamos al usuario a pulsar los botones para cerrarlo
                        dialogo.setCancelable(true);
                        //establecemos el contenido de nuestro dialog
                        dialogo.setContentView(R.layout.nombrar_font);

                        final EditText nomb = dialogo.findViewById(R.id.editText9);
                        Button go = dialogo.findViewById(R.id.button14);

                        go.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean res;
                                Fuente fontaneria = new Fuente(nomb.getText().toString());
                                switch (item.getItemId()){
                                    case R.id.mPropia:
                                        fontaneria.setUsuario(Sesion.getUserAc().getID());
                                        res = fontaneria.setFuente(font.getFuente());
                                        if(res){
                                            Toast.makeText(Crear_fuentes.this, R.string.guardar, Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(Crear_fuentes.this, "Error con el servidor", Toast.LENGTH_SHORT).show();
                                        }
                                    break;
                                    case R.id.mGeneral:
                                        res = fontaneria.setFuente(font.getFuente());
                                        if(res){
                                            Toast.makeText(Crear_fuentes.this, R.string.guardar, Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(Crear_fuentes.this, "Error con el servidor", Toast.LENGTH_SHORT).show();
                                        }
                                    break;
                                }
                                dialogo.dismiss();
                            }
                        });

                        dialogo.show();
                        return true;
                    }
                });

                popupMenu.show();
            }
        });

    }
}
