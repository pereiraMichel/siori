/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicor.classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Debug
 */
public class Relatorios {
    
    ConexaoBanco con;
    Connection conn;
    Statement stmt;
    ResultSet rs;

    
    public void geraRelatorioUsuarios(){
        String sql = "SELECT t.nome AS nomeUsuario, t.identificador, t.identidade, t.cpf, i.nome AS instituto, "
                +   "r.nome AS responsavel, t.total_cafe, t.total_almoco, t.total_jantar " +
                    "FROM tblusuarios t " +
                    "LEFT JOIN responsavel r ON t.codresponsavel = r.idresponsavel " +
                    "LEFT JOIN delegacao d ON r.idresponsavel = d.iddelegacao " +
                    "LEFT JOIN instituto i ON d.codinstituto = i.idinstituto";

        try{
            con = new ConexaoBanco();
            conn = con.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            //Gera o relatório                
            String path = "relatorios//reportUsuarios.jasper";
            JRResultSetDataSource jr = new JRResultSetDataSource(rs); // Cria um resultset do banco de dados
            Map param = new HashMap(); // Abre o parâmetro
            JasperPrint print = JasperFillManager.fillReport(path, param, jr); // Junta as informações do banco e parâmetros
            JasperViewer view = new JasperViewer(print, false);//Prepara a visualização do relatório
            view.setTitle("Relatório de Usuários"); //Preenche o título do relatório
            view.setVisible(true); //Visualiza o relatório
            view.toFront();//Puxa o relatório para frente do frame.

        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
    
    public void geraRelatorioMovimento(){
        String sql = "SELECT t.idusuario, t.identificador, t.nome AS usuario, t.identidade, t.cpf, "
                + "i.nome as instituto, r.nome as responsavel, " +
"	   DATE_FORMAT(m.dataregistro, '%d/%m/%Y') AS dataRegistro, " +
"	   (SELECT COUNT(mo.idmovimento) from movimento mo where mo.codusuario = t.idusuario and mo.refeicoes = 1) AS totalCafe, " +
"	   (SELECT COUNT(mo.idmovimento) from movimento mo where mo.codusuario = t.idusuario and mo.refeicoes = 2) AS totalAlmoco, " +
"	   (SELECT COUNT(mo.idmovimento) from movimento mo where mo.codusuario = t.idusuario and mo.refeicoes = 3) AS totalJantar, " +
"	   (SELECT t.total_cafe - count(mo.idmovimento) from movimento mo where mo.codusuario = t.idusuario and mo.refeicoes = 1) AS difCafe, " +
"	   (SELECT t.total_almoco - count(mo.idmovimento) from movimento mo where mo.codusuario = t.idusuario and mo.refeicoes = 2) AS difAlmoco, " +
"	   (SELECT t.total_Jantar - count(mo.idmovimento) from movimento mo where mo.codusuario = t.idusuario and mo.refeicoes = 3) AS difJantar " +
"FROM movimento m " +
"LEFT JOIN tblusuarios t ON m.codusuario = t.idusuario " +
"LEFT JOIN refeicoes re ON m.refeicoes = re.idrefeicoes " +
"LEFT JOIN responsavel r ON t.codresponsavel = r.idresponsavel " +
"LEFT JOIN delegacao d ON r.idresponsavel = d.iddelegacao " +
"LEFT JOIN instituto i ON d.codinstituto = i.idinstituto " +
"GROUP BY t.nome";

        
        try{
            con = new ConexaoBanco();
            conn = con.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            //Gera o relatório                
            String path = "relatorios//reportMovimento.jasper";
            JRResultSetDataSource jr = new JRResultSetDataSource(rs); // Cria um resultset do banco de dados
            Map param = new HashMap(); // Abre o parâmetro
            JasperPrint print = JasperFillManager.fillReport(path, param, jr); // Junta as informações do banco e parâmetros
            JasperViewer view = new JasperViewer(print, false);//Prepara a visualização do relatório
            view.setTitle("Relatório de Movimento"); //Preenche o título do relatório
            view.setVisible(true); //Visualiza o relatório
            view.toFront();//Puxa o relatório para frente do frame.

        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    
}
