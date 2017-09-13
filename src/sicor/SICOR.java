/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sicor;

import br.com.sicor.view.*;
import java.awt.Color;

/**
 *
 * @author Debug
 */
public class SICOR {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Principal tela = new Principal();
        
        tela.setTitle("SICOR");
        tela.setExtendedState(tela.MAXIMIZED_BOTH);
        tela.setLocationRelativeTo(tela);
        tela.getContentPane().setBackground(Color.getHSBColor(255, 204, 204)); //[255,204,204]
        tela.setVisible(true);
        
        // TODO code application logic here
    }
    
}
