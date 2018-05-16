package tfg.oliver.javier.frontend_android.Visualizacion;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import tfg.oliver.javier.frontend_android.Control.Fuente;
import tfg.oliver.javier.frontend_android.Control.FuentesBBDD;
import tfg.oliver.javier.frontend_android.Control.Texto;
import tfg.oliver.javier.frontend_android.R;

public class Escribir extends AppCompatActivity {
    private double ver, hor;
    private Texto textTras;
    private String fontName;
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(Escribir.this, Menu_principal.class);
        startActivity(in);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.escrbir);
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        //fuentes
        FuentesBBDD fontSys = new FuentesBBDD();
        fontSys.setFuentes();


        Button generar = findViewById(R.id.button9);

        final RadioButton yacreada = findViewById(R.id.radioButton3);
        final RadioButton nueva = findViewById(R.id.radioButton4);
        final Spinner fuentes = findViewById(R.id.spinner);
        final SeekBar vertical,horizontal;
        final TextView pVer, pHor;
        pVer = findViewById(R.id.textView8);
        pHor = findViewById(R.id.textView9);
        vertical = findViewById(R.id.seekBar);
        horizontal = findViewById(R.id.seekBar4);
        final EditText textamento = findViewById(R.id.editText3);
        vertical.setEnabled(false);
        horizontal.setEnabled(false);

        generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textTras = new Texto(textamento.getText().toString(),fontName);
                boolean res = false;
                if (yacreada.isChecked()){
                    res = textTras.escribirTxt();
                }else
                if(nueva.isChecked()){
                    res = textTras.escribirTxt(hor+"", ver+"");
                }

                final Dialog dialogo = new Dialog(Escribir.this,R.style.Theme_AppCompat_Dialog);

                dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //obligamos al usuario a pulsar los botones para cerrarlo
                dialogo.setCancelable(false);
                //establecemos el contenido de nuestro dialog
                dialogo.setContentView(R.layout.dialogo_texto);
                ImageView imagenTxt = dialogo.findViewById(R.id.imageView6);

                imagenTxt.setImageBitmap(textTras.getTextoF());

                Button guardar = dialogo.findViewById(R.id.button10);
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(textTras.guardarMemoria()){
                            Toast.makeText(Escribir.this, R.string.guardarText, Toast.LENGTH_SHORT).show();
                            dialogo.dismiss();
                        }else{
                            Toast.makeText(Escribir.this, "Error guardando imagen", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Button cancelar = dialogo.findViewById(R.id.button11);
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();
                    }
                });

                if(res){
                    dialogo.show();
                }else{
                    Toast.makeText(Escribir.this, "Error con el servidor", Toast.LENGTH_SHORT).show();
                }
            }
        });

        yacreada.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    fuentes.setEnabled(false);
                }else{
                    fuentes.setEnabled(true);
                }
            }
        });
        nueva.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    vertical.setEnabled(false);
                    horizontal.setEnabled(false);

                }else{
                    vertical.setEnabled(true);
                    horizontal.setEnabled(true);
                }
            }
        });

        vertical.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        horizontal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(Escribir.this,R.layout.lista_fuentes);


        for(Fuente i: fontSys.getFuentesP()){
            adapter.add(i.getNombre());
        }
        for(Fuente j: fontSys.getFuentesG()){
            adapter.add(j.getNombre());
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fuentes.setAdapter(adapter);

        fuentes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fontName = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


    }
}
