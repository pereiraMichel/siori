/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sicor.view;

import br.com.sicor.classes.ConexaoBanco;
import br.com.sicor.classes.Dados;
import br.com.sicor.classes.Movimento;
import br.com.sicor.classes.Refeicoes;
import br.com.sicor.classes.Relatorios;
import br.com.sicor.classes.Usuarios;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Debug
 */
public class Principal extends javax.swing.JFrame {

    Date horaDate;
    Locale locale = new Locale("pt", "BR");
    Calendar cal = Calendar.getInstance(locale);
    SimpleDateFormat sdf;
    Dados dados;
    ConexaoBanco conexao;

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        this.desativaFuncoes();
        this.tamanhoMenus();
        this.bloqueiaListText();
        this.mensagensBoasVindas();
        this.verificaAtivacao();
//        ativaComboRefeicao();
    }
    
    public void bloqueiaListText(){
        textMensagens.setEditable(false);
        btSalvar.setVisible(false);
        //textMensagens.setEnabled(false);
    }
    
    public void sair(){
        if(JOptionPane.showConfirmDialog(null, "Confirma saída?", "SAIR", JOptionPane.YES_NO_OPTION) == 0){
            System.gc();
            System.exit(0);
            this.dispose();
        }else{
            this.setDefaultCloseOperation(Principal.DO_NOTHING_ON_CLOSE);
        }
    }
    
    public void validCode(){
        
        //Pegar o valor constante no campo.
        String dataFinal = "17/08/2017";
        horaDate = new Date();
        textMensagens.append("\nValidando Data\n");
        textMensagens.append("\n===============================\n");
        if(horaDate.equals(dataFinal)){
            textMensagens.append("\nData válida\n");
        }else{
            textMensagens.append("\nData Inválida\n");
        }

    }
    public boolean data(String data) { // só verifica se o formato é válido
        try {
            sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            sdf.parse(data);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }    
    
    public void verificaAtivacao(){
        dados = new Dados();
        
        if(dados.leituraSistema()){
            menuArquivo.setEnabled(true);
            menuRelatorios.setEnabled(true);
            subMenuDatabase.setEnabled(true);
            toolbar.setEnabled(true);
            textUsuario.setEnabled(true);
        }else{
            menuArquivo.setEnabled(false);
            menuRelatorios.setEnabled(false);
            subMenuDatabase.setEnabled(false);
            toolbar.setEnabled(false);
            textUsuario.setEnabled(false);
        }
        dados.limitado();
        
        
    }

    public void desativaFuncoes(){
        subMenuLogin.setVisible(false);
        separador1.setVisible(false);
        subMenuLogoff.setVisible(false);
        panelMovimento.setVisible(false);
//        comboRefeicao.setVisible(false);
        //labelNomeUsuario.setVisible(false);
        
        //subMenuDelegacao.setEnabled(false);
        //subMenuInstituto.setEnabled(false);
        //subMenuMovimento.setEnabled(false);
        //subMenuRelMovimento.setEnabled(false);
        //subMenuRelUsuarios.setEnabled(false);
        //subMenuResponsavel.setEnabled(false);
        //subMenuUsuarios.setEnabled(false);
    }
    
    public void bancoAtivado(){
        textMensagens.append("\nInformações do banco de dados salvos com sucesso.\n");
    }
    
    public void centralizarTextMensagens(){
        
        textMensagens.setLayout(new FlowLayout());
        textMensagens.add(textMensagens, CENTER_ALIGNMENT);
/*        
        StyledDocument doc = textMensagens.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        */
    }
    
    public void mensagensBoasVindas(){
        int hora = cal.get(Calendar.HOUR_OF_DAY);
        int minuto = cal.get(Calendar.MINUTE);
        
        JLabel inicio = new JLabel();
        
//        textMensagens.setLayout(new FlowLayout());
//        this.add(textMensagens, CENTER_ALIGNMENT);

        dados = new Dados();
        
        inicio.setText("\tINICIO DO SISTEMA");
        inicio.setHorizontalAlignment(SwingConstants.CENTER);

        sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        horaDate = new Date();
        
        textMensagens.append("\t" + inicio.getText() + "\n");
        textMensagens.append("\t===============================\n");

        if(hora >= 6 && hora < 12){
            textMensagens.append("\tBom dia!\n");
        }else if(hora >= 12 && hora < 18){
            textMensagens.append("\tBoa tarde!\n");
        }else if(hora >= 18 && hora <= 23){
            textMensagens.append("\tBoa noite!\n");
        }else if(hora > 00 && hora < 6){
            textMensagens.append("\tEstamos na madrugada");
        }else{
            System.out.println("\tNão tem hora\n");
        }
        
        if(hora >= 6 && hora <= 10){
            EventQueue.invokeLater(new Runnable() {  
                public void run() {  
                   labelNomeUsuario.setText("Café da Manhã");
                }  
            });            
        }else if(hora >= 11 && hora < 16){
            EventQueue.invokeLater(new Runnable() {  
                public void run() {  
                   labelNomeUsuario.setText("Almoço");
                }  
            });            
        }else if(hora >= 18 && hora <= 23){
            if(hora == 23 || minuto > 30){
                EventQueue.invokeLater(new Runnable() {  
                    public void run() {  
                       labelNomeUsuario.setText("Sem expediente");
                    }  
                });            
            }else{
                EventQueue.invokeLater(new Runnable() {  
                    public void run() {  
                       labelNomeUsuario.setText("Jantar");
                    }  
                });            
            }
        }else{
            EventQueue.invokeLater(new Runnable() {  
                public void run() {  
                   labelNomeUsuario.setText("Sem expediente");
                }  
            });            
        }
        textMensagens.append("\n\t" + sdf.format(horaDate) + " - " + hora + ":" + minuto + "h\n");
        textMensagens.append("\t===============================\n");
        dados = new Dados();
        dados.verificaPastaPrincipal();
//        textMensagens.append("\nLendo informações do banco de dados...\n");
        dados.leituraDataBaseSicor();
//        textMensagens.append("\nOk.\n");
        textMensagens.append("\tVerificando conexão ao banco de dados...\n");
        conexao = new ConexaoBanco();
        try {
            if(conexao.getConnection() == null){
                textMensagens.append("\tConexão não realizada. Verifique com o analista de sistemas\n");
            }else{
                textMensagens.append("\tConexão ativa\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        textMensagens.append("\t===============================\n");
        if(dados.getPastaExiste()){
            textMensagens.append("\tSistema pronto.\n\nIniciando o movimento...\n");
            panelMovimento.setVisible(true);
            textData.setText(sdf.format(horaDate));
            textData.setEditable(false);
            textUsuario.requestFocus();
        }else{
            textMensagens.append("\tBanco de dados não cadastrado. Vá em Opções > Banco de dados");
        }
        textMensagens.append("\n===============================\n");
        textMensagens.setText("");
//        textMensagens.append("\t***************************************************\n");
/*        
        ConexaoBanco con = new ConexaoBanco();
        try {
            con.getConnection();
            if(con.isConectado()){
                textMensagens.append("\t***************************************************\n");
                textMensagens.append("\tVerificando a conexão... ativo!\n");
            }else{
                textMensagens.append("\tVerificando a conexão... inativo! Verifique sua conexão\n");
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      */  
        
        textMensagens.append("*************************************************************\n");
        textMensagens.append("\t" + sdf.format(horaDate) + " - " + hora + ":" + minuto + "h\n");
        textMensagens.append("*************************************************************\n");
        textMensagens.append("\tSICOR\n");
        textMensagens.append("*************************************************************\n");
        
//        textMensagens.;

        
    }

    public void ativaComboRefeicao(){
/*
        String selecao = null;
        int hora = cal.get(Calendar.HOUR_OF_DAY);
        
        if(hora >= 6 && hora < 12){
            selecao = "Café da Manhã";
        }else if(hora >= 12 && hora < 18){
            selecao = "Almoço";
        }else if(hora >= 18 && hora <= 23){
            selecao = "Jantar";
        }else if(hora > 00 && hora < 6){
            selecao = "Café da Manhã";
        }

        comboRefeicao.addItem(selecao);
        */
//        Refeicoes re = new Refeicoes();
        
//        re.listaCombo(comboRefeicao);
        
    }

    public void tamanhoMenus(){
        
        Dimension dimensao = new Dimension();
        dimensao.setSize(150, 30);
        
        //menuArquivo.setPreferredSize(dimensao);
        //menuOpcoes.setPreferredSize(dimensao);
        //menuSobre.setPreferredSize(dimensao);
        
        subMenuUsuarios.setPreferredSize(dimensao);
        subMenuDelegacao.setPreferredSize(dimensao);
        subMenuInstituto.setPreferredSize(dimensao);
        subMenuResponsavel.setPreferredSize(dimensao);
        subMenuSair.setPreferredSize(dimensao);
        subMenuLogin.setPreferredSize(dimensao);
        subMenuLogoff.setPreferredSize(dimensao);
        subMenuProjeto.setPreferredSize(dimensao);
        subMenuSicor.setPreferredSize(dimensao);
        subMenuMovimento.setPreferredSize(dimensao);
        subMenuAtivacao.setPreferredSize(dimensao);

        subMenuRelUsuarios.setPreferredSize(dimensao);
        subMenuRelMovimento.setPreferredSize(dimensao);
        
        textMensagens.setLineWrap(true);
        textMensagens.setWrapStyleWord(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        toolbar = new javax.swing.JToolBar();
        btMovimento = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btUsuarios = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        textMensagens = new javax.swing.JTextArea();
        panelBaixo = new javax.swing.JPanel();
        panelMovimento = new javax.swing.JPanel();
        labelUsuario = new javax.swing.JLabel();
        labelRefeicao = new javax.swing.JLabel();
        labelData = new javax.swing.JLabel();
        textUsuario = new javax.swing.JTextField();
        textData = new javax.swing.JTextField();
        btSalvar = new javax.swing.JButton();
        labelNomeUsuario = new javax.swing.JLabel();
        labelFechamento = new javax.swing.JLabel();
        menu = new javax.swing.JMenuBar();
        menuArquivo = new javax.swing.JMenu();
        subMenuLogin = new javax.swing.JMenuItem();
        separador1 = new javax.swing.JPopupMenu.Separator();
        subMenuUsuarios = new javax.swing.JMenuItem();
        subMenuDelegacao = new javax.swing.JMenuItem();
        subMenuInstituto = new javax.swing.JMenuItem();
        subMenuResponsavel = new javax.swing.JMenuItem();
        subMenuMovimento = new javax.swing.JMenuItem();
        subMenuFechamento = new javax.swing.JMenuItem();
        separador2 = new javax.swing.JPopupMenu.Separator();
        subMenuLogoff = new javax.swing.JMenuItem();
        subMenuSair = new javax.swing.JMenuItem();
        menuOpcoes = new javax.swing.JMenu();
        subMenuAtivacao = new javax.swing.JMenuItem();
        subMenuDatabase = new javax.swing.JMenuItem();
        menuRelatorios = new javax.swing.JMenu();
        subMenuRelUsuarios = new javax.swing.JMenuItem();
        subMenuRelMovimento = new javax.swing.JMenuItem();
        menuSobre = new javax.swing.JMenu();
        subMenuSicor = new javax.swing.JMenuItem();
        subMenuProjeto = new javax.swing.JMenuItem();
        subMenuAjuda = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 204, 204));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        toolbar.setBackground(new java.awt.Color(255, 255, 204));
        toolbar.setFloatable(false);

        btMovimento.setBackground(new java.awt.Color(255, 204, 204));
        btMovimento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sicor/images/icon16x16Agendamento.png"))); // NOI18N
        btMovimento.setToolTipText("Movimento");
        buttonGroup.add(btMovimento);
        btMovimento.setFocusable(false);
        btMovimento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btMovimento.setMaximumSize(new java.awt.Dimension(50, 150));
        btMovimento.setMinimumSize(new java.awt.Dimension(150, 150));
        btMovimento.setPreferredSize(new java.awt.Dimension(150, 150));
        btMovimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMovimentoActionPerformed(evt);
            }
        });
        toolbar.add(btMovimento);
        toolbar.add(jSeparator1);

        btUsuarios.setBackground(new java.awt.Color(255, 204, 204));
        btUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sicor/images/icon16x16PerfilUnico.png"))); // NOI18N
        btUsuarios.setFocusable(false);
        btUsuarios.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btUsuarios.setMaximumSize(new java.awt.Dimension(50, 150));
        btUsuarios.setMinimumSize(new java.awt.Dimension(150, 150));
        btUsuarios.setPreferredSize(new java.awt.Dimension(150, 150));
        btUsuarios.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar.add(btUsuarios);

        scrollPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        textMensagens.setEditable(false);
        textMensagens.setColumns(20);
        textMensagens.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        textMensagens.setLineWrap(true);
        textMensagens.setRows(5);
        textMensagens.setWrapStyleWord(true);
        scrollPane.setViewportView(textMensagens);

        panelBaixo.setBackground(new java.awt.Color(255, 255, 255));
        panelBaixo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        javax.swing.GroupLayout panelBaixoLayout = new javax.swing.GroupLayout(panelBaixo);
        panelBaixo.setLayout(panelBaixoLayout);
        panelBaixoLayout.setHorizontalGroup(
            panelBaixoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelBaixoLayout.setVerticalGroup(
            panelBaixoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 21, Short.MAX_VALUE)
        );

        panelMovimento.setBackground(new java.awt.Color(255, 255, 204));
        panelMovimento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        labelUsuario.setText("Usuário:");

        labelRefeicao.setText("Refeição:");

        labelData.setText("Data:");

        textUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textUsuarioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textUsuarioKeyReleased(evt);
            }
        });

        btSalvar.setText("Salvar");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        labelNomeUsuario.setText("jLabel1");

        javax.swing.GroupLayout panelMovimentoLayout = new javax.swing.GroupLayout(panelMovimento);
        panelMovimento.setLayout(panelMovimentoLayout);
        panelMovimentoLayout.setHorizontalGroup(
            panelMovimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovimentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMovimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btSalvar)
                    .addGroup(panelMovimentoLayout.createSequentialGroup()
                        .addComponent(labelUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelMovimentoLayout.createSequentialGroup()
                        .addGroup(panelMovimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelRefeicao)
                            .addComponent(labelData))
                        .addGroup(panelMovimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelMovimentoLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textData, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMovimentoLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(labelNomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        panelMovimentoLayout.setVerticalGroup(
            panelMovimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovimentoLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panelMovimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelData)
                    .addComponent(textData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelMovimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelRefeicao)
                    .addComponent(labelNomeUsuario))
                .addGap(18, 18, 18)
                .addGroup(panelMovimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUsuario)
                    .addComponent(textUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btSalvar)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        labelFechamento.setText("Fechamento: Ctrl + F");

        menuArquivo.setText("Arquivo");

        subMenuLogin.setText("Login");
        subMenuLogin.setPreferredSize(new java.awt.Dimension(150, 21));
        menuArquivo.add(subMenuLogin);
        menuArquivo.add(separador1);

        subMenuUsuarios.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        subMenuUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sicor/images/icon16x16PerfilUnico.png"))); // NOI18N
        subMenuUsuarios.setText("Usuários");
        subMenuUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuUsuariosActionPerformed(evt);
            }
        });
        menuArquivo.add(subMenuUsuarios);

        subMenuDelegacao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        subMenuDelegacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sicor/images/icon16x16RelatorioGrupo.png"))); // NOI18N
        subMenuDelegacao.setText("Delegação");
        subMenuDelegacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuDelegacaoActionPerformed(evt);
            }
        });
        menuArquivo.add(subMenuDelegacao);

        subMenuInstituto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        subMenuInstituto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sicor/images/icon16x16Grupo.png"))); // NOI18N
        subMenuInstituto.setText("Instituto");
        subMenuInstituto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuInstitutoActionPerformed(evt);
            }
        });
        menuArquivo.add(subMenuInstituto);

        subMenuResponsavel.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        subMenuResponsavel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sicor/images/icon16x16HM.png"))); // NOI18N
        subMenuResponsavel.setText("Responsável");
        subMenuResponsavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuResponsavelActionPerformed(evt);
            }
        });
        menuArquivo.add(subMenuResponsavel);

        subMenuMovimento.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        subMenuMovimento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sicor/images/icon16x16Agendamento.png"))); // NOI18N
        subMenuMovimento.setText("Movimento");
        subMenuMovimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuMovimentoActionPerformed(evt);
            }
        });
        menuArquivo.add(subMenuMovimento);

        subMenuFechamento.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        subMenuFechamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sicor/images/icon16x16Fechamento.png"))); // NOI18N
        subMenuFechamento.setText("Fechamento");
        subMenuFechamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuFechamentoActionPerformed(evt);
            }
        });
        menuArquivo.add(subMenuFechamento);
        menuArquivo.add(separador2);

        subMenuLogoff.setText("Logoff");
        menuArquivo.add(subMenuLogoff);

        subMenuSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        subMenuSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sicor/images/site_icon-16x16.png"))); // NOI18N
        subMenuSair.setText("Sair");
        subMenuSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuSairActionPerformed(evt);
            }
        });
        menuArquivo.add(subMenuSair);

        menu.add(menuArquivo);

        menuOpcoes.setText("Opções");

        subMenuAtivacao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
        subMenuAtivacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sicor/images/icon16x16Cadeado.png"))); // NOI18N
        subMenuAtivacao.setText("Ativação");
        subMenuAtivacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuAtivacaoActionPerformed(evt);
            }
        });
        menuOpcoes.add(subMenuAtivacao);

        subMenuDatabase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sicor/images/icon16x16BancoDados.png"))); // NOI18N
        subMenuDatabase.setText("Banco de dados");
        subMenuDatabase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuDatabaseActionPerformed(evt);
            }
        });
        menuOpcoes.add(subMenuDatabase);

        menu.add(menuOpcoes);

        menuRelatorios.setText("Relatórios");

        subMenuRelUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sicor/images/icon16x16RelatorioGeneros.png"))); // NOI18N
        subMenuRelUsuarios.setText("Usuários");
        subMenuRelUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuRelUsuariosActionPerformed(evt);
            }
        });
        menuRelatorios.add(subMenuRelUsuarios);

        subMenuRelMovimento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sicor/images/icon16x16RelatorioGrupo.png"))); // NOI18N
        subMenuRelMovimento.setText("Movimento");
        subMenuRelMovimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuRelMovimentoActionPerformed(evt);
            }
        });
        menuRelatorios.add(subMenuRelMovimento);

        menu.add(menuRelatorios);

        menuSobre.setText("Sobre");

        subMenuSicor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sicor/images/icon16x16Sicor.png"))); // NOI18N
        subMenuSicor.setText("SICOR");
        subMenuSicor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuSicorActionPerformed(evt);
            }
        });
        menuSobre.add(subMenuSicor);

        subMenuProjeto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sicor/images/icon16x16MapTI.png"))); // NOI18N
        subMenuProjeto.setText("Projeto | Autor");
        subMenuProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuProjetoActionPerformed(evt);
            }
        });
        menuSobre.add(subMenuProjeto);

        subMenuAjuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sicor/images/icon16x16Help2.png"))); // NOI18N
        subMenuAjuda.setText("Ajuda");
        subMenuAjuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMenuAjudaActionPerformed(evt);
            }
        });
        menuSobre.add(subMenuAjuda);

        menu.add(menuSobre);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelMovimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelFechamento))
                .addGap(18, 18, 18)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(panelBaixo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelMovimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelFechamento)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(panelBaixo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void subMenuSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuSairActionPerformed
        this.sair();
        // TODO add your handling code here:
    }//GEN-LAST:event_subMenuSairActionPerformed

    private void subMenuUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuUsuariosActionPerformed

        DialogUsuarios user = new DialogUsuarios(this, true);
        
        user.setTitle("Usuários");
        user.setLocationRelativeTo(user);
        user.setVisible(true);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_subMenuUsuariosActionPerformed

    public void telaMovimento(){
        DialogMovimento mov = new DialogMovimento(this, true);
        
        mov.setTitle("Movimento");
        mov.setLocationRelativeTo(mov);
        mov.setVisible(true);
        
    }
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.sair();
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void btMovimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMovimentoActionPerformed
        this.telaMovimento();
        
    }//GEN-LAST:event_btMovimentoActionPerformed

    private void subMenuMovimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuMovimentoActionPerformed
        this.telaMovimento();
        // TODO add your handling code here:
    }//GEN-LAST:event_subMenuMovimentoActionPerformed

    private void subMenuAtivacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuAtivacaoActionPerformed
        DialogAtivacao ativa = new DialogAtivacao(this, true);
        
        ativa.setTitle("Ativação do sistema");
        ativa.setLocationRelativeTo(ativa);
        ativa.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_subMenuAtivacaoActionPerformed

    private void subMenuDatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuDatabaseActionPerformed

        Database database = new Database(this, true);
        
        database.setTitle("Banco de Dados");
        database.setLocationRelativeTo(database);
        database.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_subMenuDatabaseActionPerformed

    private void subMenuSicorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuSicorActionPerformed

        SobreSicor sobreSicor = new SobreSicor(this, true);
        
        sobreSicor.setTitle("Sobre SICOR");
        sobreSicor.setLocationRelativeTo(sobreSicor);
        sobreSicor.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_subMenuSicorActionPerformed

    private void subMenuProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuProjetoActionPerformed

        SobreProjeto projeto = new SobreProjeto(this, true);
        
        projeto.setTitle("Sobre o Projeto | Autor");
        projeto.setLocationRelativeTo(projeto);
        projeto.setVisible(true);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_subMenuProjetoActionPerformed

    public void incluirMovimento(){
        Movimento movimento = new Movimento();
        Refeicoes refeicoes = new Refeicoes();
        Usuarios usuario = new Usuarios();
        int hora = cal.get(Calendar.HOUR_OF_DAY);
        int minuto = cal.get(Calendar.MINUTE);

        try{
        
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        String data = textData.getText();
        Date dataFormat = sdf.parse(data);

        DateFormat formata = new SimpleDateFormat("yyyy-MM-dd");
        
        String novaData = formata.format(dataFormat);

//        System.out.println(novaData);
        
        int codUsuario = usuario.consultaIdUsuario(textUsuario.getText());
//        int idRefeicao = 1;
        int idRefeicao = refeicoes.consultaIdRefeicao(labelNomeUsuario.getText());
//        int idRefeicao = refeicoes.consultaIdRefeicao(comboRefeicao.getSelectedItem().toString());

    if(codUsuario == 0){
        textMensagens.append("\tIdentificador inválido\n");
    }else if(idRefeicao == 0){
        textMensagens.append("\tRefeição inválida\n");
    }else{
        movimento.setDataregistro(novaData);
        movimento.setCodusuario(codUsuario);
        movimento.setRefeicoes(idRefeicao);
        String mensagem = movimento.incluiMovimento();
        textMensagens.append("\t" + hora + ":" + minuto + "h\n");
        textMensagens.append("\t" + mensagem + "\n");
        textMensagens.append("*************************************************************\n");        
        //" + textUsuario.getText() + ". 
    }

        textUsuario.setText("");
        textUsuario.requestFocus();
        
        }catch(Exception ex){
            System.out.println("Erro com exception. Descrição: " + ex.getMessage());
        }
        
    }
    
    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed

        this.incluirMovimento();
        // TODO add your handling code here:
    }//GEN-LAST:event_btSalvarActionPerformed

    private void textUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textUsuarioKeyPressed

        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            verificaUsuario();
            
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_textUsuarioKeyPressed

    private void textUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textUsuarioKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            verificaUsuario();
            
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_textUsuarioKeyReleased

    private void subMenuDelegacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuDelegacaoActionPerformed
        DialogDelegacao dd = new DialogDelegacao(this, true);
        dd.setTitle("Delegação");
        dd.setLocationRelativeTo(dd);
        dd.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_subMenuDelegacaoActionPerformed

    private void subMenuInstitutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuInstitutoActionPerformed
        DialogInstituto di = new DialogInstituto(this, true);
        di.setTitle("Instituto");
        di.setLocationRelativeTo(di);
        di.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_subMenuInstitutoActionPerformed

    private void subMenuResponsavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuResponsavelActionPerformed

        DialogResponsavel dr = new DialogResponsavel(this, true);
        dr.setTitle("Responsável");
        dr.setLocationRelativeTo(dr);
        dr.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_subMenuResponsavelActionPerformed

    private void subMenuRelUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuRelUsuariosActionPerformed

        RelatorioUsuarios du = new RelatorioUsuarios();
        
        du.setTitle("Relatório de Usuários");
        du.setLocationRelativeTo(du);
        du.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_subMenuRelUsuariosActionPerformed

    private void subMenuRelMovimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuRelMovimentoActionPerformed
        RelatorioMovimento rm = new RelatorioMovimento();
        rm.setTitle("Relatório de Movimento");
        rm.setLocationRelativeTo(rm);
        rm.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_subMenuRelMovimentoActionPerformed

    private void subMenuAjudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuAjudaActionPerformed
        SicorAjuda sa = new SicorAjuda();
        
        sa.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        sa.setTitle("Ajuda do SICOR");
        sa.setLocationRelativeTo(sa);
        sa.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_subMenuAjudaActionPerformed

    private void subMenuFechamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMenuFechamentoActionPerformed
//        Fechamento f = new Fechamento();

        DialogFechamento f = new DialogFechamento(this, true);
//        f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        f.setTitle("Fechamento do movimento");
        f.setLocationRelativeTo(f);
        f.setContent(textMensagens.getText());
        f.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_subMenuFechamentoActionPerformed

    public void preencheNomeUsuario(String nomeUsuario){
//        labelNomeUsuario.setVisible(true);
//        labelNomeUsuario.setText(nomeUsuario);
        if(nomeUsuario.equals("")){
            textMensagens.append("\tIdentificador não localizado\n");
            textMensagens.append("\t***************************************************\n");
        }else{
            textMensagens.append("\t" + textUsuario.getText() + " - " + nomeUsuario + "\n");
        }
    }
    
    public void preencheMensagemSucesso(String mensagem){
        textMensagens.append("\t" + mensagem + "\n");
        textMensagens.append("\t***************************************************\n");
    }
    
    public void verificaUsuario(){
        Usuarios user = new Usuarios();
        String usuario = user.retornoUsuario(textUsuario.getText());
        if(!usuario.isEmpty()){
//            btSalvar.setEnabled(false);
//        }else{
            this.preencheNomeUsuario(usuario);
            this.incluirMovimento();
//            btSalvar.setEnabled(true);
            
        }else{
            textUsuario.setText("");
            textUsuario.requestFocus();
        }
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btMovimento;
    private javax.swing.JButton btSalvar;
    private javax.swing.JButton btUsuarios;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JLabel labelData;
    private javax.swing.JLabel labelFechamento;
    private javax.swing.JLabel labelNomeUsuario;
    private javax.swing.JLabel labelRefeicao;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenu menuArquivo;
    private javax.swing.JMenu menuOpcoes;
    private javax.swing.JMenu menuRelatorios;
    private javax.swing.JMenu menuSobre;
    private javax.swing.JPanel panelBaixo;
    private javax.swing.JPanel panelMovimento;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JPopupMenu.Separator separador1;
    private javax.swing.JPopupMenu.Separator separador2;
    private javax.swing.JMenuItem subMenuAjuda;
    private javax.swing.JMenuItem subMenuAtivacao;
    private javax.swing.JMenuItem subMenuDatabase;
    private javax.swing.JMenuItem subMenuDelegacao;
    private javax.swing.JMenuItem subMenuFechamento;
    private javax.swing.JMenuItem subMenuInstituto;
    private javax.swing.JMenuItem subMenuLogin;
    private javax.swing.JMenuItem subMenuLogoff;
    private javax.swing.JMenuItem subMenuMovimento;
    private javax.swing.JMenuItem subMenuProjeto;
    private javax.swing.JMenuItem subMenuRelMovimento;
    private javax.swing.JMenuItem subMenuRelUsuarios;
    private javax.swing.JMenuItem subMenuResponsavel;
    private javax.swing.JMenuItem subMenuSair;
    private javax.swing.JMenuItem subMenuSicor;
    private javax.swing.JMenuItem subMenuUsuarios;
    private javax.swing.JTextField textData;
    private javax.swing.JTextArea textMensagens;
    private javax.swing.JTextField textUsuario;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables
}
