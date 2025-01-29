// 0 - nome do pacote

// 1 - Bibilioteca

import io.restassured.response.Response; // Classe Resposta do Rest-Assured

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given; // Função given
import static org.hamcrest.CoreMatchers.*;


    // 2 - Classe
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Definação de ordem dos testes
public class TestPet {
    // 2.1 atributos
    static String ct = "application/json";
    static String uriPet = "https://petstore.swagger.io/v2/pet";
    static int petId = 92981; // Codigo do pet
    String petName = "Cristal";
    String categoryName = "cachorro";
    String tagName = "vacinado";
    String[] Status = {"available","sold"};

    // 2.2 funções e métodos
    // 2.2.1 funções e métodos comuns / úteis

    // Função de leitura de um arquivo json
    public static String lerArquivoJson(String arquivoJson) throws IOException{
        
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }


    @Test @Order(1)
    public void testPostPet() throws IOException {
        // carregar os dados do arquivo json do pet
        String jsonBody = lerArquivoJson("src/test/resources/Json/pet1.json");

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
            .body("name", is(petName))
            .body("id", is(petId))
            .body("category.name", is(categoryName))
            .body("tags[0].name", is(tagName));

    }


    
    @Test @Order(2)
    public void testGetPet(){
        // Configura
        // Entrada e saidas definidas no nivel da classe

        given()
            .contentType(ct)
            .log().all()
            // quando é get ou delete não tem body
        
            // Execute
        .when()
            .get(uriPet + "/" + petId)

            //valida
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is(petName))
            .body("id", is(petId))
            .body("category.name", is(categoryName))
            .body("tags[0].name", is(tagName)); 


        // Fim do given
    }

    @Test @Order(3)
    public void testPutPet() throws IOException{
        // Configura
        String jsonBody = lerArquivoJson("src/test/resources/Json/pet2.json");
        given()
            .contentType(ct)
            .log().all()
            .body(jsonBody)

        //Executa
        .when()
            .put(uriPet)

        //Valida
        .then()
        .log().all()
        .statusCode(200)
        .body("name", is(petName))    // verifica se o nome é Snoopy
        .body("id", is(petId))         // verifique o código do pet
        .body("category.name", is(categoryName)) // se é cachorro
        .body("tags[0].name", is(tagName))  // se está vacinado
        .body("status", is(Status[1]))
        ;
    }


    @Test @Order(4)
    public void testDeletePet(){

        given()
        .contentType(ct)
        .log().all()

        .when()
        .delete(uriPet + "/" + petId)

        .then()
        .log().all()
        .statusCode(200)
        .body("code", is(200))
        .body("type", is("unknown"))
        .body("message", is(String.valueOf(petId)))

        ;

    }

    // Data Driven Testing (DDT) / Teste Direcionado por Dados / Teste com Massa
    // Teste com Json parametrizado


