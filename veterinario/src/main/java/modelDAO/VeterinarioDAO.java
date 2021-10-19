package modelDAO;

import model.Veterinario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeterinarioDAO extends DAO {
    private static VeterinarioDAO instance;

    private VeterinarioDAO() {
        getConnection();
        createTable();
    }

    // Singleton
    public static VeterinarioDAO getInstance() {
        return (instance==null?(instance = new VeterinarioDAO()):instance);
    }
    
// CRUD    
    public Veterinario create(String nome, String email, String telefone) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO vet (nome, email, telefone) VALUES (?,?,?)");
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, telefone);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.retrieveById(lastId("vet","id"));
    }
    
    

    private Veterinario buildObject(ResultSet rs) {
        Veterinario veterinario = null;
        try {
            veterinario = new Veterinario(
                    rs.getInt("id"), 
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("telefone")
            );
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return veterinario;
    }

    // Generic Retriever
    public List retrieve(String query) {
        List<Veterinario> veterinarios = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                veterinarios.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return veterinarios;
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM vet");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM vet WHERE id = " + lastId("veterinerio","id"));
    }

    // RetrieveById
    public Veterinario retrieveById(int id) {
        List<Veterinario> veterinarios = this.retrieve("SELECT * FROM vet WHERE id = " + id);
        return (veterinarios.isEmpty()?null:veterinarios.get(0));
    }
    
    public List retrieveByNome(String nome) {
        return this.retrieve("SELECT * FROM vet WHERE nome = " + nome);
    }
    
    public List retrieveByIdEmail(String email) {
        return this.retrieve("SELECT * FROM vet WHERE email = " + email);
    }
    
    public List retrieveByTerminado(String telefone) {
        return this.retrieve("SELECT * FROM vet WHERE telefone = " + telefone);
    }

    // RetrieveBySimilarName
    public List retrieveBySimilarComentarios(String nome) {
        return this.retrieve("SELECT * FROM vet WHERE nome LIKE '%" + nome + "%'");
    }    
        
    // Updade
    public void update(Veterinario veterinario) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE vet SET nome=?, email=?, telefone=?");
            stmt.setString(1, veterinario.getNome());
            stmt.setString(2, veterinario.getEmail());
            stmt.setString(3, veterinario.getTelefone());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
        // Delete   
    public void delete(Veterinario veterinario) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM vet WHERE id = ?");
            stmt.setInt(1, veterinario.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    public void deleteById(int id) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM vet WHERE id = ?");
            stmt.setInt(1, id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}
