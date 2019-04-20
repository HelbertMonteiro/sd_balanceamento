/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

/**
 *
 * @author Helbert Monteiro
 */
public class Servidor {
    
    private int     id;
    private boolean disponivel;
    private double  cpu;
    private boolean selecionado;
    
    public Servidor(int id, boolean disponivel, double cpu){
        this.id         = id;
        this.disponivel = disponivel;
        this.cpu        = cpu;
    }
    
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

    
    public boolean isDisponivel() {
        return disponivel;
    }

    
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
    
    public boolean isSelecionado(){
        return selecionado;
    }
    
    public void setSelecionado(boolean selecionado){
        this.selecionado = selecionado;
    }
    
}
