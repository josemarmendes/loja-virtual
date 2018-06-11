package br.caelum.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.jdbc.modelo.Categoria;
import br.com.caelum.jdbc.modelo.Produto;

public class CategoriasDAO {
	
	private final Connection con;
	
	public CategoriasDAO(Connection con) {
		this.con = con;
	}
	
	public List<Categoria> lista() throws SQLException {
		List<Categoria> categorias = new ArrayList<>();
		String sql = "select * from Categoria";
		
		try(PreparedStatement statement = con.prepareStatement(sql)){
			statement.execute();
			try(ResultSet resultSet = statement.getResultSet()){
				while(resultSet.next()) {
					
					int id = resultSet.getInt("id");
					String nome = resultSet.getString("nome");
					Categoria categoria = new Categoria(id, nome);
					categorias.add(categoria);
				}
			}
		}
		return categorias;
	}

	public List<Categoria> listaComProdutos() throws SQLException {
		List<Categoria> categorias = new ArrayList<>();
		String sql = "select c.id as c_id, c.nome as c_nome, p.id as p_id, p.nome as p_nome, p.descricao as p_descricao "
				+ "from Produto as p join Categoria as c on p.categoria_id = c.id order by c.id";
		Categoria ultima = null;
		
		try(PreparedStatement statement = con.prepareStatement(sql)){
			statement.execute();
			try(ResultSet resultSet = statement.getResultSet()){
				while(resultSet.next()) {
					
					int id = resultSet.getInt("c_id");
					String nome = resultSet.getString("C_nome");
					
					if(ultima == null || !ultima.getNome().equals(nome)) {
						Categoria categoria = new Categoria(id, nome);
						categorias.add(categoria);
						ultima = categoria;
					}
					String nomeDoProduto = resultSet.getString("p_nome");
					String descricaoDoProduto = resultSet.getString("p_descricao");
					int idDoProduto = resultSet.getInt("p_id");
					Produto produto = new Produto(nomeDoProduto, descricaoDoProduto);
					produto.setId(idDoProduto);
					ultima.adicona(produto);
					
				}
			}
		}
		return categorias;
	}
}
