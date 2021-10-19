package modelDAO;

import model.Tratamento;

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

public class TratamentoDAO extends DAO {
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
    public Tratamento create(String nome, Calendar dataIni, Calendar dataFim, int idAnimal, boolean terminado) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO tratamento (nome, dataIni, dataFim, id_animal, terminado) VALUES (?,?,?,?,?)");
            stmt.setString(1, nome);
            stmt.setString(2, dateFormat.format(dataIni.getTime()));
            stmt.setString(3, dateFormat.format(dataFim.getTime()));
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
            Calendar dataIni = Calendar.getInstance();
            dataIni.setTime(dateFormat.parse(rs.getString("dataIni")));
            Calendar dataFim = Calendar.getInstance();
            dataFim.setTime(dateFormat.parse(rs.getString("dataFim")));
            tratamento = new Tratamento(
                    rs.getInt("id"), 
                    rs.getString("nome"),
                    dataIni,
                    dataFim,
                    rs.getInt("id_animal"),
                    rs.getBoolean("terminado")
            );
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
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
        
    // Updade
    public void update(Tratamento tratamento) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE tratamento SET nome=?, dataIni=?, dataFim=?, id_animal=?, terminado=?");
            stmt.setString(1, tratamento.getNome());
            stmt.setString(2, dateFormat.format(tratamento.getDataIni().getTime()));
            stmt.setString(3, dateFormat.format(tratamento.getDataFim().getTime()));
            stmt.setInt(4, tratamento.getIdAnimal());
            stmt.setBoolean(5, tratamento.isTerminado());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
        // Delete   
    public void delete(Tratamento tratamento) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM tratamento WHERE id = ?");
            stmt.setInt(1, tratamento.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    public void deleteById(int id) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM tratamento WHERE id = ?");
            stmt.setInt(1, id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}
