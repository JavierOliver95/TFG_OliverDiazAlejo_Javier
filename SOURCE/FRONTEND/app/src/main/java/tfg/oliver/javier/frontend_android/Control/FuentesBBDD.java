package tfg.oliver.javier.frontend_android.Control;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import tfg.oliver.javier.frontend_android.Comunicacion.API;

public class FuentesBBDD {
    private API api;

    public Fuente[] getFuentesP() {
        return fuentesP;
    }

    public Fuente[] getFuentesG() {
        return fuentesG;
    }

    private Fuente [] fuentesP;
    private Fuente [] fuentesG;

    public FuentesBBDD(){

    }

    public boolean setFuentes(){
        String response;
        String response1;
        api = new API(Sesion.getUserAc().getUrl());

        response = api.getFuentesG();
        response1= api.getFuentesP(Sesion.getUserAc().getID());

        try{
            JSONObject u = new JSONObject(response);
            JSONObject v = new JSONObject(response1);

            JSONArray per = v.getJSONArray("fuentesG");
            JSONArray gen = u.getJSONArray("fuentesG");
            fuentesP=new Fuente[per.length()];
            fuentesG=new Fuente[gen.length()];

            if(per.length()>0){
                for(int i=0;i<per.length();i++){
                    JSONObject font = new JSONObject(per.getString(i));

                    String base64 = font.getString("fuente");
                    String name = font.getString("name");

                    byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                    Bitmap fEscrita = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    fuentesP[i]=new Fuente(fEscrita,name,true);

                }
            }

            if(gen.length()>0){
                for(int j=0;j<gen.length();j++){

                    JSONObject font = new JSONObject(gen.getString(j));

                    String base64 = font.getString("fuente");
                    String name = font.getString("name");

                    byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                    Bitmap fEscrita = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    fuentesG[j]=new Fuente(fEscrita,name);

                }
            }

        }catch(Exception e){
            return false;
        }

        return true;
    }

}
