package dao;

import pojo.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDAO extends GenericDAO {
    private static ClienteDAO instance;

    private ClienteDAO() {
        getConnection();
        createTable();
    }

    /**
     * Implementação do Singleton. Se a propriedade instance
     * estiver vazia, ela é instanciada, se não, ela é retornada
     * @return propriedade instance
     */
    public static ClienteDAO getInstance() {
        return (instance == null ? (instance = new ClienteDAO()) : instance);
    }

    /**
     * Insere um cliente no banco de dados e retorna esse cliente
     * @param nome
     * @param endereco
     * @param telefone
     * @param cep
     * @param email
     * @return Último cliente no banco de dados
     */
    public Cliente create(
            String nome,
            String endereco,
            String telefone,
            String cep,
            String email
    ) {
        try {
            PreparedStatement stmt;
            stmt = ClienteDAO.getConnection().prepareStatement(
                    "INSERT INTO cliente (nome, endereco, cep, email, telefone) VALUES (?,?,?,?,?)");
            stmt.setString(1, nome);
            stmt.setString(2, endereco);
            stmt.setString(3, cep);
            stmt.setString(4, email);
            stmt.setString(5, telefone);
            executeUpdate(stmt);
        } catch (SQLException e) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return this.retrieveById(lastId("cliente", "id"));
    }

    /**
     * Cria um objeto cliente a partir do ResultSet obtido do banco de dados.
     * Ele é necessário para converter os resultados do banco de dados para objetos do sistema
     * @param rs Resultado de busca do banco de dados
     * @return bjeto Cliente com os dados obtidos do banco de dados
     */
    private Cliente buildObject(ResultSet rs) {
        Cliente cliente = null;
        try {
            cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("cep"),
                    rs.getString("email"),
                    rs.getString("telefone")
            );
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return cliente;
    }

    /**
     * Executa query de SELECT para recuperar os dados
     * @param query Comando SELECT do banco de dados
     * @return Lista de objetos Cliente criados a partir dos dados do banco de dados
     */
    public List retrieve(String query) {
        List<Cliente> clientes = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                clientes.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return clientes;
    }

    /**
     * Executa query de SELECT para recuperar
     * todos os clientes do banco de dados
     * @return Lista de clientes do banco de dados
     */
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM cliente");
    }

    /**
     * Executa query de SELECT para recuperar
     * o último cliente registrado no banco de dados
     * @return Último cliente registrado
     */
    public Cliente retrieveLast(){
        List<Cliente> clientes = this.retrieve(
                "SELECT * FROM cliente WHERE id = " + lastId("cliente","id"));
        return clientes.get(0);
    }

    /**
     * Executa query de SELECT no banco de dados para recuperar
     * o cliente pelo seu id no banco de dados
     * @param id id do cliente no banco de dados
     * @return Cliente do banco de dados
     */
    public Cliente retrieveById(int id) {
        List<Cliente> clientes = this.retrieve("SELECT * FROM cliente WHERE id = " + id);
        return (clientes.isEmpty() ? null : clientes.get(0));
    }

    /**
     * Executa query de SELECT no banco de dados para recuperar
     * o cliente por um nome similar ao passado pelo parâmetro nome
     * @param nome Nome do cliente a ser buscado
     * @return Lista de clientes com nome parecido
     */
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM cliente WHERE nome LIKE '%" + nome + "%'");
    }

    /**
     * Executa query de UPDATE no banco de dados para atualizar
     * o cliente passado pelo parâmetro cliente no banco de dados
     * @param cliente cliente a ser atualizado
     */
    public void update(Cliente cliente) {
        try {
            PreparedStatement stmt;
            stmt = GenericDAO.getConnection().prepareStatement(
                    "UPDATE cliente SET nome=?, endereco=?, cep=?, email=?, telefone=? WHERE id=?");
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getCep());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getTelefone());
            stmt.setInt(6, cliente.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Executa query de DELETE em um cliente no banco de dados
     * @param cliente cliente a ser deletado
     */
    public void delete(Cliente cliente) {
        PreparedStatement stmt;
        try {
            stmt = GenericDAO.getConnection().prepareStatement("DELETE FROM cliente WHERE id = ?");
            stmt.setInt(1, cliente.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Executa query de DELETE em um cliente pelo seu id no banco de dados
     * @param id
     */
    public void deleteById(int id) {
        PreparedStatement stmt;
        try {
            stmt = GenericDAO.getConnection().prepareStatement("DELETE FROM cliente WHERE id = ?");
            stmt.setInt(1, id);
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
