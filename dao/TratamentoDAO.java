package dao;

import pojo.Tratamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojo.Animal;
import pojo.Veterinario;

public class TratamentoDAO extends GenericDAO {
    private static TratamentoDAO instance;

    private TratamentoDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static TratamentoDAO getInstance() {
        return (instance==null?(instance = new TratamentoDAO()):instance);
    }

    // CRUD
    public Tratamento create(
            String nome,
            Calendar dataInicio,
            Calendar dataFim,
            int idAnimal,
            boolean terminado
    ) {
        try {
            PreparedStatement stmt;
            stmt = GenericDAO.getConnection().prepareStatement(
                    "INSERT INTO tratamento (nome, data_inicio, data_fim, id_animal, terminado) VALUES (?,?,?,?,?)");
            stmt.setString(1, nome);
            stmt.setDate(2, calendarToSqlDate(dataInicio));
            stmt.setDate(3, calendarToSqlDate((dataFim)));
            stmt.setInt(4, idAnimal);
            stmt.setBoolean(5, terminado);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("tratamento","id"));
    }


    private Tratamento buildObject(ResultSet rs) {
        Tratamento tratamento = null;
        try {
            tratamento = new Tratamento(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    sqlDateToCalendar(rs.getDate("data_inicio")),
                    sqlDateToCalendar(rs.getDate("data_fim")),
                    rs.getInt("id_animal"),
                    rs.getBoolean("terminado")
            );
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return tratamento;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Tratamento> tratamentos = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                tratamentos.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return tratamentos;
    }

    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM tratamento");
    }

    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM tratamento WHERE id = " + lastId("tratamento","id"));
    }

    // RetrieveById
    public Tratamento retrieveById(int id) {
        List<Tratamento> tratamentos = this.retrieve("SELECT * FROM tratamento WHERE id = " + id);
        return (tratamentos.isEmpty()?null:tratamentos.get(0));
    }

    public List retrieveByNome(String nome) {
        return this.retrieve("SELECT * FROM tratamento WHERE nome = " + nome);
    }

    public List retrieveByIdAnimal(int idAnimal) {
        return this.retrieve("SELECT * FROM tratamento WHERE id_animal = " + idAnimal);
    }

    public List retrieveByTerminado(boolean terminado) {
        return this.retrieve("SELECT * FROM tratamento WHERE terminado = " + terminado);
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarComentarios(String comentarios) {
        return this.retrieve("SELECT * FROM tratamento WHERE comentarios LIKE '%" + comentarios + "%'");
    }
    
    public List retrieveByAnimal(Animal animal) {
        return this.retrieve("SELECT * FROM tratamento WHERE id_animal=" + animal.getId());
    }

    // Updade
    public void update(Tratamento tratamento) {
        try {
            PreparedStatement stmt;
            stmt = GenericDAO.getConnection().prepareStatement("UPDATE tratamento SET nome=?, data_inicio=?, data_fim =?, id_animal=?, terminado=? WHERE id=?");
            stmt.setString(1, tratamento.getNome());
            stmt.setDate(2, calendarToSqlDate(tratamento.getDataInicio()));
            stmt.setDate(3, calendarToSqlDate(tratamento.getDataFim()));
            stmt.setInt(4, tratamento.getIdAnimal());
            stmt.setBoolean(5, tratamento.isTerminado());
            stmt.setInt(6, tratamento.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    // Delete
    public void delete(Tratamento tratamento) {
        PreparedStatement stmt;
        try {
            stmt = GenericDAO.getConnection().prepareStatement("DELETE FROM tratamento WHERE id = ?");
            stmt.setInt(1, tratamento.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void deleteById(int id) {
        PreparedStatement stmt;
        try {
            stmt = GenericDAO.getConnection().prepareStatement("DELETE FROM tratamento WHERE id = ?");
            stmt.setInt(1, id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
