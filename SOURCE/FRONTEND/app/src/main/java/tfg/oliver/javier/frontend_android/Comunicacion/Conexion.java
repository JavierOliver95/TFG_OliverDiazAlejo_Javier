package tfg.oliver.javier.frontend_android.Comunicacion;

import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class Conexion extends AsyncTask<String,Integer,String>{

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder responseBuild;
        try{
            responseBuild=new StringBuilder();
            HttpURLConnection conn = (HttpURLConnection) new URL(strings[0]).openConnection();
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine())
                responseBuild.append(inStream.nextLine());

        }catch(Exception e){
            return "error";
        }
        return responseBuild.toString();
    }
}

