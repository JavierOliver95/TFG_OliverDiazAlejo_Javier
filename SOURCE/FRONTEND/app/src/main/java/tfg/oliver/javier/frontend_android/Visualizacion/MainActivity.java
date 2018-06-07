package tfg.oliver.javier.frontend_android.Visualizacion;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncStatusObserver;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnSuccessListener;

import tfg.oliver.javier.frontend_android.Comunicacion.API;
import tfg.oliver.javier.frontend_android.Control.Sesion;
import tfg.oliver.javier.frontend_android.Control.Usuario;
import tfg.oliver.javier.frontend_android.R;

public class MainActivity extends AppCompatActivity {


    final String SiteKey = "6LdaU1UUAAAAAGLFtD0LLPj-oTMyq-KELwPbKUMI";
    final String SecretKey  = "6LdaU1UUAAAAAM-2jMXT8TOUdzxWgzvUcaLJFIoQ";
    private SharedPreferences pref;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        final EditText campoIp = findViewById(R.id.editText4);
        final EditText nUser = findViewById(R.id.editText2);
        final EditText passUser = findViewById(R.id.editText);

        Button login = (Button) findViewById(R.id.button2);
        Button signin = findViewById(R.id.button);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        campoIp.setText(pref.getString("UltimaIP",""));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sesion.setIP(campoIp.getText().toString());


                pref.edit().putString("UltimaIP",campoIp.getText().toString()).apply();

                Sesion.setUser(new Usuario(nUser.getText().toString()));

                boolean res = Sesion.getUserAc().login(passUser.getText().toString());

                if(res){
                    Intent in = new Intent(MainActivity.this, Menu_principal.class);
                    startActivity(in);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this,"Error con el servidor",Toast.LENGTH_SHORT).show();
                }

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(campoIp.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this,R.string.Servidor,Toast.LENGTH_SHORT).show();
                }else {
                    Sesion.setIP(campoIp.getText().toString());




                    final Dialog dialogo = new Dialog(MainActivity.this, R.style.Theme_AppCompat_Dialog);

                    dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    //obligamos al usuario a pulsar los botones para cerrarlo
                    dialogo.setCancelable(true);
                    //establecemos el contenido de nuestro dialog
                    dialogo.setContentView(R.layout.dialogo_alta_cuenta);

                    Button guardar = dialogo.findViewById(R.id.button12);
                    final EditText user = dialogo.findViewById(R.id.editText5);
                    final EditText pass = dialogo.findViewById(R.id.editText6);


                    guardar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!(user.getText().toString().equals("") || pass.getText().toString().equals(""))) {
                                SafetyNet.getClient(MainActivity.this).verifyWithRecaptcha(SiteKey).addOnSuccessListener(new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                                    @Override
                                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse recaptchaTokenResponse) {
                                        //Mandar peticion al servidor de registro de usuario
                                        Usuario nUser = new Usuario(user.getText().toString());
                                        boolean res = nUser.nuevoUser(pass.getText().toString());
                                        if (res) {
                                            Toast.makeText(MainActivity.this, R.string.Succes_on_alta, Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(MainActivity.this, "Error con el servidor", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                dialogo.dismiss();
                            } else {
                                dialogo.dismiss();
                                Toast.makeText(MainActivity.this, R.string.Fail_on_alta_datos, Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    dialogo.show();
                }
            }
        });

    }

}
