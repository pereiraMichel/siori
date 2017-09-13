/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicor.classes;

import br.com.sicor.view.Principal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Michel Pereira
 */
public class Dados {
    
    private String nome;
    private String opcao;
    private String mensagem;
    private String s; //SubMáscara Rede
    private String ip; //IP Rede
    private byte[] mac;
    private Boolean pastaExiste;
    private String ipLocal;
    private String macFisico;
    Properties prop;
    Principal tela;
    
    ConexaoBanco con;
    Connection conn;
    Statement stmt;
    ResultSet rs;

    public String getMacFisico() {
        return macFisico;
    }

    public void setMacFisico(String macFisico) {
        this.macFisico = macFisico;
    }
    
    public String getIpLocal() {
        return ipLocal;
    }

    public void setIpLocal(String ipLocal) {
        this.ipLocal = ipLocal;
    }
    
    public Boolean getPastaExiste() {
        return pastaExiste;
    }

    public void setPastaExiste(Boolean pastaExiste) {
        this.pastaExiste = pastaExiste;
    }
    
    public byte[] getMac() {
        return mac;
    }

    public void setMac(byte[] mac) {
        this.mac = mac;
    }
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getOpcao() {
        return opcao;
    }

    public void setOpcao(String opcao) {
        this.opcao = opcao;
    }
    
    public void setMensagem(String mensagem){
        this.mensagem = mensagem;
    }
    
    public String getMensagem(){
        return mensagem;
    }
    
    
    public void leituraSicor(){
        File documento = new File("C:\\Sicor\\config\\dados.properties");
        
        if(documento.exists()){
            System.out.println("\nPasta localizada.");
            this.pastaExiste = true;
        }else{
            System.out.println("\nPasta não localizada");
            this.pastaExiste = false;
        }
    }
    public void leituraDataBaseSicor(){
        File documento = new File("C:\\Sicor\\config\\database.properties");
        
        if(documento.exists()){
//            System.out.println("\nArquivo database localizada.");
            this.setPastaExiste(true);
        }else{
//            System.out.println("\nArquivo database não localizada");
            this.setPastaExiste(false);
        }
    }
    
    public void verificaPastaPrincipal(){

        File local = new File("C:\\Sicor\\");
        File pastaConfig = new File("C:\\Sicor\\config\\");
        File pastaXml = new File("C:\\Sicor\\xml\\");

        if(!local.exists()){
            local.mkdir();
        }
        if(!pastaConfig.exists()){
            pastaConfig.mkdir();
        }
        if(!pastaXml.exists()){
            pastaXml.mkdir();
        }
    }

    public void inforMac(){

        try {         
           InetAddress address = InetAddress.getLocalHost();  
           NetworkInterface ni = NetworkInterface.getByInetAddress(address);  
           mac = ni.getHardwareAddress();
           String macAddress = "";
           for (int i = 0; i < mac.length; i++) {             
               macAddress += (String.format("%02X-", mac[i]));  
           }
           this.macFisico = macAddress.substring(0, macAddress.length()-1);
//           System.out.println(macAddress.substring(0, macAddress.length()-1));
        } catch (UnknownHostException e) {  
           e.printStackTrace();
        } catch (SocketException e) {  
           e.printStackTrace();  
        }        
    }
    
    public String mac(){

           String macAddress = "";
        try {         
           InetAddress address = InetAddress.getLocalHost();  
           NetworkInterface ni = NetworkInterface.getByInetAddress(address);  
           mac = ni.getHardwareAddress();
           for (int i = 0; i < mac.length; i++) {             
               macAddress += (String.format("%02X-", mac[i]));  
           }
//           return macAddress.substring(0, macAddress.length()-1);
        } catch (UnknownHostException e) {  
           e.printStackTrace();
        } catch (SocketException e) {  
           e.printStackTrace();  
        }
           return macAddress.substring(0, macAddress.length()-1);
    }
    
    
    public void inforRede(){
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
// Objeto para as interfaces de rede
                NetworkInterface iface = interfaces.nextElement();
// Não mostra endereços de loopback
                if (iface.isLoopback() || !iface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
// Pega IP
                    this.ip = addr.getHostAddress();
// Pega MAC
                    this.mac = iface.getHardwareAddress();
                    
// Mostra MAC formatado
                    for (int i = 0; i < mac.length; i++) {
                        this.s += String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "");
                    }
// Mostra tudo
//                    System.out.println(iface.getDisplayName() + "\n"
//                            + this.s + " - "
//                            + this.ip);
                }
            }
                    InetAddress localHost = Inet4Address.getLocalHost();
                    NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
//                    mensagens.append("\n\nPlaca de Rede: " + networkInterface.getDisplayName());
                    java.util.List<InterfaceAddress> lista = networkInterface.getInterfaceAddresses();
                    this.ipLocal=lista.get(0).getAddress().getHostAddress();
        
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Dados.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    
    public void gravaDatabase(String tabela, String usuario, String senha, String servidor){
        String nomeArquivo = "database.properties";
        String path = "C:\\Sicor\\config\\";
        prop = new Properties();
        
        try {
            String pastaSistema = "C:\\Sicor";

            prop.setProperty("prop.server.tabela", tabela);
            prop.setProperty("prop.server.usuario", usuario);
            prop.setProperty("prop.server.senha", senha);
            prop.setProperty("prop.server.servidor", servidor);
            prop.setProperty("prop.server.pathSistema", pastaSistema);

            prop.store(new FileOutputStream(path + nomeArquivo), "prop.server");

            this.pastaExiste = true;
            
            tela = new Principal();
            tela.bancoAtivado();
                    
        } catch (FileNotFoundException ex) {
            System.out.println("Erro File Not Found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro IOException: " + ex.getMessage());
        }
        
    }
    
    public void gravaProperties() throws IOException{
        inforRede(); // Busca informações da rede
        inforMac();
        String nomeArquivo = "dados.properties";
        String path = "C:\\Sicor\\config\\";
        prop = new Properties();
        
        try {
            String pastaSistema = "C:\\Sicor";

            prop.setProperty("prop.server.usuarioLogado", System.getProperty("user.name"));
            prop.setProperty("prop.server.nomeUsuario", this.nome);
            prop.setProperty("prop.server.ipv4", this.ipLocal);
            prop.setProperty("prop.server.ipv6", this.ip);
            prop.setProperty("prop.server.path", path);
            prop.setProperty("prop.server.mac", this.macFisico);
            prop.setProperty("prop.server.so", System.getProperty("os.name"));
            prop.setProperty("prop.server.pathSistema", pastaSistema);

            prop.store(new FileOutputStream(path + nomeArquivo), "prop.server");

            this.pastaExiste = true;
            
            tela = new Principal();
            tela.mensagensBoasVindas();
                    
        } catch (FileNotFoundException ex) {
            System.out.println("Erro File Not Found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro IOException: " + ex.getMessage());
        }
    }
    
    public void gravaXml(){
        
        Element dados = new Element("dadosUsuario");
        Document data = new Document(dados);
        
        Element nome = new Element("nomeUsuario");
        nome.setText(System.getProperty("user.name"));
        
        Element contato = new Element("usuario");
        contato.setAttribute("id", "123");
        
        contato.addContent(nome);
        
        dados.addContent(contato);
        
        XMLOutputter xout = new XMLOutputter();
        
        org.jdom2.output.Format formato = org.jdom2.output.Format.getPrettyFormat();
        
        xout.setFormat(formato);
        
/*        try{
            xout.output(data, System.out);
        }catch(Exception ex){
            ex.printStackTrace();
        }
*/        
//            if(!local.exists()){
//                local.mkdir();
//            }
        
        try{

            File local = new File("C:\\Sicor\\xml\\");
            if(!local.exists()){
                File pasta = new File("C:\\Sicor");
                File pasta2 = new File("C:\\Sicor\\xml");
                pasta.mkdir();
                pasta2.mkdir();
            }
            FileWriter arquivo = new FileWriter(new File(local + "\\usuario.xml"));
            xout.output(data, arquivo);
            JOptionPane.showMessageDialog(null, "Arquivo criado com sucesso!");
            System.out.println(local.getAbsolutePath());

        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    public void limitado(){
        String cript = null;
        
        String macAtual = this.mac();
        String nomeArquivo = "config.properties";
        String path = "C:\\Sicor\\config\\";
        //String dataFinal = "25/08/2017"; //pegar valor no properties
        Date horaDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        String dataFinal = prop.getProperty("prop.server.validade");
        String dataHoje = sdf.format(horaDate);
        
        try{
            Date dataH = new Date(sdf.parse(dataHoje).getTime());
            Date dataF = new Date(sdf.parse(dataFinal).getTime());

            if(dataH.after(dataF)){ // Valida data
                System.out.println("Passou do vencimento");
                cript = Criptografia.criptografa("false");
                prop.setProperty("prop.server.autorizada", cript);

            }else{
                System.out.println("As datas não são iguais");
            }
            
            String macGravado = prop.getProperty("prop.server.mac");
            if(macGravado.equals(macAtual)){ // Valida sub-mascara (mac)
                cript = Criptografia.criptografa("true");
                prop.setProperty("prop.server.original", cript);
            }else{
                cript = Criptografia.criptografa("false");
                prop.setProperty("prop.server.original", cript);
            }

            prop.store(new FileOutputStream(path + nomeArquivo), "prop.server");
            
        } catch (FileNotFoundException ex) {
            System.out.println("Erro File Not Found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro IOException: " + ex.getMessage());
        } catch (ParseException ex) {
            System.out.println("Erro ParseException: " + ex.getMessage());
        }
    }
    
    public boolean leituraSistema(){
/*        
try {
			File fTmp = new File("c:\file.tmp");
			File fTxt = new File("c:\file.txt");
			fTxt.createNewFile();
			Writer arquivo = new FileWriter(fTmp);
			fTmp.renameTo(fTxt);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
            String cript = null;

            prop = new Properties();
            String path = "C:\\Sicor\\config\\";
            String nomeArquivo = "config.properties";
            File arquivo = new File("C:\\Sicor\\config\\config.properties");
            
            if(arquivo.exists()){
                try{
                    FileInputStream file = new FileInputStream(path + nomeArquivo);

                    prop.load(file);//Leitura do arquivo
                    
                    
                    
                    if(prop.getProperty("prop.server.autorizada").equals("false")){
                        return false;
                    }

                }catch(FileNotFoundException ex){
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
        //            Logger.getLogger(Dados.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }else{
                return false;
            }
    }
    
    public void leituraUsuario(){
        try{
            prop = new Properties();
            String path = "C:\\Sicor\\config\\";
            String nomeArquivo = "dados.properties";

            System.out.println("Pronto para leitura em properties");
            System.out.println("A pasta existe.");
            FileInputStream file = new FileInputStream(path + nomeArquivo);

            prop.load(file);//Leitura do arquivo

            String nomeUsuario = prop.getProperty("prop.server.nomeUsuario");
            String usuarioLogado = prop.getProperty("prop.server.usuarioLogado");
            String caminho = prop.getProperty("prop.server.path");
            String pathSistema = prop.getProperty("prop.server.pathSistema");
            
            this.setNome(nomeUsuario);
            
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
//            Logger.getLogger(Dados.class.getName()).log(Level.SEVERE, null, ex);
        }
        
// LEITURA XML        
//        Document doc = null;
//        SAXBuilder builder = new SAXBuilder();
//        
//        try{
//            
//            doc = builder.build("C:\\Aida\\xml\\usuario.xml");
//            
//            Element agenda = doc.getRootElement();
//            
//            List<Element> lista = agenda.getChildren();
//            
//            for(Element e: lista){
////                System.out.println(e.getAttributeValue("id"));
////                System.out.println("Nome: " + e.getChildText("nomeUsuario"));
//                this.nome = e.getChildText("nomeUsuario");
//            }
//            
//        }catch(Exception e){
//            e.printStackTrace();
//        }
        
    }
    
    public String retornaNome() {
        
        try{
            prop = new Properties();
            String path = "C:\\Sicor\\config\\";
            String nomeArquivo = "dados.properties";

            System.out.println("Pronto para leitura em properties");
            System.out.println("A pasta existe.");
            FileInputStream file = new FileInputStream(path + nomeArquivo);

            prop.load(file);//Leitura do arquivo

            String nomeUsuario = prop.getProperty("prop.server.nomeUsuario");
            return nomeUsuario;
            
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public int consultaUltimoId(String id, String tabela){
        try{
            String sql = "SELECT MAX(" + id + ") as " + id + " FROM " + tabela;
            
//            JOptionPane.showMessageDialog(null, sql);
            
            con = new ConexaoBanco();
            conn = con.getConnection();
            stmt = conn.createStatement();
            
            rs = stmt.executeQuery(sql);
            rs.next();
            
            int resultado = rs.getInt(id);
            int soma = resultado + 1;
            
            return soma;
            
        }catch(IOException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Houve um erro na consulta. Erro: " + ex.getMessage(), "ERRO", 0);
        }
        return 0;
    }

    private Object byteToHexString(byte parte) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String byteToHexString(StringBuffer mac) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
