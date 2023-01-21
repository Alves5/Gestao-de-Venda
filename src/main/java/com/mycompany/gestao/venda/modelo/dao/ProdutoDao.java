/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestao.venda.modelo.dao;

import com.mycompany.gestao.venda.modelo.conexao.Conexao;
import com.mycompany.gestao.venda.modelo.conexao.ConexaoMysql;
import com.mycompany.gestao.venda.modelo.dominio.Categoria;
import com.mycompany.gestao.venda.modelo.dominio.Produto;
import com.mycompany.gestao.venda.modelo.dominio.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Rodrigues
 */
public class ProdutoDao {
    private final Conexao conexao;

    public ProdutoDao() {
        this.conexao = new ConexaoMysql();
    }
    
    public List<Produto> buscarTodosOsProdutos(){
        String sql = "SELECT * FROM Produto";
        List<Produto> produtos = new ArrayList<>();
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            while(result.next()){
                produtos.add(getProduto(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("Error", e.getMessage()));
        }
        return produtos;
    }
    
    private Produto getProduto(ResultSet result) throws SQLException{
        Produto produto = new Produto();
        produto.setId(result.getLong("id"));
        produto.setNome(result.getString("nome"));
        produto.setDescricao(result.getString("descricao"));
        produto.setPreco(result.getBigDecimal("preco"));
        produto.setQuantidade(result.getInt("quantidade"));
        produto.setCategoria(result.getInt("categoria_id"));
        produto.setUsuario(result.getInt("usuarios_id"));
        produto.setDataHoraCriacao(result.getObject("data_hora_criacao", LocalDateTime.class));
        return produto;
    }
}
