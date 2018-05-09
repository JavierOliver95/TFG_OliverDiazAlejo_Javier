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
import android.widget.Toast;

import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnSuccessListener;

import tfg.oliver.javier.frontend_android.Control.Sesion;
import tfg.oliver.javier.frontend_android.R;

public class Menu_principal extends AppCompatActivity {
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        ImageView ajustes = (ImageView) findViewById(R.id.imageView2);
        Button crear = findViewById(R.id.button3);
        Button ver = findViewById(R.id.button4);
        Button borrar = findViewById(R.id.button5);
        Button escribir = findViewById(R.id.button6);

        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),findViewById(R.id.imageView2));
                popupMenu.getMenuInflater().inflate(R.menu.menu_pantalla_menu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        final Dialog dialogo;
                        switch (item.getItemId()){
                            case R.id.menu_rv_a:
                                dialogo = new Dialog(Menu_principal.this, R.style.Theme_AppCompat_Dialog);
                                dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                //obligamos al usuario a pulsar los botones para cerrarlo
                                dialogo.setCancelable(true);
                                //establecemos el contenido de nuestro dialog
                                dialogo.setContentView(R.layout.pedir_pass);

                                Button aceptar = dialogo.findViewById(R.id.button13);
                                final EditText pass = dialogo.findViewById(R.id.editText7);
                                final EditText nPass = dialogo.findViewById(R.id.editText8);

                                aceptar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        boolean res = Sesion.getUserAc().modificar(nPass.getText().toString(),pass.getText().toString());
                                        if(res){
                                            Toast.makeText(Menu_principal.this,R.string.modificarBien,Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(Menu_principal.this, "Error con el servidor", Toast.LENGTH_SHORT).show();
                                        }
                                        dialogo.dismiss();
                                    }
                                });

                                dialogo.show();

                                return true;
                            case R.id.menu_rv_b:
                                Sesion.cerrar();
                                Intent in = new Intent(Menu_principal.this, MainActivity.class);
                                startActivity(in);
                                finish();
                                return true;
                            case R.id.menu_rv_c:
                                dialogo = new Dialog(Menu_principal.this, R.style.Theme_AppCompat_Dialog);
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
                                        boolean res = Sesion.getUserAc().borrar();
                                        if(res){
                                            Intent in = new Intent(Menu_principal.this, MainActivity.class);
                                            startActivity(in);
                                            finish();
                                        }else{
                                            Toast.makeText(Menu_principal.this, "Error con el servidor", Toast.LENGTH_SHORT).show();
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
                                return true;
                            case R.id.menu_rv_d:
                                 dialogo = new Dialog(Menu_principal.this, R.style.Theme_AppCompat_Dialog);

                                dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                //obligamos al usuario a pulsar los botones para cerrarlo
                                dialogo.setCancelable(true);
                                //establecemos el contenido de nuestro dialog
                                dialogo.setContentView(R.layout.dialogo_faq);

                                dialogo.show();
                                return true;
                            case R.id.menu_rv_e:
                                finish();
                                return true;
                            default:
                                return false;
                        }

                    }
                });

                popupMenu.show();
            }
        });

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Menu_principal.this, Crear_fuentes.class);
                startActivity(in);

            }
        });

        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Menu_principal.this, VerFuentes.class);
                startActivity(in);
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Menu_principal.this, Borrar_fuentes.class);
                startActivity(in);

            }
        });

        escribir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Menu_principal.this, Escribir.class);
                startActivity(in);

            }
        });
    }
}
