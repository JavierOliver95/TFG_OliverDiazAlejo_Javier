package tfg.oliver.javier.frontend_android.Control;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import tfg.oliver.javier.frontend_android.Comunicacion.API;


public class Texto {
    private String texto;
    private String fuente;
    private Bitmap textoF;
    private API api;

    public Texto(String t, String f){
        texto=t;
        fuente = f;
    }
    public Texto(String t){
        texto=t;
    }
    public Texto (){

    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Bitmap getTextoF() {
        return textoF;
    }

    public void setTextoF(Bitmap textoF) {
        this.textoF = textoF;
    }

    public boolean escribirTxt(){
        String response;
        api = new API(Sesion.getUserAc().getUrl());

        response = api.escribirFont(texto,fuente);

        try{
            JSONObject u = new JSONObject(response);
            String base64 = u.getString("texto");

            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
            textoF = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        }catch (Exception e){
            return false;
        }

        return true;
    }
    public boolean escribirTxt(String x, String y){
        String response;
        api = new API(Sesion.getUserAc().getUrl());

        response = api.escribirFontN(texto,x,y);

        try{
            JSONObject u = new JSONObject(response);
            String base64 = u.getString("texto");

            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
            textoF = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        }catch (Exception e){
            return false;
        }
        return true;
    }
    public boolean guardarMemoria(){

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root);
        myDir.mkdirs();
        int image_name =  new Random().nextInt(1000);



        String fname = "Image-" + image_name+ ".jpg";
        System.out.println(fname);
        File file = new File(myDir, fname);


        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            textoF.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
