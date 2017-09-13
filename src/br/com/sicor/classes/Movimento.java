/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicor.classes;

import br.com.sicor.view.Principal;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Michel Pereira
 */
public class Movimento {

    private int idmovimento;
    private String dataregistro;
    private int codusuario;
    private int refeicoes;
    private int almoco;
    private int jantar;
    private int cafe;
    
    ConexaoBanco con;
    Connection conn;
    Statement stmt;
    ResultSet rs;
    ResultSet rs_data;

    public int getAlmoco() {
        return almoco;
    }

    public void setAlmoco(int almoco) {
        this.almoco = almoco;
    }

    public int getJantar() {
        return jantar;
    }

    public void setJantar(int jantar) {
        this.jantar = jantar;
    }

    public int getCafe() {
        return cafe;
    }

    public void setCafe(int cafe) {
        this.cafe = cafe;
    }

    public int getIdmovimento() {
        return idmovimento;
    }

    public void setIdmovimento(int idmovimento) {
        this.idmovimento = idmovimento;
    }

    public String getDataregistro() {
        return dataregistro;
    }

    public void setDataregistro(String dataregistro) {
        this.dataregistro = dataregistro;
    }

    public int getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(int codusuario) {
        this.codusuario = codusuario;
    }

    public int getRefeicoes() {
        return refeicoes;
    }

    public void setRefeicoes(int refeicoes) {
        this.refeicoes = refeicoes;
    }
    
    public boolean verificaDuplicidade(){

            try{

                String sql = "SELECT * FROM movimento "
                        + "WHERE dataregistro = '" + this.dataregistro + "' AND "
                        + "codusuario = " + this.codusuario + " AND "
                        + "refeicoes = " + this.refeicoes;
                
                con = new ConexaoBanco();
                conn = con.getConnection();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);

                if(rs.absolute(1)){
                    return false;
                }
                
    //            Principal principal = new Principal();
    //            principal.preencheMensagemSucesso("Incluído com sucesso!");

                stmt.close();
                conn.close();

            }catch (IOException | SQLException ex){
                System.out.println("Erro na inclusão. Descrição: " + ex.getMessage());
            }

            return true;
    }
    
    public String incluiMovimento(){
        if (verificaDuplicidade()){
            Dados dados = new Dados();
            this.setIdmovimento(dados.consultaUltimoId("idmovimento", "movimento"));

            String sql = "INSERT INTO movimento (idmovimento, dataregistro, codusuario, refeicoes) "
                    + "VALUES (" + this.idmovimento + ", '" + this.dataregistro + "', " + this.codusuario + ", " + this.refeicoes + ")";
            //JOptionPane.showMessageDialog(null, sql);

            try{

                con = new ConexaoBanco();
                conn = con.getConnection();
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);

                stmt.close();
                conn.close();
                return "Inclusão efetuada com sucesso !";

            }catch (IOException | SQLException ex){
                System.out.println("Erro na inclusão. Descrição: " + ex.getMessage());
            }
            return "Problemas ao incluir.";
        
        }else{ //caso falso
            return "Cadastro já efetuado!";
        }        
        
    }
    
    public void tabelaGeralMovimentoData(JTable tabela){

        String sql = "SELECT DATE_FORMAT(m.dataregistro, '%d/%m/%Y') AS dataRegistro, t.identificador, t.nome AS usuario, r.nome AS refeicao, " +
                     "(SELECT COUNT(mo.idmovimento) FROM movimento mo WHERE mo.codusuario = t.idusuario AND mo.refeicoes = 1) as cafe, " +
                     "(SELECT COUNT(mo.idmovimento) FROM movimento mo WHERE mo.codusuario = t.idusuario AND mo.refeicoes = 2) as almoco, " +
                     "(SELECT COUNT(mo.idmovimento) FROM movimento mo WHERE mo.codusuario = t.idusuario AND mo.refeicoes = 3) as jantar " +
                     "FROM movimento m " +
                     "LEFT JOIN tblusuarios t ON m.codusuario = t.idusuario " +
                     "LEFT JOIN refeicoes r ON m.refeicoes = r.idrefeicoes " +
                     "ORDER BY m.dataregistro";
        
        ArrayList dados = new ArrayList();
        String[] nomeColunas = new String[]{"DATA", "IDENTIFICADOR", "USUÁRIO", "CAFÉ", "ALMOÇO", "JANTAR"};
        try{
            con = new ConexaoBanco();
            conn = con.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.first();
            
            do{
                dados.add(new Object[]{rs.getString("dataRegistro"), rs.getString("identificador"), rs.getString("usuario"), rs.getString("cafe"), rs.getString("almoco"), rs.getString("jantar")});
                //labelTotalDia.setText(rs.getString("totalGeral"));
            }while(rs.next());
            
        ModeloTabela modelo = new ModeloTabela(dados, nomeColunas);
        tabela.setModel(modelo);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(100); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(0).setResizable(false);// Não poderá alterar o tamanho.
        tabela.getColumnModel().getColumn(1).setPreferredWidth(100); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(1).setResizable(false);// Não poderá alterar o tamanho.
        tabela.getColumnModel().getColumn(2).setPreferredWidth(250); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(2).setResizable(false);// Não poderá alterar o tamanho.
        tabela.getColumnModel().getColumn(3).setPreferredWidth(50); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(3).setResizable(false);// Não poderá alterar o tamanho.
        tabela.getColumnModel().getColumn(4).setPreferredWidth(80); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(4).setResizable(false);// Não poderá alterar o tamanho.
        tabela.getColumnModel().getColumn(5).setPreferredWidth(60); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(5).setResizable(false);// Não poderá alterar o tamanho.

        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setAutoResizeMode(tabela.AUTO_RESIZE_OFF);
        
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        }catch(IOException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Não registro na tabela. Inclua um novo. Erro: " + ex.getMessage(), "ATENÇÃO", 0);
        }

        
    }    
    
    public void tabelaGeralMovimento(JTable tabela){
/*        
        String sql1 = "SELECT t.idusuario, t.identificador, t.nome AS usuario, t.identidade, t.cpf, i.nome AS instituto, r.nome AS responsavel, " +
"	   DATE_FORMAT(m.dataregistro, '%d/%m/%Y') AS dataRegistro, " +
"	   (SELECT COUNT(mo.idmovimento) from movimento mo where mo.codusuario = t.idusuario and mo.refeicoes = 1) AS totalCafe, " +
"	   (SELECT COUNT(mo.idmovimento) from movimento mo where mo.codusuario = t.idusuario and mo.refeicoes = 2) AS totalAlmoco, " +
"	   (SELECT COUNT(mo.idmovimento) from movimento mo where mo.codusuario = t.idusuario and mo.refeicoes = 3) AS totalJantar " +
           "FROM movimento m " +
           "LEFT JOIN tblusuarios t ON m.codusuario = t.idusuario " +
           "LEFT JOIN refeicoes re ON m.refeicoes = re.idrefeicoes " +
           "LEFT JOIN responsavel r ON t.codresponsavel = r.idresponsavel " +
           "LEFT JOIN delegacao d ON r.idresponsavel = d.iddelegacao " +
           "LEFT JOIN instituto i ON d.codinstituto = i.idinstituto " +
           "GROUP BY t.nome";
        */
        //String sqlData = "SELECT dataRegistro FROM movimento GROUP BY dataRegistro";
        
            String sql = "SELECT m.dataRegistro, t.nome AS usuario, t.identificador, i.nome AS instituto, " +
                            "(SELECT COUNT(mo.idmovimento) FROM movimento mo WHERE mo.codusuario = t.idusuario AND mo.refeicoes = 1 "
                    +       "AND mo.dataRegistro IS NOT NULL) AS totalCafe, " +
                            "(SELECT COUNT(mo.idmovimento) FROM movimento mo WHERE mo.codusuario = t.idusuario AND mo.refeicoes = 2 "
                    +       "AND mo.dataRegistro IS NOT NULL) AS totalAlmoco, " +
                            "(SELECT COUNT(mo.idmovimento) FROM movimento mo WHERE mo.codusuario = t.idusuario AND mo.refeicoes = 3 "
                    +       "AND mo.dataRegistro IS NOT NULL) AS totalJantar " +
                            "FROM movimento m " +
                            "LEFT JOIN tblusuarios t ON m.codusuario = t.idusuario " +
                            "LEFT JOIN refeicoes re ON m.refeicoes = re.idrefeicoes " +
                            "LEFT JOIN responsavel r ON t.codresponsavel = r.idresponsavel " +
                            "LEFT JOIN delegacao d ON r.idresponsavel = d.iddelegacao " +
                            "LEFT JOIN instituto i ON d.codinstituto = i.idinstituto " +
                            "WHERE m.dataRegistro IS NOT NULL " +
                            "GROUP BY t.nome";

        ArrayList dados = new ArrayList();
        String[] nomeColunas = new String[]{"IDENTIFICADOR", "USUÁRIO", "DELEGAÇÃO", "CAFÉ", "ALMOÇO", "JANTAR"};
        try{
            con = new ConexaoBanco();
            conn = con.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.first();
//            rs_data = stmt.executeQuery(sqlData);
            
            //System.out.println("SQL Movimentos: " + sqlData);
            
/*
            String sql = "SELECT m.dataRegistro, t.nome AS usuario, t.identificador, i.nome AS instituto, " +
                            "(SELECT COUNT(mo.idmovimento) FROM movimento mo WHERE mo.codusuario = t.idusuario AND mo.refeicoes = 1 "
                    +       "AND mo.dataRegistro = '" + rs_data.getString("dataRegistro") + "') AS totalCafe, " +
                            "(SELECT COUNT(mo.idmovimento) FROM movimento mo WHERE mo.codusuario = t.idusuario AND mo.refeicoes = 2 "
                    +       "AND mo.dataRegistro = '" + rs_data.getString("dataRegistro") + "') AS totalAlmoco, " +
                            "(SELECT COUNT(mo.idmovimento) FROM movimento mo WHERE mo.codusuario = t.idusuario AND mo.refeicoes = 3 "
                    +       "AND mo.dataRegistro = '" + rs_data.getString("dataRegistro") + "') AS totalJantar " +
                            "FROM movimento m " +
                            "LEFT JOIN tblusuarios t ON m.codusuario = t.idusuario " +
                            "LEFT JOIN refeicoes re ON m.refeicoes = re.idrefeicoes " +
                            "LEFT JOIN responsavel r ON t.codresponsavel = r.idresponsavel " +
                            "LEFT JOIN delegacao d ON r.idresponsavel = d.iddelegacao " +
                            "LEFT JOIN instituto i ON d.codinstituto = i.idinstituto " +
                            "WHERE m.dataRegistro = '" + rs_data.getString("dataRegistro") +"' " +
                            "GROUP BY t.nome";
*/
            //System.out.println("SQL principal: " + sql);
            
            do{
                dados.add(new Object[]{rs.getString("identificador"), rs.getString("usuario"), rs.getString("instituto"), rs.getString("totalCafe"), rs.getString("totalAlmoco"), rs.getString("totalJantar")});
                //labelTotalDia.setText(rs.getString("totalGeral"));
            }while(rs.next());
            
        ModeloTabela modelo = new ModeloTabela(dados, nomeColunas);
        tabela.setModel(modelo);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(100); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(0).setResizable(false);// Não poderá alterar o tamanho.
        tabela.getColumnModel().getColumn(1).setPreferredWidth(250); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(1).setResizable(false);// Não poderá alterar o tamanho.
        tabela.getColumnModel().getColumn(2).setPreferredWidth(100); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(2).setResizable(false);// Não poderá alterar o tamanho.
        tabela.getColumnModel().getColumn(3).setPreferredWidth(50); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(3).setResizable(false);// Não poderá alterar o tamanho.
        tabela.getColumnModel().getColumn(4).setPreferredWidth(80); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(4).setResizable(false);// Não poderá alterar o tamanho.
        tabela.getColumnModel().getColumn(5).setPreferredWidth(60); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(5).setResizable(false);// Não poderá alterar o tamanho.

        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setAutoResizeMode(tabela.AUTO_RESIZE_OFF);
        
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        }catch(IOException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Não registro na tabela. Inclua um novo. Erro: " + ex.getMessage(), "ATENÇÃO", 0);
//            labelMensagem.setVisible(true);
//            labelMensagem.setText("Não há registro na tabela. Inclua um novo.");
        }
        
        
    }
/*    
    public void quantidadeRegistroDia(JLabel mensagem){
        
        String complementoSQL = null;
        int qtde = 0;
        
            if(this.dataregistro.equals("")){
                complementoSQL = "";
            }else{
                complementoSQL = " WHERE m.dataregistro = '" + this.dataregistro + "'";
            }
            
            String sql = "SELECT COUNT(m.idmovimento) AS quantidade FROM movimento m " + complementoSQL;

        try{
            con = new ConexaoBanco();
            conn = con.getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery(sql);
            rs.first();
//            rs.next();

            mensagem.setVisible(true);

            if(rs.absolute(1)){
                qtde = rs.getInt("quantidade");
                mensagem.setText("Quantidade de movimento do dia: " + qtde);
            }else{
                mensagem.setText("Quantidade: 0");
            }
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
    }
*/
    
}
