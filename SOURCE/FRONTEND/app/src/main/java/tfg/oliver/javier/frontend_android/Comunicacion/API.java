package tfg.oliver.javier.frontend_android.Comunicacion;

import java.util.concurrent.TimeUnit;

public class API {

    private Conexion conect;
    private Envio envio;
    private String url;
    private final int TIMEOUT=2000;

    public API(String url){
        this.url = url;
    }

    public String login(String user, String pass){
        String conexio;
        conect = new Conexion();
        try{
            conexio=url+"login/"+user+"/"+pass;
            return conect.execute(conexio).get(TIMEOUT, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            return "error";
        }

    }

    public String singin(String user, String pass){
        String conexio;
        conect = new Conexion();
        try{
            conexio=url+"new/"+user+"/"+pass;
            return conect.execute(conexio).get(TIMEOUT, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            return "error";
        }
    }

    public String modificarCuenta(String user, String pass, String newpass){
        String conexio;
        conect = new Conexion();
        try{
            conexio=url+"pass/"+user+"/"+pass+"/"+newpass;
            return conect.execute(conexio).get(TIMEOUT, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            return "error";
        }
    }

    public String borrarCuenta(String user){
        String conexio;
        conect = new Conexion();
        try{
            conexio=url+"del/"+user;
            return conect.execute(conexio).get(TIMEOUT, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            return "error";
        }
    }
    public String guardarFontP(String name, String font, String user){
        String conexio;
        envio = new Envio();
        try{
            conexio=url+"fuenteG/"+name+"/"+user;
            return envio.execute(conexio,font).get(TIMEOUT, TimeUnit.MILLISECONDS);
        }catch (Exception e){

            return "error";
        }
    }
    public String guardarFontG(String name, String font){
        String conexio;
        envio = new Envio();
        try{
            conexio=url+"fuenteG/"+name;
            return envio.execute(conexio,font).get(TIMEOUT, TimeUnit.MILLISECONDS);
        }catch (Exception e){

            return "error";
        }
    }
    public String borrarFont(String name){
        String conexio;
        conect = new Conexion();
        try{
            conexio=url+"fuenteD/"+name;
            return conect.execute(conexio).get(TIMEOUT, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            return "error";
        }
    }
    public String getFont(String name){
        String conexio;
        conect = new Conexion();
        try{
            conexio=url+"fuente/"+name;
            return conect.execute(conexio).get(TIMEOUT, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            return "error";
        }
    }
    public String generarFont(String x, String y){
        String conexio;
        conect = new Conexion();
        try{
            conexio=url+"fuente/"+x+"/"+y;
            return conect.execute(conexio).get(TIMEOUT, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            return "error";
        }
    }

    public String escribirFontN(String text, String x,String y){
        String conexio;
        conect = new Conexion();
        try{
            conexio=url+"texto/"+text+"/"+x+"/"+y;
            return conect.execute(conexio).get(TIMEOUT, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            return "error";
        }
    }

    public String escribirFont(String text, String name){
        String conexio;
        conect = new Conexion();
        try{
            conexio=url+"texto/"+text+"/"+name;
            return conect.execute(conexio).get(TIMEOUT, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            return "error";
        }
    }

    public String getFuentesG(){
        String conexio;
        conect = new Conexion();
        try{
            conexio=url+"generales";
            return conect.execute(conexio).get(TIMEOUT, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            return "error";
        }
    }

    public String getFuentesP(String user){
        String conexio;
        conect = new Conexion();
        try{
            conexio=url+"generales/"+user;
            return conect.execute(conexio).get(TIMEOUT, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            return "error";
        }
    }
}
