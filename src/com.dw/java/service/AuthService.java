package service;

import dao.UsuarioDAO;
import model.Usuario;

public class AuthService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean cadastrar(Usuario usuario) {
        // poderia validar email repetido aqui
        usuarioDAO.cadastrar(usuario);
        return true;
    }

    public Usuario login(String email, String senha) {
        return usuarioDAO.login(email, senha);
    }

    public boolean atualizarPerfil(Usuario usuario) {
        usuarioDAO.atualizarPerfil(usuario);
        return true;
    }

}
