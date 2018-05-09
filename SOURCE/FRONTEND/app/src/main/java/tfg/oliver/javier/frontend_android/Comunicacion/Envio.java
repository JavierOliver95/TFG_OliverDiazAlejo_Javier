package tfg.oliver.javier.frontend_android.Comunicacion;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Envio extends AsyncTask<String,Integer,String> {

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder responseBuild;
        try{
            responseBuild=new StringBuilder();
            HttpURLConnection conn = (HttpURLConnection) new URL(strings[0]).openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //metemos parametros
            JSONObject objeto = new JSONObject();
            objeto.put("data",strings[1]);

            OutputStream stream = conn.getOutputStream();
            DataOutputStream writer = new DataOutputStream(stream);


            writer.writeBytes(objeto.toString());
            writer.flush();
            writer.close();
            stream.close();

            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine())
                responseBuild.append(inStream.nextLine());

        }catch(Exception e){
            return "error";
        }
        return responseBuild.toString();
    }
}