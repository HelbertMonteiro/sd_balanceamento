/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Helbert Monteiro
 */
public class Contador {
    
    String[] listaPalavra;
    String[] quebraLinhas;
    
    public int contar(String texto){
        quebraLinhas = texto.split("\n");
        listaPalavra = texto.split(" ");
        return listaPalavra.length + (quebraLinhas.length - 1);
    }
    
}
