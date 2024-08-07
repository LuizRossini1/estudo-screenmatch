package br.com.alura.screenmatch.principal;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner leitura = new Scanner(System.in);
        System.out.println("Digite um filme para busca: ");
        var busca = leitura.nextLine();

        String endereco = "https://www.omdbapi.com/?t=" + busca + "&apikey=82f2795b";

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            System.out.println(json);

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .create();
            //Titulo meuTitulo = gson.fromJson(json, Titulo.class);
            TituloOmdb meuTituloOmbd = gson.fromJson(json, TituloOmdb.class);
            System.out.println(meuTituloOmbd);
            //try {
            Titulo meuTitulo = new Titulo(meuTituloOmbd);
            System.out.println("Titulo já convertido");
            System.out.println(meuTitulo);
        } catch (NumberFormatException exception) {
            System.out.println("Aconteceu um erro: ");
            System.out.println(exception.getMessage());
        } catch (IllegalAccessError e) {
            System.out.println("Algum erro de argumento na busca, verifique o endereço");
        }

        System.out.println("O programa finalizou corretamente!");
    }
}