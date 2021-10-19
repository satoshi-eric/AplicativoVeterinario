/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author satos
 */
public class Animal {
    private int id;
    private String nome;
    private int anoNasc;
    private String sexo;
    private int idEspecie;
    private int idCliente;

    public Animal(int id, String nome, int anoNasc, String sexo, int idEspecie, int idCliente) {
        this.id = id;
        this.nome = nome;
        this.anoNasc = anoNasc;
        this.sexo = sexo;
        this.idEspecie = idEspecie;
        this.idCliente = idCliente;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAnoNasc() {
        return anoNasc;
    }

    public void setAnoNasc(int anoNasc) {
        this.anoNasc = anoNasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(int idEspecie) {
        this.idEspecie = idEspecie;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

//    @Override
//    public String toString() {
//        return "Animal{" + "id=" + id + ", nome=" + nome + ", anoNasc=" + anoNasc + ", sexo=" + sexo + ", idEspecie=" + idEspecie + ", idCliente=" + idCliente + '}';
//    }

    @Override
    public String toString() {
        return "Animal{" + "nome=" + nome + '}';
    }
    
    
}
