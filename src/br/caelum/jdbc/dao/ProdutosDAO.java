package br.caelum.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.jdbc.modelo.Categoria;
import br.com.caelum.jdbc.modelo.Produto;

public class ProdutosDAO {
	Connection con;
	
	public ProdutosDAO(Connection con) {
		this.con = con;
	}
	
	public void salva( Produto produto) throws SQLException {

		String sql = "insert into Produto (nome, descricao) values (?, ?)";

		try (PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, produto.getNome());
			statement.setString(2, produto.getDescricao());

			statement.execute();

			try (ResultSet resultSet = statement.getGeneratedKeys()) {
				if (resultSet.next()) {
					int id = resultSet.getInt("id");
					produto.setId(id);
					System.out.println("A mesa foi inserida com sucesso " + produto);
				}

			}
		}

	}

	public List<Produto> lista() throws SQLException {
		List<Produto> produtos = new ArrayList<>();
		String sql = "select * from Produto";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.execute();
           transformaResultadoEmProdutos(stmt, produtos);
        }
		return produtos;
	}
	
	private void transformaResultadoEmProdutos(PreparedStatement stmt, List<Produto> produtos)
            throws SQLException {
        try (ResultSet rs = stmt.getResultSet()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                Produto produto = new Produto(nome, descricao);
                produto.setId(id);
                produtos.add(produto);
            }
        }
    }

	public List<Produto> busca(Categoria categoria) throws SQLException {
		List<Produto> produtos = new ArrayList<>();
		String sql = "select * from Produto where categoria_id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
        	stmt.setInt(1, categoria.getId());
        	stmt.execute();
        	transformaResultadoEmProdutos(stmt, produtos);
        }
		return produtos;
	}
}
