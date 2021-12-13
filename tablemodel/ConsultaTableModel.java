/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodel;

import dao.AnimalDAO;
import dao.TratamentoDAO;
import dao.VeterinarioDAO;
import pojo.Consulta;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojo.Animal;
import pojo.Veterinario;
import pojo.Tratamento;

/**
 *
 * @author satos
 */
public class ConsultaTableModel extends GenericTableModel {

    public ConsultaTableModel(List vDados) {
        super(vDados, new String[]{"Data", "Hora", "Comentários", "Animal", "Veterinario", "Tratamento", "Finalizado"});
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0 -> {
                return String.class;
            }
            case 1 -> {
                return Integer.class;
            }
            case 2 -> {
                return String.class;
            }
            case 3 -> {
                return String.class;
            }
            case 4 -> {
                return String.class;
            }
            case 5 -> {
                return String.class;
            }
            case 6 -> {
                return Boolean.class;
            }
            default -> throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }



    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Consulta consulta = (Consulta) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0 -> {
                return calendarToString(consulta.getData());
            }
            case 1 -> {
                return consulta.getHora();
            }
            case 2 -> {
                return consulta.getComentarios();
            }
            case 3 -> {
                Animal animal = AnimalDAO.getInstance().retrieveById(consulta.getIdAnimal());
                if (animal != null) {
                    return animal.getNome();
                }
                return "";
            }
            case 4 -> {
                Veterinario veterinario = VeterinarioDAO.getInstance().retrieveById(consulta.getIdVeterinario());
                if (veterinario != null) {
                    return veterinario.getNome();
                }
                return "";
            }
            case 5 -> {
                Tratamento tratamento = TratamentoDAO.getInstance().retrieveById(consulta.getIdTratamento());
                if (tratamento != null) {
                    return tratamento.getNome();
                }
                return "";
            }
            case 6 -> {
                return consulta.isTerminou();
            }
            default -> throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }



    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        Consulta consulta = (Consulta) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0 -> consulta.setData(stringToCalendar((String) aValue));
            case 1 -> consulta.setHora((Integer) aValue);
            case 2 -> consulta.setComentarios((String) aValue);
            case 3 -> consulta.setTerminou((Boolean) aValue);
            default -> throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

}
