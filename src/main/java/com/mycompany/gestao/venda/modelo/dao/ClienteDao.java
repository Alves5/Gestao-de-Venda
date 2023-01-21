/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestao.venda.modelo.dao;

import com.mycompany.gestao.venda.modelo.conexao.Conexao;
import com.mycompany.gestao.venda.modelo.conexao.ConexaoMysql;
import com.mycompany.gestao.venda.modelo.dominio.Cliente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Rodrigues
 */
public class ClienteDao {
    private final Conexao conexao;

    public ClienteDao() {
        this.conexao = new ConexaoMysql();
    }
    
    public List<Cliente> buscarTodosOsClientes(){
        String sql = "SELECT * FROM cliente";
        List<Cliente> clientes = new ArrayList<>();
        try {
            ResultSet result = conexao.obterConexao().prepareStatement(sql).executeQuery();
            while(result.next()){
                clientes.add(getCliente(result));
            }
        } catch (Exception e) {
            System.out.println(String.format("Error", e.getMessage()));
        }
        return clientes;
    }
    
    private Cliente getCliente(ResultSet result) throws SQLException{
        Cliente cliente = new Cliente();
        cliente.setId(result.getLong("id"));
        cliente.setNome(result.getString("nome"));
        cliente.setTelefone(result.getString("telefone"));
        cliente.setMorada(result.getString("morada"));
        return cliente;
    }
}
