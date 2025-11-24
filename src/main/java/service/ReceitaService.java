package service;

import model.Ingrediente;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReceitaService {

    // Lista fixa de receitas (poderia vir de banco)
    Map<String, List<String>> receitas = Map.of(
            "Omelete", List.of("ovo", "queijo", "sal"),
            "Macarrão ao alho", List.of("macarrão", "alho", "óleo"),
            "Arroz temperado", List.of("arroz", "cenoura", "alho")
    );

    public List<String> recomendar(List<Ingrediente> ingredientesUsuario) {
        List<String> recomendadas = new ArrayList<>();

        for (var receita : receitas.entrySet()) {
            String nomeReceita = receita.getKey();
            List<String> ingredientesDaReceita = receita.getValue();

            for (Ingrediente ing : ingredientesUsuario) {
                if (ingredientesDaReceita.contains(ing.getNome().toLowerCase())) {
                    recomendadas.add(nomeReceita);
                    break;
                }
            }
        }
        return recomendadas;
    }
}
