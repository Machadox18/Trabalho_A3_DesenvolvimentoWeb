package com.dw.controller;

import com.google.gson.Gson;
import dao.UsuarioDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/usuario/*")
public class UsuarioServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Gson gson = new Gson();


    private void liberarCors(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    // --- Método para ler JSON do corpo da requisição ---
    private String lerJson(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String linha;
        while ((linha = reader.readLine()) != null) {
            sb.append(linha);
        }
        return sb.toString();
    }

    // -------------------------
    // CADASTRAR (POST /usuario/cadastro)
    // -------------------------
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        liberarCors(resp);

        String path = req.getPathInfo(); // /cadastro ou /login
        resp.setContentType("application/json");

        // --- Cadastro ---
        if ("/cadastro".equals(path)) {
            String json = lerJson(req);
            Usuario u = gson.fromJson(json, Usuario.class);

            usuarioDAO.cadastrar(u);

            resp.getWriter().write("{\"status\":\"ok\",\"mensagem\":\"Usuário cadastrado\"}");
            return;
        }

        // --- Login ---
        if ("/login".equals(path)) {
            String json = lerJson(req);
            Usuario dados = gson.fromJson(json, Usuario.class);

            Usuario u = usuarioDAO.login(dados.getEmail(), dados.getSenha());

            if (u != null) {
                resp.getWriter().write(gson.toJson(u));
            } else {
                resp.setStatus(401);
                resp.getWriter().write("{\"erro\":\"Credenciais inválidas\"}");
            }
        }
    }

    // -------------------------
    // ATUALIZAR PERFIL (PUT /usuario/atualizar)
    // -------------------------
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        liberarCors(resp);
        String path = req.getPathInfo();

        if ("/atualizar".equals(path)) {
            String json = lerJson(req);
            Usuario u = gson.fromJson(json, Usuario.class);

            usuarioDAO.atualizarPerfil(u);

            resp.setContentType("application/json");
            resp.getWriter().write("{\"status\":\"ok\",\"mensagem\":\"Perfil atualizado\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        liberarCors(resp);

        resp.setContentType("application/json");
        String path = req.getPathInfo(); // /teste, /listar, /1 etc.

        // ---------- /usuario/teste ----------
        if (path == null || "/teste".equals(path)) {
            resp.getWriter().write("{\"status\":\"ok\",\"mensagem\":\"GET funcionando\"}");
            return;
        }

        // ---------- /usuario/listar ----------
        if ("/listar".equals(path)) {
            var usuarios = usuarioDAO.listarTodos();
            resp.getWriter().write(gson.toJson(usuarios));
            return;
        }

        // ---------- /usuario/{id} ----------
        if (path.matches("/\\d+")) {
            int id = Integer.parseInt(path.substring(1));
            Usuario u = usuarioDAO.buscarPorId(id);

            if (u != null) {
                resp.getWriter().write(gson.toJson(u));
            } else {
                resp.setStatus(404);
                resp.getWriter().write("{\"erro\":\"Usuário não encontrado\"}");
            }
            return;
        }

        // ---------- Se o caminho não for nenhum dos acima ----------
        resp.setStatus(404);
        resp.getWriter().write("{\"erro\":\"Endpoint GET inválido\"}");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        liberarCors(resp);
    }


}
