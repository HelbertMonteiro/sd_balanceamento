/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorconsumo;

import com.google.gson.Gson;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

/**
 *
 * @author Helbert Monteiro
 */
public class ServidorConsumo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
        .getOperatingSystemMXBean();
        
        Servidor servidor = new Servidor();
        
        String url = "http://localhost:8080/MiddlewareBalanceamento/middleware/middleware/consumo";
        int id = 0;
       
        while (true) {
            servidor.setId(0);
            servidor.setCpu(bean.getSystemCpuLoad());
                
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Conexao.conectaWSPP(url, new Gson().toJson(servidor), "POST");
                    System.out.println(new Gson().toJson(servidor));
                }
            }).start();
        }
        
    }
    
}
