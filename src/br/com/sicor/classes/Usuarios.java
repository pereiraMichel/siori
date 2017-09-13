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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Debug
 */
public class Usuarios {
    
    private int idusuario;
    private String nome;
    private String cpf;
    private String identidade;
    private int total_refeicao;
    private int total_cafe;
    private int total_almoco;
    private int total_jantar;
    private int codResponsavel;
    private String identificador;
    private int coddelegacao;
    
    ConexaoBanco con;
    Connection conn;
    Statement stmt;
    ResultSet rs;

    public int getTotal_almoco() {
        return total_almoco;
    }

    public void setTotal_almoco(int total_almoco) {
        this.total_almoco = total_almoco;
    }

    public int getTotal_jantar() {
        return total_jantar;
    }

    public void setTotal_jantar(int total_jantar) {
        this.total_jantar = total_jantar;
    }

    public int getCodResponsavel() {
        return codResponsavel;
    }

    public void setCodResponsavel(int codResponsavel) {
        this.codResponsavel = codResponsavel;
    }
    

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getIdentidade() {
        return identidade;
    }

    public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }

    public int getTotal_refeicao() {
        return total_refeicao;
    }

    public void setTotal_refeicao(int total_refeicao) {
        this.total_refeicao = total_refeicao;
    }

    public int getTotal_cafe() {
        return total_cafe;
    }

    public void setTotal_cafe(int total_cafe) {
        this.total_cafe = total_cafe;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public int getCoddelegacao() {
        return coddelegacao;
    }

    public void setCoddelegacao(int coddelegacao) {
        this.coddelegacao = coddelegacao;
    }
    
    public int consultaIdUsuario(String user){
        try{
            String sql = "SELECT * FROM tblusuarios WHERE identificador = '" + user + "' or nome = '" + user + "'";
            con = new ConexaoBanco();
            conn = con.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            //System.out.println(sql);
            if(rs.absolute(1)){
                //JOptionPane.showMessageDialog(null, "Id Usuário: " + rs.getInt("idusuario"));
                return rs.getInt("idusuario");
            }else{
                return 0;
            }
            
        }catch (IOException | SQLException ex){
            System.out.println("Erro no banco de dados. Descrição: " + ex.getMessage());
            return -1;
        }
    }
    
    public String retornoUsuario(String usuario){
        String sql = "SELECT identificador, nome FROM tblusuarios WHERE identificador = '" + usuario + "'";
        
        try{
            con = new ConexaoBanco();
            conn = con.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            if(rs.absolute(1)){
//                Principal principal = new Principal();
//                principal.setNomeUsuario(rs.getString("nome"));
                return rs.getString("nome");
            }else{
                return "";
            }
            
        }catch(IOException | SQLException ex){
            System.out.println("Erro na consulta. Descrição: " + ex.getMessage() + ". SQL preenchido: " + sql);
        }
        return null;
    }
    
    public void tabelaUsuarios(JTable tabela){
        ArrayList dados = new ArrayList();
        String[] nomeColunas = new String[]{"IDENTIFICADOR", "USUÁRIO", "INSTITUTO","RESPONSÁVEL"};
        String sql = "SELECT t.identificador, t.nome AS nomeUsuario, i.nome AS instituto, r.nome AS responsavel " +
                     "FROM tblusuarios t " +
                     "LEFT JOIN responsavel r ON t.codresponsavel = r.idresponsavel " +
                     "LEFT JOIN delegacao d ON r.idresponsavel = d.iddelegacao " +
                     "LEFT JOIN instituto i ON d.codinstituto = i.idinstituto";
        try{
            con = new ConexaoBanco();
            conn = con.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.first();
            
            do{
                dados.add(new Object[]{rs.getString("identificador"), rs.getString("nomeUsuario"), rs.getString("instituto"), rs.getString("responsavel")});
                //labelTotalDia.setText(rs.getString("totalGeral"));
            }while(rs.next());
            
        ModeloTabela modelo = new ModeloTabela(dados, nomeColunas);
        tabela.setModel(modelo);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(100); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(0).setResizable(false);// Não poderá alterar o tamanho.
        tabela.getColumnModel().getColumn(1).setPreferredWidth(250); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(1).setResizable(false);// Não poderá alterar o tamanho.
        tabela.getColumnModel().getColumn(2).setPreferredWidth(90); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(2).setResizable(false);// Não poderá alterar o tamanho.
        tabela.getColumnModel().getColumn(3).setPreferredWidth(200); // define o tamanho da coluna.
        tabela.getColumnModel().getColumn(3).setResizable(false);// Não poderá alterar o tamanho.
        
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setAutoResizeMode(tabela.AUTO_RESIZE_OFF);
        
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
        }catch(IOException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Não registro na tabela. Inclua um novo. Erro: " + ex.getMessage(), "ATENÇÃO", 0);
//            labelMensagem.setVisible(true);
//            labelMensagem.setText("Não há registro na tabela. Inclua um novo.");
        }
        
    }
    
    public void inserirUsuario(){

        Dados d = new Dados();
        
        this.setIdusuario(d.consultaUltimoId("idusuario", "tblusuarios"));

        String sql = "INSERT INTO tblusuarios (idusuario, identificador, nome, identidade, cpf, total_almoco, total_jantar, total_cafe, coddelegacao, codresponsavel) "
                + "VALUES (" + this.idusuario + ", '" + this.identificador + "', '" + this.nome + "', '" + this.identidade + "', '" + this.cpf + "', " + this.total_almoco + ", " + this.total_jantar + ", " + this.total_cafe + ", " + this.coddelegacao + ", " + this.codResponsavel + ")";
        //JOptionPane.showMessageDialog(null, sql);
        
        try{
            
            con = new ConexaoBanco();
            conn = con.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            
//            Principal principal = new Principal();
//            principal.preencheMensagemSucesso("Incluído com sucesso!");
            
            stmt.close();
            conn.close();

        }catch (IOException | SQLException ex){
            System.out.println("Erro na inclusão. Descrição: " + ex.getMessage());
        }
        
        
    }
    
    public String buscaUltimoIdentificador(){
        String sql = "select identificador from tblusuarios ORDER BY identificador DESC limit 1";
        
        try{
            con = new ConexaoBanco();
            conn = con.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            if(rs.absolute(1)){
                
                int valor = Integer.valueOf(rs.getString("identificador"));
                
                int soma = valor + 1;
                
                
//                Principal principal = new Principal();
//                principal.setNomeUsuario(rs.getString("nome"));
                return String.valueOf(soma);
            }else{
                return "";
            }
            
        }catch(IOException | SQLException ex){
            System.out.println("Erro na consulta. Descrição: " + ex.getMessage() + ". SQL preenchido: " + sql);
        }
        return "";
        
    }
    
}
