/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Helbert Monteiro
 */
@Path("middleware")
public class Middleware{
    
    private final List<Servidor> listaServidor;
    private       String         url;
    private       int            numeroPalavras;
    
    private Servidor servidor1 = new Servidor(0, true, 99);
    private Servidor servidor2 = new Servidor(1, true, 100);

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Middleware
     */
    public Middleware() {
        listaServidor  = new ArrayList<>();
        /*listaServidor.add(new Servidor(1));
        listaServidor.add(new Servidor(2));*/
    }

    /**
     * Retrieves representation of an instance of middleware.Middleware
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of Middleware
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
    
    @Path("consumo")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void consumo(String json){
        Servidor servidor = new Servidor();
        servidor = new Gson().fromJson(json, Servidor.class);
        switch(servidor.getId()){
            case 0:
                servidor1.setCpu(servidor.getCpu());
                System.out.println("Servidor 1 atualizado.           |          " + "CPU: " + servidor1.getCpu());
                break;
            case 1:
                servidor2.setCpu(servidor.getCpu());
                System.out.println("Servidor 2 atualizado.           |          " + "CPU: " + servidor2.getCpu());
                break;
        }
    }
    
    @Path("contar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String contar(String texto) throws UnsupportedEncodingException{
        System.out.println("Texto recebido!");
       
        while(!servidor1.isDisponivel() && !servidor2.isDisponivel()){
            System.out.println("Aguardando servidor disponível...");
        }
        
        if(servidor1.isDisponivel() && servidor2.isDisponivel()){
            if(servidor1.getCpu() < servidor2.getCpu()){
                System.out.println("Servidor 1 está livre e tem melhor nível de CPU: " + servidor1.getCpu());
                url = "http://192.168.43.220:8080/ServidorBalanceamento/servidor/servidor/contar";
                servidor1.setSelecionado(true);
                servidor1.setDisponivel(false);
            }else{
                System.out.println("Servidor 2 está livre e tem melhor nível de CPU: " + servidor2.getCpu());
                url = "http://192.168.43.157:8084/ServidorBalanceamento/servidor/servidor/contar";
                servidor2.setSelecionado(true);
                servidor2.setDisponivel(false);
            }
        }else{
            if(servidor1.isDisponivel()){
                System.out.println("Servidor 1 é único livre e tem nível de CPU: " + servidor1.getCpu());
                url = "http://192.168.43.220:8080/ServidorBalanceamento/servidor/servidor/contar";
                servidor1.setSelecionado(true);
                servidor1.setDisponivel(false);
            }else{
                System.out.println("Servidor 2 é único livre e tem nível de CPU: " + servidor2.getCpu());
                url = "http://192.168.43.157:8084/ServidorBalanceamento/servidor/servidor/contar";
                servidor2.setSelecionado(true);
                servidor2.setDisponivel(false);
            }
        }
        
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    numeroPalavras = Conexao.conectaWSPP(url, texto, "GET");
                }
            }).run();
            
            if(servidor1.isSelecionado()){
                servidor1.setSelecionado(false);
                servidor1.setDisponivel(true);
            }else{
                servidor2.setSelecionado(false);
                servidor2.setDisponivel(true);
            }
            
            return String.valueOf(numeroPalavras);
        }catch(Exception erro){
            System.out.println("Erro na conexao com servidor: " + erro.getMessage());
            return null;
        }
    }
    
}