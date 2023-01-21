/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestao.venda.modelo.dao;

import com.mycompany.gestao.modelo.exception.NegocioException;
import com.mycompany.gestao.venda.modelo.dominio.Perfil;
import com.mycompany.gestao.venda.modelo.dominio.Usuario;
import com.mycompany.gestao.venda.view.modelo.LoginDto;
import javax.swing.JOptionPane;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Daniel Rodrigues
 */
public class AutenticacaoDao {

    private final UsuarioDao usuarioDao;
    
    public AutenticacaoDao(){
        this.usuarioDao = new UsuarioDao();
    }
    
    public boolean temPermissao(Usuario usuario){
        try {
            permissao(usuario);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(), "Usuário sem permissao", 0);
            return false;
        }
    }
    private void permissao(Usuario usuario){
        if(!Perfil.ADMIN.equals(usuario.getPerfil())){
            throw new NegocioException("Sem permissão para realizar essa ação.");
        }
    }
    
    public Usuario login(LoginDto login){
        Usuario usuario = usuarioDao.buscarUsuarioPeloUsuario(login.getUsuario());
        
        if(usuario == null || !usuario.isEstado())
            return null;
        
        if(usuario.isEstado() && validaSenha(usuario.getSenha(), login.getSenha())){
            return usuario;
        }
        
        return null;
    }

    private boolean validaSenha(String senhaUsuario, String senhaLogin) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(senhaLogin, senhaUsuario);
    }
}
