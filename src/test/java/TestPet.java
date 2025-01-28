// 0 - nome do pacote

// 1 - Bibilioteca

import io.restassured.response.Response; // Classe Resposta do Rest-Assured

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given; // Função given
import static org.hamcrest.CoreMatchers.*;


    // 2 - Classe
public class TestPet {
    // 2.1 atributos
    static String ct = "application/json";
    static String uriPet = "https://petstore.swagger.io/v2/pet";

    // 2.2 funções e métodos
    // 2.2.1 funções e métodos comuns / úteis

    // Função de leitura de um arquivo json
    public static String lerArquivoJson(String arquivoJson) throws IOException{
        
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }


    @Test
    public void testPostPet() throws IOException {
        // carregar os dados do arquivo json do pet
        String jsonBody = lerArquivoJson("src/test/resources/Json/pet1.json");
        int petId = 92981; // Codigo do pet

        // começa o teste via rest-assured
        // 1 - cria a instância do Rest-assured

        given()
            .contentType(ct)
            .log().all()
            .body(jsonBody)
        // Execute o Post
        .when()
            .post(uriPet)
            
        // Verifique a resposta
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is("Cristal"))
            .body("id", is(petId))
            .body("category.name", is("cachorro"))
            .body("tags[0].name", is("vacinado"));

    }


    }
