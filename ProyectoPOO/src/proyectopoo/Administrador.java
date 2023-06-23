/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectopoo;

/**
 *
 * @author svela
 */
public class Administrador {
    private String celular;
    private String ultimoLogin;

    public Administrador() {
    }

    public Administrador(String celular, String ultimoLogin) {
        this.celular = celular;
        this.ultimoLogin = ultimoLogin;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(String ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }
    
    
}
