/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestao.venda.controle;

import com.mycompany.gestao.venda.modelo.dao.AutenticacaoDao;
import com.mycompany.gestao.venda.modelo.dominio.Usuario;
import com.mycompany.gestao.venda.view.formulario.Login;
import com.mycompany.gestao.venda.view.modelo.LoginDto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel Rodrigues
 */
public class LoginControle implements ActionListener{

    private final Login login;
    private AutenticacaoDao autenticacaoDao;

    public LoginControle(Login login) {
        this.login = login;
        this.autenticacaoDao = new AutenticacaoDao();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String acao = e.getActionCommand().toLowerCase();
        
        switch(acao){
            case "login": login(); break;
            case "cancelar": cancelar(); break;
        }
    }

    private void login() {
        String usuario = this.login.getTxtLoginUsuario().getText();
        String senha = this.login.getTxtLoginSenha().getText();
        
        if(usuario.equals("") || senha.equals("")){
            this.login.getLabelLoginMensagem().setText("Usuário e senha devem ser preenchidos.");
            return;
        }
        
        LoginDto loginDto = new LoginDto(usuario, senha);
        Usuario usuarioTemp = this.autenticacaoDao.login(loginDto);
        
        if(usuarioTemp != null){
//            JOptionPane.showConfirmDialog(null,usuarioTemp.getNome());
//            teste = new Teste();
//            teste.setVisible(true);
            limparCampos();
        }else{
            this.login.getLabelLoginMensagem().setText("Usuário ou senha incorreto.");
        }
    }

    private void cancelar() {
        int confirmar = JOptionPane.showConfirmDialog(login,"Tem certeza que deseja sair?", "Sair do sistema", JOptionPane.YES_NO_OPTION);
        if(confirmar == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }
    
    private void limparCampos(){
        this.login.getTxtLoginUsuario().setText("");
        this.login.getTxtLoginSenha().setText("");
        this.login.getLabelLoginMensagem().setText("");
    }
    
    
}
