package tfg.oliver.javier.frontend_android.Control;

import tfg.oliver.javier.frontend_android.Comunicacion.API;

public class Usuario {
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    private String ID;
    private API api;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public Usuario(String name){
        ID=name;
        url = "http://"+Sesion.getIP()+"/";
    }

    public boolean login(String pass){
        String response;
        api=new API(url);

        response= api.login(ID,pass);

        return !response.equals("error");

    }

    public boolean nuevoUser(String pass){


        String response;
        api=new API(url);

        response= api.singin(ID,pass);

        return !(response.equals("error"));
    }

    public boolean modificar(String neCon, String pass){
        String response;
        api=new API(url);

        response= api.modificarCuenta(ID,pass,neCon);

        return !(response.equals("error"));
    }
    public boolean borrar(){
        String response;
        api=new API(url);

        response= api.borrarCuenta(ID);

        return !(response.equals("error"));
    }


}
