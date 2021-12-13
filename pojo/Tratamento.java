package pojo;

import java.util.Calendar;

public class Tratamento {
    private int id;
    private String nome;
    private Calendar dataInicio;
    private Calendar dataFim;
    private int idAnimal;
    private boolean terminado;

    public Tratamento(int id, String nome, Calendar dataInicio, Calendar dataFim, int idAnimal, boolean terminado) {
        this.id = id;
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.idAnimal = idAnimal;
        this.terminado = terminado;
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

    public Calendar getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Calendar dataInicio) {
        this.dataInicio = dataInicio;
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
        return "Tratamento{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
