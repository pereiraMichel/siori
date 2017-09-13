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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Debug
 */
public class Delegacao {
    
    private int iddelegacao;
    private int codinstituto;
    private int codresponsavel;
    
    ConexaoBanco con;
    Connection conn;
    Statement stmt;
    ResultSet rs;

    public int getIddelegacao() {
        return iddelegacao;
    }

    public void setIddelegacao(int iddelegacao) {
        this.iddelegacao = iddelegacao;
    }

    public int getCodinstituto() {
        return codinstituto;
    }

    public void setCodinstituto(int codinstituto) {
        this.codinstituto = codinstituto;
    }

    public int getCodresponsavel() {
        return codresponsavel;
    }

    public void setCodresponsavel(int codresponsavel) {
        this.codresponsavel = codresponsavel;
    }
    
    public void tabelaDelegacao(JTable tabela){
        String sql = "SELECT i.nome AS instituto, r.nome AS responsavel " +
                     "FROM delegacao d " +
                     "LEFT JOIN instituto i ON d.codinstituto = i.idinstituto " +
                     "LEFT JOIN responsavel r ON d.codresponsavel = r.idresponsavel " +
                     "ORDER BY i.nome";
        
        ArrayList dados = new ArrayList();
        String[] nomeColunas = new String[]{"DELEGAÇÃO", "RESPONSÁVEL"};
        try{
            con = new ConexaoBanco();
            conn = con.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.first();
            
            do{
                dados.add(new Object[]{rs.getString("instituto"), rs.getString("responsavel")});
                //labelTotalDia.setText(rs.getString("totalGeral"));
            }while(rs.next());
            
        ModeloTabela modelo = new ModeloTabela(dados, nomeColunas);
        tabela.setModel(modelo);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(100); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(0).setResizable(false);// Não poderá alterar o tamanho.
        tabela.getColumnModel().getColumn(1).setPreferredWidth(300); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(1).setResizable(false);// Não poderá alterar o tamanho.
        /*
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
    
}
