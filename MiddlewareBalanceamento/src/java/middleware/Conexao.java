/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

import com.sun.istack.internal.Nullable;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Conexao {

    private static URL                url;
    private static HttpURLConnection  connection;
    private static OutputStreamWriter outputStreamWriter;
    private static InputStream        inputStream;
    private static BufferedReader     reader;
    private static String             linha, parametroLength;
    private static StringBuffer       resposta;

    //PARA CONEXOES GET E DELETE
    @Nullable
    public static String conectaWSGD(String urlUsuario, String metodo){
        try{
            url = new URL(urlUsuario);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(metodo);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Contetn-Language", "pt-BR");
            connection.setUseCaches(false);
            connection.connect();

            inputStream = connection.getInputStream();

            if(inputStream == null){
                return null;
            }

            reader   = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            resposta = new StringBuffer();

            while((linha = reader.readLine()) != null){
                resposta.append(linha);
            }

            if(resposta.length() == 0){
                return null;
            }else {

                if (connection != null) {
                    connection.disconnect();
                }

                if (reader != null) {
                    reader.close();
                }

                return resposta.toString();
            }

        }catch (Exception erro){
            System.out.println("Conexao - Get:\n" + erro.getMessage());
            return null;
        }
    }

    //PARA CONEXOES POST E PUT
    @Nullable
    public static int conectaWSPP(String urlUsuario, String parametro, String metodo){
        try{
            url = new URL(urlUsuario);
            connection = (HttpURLConnection) url.openConnection();

            parametroLength  = Integer.toString(parametro.getBytes().length);

            connection.setRequestMethod(metodo);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", parametroLength);
            connection.setRequestProperty("Contetn-Language", "pt-BR");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            outputStreamWriter.write(parametro);
            outputStreamWriter.flush();

            inputStream = connection.getInputStream();

            if(inputStream == null){
                return 0;
            }

            reader   = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            resposta = new StringBuffer();

            while((linha = reader.readLine()) != null){
                resposta.append(linha);
            }

            if(resposta.length() == 0){
                return 0;
            }

            if(connection != null){
                connection.disconnect();
            }

            if(reader != null){
                reader.close();
            }
            
            int r = Integer.parseInt(resposta.toString());

            System.out.println("Resposta do servidor: " + r);
            return r;

        }catch (Exception erro){
            System.out.println("Post: \n"+ erro.getMessage());
            return 0;
        }
    }

}
