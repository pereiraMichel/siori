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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Debug
 */
public class Refeicoes {
    
    private int idrefeicoes;
    private String nome;

    ConexaoBanco con;
    Connection conn;
    Statement stmt;
    ResultSet rs;

    public int getIdrefeicoes() {
        return idrefeicoes;
    }

    public void setIdrefeicoes(int idrefeicoes) {
        this.idrefeicoes = idrefeicoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void consultaUltimoId(){
        try{
            String sql = "SELECT MAX(idrefeicoes) as idRefeicoes FROM refeicoes";
            
            con = new ConexaoBanco();
            conn = con.getConnection();
            stmt = conn.createStatement();
            
            rs = stmt.executeQuery(sql);
            rs.next();
            
            int resultado = rs.getInt("idCliente");
            int soma = resultado + 1;
            
            this.idrefeicoes = soma;
            
        }catch(IOException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Houve um erro na consulta. Erro: " + ex.getMessage(), "ERRO", 0);
        }
        
        
    }    
    
    public void listaCombo(JComboBox combo){
        
        try{
        String sql = "SELECT * FROM refeicoes";

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
    
    public int consultaIdRefeicao(String refeicao){
        String sql = "SELECT * FROM refeicoes WHERE nome = '" + refeicao + "'";
        
        try{
            con = new ConexaoBanco();
            conn = con.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            
            if(rs.absolute(1)){
                return rs.getInt("idrefeicoes");
            }else{
                return 0;
            }
            
        }catch (IOException | SQLException ex){
            System.out.println("Erro no banco de dados. Descrição: " + ex.getMessage());
            return -1;
        }
    }
    
}
