package main;

import modelDAO.ConsultaDAO;

public class Main {
    public static void main(String[] args) {
        System.out.println(ConsultaDAO.getInstance().retrieveAll());
    }
}


