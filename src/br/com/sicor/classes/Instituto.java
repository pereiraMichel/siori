/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicor.classes;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Debug
 */
public class Instituto {
    
    private int idinstituto;
    private String nome;
    
    ConexaoBanco con;
    Connection conn;
    Statement stmt;
    ResultSet rs;

    public int getIdinstituto() {
        return idinstituto;
    }

    public void setIdinstituto(int idinstituto) {
        this.idinstituto = idinstituto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void tabelaInstituto(JTable tabela){
        ArrayList dados = new ArrayList();
        String[] nomeColunas = new String[]{"INSTITUTO"};
        String sql = "SELECT i.nome AS instituto " +
                     "FROM instituto i ";
        try{
            con = new ConexaoBanco();
            conn = con.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.first();
            
            do{
                dados.add(new Object[]{rs.getString("instituto")});
                //labelTotalDia.setText(rs.getString("totalGeral"));
            }while(rs.next());
            
        ModeloTabela modelo = new ModeloTabela(dados, nomeColunas);
        tabela.setModel(modelo);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(150); // define o tamanho da coluna.
        /*
        tabela.getColumnModel().getColumn(0).setResizable(false);// Não poderá alterar o tamanho.
        tabela.getColumnModel().getColumn(1).setPreferredWidth(150); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(1).setResizable(false);// Não poderá alterar o tamanho.
        tabela.getColumnModel().getColumn(2).setPreferredWidth(150); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(2).setResizable(false);// Não poderá alterar o tamanho.
        */
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setAutoResizeMode(tabela.AUTO_RESIZE_OFF);
        
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
        }catch(IOException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Não registro na tabela. Inclua um novo. Erro: " + ex.getMessage(), "ATENÇÃO", 0);
//            labelMensagem.setVisible(true);
//            labelMensagem.setText("Não há registro na tabela. Inclua um novo.");
        }
        
    }
    
    public int buscaCodInstituto(String campo){
        String sql = "SELECT * FROM instituto WHERE nome = '" + campo + "'";
        
        try{
            con = new ConexaoBanco();
            conn = con.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            
            if(rs.absolute(1)){
                return rs.getInt("idinstituto");
            }else{
                return 0;
            }
            
        }catch (IOException | SQLException ex){
            System.out.println("Erro no banco de dados. Descrição: " + ex.getMessage());
            return -1;
        }
        
    }

    public void listaCombo(JComboBox combo){
        
        try{
        String sql = "SELECT * FROM instituto";

        con = new ConexaoBanco();
        conn = con.getConnection();
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        while (rs.next()){
            if(!rs.getString("nome").equals("")){
                String nomeStatus = rs.getString("nome");
                combo.addItem(nomeStatus);
            }else{
                combo.addItem("Não há refeições para consulta.");
            }
        }

        con.fechaConexao();
        stmt.close();
        
        
        }catch(IOException | SQLException ex){
            System.out.println("Erro no banco de dados. Descrição: " + ex.getMessage());
        }
    }
    
    
}
