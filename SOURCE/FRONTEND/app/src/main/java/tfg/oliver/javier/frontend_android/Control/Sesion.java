package tfg.oliver.javier.frontend_android.Control;

public class Sesion {
    private static String IPac;
    private static Usuario userAc;

    public static void setSesion(String IP,Usuario user){
        IPac=IP;
        userAc=user;
    }

    public static void setIP(String nip){
        IPac=nip;
    }
    public static void setUser(Usuario user){
        userAc=user;
    }
    public static String getIP(){
        return IPac;
    }
    public static Usuario getUserAc(){
        return userAc;
    }

    public static void cerrar(){
        IPac=null;
        userAc=null;
    }
}
