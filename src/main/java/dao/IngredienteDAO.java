package dao;

import model.Ingrediente;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IngredienteDAO {

    public void adicionar(Ingrediente i) {
        String sql = "INSERT INTO ingrediente (nome, categoria, quantidade, unidade, validade, id_usuario) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, i.getNome());
            stmt.setString(2, i.getCategoria());
            stmt.setDouble(3, i.getQuantidade());
            stmt.setString(4, i.getUnidade());
            stmt.setString(5, i.getValidade());
            stmt.setInt(6, i.getIdUsuario());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Ingrediente> listar(int idUsuario) {
        List<Ingrediente> lista = new ArrayList<>();
        String sql = "SELECT * FROM ingrediente WHERE id_usuario = ?";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ingrediente ing = new Ingrediente();
                ing.setId(rs.getInt("id"));
                ing.setNome(rs.getString("nome"));
                ing.setCategoria(rs.getString("categoria"));
                ing.setQuantidade(rs.getDouble("quantidade"));
                ing.setUnidade(rs.getString("unidade"));
                ing.setValidade(rs.getString("validade"));
                ing.setIdUsuario(rs.getInt("id_usuario"));

                lista.add(ing);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void atualizar(Ingrediente i) {
        String sql = "UPDATE ingrediente SET nome=?, categoria=?, quantidade=?, unidade=?, validade=? WHERE id=?";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, i.getNome());
            stmt.setString(2, i.getCategoria());
            stmt.setDouble(3, i.getQuantidade());
            stmt.setString(4, i.getUnidade());
            stmt.setString(5, i.getValidade());
            stmt.setInt(6, i.getId());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM ingrediente WHERE id = ?";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
