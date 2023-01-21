/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestao.venda;

import com.mycompany.gestao.venda.controle.TelaInicialControle;
import com.mycompany.gestao.venda.modelo.conexao.Conexao;
import com.mycompany.gestao.venda.modelo.conexao.ConexaoMysql;
import com.mycompany.gestao.venda.modelo.dao.ClienteDao;
import com.mycompany.gestao.venda.modelo.dao.ProdutoDao;
import com.mycompany.gestao.venda.modelo.dao.UsuarioDao;
import com.mycompany.gestao.venda.modelo.dominio.Cliente;
import com.mycompany.gestao.venda.modelo.dominio.Perfil;
import com.mycompany.gestao.venda.modelo.dominio.Produto;
import com.mycompany.gestao.venda.modelo.dominio.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Daniel Rodrigues
 */
public class Main {
   public static void main(String[] args) throws SQLException{
       
//       String sql = "select * from categoria";
//
//       Conexao conexao = new ConexaoMysql();
//
//       ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
//
//       while (result.next()) {
//           System.out.println(result.getString("nome"));
//       }

//        Usuario usuario = new Usuario(2L, "Daniel Sam", "1234", "samd", Perfil.PADRÃO, null, null);
//        UsuarioDao usuarioDao = new UsuarioDao();
//        String mensagem = usuarioDao.salvar(usuario);
//        System.out.println(mensagem);
          
//           UsuarioDao usuarioDao = new UsuarioDao();
//           List<Usuario> usuarios = usuarioDao.buscarTodosUsuarios();

//            ClienteDao clienteDao = new ClienteDao();
//            List<Cliente> clientes = clienteDao.buscarTodosOsClientes();
//           int con =0;
//           for (Cliente cliente : clientes) {
//               con++;
//           }
//            System.out.println(con);
            
            
          ProdutoDao produtoDao = new ProdutoDao();
          List<Produto> produtos = produtoDao.buscarTodosOsProdutos();
          int cont = 0;
          for(Produto produto : produtos){
              cont++;
          }
          System.out.println(cont);
   }
}
