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
import model.Tratamento;
import modelDAO.TratamentoDAO;

/**
 *
 * @author satos
 */
public class TratamentoTableModel extends GenericTableModel {

    public TratamentoTableModel(List vDados) {
        super(vDados, new String[]{"Nome", "Data de Início", "Data de Término", "Finalizado"});
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        
        switch (columnIndex) {
            case 0 -> {
                return String.class;
            }
            case 1 -> {
                return String.class;
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
        Tratamento tratamento = (Tratamento) vDados.get(rowIndex);
        
        switch (columnIndex) {
            case 0 -> {
                return tratamento.getNome();
            }
            case 1 -> {
                return dateFormat.format(tratamento.getDataIni().getTime());
            }
            case 2 -> {
                return dateFormat.format(tratamento.getDataFim().getTime());
            }
            case 3 -> {
                return tratamento.isTerminado();
            }
            default -> throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        Tratamento tratamento = (Tratamento) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0 -> tratamento.setNome((String) aValue);
            case 1 -> tratamento.setDataIni(stringToCalendar((String) aValue));
            case 2 -> tratamento.setDataFim(stringToCalendar((String) aValue));
            case 3 ->tratamento.setTerminado((Boolean) aValue);
            default -> throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    } 
    
}
