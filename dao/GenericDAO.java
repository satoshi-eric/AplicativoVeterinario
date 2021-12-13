/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import pojo.Cliente;

import java.sql.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenericDAO {
    protected static final String DBURL = "jdbc:sqlite:vet2021.db";
    private static Connection con;

    /**
     * Se a conexão com o banco de dados não tiver sido feita, ela é criada
     * @return Atributo con com a conexão com o banco de dados
     */
    protected static Connection getConnection() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(DBURL);
                if (con != null) {
                    DatabaseMetaData meta = con.getMetaData();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return con;
    }

    /**
     * Executa uma query de SELECT no banco de dados
     * @param query Comando SQL de SELECT
     * @return Dados do banco de dados
     */
    protected ResultSet getResultSet(String query) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = (Statement) con.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Executa uma query de CREATE, UPDATE ou DELETE no banco de dados
     * @param queryStatement Comando SQL de CREATE, UPDATE ou DELETE
     * @return Quantidade de linhas afetadas no banco de dados pelo comando SQL
     * @throws SQLException Quando ocorre algum problema na cunsulta
     */
    protected int executeUpdate(PreparedStatement queryStatement) throws SQLException {
        int update;
        update = queryStatement.executeUpdate();
        return update;
    }

    /**
     * Procura o último id armazenado em uma tabela do banco de dados
     * @param tableName Nome da tabela
     * @param primaryKey Nome da chave primária
     * @return Último id da tabela
     */
    protected int lastId(String  tableName, String primaryKey) {
        Statement s;
        int lastId = -1;
        try {
            s = (Statement) con.createStatement();
            ResultSet rs;
            rs = s.executeQuery("SELECT MAX(" + primaryKey + ") as id FROM " + tableName);
            if (rs.next()) {
                lastId = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return lastId;
    }

    /**
     * Fecha a conexão com o banco de dados
     */
    public static void terminar() {
        try {
            (GenericDAO.getConnection()).close();
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Cria as tabelas no banco de dados se elas não existirem e cria um registro na tabela especie
     * @return true se não ocorrer nunhum erro, false se algum SQLException ocorrer
     */
    protected final boolean createTable() {
        try {
            PreparedStatement stmt;
            stmt = GenericDAO.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS cliente( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "nome VARCHAR, \n"
                    + "endereco VARCHAR, \n"
                    + "cep VARCHAR, \n"
                    + "email VARCHAR, \n"
                    + "telefone VARCHAR); \n");
            executeUpdate(stmt);
            stmt = GenericDAO.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS animal( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "nome VARCHAR, \n"
                    + "ano_nasc INTEGER, \n"
                    + "sexo VARCHAR, \n"
                    + "id_especie INTEGER, \n"
                    + "id_cliente INTEGER); \n");
            executeUpdate(stmt);
            stmt = GenericDAO.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS especie( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "nome VARCHAR); \n");
            executeUpdate(stmt);
            stmt = GenericDAO.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS veterinario( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "nome VARCHAR, \n"
                    + "email VARCHAR, \n"
                    + "telefone VARCHAR); \n");
            executeUpdate(stmt);
            stmt = GenericDAO.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS tratamento( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "id_animal INTEGER, \n"
                    + "nome VARCHAR, \n"
                    + "data_inicio DATE, \n"
                    + "data_fim DATE, \n"
                    + "terminado INTEGER); \n");
            executeUpdate(stmt);
            stmt = GenericDAO.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS consulta( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "data DATE, \n"
                    + "hora VARCHAR, \n"
                    + "comentarios VARCHAR, \n"
                    + "id_animal INTEGER, \n"
                    + "id_veterinario INTEGER, \n"
                    + "id_tratamento INTEGER, \n"
                    + "terminou INTEGER); \n");
            executeUpdate(stmt);
            stmt = GenericDAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS exame( \n"
                    + "id INTEGER PRIMARY KEY, \n"
                    + "descricao VARCHAR, \n"
                    + "id_consulta INTEGER); \n");
            executeUpdate(stmt);
            // Default element for species:
            stmt = GenericDAO.getConnection().prepareStatement("INSERT OR IGNORE INTO especie (id, nome) VALUES (1, 'Cachorro')");
            executeUpdate(stmt);
            return true;
        } catch (SQLException e) {
            Logger.getLogger(GenericDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    protected Calendar sqlDateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    protected Date calendarToSqlDate(Calendar calendar) {
        Date date = new Date(calendar.getTimeInMillis());
        return date;
    }
}
