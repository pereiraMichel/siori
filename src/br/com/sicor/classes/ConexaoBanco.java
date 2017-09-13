/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicor.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Debug
 */
public class ConexaoBanco {
 
    private String servidor;
    private String usuario;
    private String senha;
    private String database;
//    private String driver;
    private String caminho;
    private boolean conectado;

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }
    
    public static Properties getProp() throws IOException {
        File pasta = new File("C:\\Sicor\\config");
        
        if(pasta.exists()){
            Properties props = new Properties();
            FileInputStream file = new FileInputStream("database.properties");
            props.load(file);
            return props;
        }
        return null;
    }    
    
    
    public Connection getConnection() throws IOException{

        Connection con = null;
        Properties prop = getProp();

        this.servidor = prop.getProperty("prop.server.servidor");
        this.usuario = prop.getProperty("prop.server.usuario");
        this.senha = prop.getProperty("prop.server.senha");
        this.database = prop.getProperty("prop.server.tabela");
        this.caminho = "jdbc:mysql://";
//        this.driver = "com.mysql.jdbc.Driver";
        
        try {
            con = DriverManager.getConnection(this.caminho + this.servidor + "/" + this.database, this.usuario, this.senha);

            if (con == null) {
                JOptionPane.showMessageDialog(null, "Houve algum erro de conexão. Consulte o código.");
                this.setConectado(false);
            }

            this.setConectado(true);
            return con;
        } catch (SQLException e) {
//            return false;
//            JOptionPane.showMessageDialog(null, "Banco não conectado! Consulte as informações no código.");
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            
            return null;
        }
    }
    
    public void fechaConexao() throws IOException, SQLException{
        this.getConnection().close();
    }
    
        
       
    
    
}
