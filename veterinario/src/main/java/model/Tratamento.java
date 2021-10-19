
package model;

import java.util.Calendar;


public class Tratamento {
    private int id;
    private String nome;
    private Calendar dataIni;
    private Calendar dataFim;
    private int idAnimal;
    private boolean terminado;

    public Tratamento(int id, String nome, Calendar dataIni, Calendar dataFim, int idAnimal, boolean terminado) {
        this.id = id;
        this.nome = nome;
        this.dataIni = dataIni;
        this.dataFim = dataFim;
        this.idAnimal = idAnimal;
        this.terminado = terminado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Calendar getDataIni() {
        return dataIni;
    }

    public void setDataIni(Calendar dataIni) {
        this.dataIni = dataIni;
    }

    public Calendar getDataFim() {
        return dataFim;
    }

    public void setDataFim(Calendar dataFim) {
        this.dataFim = dataFim;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public boolean isTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    @Override
    public String toString() {
        return "Tratamento{" + "id=" + id + ", nome=" + nome;
    }

    
       
}
