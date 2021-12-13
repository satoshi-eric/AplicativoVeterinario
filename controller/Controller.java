/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AnimalDAO;
import dao.ClienteDAO;
import dao.ConsultaDAO;
import dao.EspecieDAO;
import dao.TratamentoDAO;
import dao.VeterinarioDAO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import tablemodel.ClienteTableModel;
import tablemodel.GenericTableModel;
import pojo.Cliente;
import tablemodel.AnimalTableModel;
import tablemodel.EspecieTableModel;
import tablemodel.VeterinarioTableModel;
import view.ClienteForm;
import java.util.List;
import pojo.Animal;
import pojo.Consulta;
import pojo.Especie;
import pojo.Veterinario;
import tablemodel.ConsultaTableModel;
import tablemodel.TratamentoTableModel;
import view.AnimalForm;
import view.EspecieForm;
import view.TratamentoForm;
import view.VeterinarioForm;
import pojo.Tratamento;
import view.ConsultaForm;

public class Controller {
    
    private static JTextField clienteField = null;
    private static JTextField animalField = null;
    private static JTextField veterinarioField = null;
    private static Cliente currentCliente = null;
    private static Animal currentAnimal = null;
    private static Veterinario currentVeterinario = null;
    private static JTable table = null;
    private static boolean isTodos = false;
    
    /**
     * Setters para os textFields de cliente e animal para controllar seus
     * estados.
     * @param clienteField 
     * @param animalField 
     */
    public static void setComponents(
            JTextField clienteField, 
            JTextField animalField,
            JTextField veterinarioField
        ) {
        Controller.clienteField = clienteField;
        Controller.animalField = animalField;
        Controller.veterinarioField = veterinarioField;
    }
    
    /**
     * Reage ao evento de clicar na tabela de cadastros. Seta os textField de
     * cliente e de animal.
     * @param table Tabela de onde serão pegos os dados dos clientes ou animais.
     * @return Retorna falso caso o cliente não tiver sido selecionado quando.
     * tentar selecionar um animal.
     */
    public static boolean setClienteAnimalVeterinarioField(JTable table) {
        if (table.getModel() instanceof ClienteTableModel) {
            int currentRow = table.getSelectedRow();
            Cliente cliente = (Cliente) ((GenericTableModel) table.getModel()).getItem(currentRow);
            Controller.currentCliente = cliente;
            Controller.clienteField.setText(cliente.getNome());
        } else if (table.getModel() instanceof AnimalTableModel) {
            if (Controller.clienteField.getText().equals("")) {
                return false;
            } else {
                int currentRow = table.getSelectedRow();
                Animal animal = (Animal) ((GenericTableModel) table.getModel()).getItem(currentRow);
                Controller.currentAnimal = animal;
                Controller.animalField.setText(animal.getNome());
            }
        } else if (table.getModel() instanceof VeterinarioTableModel) {
            int currentRow = table.getSelectedRow();
            Veterinario veterinario = (Veterinario) ((GenericTableModel) table.getModel()).getItem(currentRow);
            Controller.currentVeterinario = veterinario;
            Controller.veterinarioField.setText(veterinario.getNome());
        }
        return true;
    }
    
    /**
     * Retorna o objeto selecionado em uma tabela.
     * @param table Tabela de onde o objeto será pego
     * @return Objeto selecionado na tabela
     */
    private static Object getCurrentObject(JTable table) {
        int currentRow = table.getSelectedRow();
        Object selectedObject = ((GenericTableModel) table.getModel()).getItem(currentRow);
        return selectedObject;
    }
    
    /**
     * Fecha o aplicativo.
     */
    public static void closeApp() {
        System.exit(0);
    }
    
    /**
     * Insere dados na tabela.
     * @param table Tabela onde os dados serão inseridos.
     * @param model Model pela qual serão passados os dados.
     */
    public static void setTableModel(JTable table, GenericTableModel model) {
        table.setModel(model);
    }
    
    /**
     * Adiciona dados de clientes na tabela
     * @param table 
     */
    public static void setClienteTableModel(JTable table) {
        setTableModel(table, new ClienteTableModel(ClienteDAO.getInstance().retrieveAll()));
        isTodos = false;
    }
    
    /**
     * Adiciona dados de animais na tabela se um cliete estiver selecionado
     * @param table Tabela a ser manipulada.
     * @param clienteField Campo de texto de cliente.
     * @return Retorna falso se o cliente dono do animal não estiver selecionado
     */
    public static boolean setAnimalTableModel(JTable table) {
        if (currentCliente == null) {
            return false;
        } else {
            setTableModel(
                    table, new AnimalTableModel(
                            AnimalDAO.getInstance().retrieveByIdCliente(
                                    Controller.currentCliente.getId())));
        }
        return true;
    }
    
    /**
     * Adiciona dados de espécies na tabela.
     * @param table Tabela a ser manipulada
     */
    public static void setEspecieTableModel(JTable table) {
        setTableModel(table, new EspecieTableModel(EspecieDAO.getInstance().retrieveAll()));
        isTodos = false;
    }
    
    /**
     * Adiciona dados de veterinários na tabela.
     * @param table Tabeela a ser manipulada.
     */
    public static void setVeterinarioTableModel(JTable table) {
        setTableModel(table, new VeterinarioTableModel(VeterinarioDAO.getInstance().retrieveAll()));
        isTodos = false;
    }
    
    /**
     * Adiciona dados de consulta na tabela.
     * @param table Tabela a ser manipulada.
     */
    public static void setConsultaTableModel(JTable table) {
        if (currentAnimal == null) {
            JOptionPane.showMessageDialog(null, "Selecione um animal");
        } else if (currentVeterinario == null) {
            JOptionPane.showMessageDialog(null, "Selecione um veterinario");
        } else {
            setTableModel(table, new ConsultaTableModel(ConsultaDAO.getInstance().retrieveAll()));
        }
        isTodos = false;
    }
    
    /**
     * Adiciona dados de tratamento na tabela.
     * @param table Tabela a ser manipulada.
     */
    public static boolean setTratamentoTableModel(JTable table) {
        if (currentAnimal != null) {
            setTableModel(table, new TratamentoTableModel(TratamentoDAO.getInstance().retrieveByAnimal(currentAnimal)));
            isTodos = false;
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um animal");
            return false;
        }
    }
    
    /**
     * Reage ao botão de todos
     */
    public static void todos(JTextField buscaField, JTable table) {
        if (table.getModel() instanceof AnimalTableModel) {
            setTableModel(table, new AnimalTableModel(AnimalDAO.getInstance().retrieveAll()));
            Controller.isTodos = true;
        }
        buscaField.setText("");
    }
    
    /**
     * Reage ao evento de digitar no textField de busca. Filtra os objetos da 
     * tabela.
     * @param table Tabela a ser filtrada.
     * @param buscaField Campo de texto de busca.
     */
    public static void buscar(JTable table, JTextField buscaField) {
        GenericTableModel model = null;
        if (table.getModel() instanceof ClienteTableModel) {
            model = new ClienteTableModel(ClienteDAO.getInstance().retrieveBySimilarName(buscaField.getText()));
        } else if (table.getModel() instanceof AnimalTableModel) {
            model = new AnimalTableModel(AnimalDAO.getInstance().retrieveBySimilarName(buscaField.getText()));
        } else if (table.getModel() instanceof EspecieTableModel) {
            model = new EspecieTableModel(EspecieDAO.getInstance().retrieveBySimilarName(buscaField.getText()));
        } else if (table.getModel() instanceof VeterinarioTableModel) {
            model = new VeterinarioTableModel(VeterinarioDAO.getInstance().retrieveBySimilarName(buscaField.getText()));
        } else if (table.getModel() instanceof ConsultaTableModel) {
            
        }
        setTableModel(table, model);
    }
    
    /**
     * Filtros aplicados a tabela.
     * @param table tabela onde o filtro será aplicado.
     * @param filtro tipo de filtro.
     */
    public static void filtrar(JTable table, String filtro) {
        GenericTableModel model = null;
        
        if (filtro.equals("Hoje")) {
            model = new ConsultaTableModel(ConsultaDAO.getInstance().retrieveHoje());
        } else if (filtro.equals("Todas")) {
            model = new ConsultaTableModel(ConsultaDAO.getInstance().retrieveAll());
        } else if (filtro.equals("Ultimo Mes")) {
            model = new ConsultaTableModel(ConsultaDAO.getInstance().retrieveLastMes());
        } else if (filtro.equals("Ultimo Ano")) {
            model = new ConsultaTableModel(ConsultaDAO.getInstance().retrieveLastAno());
        } else if (filtro.equals("Finalizado")) {
            model = new ConsultaTableModel(ConsultaDAO.getInstance().retrieveFinalizado());
        }
        
        setTableModel(table, model);
    }
    
    
    
    /**
     * Deleta um objeto do banco de dados.
     * @param table Tabela com o objeto a ser deletado
     */
    public static void deletar(JTable table) {
        Object selectedObject = getCurrentObject(table);
        if (table.getModel() instanceof ClienteTableModel) {
            Cliente cliente = (Cliente) selectedObject;
            ClienteDAO.getInstance().delete(cliente);
            setClienteTableModel(table);
            for (Object animal : AnimalDAO.getInstance().retrieveByIdCliente(cliente.getId())) {
                AnimalDAO.getInstance().delete((Animal) animal);
            }
            currentCliente = null;
            currentAnimal = null;
            clienteField.setText("");
            animalField.setText("");
        } else if (table.getModel() instanceof AnimalTableModel) {
            AnimalDAO.getInstance().delete((Animal) selectedObject);
            if (isTodos) {
                setTableModel(table, new AnimalTableModel(AnimalDAO.getInstance().retrieveAll()));
            } else {
                setAnimalTableModel(table);
            }
            currentAnimal = null;
            animalField.setText("");
        } else if (table.getModel() instanceof VeterinarioTableModel) {
            VeterinarioDAO.getInstance().delete((Veterinario) selectedObject);
            setVeterinarioTableModel(table);
        } else if (table.getModel() instanceof ConsultaTableModel) {
            ConsultaDAO.getInstance().delete((Consulta) selectedObject);
            setTableModel(table, new ConsultaTableModel(ConsultaDAO.getInstance().retrieveAll()));
        } else if (table.getModel() instanceof TratamentoTableModel) {
            TratamentoDAO.getInstance().delete((Tratamento) selectedObject);
            setConsultaTableModel(table);
        } 
    }
    
    /**
     * Mostra tela de cadastro.
     * @param table Tabela a ser atualizada quando o cadastro for realizado.
     */
    public static void novo(JTable table) {
        if (table.getModel() instanceof ClienteTableModel) {
            new  ClienteForm(table).setVisible(true);
        } else if (table.getModel() instanceof AnimalTableModel) {
            if (currentCliente == null) {
                JOptionPane.showMessageDialog(null, "Selecione um cliente");
            } else {
                new AnimalForm(currentCliente, table).setVisible(true);
            }
        } else if (table.getModel() instanceof EspecieTableModel) {
            new EspecieForm(table).setVisible(true);
        } else if (table.getModel() instanceof VeterinarioTableModel) {
            new VeterinarioForm(table).setVisible(true);
        } else if (table.getModel() instanceof ConsultaTableModel) {
            if (currentAnimal == null) {
                JOptionPane.showMessageDialog(null, "Selecione um animal");
            } else if ( currentVeterinario == null) {
                JOptionPane.showMessageDialog(null, "Selecione um veterinario");
            } else {
                new ConsultaForm(currentAnimal, currentVeterinario, table).setVisible(true);
            }
        } else if (table.getModel() instanceof TratamentoTableModel) {
            if (currentAnimal == null) {
                JOptionPane.showMessageDialog(null, "Selecione um animal");
            } else {
                new TratamentoForm(currentAnimal, table).setVisible(true);
            }
        }
    }
    
    public static void atualizar(JTable table) {
        Object selectedObject = getCurrentObject(table);
        if (selectedObject != null) {
            if (table.getModel() instanceof ClienteTableModel) {
                new  ClienteForm((Cliente) selectedObject, table).setVisible(true);
            } else if (table.getModel() instanceof AnimalTableModel) {
                new AnimalForm(currentCliente, (Animal) selectedObject, table).setVisible(true);
            } else if (table.getModel() instanceof EspecieTableModel) {
                new EspecieForm((Especie) selectedObject,table).setVisible(true);
            } else if (table.getModel() instanceof VeterinarioTableModel) {
                new VeterinarioForm((Veterinario) selectedObject, table).setVisible(true);
            } else if (table.getModel() instanceof ConsultaTableModel) {
                new ConsultaForm((Consulta) selectedObject, table).setVisible(true);
            } else if (table.getModel() instanceof TratamentoTableModel) {
                new TratamentoForm((Tratamento) selectedObject, table).setVisible(true);
            }
        }
    }
}
