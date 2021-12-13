package dao;

import pojo.Consulta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultaDAO extends GenericDAO {
    private static ConsultaDAO instance;

    private ConsultaDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static ConsultaDAO getInstance() {
        return (instance==null?(instance = new ConsultaDAO()):instance);
    }

    // CRUD
    public Consulta create(
            Calendar data,
            int hora,
            String comentarios,
            int idAnimal,
            int idVeterinario,
            int idTratamento,
            boolean terminou
    ) {
        try {
            PreparedStatement stmt;
            stmt = GenericDAO.getConnection().prepareStatement(
                    "INSERT INTO consulta (data, hora, comentarios, id_animal, id_veterinario, id_tratamento, terminou) VALUES (?,?,?,?,?,?,?)");
            stmt.setDate(1, calendarToSqlDate(data));
            stmt.setInt(2, hora);
            stmt.setString(3, comentarios);
            stmt.setInt(4, idAnimal);
            stmt.setInt(5, idVeterinario);
            stmt.setInt(6, idTratamento);
            stmt.setInt(7, terminou ? 1 : 0);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("consulta","id"));
    }



    private Consulta buildObject(ResultSet rs) {
        Consulta consulta = null;
        try {
            consulta = new Consulta(
                    rs.getInt("id"),
                    sqlDateToCalendar(rs.getDate("data")),
                    rs.getInt("hora"),
                    rs.getString("comentarios"),
                    rs.getInt("id_animal"),
                    rs.getInt("id_veterinario"),
                    rs.getInt("id_tratamento"),
                    rs.getInt("terminou") == 1
            );
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consulta;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Consulta> consultas = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                consultas.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consultas;
    }

    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM consulta ORDER BY data DESC");
    }

    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM consulta WHERE id = " + lastId("consulta","id"));
    }

    // RetrieveById
    public Consulta retrieveById(int id) {
        List<Consulta> consultas = this.retrieve("SELECT * FROM consulta WHERE id = " + id);
        return (consultas.isEmpty()?null:consultas.get(0));
    }

    public List retrieveByIdAnimal(int idAnimal) {
        return this.retrieve("SELECT * FROM consulta WHERE id_animal = " + idAnimal);

    }

    public List retrieveByIdVet(int idVet) {
        return this.retrieve("SELECT * FROM consulta WHERE id_veterinario = " + idVet);

    }

    public List retrieveByIdTratamento(int idTratamento) {
        return this.retrieve("SELECT * FROM consulta WHERE id_animal = " + idTratamento);
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarComentarios(String comentarios) {
        return this.retrieve("SELECT * FROM consulta WHERE comentarios LIKE '%" + comentarios + "%'");
    }
    
    public List retrieveHoje() {
        Calendar date = Calendar.getInstance();
        Calendar today = new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        
        return this.retrieve("SELECT * FROM consulta WHERE data=" + today.getTimeInMillis());
    }
    
    public List retrieveLastMes() {
        Calendar date = Calendar.getInstance();
        int dia = date.get(Calendar.DAY_OF_MONTH);
        int mes = date.get(Calendar.MONTH);
        int ano = date.get(Calendar.YEAR);
        
        Calendar start = new GregorianCalendar(ano, mes, 1);
        Calendar end = new GregorianCalendar(ano, mes, date.getActualMaximum(Calendar.DAY_OF_MONTH));
        
        return this.retrieve("SELECT * FROM consulta WHERE data BETWEEN " + start.getTimeInMillis() + " AND " + end.getTimeInMillis() + " ORDER BY data DESC");
    }
    
     public List retrieveLastAno() {
        Calendar date = Calendar.getInstance();
        int dia = date.get(Calendar.DAY_OF_MONTH);
        int mes = date.get(Calendar.MONTH);
        int ano = date.get(Calendar.YEAR);
        
        Calendar start = new GregorianCalendar(ano, 0, 1);
        Calendar end = new GregorianCalendar(ano, date.getActualMaximum(Calendar.MONTH), date.getActualMaximum(Calendar.DAY_OF_MONTH));
        
        return this.retrieve("SELECT * FROM consulta WHERE data BETWEEN " + start.getTimeInMillis() + " AND " + end.getTimeInMillis() + " ORDER BY data DESC");
    }
     
     public List retrieveFinalizado() {
         return this.retrieve("SELECT * FROM consulta WHERE terminou = 1 ORDER BY data");
     }

    // Updade
    public void update(Consulta consulta) {
        try {
            PreparedStatement stmt;
            stmt = GenericDAO.getConnection().prepareStatement(
                    "UPDATE consulta SET data=?, hora=?, comentarios=?, id_animal=?, id_veterinario=?, id_tratamento=?, terminou=? WHERE id=?");
            stmt.setDate(1, calendarToSqlDate(consulta.getData()));
            stmt.setInt(2, consulta.getHora());
            stmt.setString(3, consulta.getComentarios());
            stmt.setInt(4, consulta.getIdAnimal());
            stmt.setInt(5, consulta.getIdTratamento());
            stmt.setBoolean(6, consulta.isTerminou());
            stmt.setInt(7, consulta.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    // Delete
    public void delete(Consulta consulta) {
        PreparedStatement stmt;
        try {
            stmt = GenericDAO.getConnection().prepareStatement(
                    "DELETE FROM consulta WHERE id = ?");
            stmt.setInt(1, consulta.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void deleteById(int id) {
        PreparedStatement stmt;
        try {
            stmt = GenericDAO.getConnection().prepareStatement(
                    "DELETE FROM consulta WHERE id = ?");
            stmt.setInt(1, id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}
