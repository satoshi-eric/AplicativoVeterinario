package modelDAO;

import model.Consulta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultaDAO extends DAO {
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
    public Consulta create(Calendar data, int hora, String comentarios, int idAnimal, int idVet, int idTratamento, boolean terminou) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO consulta (data, hora, comentarios, idAnimal, idVet, idTratamento, terminou) VALUES (?,?,?,?,?,?,?)");
            stmt.setString(1, dateFormat.format(data.getTime()));
            stmt.setInt(2, hora);
            stmt.setString(3, comentarios);
            stmt.setInt(4, idAnimal);
            stmt.setInt(5, idVet);
            stmt.setInt(6, idTratamento);
            stmt.setInt(7, terminou?1:0);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("consulta","id"));
    }
    
    

    private Consulta buildObject(ResultSet rs) {
        Consulta consulta = null;
        try {
            Calendar dt = Calendar.getInstance();
            dt.setTime(dateFormat.parse(rs.getString("data")));
            consulta = new Consulta(
                    rs.getInt("id"),
                    dt,
                    rs.getInt("horario"),
                    rs.getString("comentario"),
                    rs.getInt("id_animal"),
                    rs.getInt("id_vet"),
                    rs.getInt("id_tratamento"),
                    rs.getInt("terminado") == 1
            );
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        } catch (ParseException e) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, e);
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
        return this.retrieve("SELECT * FROM consulta");
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
        return this.retrieve("SELECT * FROM consulta WHERE id_vet = " + idVet);
        
    }
    
    public List retrieveByIdTratamento(int idTratamento) {
        return this.retrieve("SELECT * FROM consulta WHERE id_animal = " + idTratamento);
        
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarComentarios(String comentarios) {
        return this.retrieve("SELECT * FROM consulta WHERE coemntarios LIKE '%" + comentarios + "%'");
    }    
        
    // Updade
    public void update(Consulta consulta) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE consulta SET data=?, horario=?, comentarios=?, id_animal=?, id_vet=?, id_tratamento=?, terminado=?");
            stmt.setString(1, dateFormat.format(consulta.getData().getTime()));
            stmt.setInt(2, consulta.getHora());
            stmt.setString(3, consulta.getComentarios());
            stmt.setInt(4, consulta.getIdAnimal());
            stmt.setInt(5, consulta.getIdTratamento());
            stmt.setBoolean(6, consulta.isTerminou());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
        // Delete   
    public void delete(Consulta consulta) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE id = ?");
            stmt.setInt(1, consulta.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    public void deleteById(int id) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE id = ?");
            stmt.setInt(1, id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}
