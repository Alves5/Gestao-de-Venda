/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestao.venda.modelo.dao;

import com.mycompany.gestao.venda.modelo.conexao.Conexao;
import com.mycompany.gestao.venda.modelo.conexao.ConexaoMysql;
import com.mycompany.gestao.venda.modelo.dominio.Perfil;
import com.mycompany.gestao.venda.modelo.dominio.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Daniel Rodrigues
 */
public class UsuarioDao {

    private final Conexao conexao;
    
    public UsuarioDao() {
        this.conexao = new ConexaoMysql();
    }
    
    public String salvar(Usuario usuario){
        return usuario.getId() == 0L ? adicionar(usuario) : editar(usuario);
    }

    private String adicionar(Usuario usuario) {
       String sql = "INSERT INTO usuarios(nome, usuario, senha, perfil, estado) VALUES(?,?,?,?,?)";
       
       Usuario usuarioTemp = buscarUsuarioPeloUsuario(usuario.getUsuario());
       if(usuarioTemp != null){
           return String.format("Error: usuario %s já existe no banco de dados", usuario.getUsuario());
       }
       
        try {
            PreparedStatement prepareStatement = conexao.obterConexao().prepareStatement(sql);
            preencherValoresNoPrepareStatement(prepareStatement, usuario);
            int resultado = prepareStatement.executeUpdate();
            return resultado == 1 ? "Usuário adicionado com sucesso" : "Não foi possivel adicionar usuário";
        } catch (Exception e) {
            return String.format("Error: &s", e.getMessage());
        }
    }

    private String editar(Usuario usuario) {
       String sql = "UPDATE usuarios SET nome = ?, usuario = ?, senha = ?, perfil = ?, estado = ? WHERE id = ?";
        try {
            PreparedStatement prepareStatement = conexao.obterConexao().prepareStatement(sql);
            preencherValoresNoPrepareStatement(prepareStatement, usuario);
            int resultado = prepareStatement.executeUpdate();
            return resultado == 1 ? "Usuário atualizado com sucesso" : "Não foi possivel atualizar usuário";
        } catch (Exception e) {
            return String.format("Error: &s", e.getMessage());
        }
    }

    private void preencherValoresNoPrepareStatement(PreparedStatement prepareStatement, Usuario usuario) throws SQLException {
        
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      
      String senhaCrypt = passwordEncoder.encode(usuario.getSenha());
        
      prepareStatement.setString(1, usuario.getNome());
      prepareStatement.setString(2, usuario.getUsuario());
      prepareStatement.setString(3, senhaCrypt);
      prepareStatement.setString(4, usuario.getPerfil().name());
      prepareStatement.setBoolean(5, usuario.isEstado());
      
      if(usuario.getId() != 0L){
          prepareStatement.setLong(6, usuario.getId());
      }
    }
    
    public List<Usuario> buscarTodosUsuarios(){
        String sql = "SELECT * FROM usuarios";
        List<Usuario> usuarios = new ArrayList<>();
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            while(result.next()){
                usuarios.add(getUsuario(result));
            }
        } catch (Exception e) {
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        return usuarios;
    }
    
    private Usuario getUsuario(ResultSet result) throws SQLException{
        Usuario usuario = new Usuario();
        usuario.setId(result.getLong("id"));
        usuario.setNome(result.getString("nome"));
        usuario.setUsuario(result.getString("usuario"));
        usuario.setSenha(result.getString("senha"));
        usuario.setPerfil(Perfil.valueOf(result.getString("perfil")));
        usuario.setEstado(result.getBoolean("estado"));
        usuario.setDataHoraCriacao(result.getObject("data_hora_criacao", LocalDateTime.class));
        usuario.setUltimoLogin(result.getObject("ultimo_login", LocalDateTime.class));
        
        return usuario;
    }
    
    public Usuario buscarUsuarioPeloId(Long id){
        String sql = String.format("SELECT * FROM usuarios WHERE id = %d",id);
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            if(result.next()){
                return getUsuario(result);
            }
        } catch (Exception e) {
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        return null;
    }
    
    public Usuario buscarUsuarioPeloUsuario(String usuario){
        String sql = String.format("SELECT * FROM usuarios WHERE usuario = '%s'",usuario);
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            
            if(result.next()){
                return getUsuario(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("Error: ", e.getMessage()));
        }
        return null;
    }
}
