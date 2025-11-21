package dao;

import model.Usuario;
import util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UsuarioDAO {
    public void cadastrar(Usuario u) {
        String sql = "INSERT INTO usuario (nome, email, senha, dieta, bio) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getSenha());
            stmt.setString(4, u.getDieta());
            stmt.setString(5, u.getBio());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Usuario login(String email, String senha) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        Usuario u = null;

        try (Connection con = Conexao.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            var rs = stmt.executeQuery();

            if (rs.next()) {
                u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setDieta(rs.getString("dieta"));
                u.setBio(rs.getString("bio"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return u; // retorna null se login falhar
    }



}
