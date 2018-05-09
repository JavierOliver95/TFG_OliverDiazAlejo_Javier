package tfg.oliver.javier.frontend_android.Control;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import tfg.oliver.javier.frontend_android.Comunicacion.API;

public class Fuente {
    private String nombre;
    private String base64;
    private Bitmap fuente;
    private Bitmap nombreF;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    private String usuario;
    private API api;


    public Fuente(String n){
        nombre = n;
        usuario = null;
    }

    public Fuente(){
        usuario = null;
    }

    public Fuente (Bitmap i, String n){
        nombreF  = i;
        nombre = n;
    }

    public Fuente (Bitmap i, String n, boolean s){
        nombreF  = i;
        nombre = n;
        usuario=Sesion.getUserAc().getID();
    }
    public boolean setFuente(){
        String response;
        api=new API(Sesion.getUserAc().getUrl());

        response = api.getFont(nombre);
        try{
            JSONObject u = new JSONObject(response);
            base64 = u.getString("fuente");

            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
            fuente = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        }catch (Exception e){
            return false;
        }

        return true;
    }

    public boolean setFuente(Bitmap f){

        fuente = f;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        f.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        base64 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

        String response;
        api=new API(Sesion.getUserAc().getUrl());

        if(usuario==null){
            response=api.guardarFontP(nombre,base64,"");
        }else{
            response=api.guardarFontP(nombre,base64,usuario);
        }
        return !response.equals("error");
    }

    public boolean borrarFuente(){
        String response;
        api=new API(Sesion.getUserAc().getUrl());

        response = api.borrarFont(nombre);

        return !response.equals("error");
    }


    public boolean generarFuente(String x, String y){
        String response;
        api=new API(Sesion.getUserAc().getUrl());

        response = api.generarFont(x,y);

        try{
            JSONObject u = new JSONObject(response);
            base64 = u.getString("fuente");

            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
            fuente = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        }catch (Exception e){
            return false;
        }

        return true;
    }

    public Bitmap getNombreF() {
        return nombreF;
    }

    public Bitmap getFuente() {
        return fuente;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
       return this.nombre;
    }

}
