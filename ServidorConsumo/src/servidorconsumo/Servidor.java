/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorconsumo;

/**
 *
 * @author Helbert Monteiro
 */
public class Servidor {
    
    private int     id;
    private double  cpu;
    
    public Servidor(){}

    public double getCpu() {
        return cpu;
    }

    public void setCpu(double cpu) {
        this.cpu = cpu;
    }

    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id = id;
    }

    
    
}