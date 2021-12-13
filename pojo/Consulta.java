package pojo;

import java.util.Calendar;

public class Consulta {
    private int id;
    private Calendar data;
    private int hora;
    private String comentarios;
    private int idAnimal;
    private int idVeterinario;
    private int idTratamento;
    private boolean terminou;

    public Consulta(int id, Calendar data, int hora, String comentarios, int idAnimal, int idVet, int idTratamento, boolean terminou) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.comentarios = comentarios;
        this.idAnimal = idAnimal;
        this.idVeterinario = idVet;
        this.idTratamento = idTratamento;
        this.terminou = terminou;
    }

    public int getId() {
        return id;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public int getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public int getIdTratamento() {
        return idTratamento;
    }

    public void setIdTratamento(int idTratamento) {
        this.idTratamento = idTratamento;
    }

    public boolean isTerminou() {
        return terminou;
    }

    public void setTerminou(boolean terminou) {
        this.terminou = terminou;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "data=" + data.getTime() +
                '}';
    }
}
