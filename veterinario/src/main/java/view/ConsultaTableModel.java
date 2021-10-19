/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Consulta;

/**
 *
 * @author satos
 */
public class ConsultaTableModel extends GenericTableModel {

    public ConsultaTableModel(List vDados) {
        super(vDados, new String[]{"Data", "Hora", "Comentários", "Finalizado"});
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
                return dateFormat.format(consulta.getData().getTime());
            }
            case 1 -> {
                return consulta.getHora();
            }
            case 2 -> {
                return consulta.getComentarios();
            }
            case 3 -> {
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
        return true;
    } 
    
}
