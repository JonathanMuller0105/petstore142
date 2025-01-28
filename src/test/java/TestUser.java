import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class UserTests {

    static {
        // Configuração base da API
        private static String ct = "application/json";
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        final int userId = 92981;

        // Função de leitura de um arquivo json
        private static String lerArquivoJson(String arquivoJson) throws IOException{
            return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }


    @Test
    public void testPostUser() {
         // carregar os dados do arquivo json do pet
        String jsonBody = lerArquivoJson("src/test/resources/Json/user1.json");


    given()

        .header("Content-Type", "application/json")
        .body(userPayload)

    .when()

        .post("/user")


    .then()
            .statusCode(200)
            .body("message", equalTo("123")); // ID deve ser retornado na mensagem
    }

    @Test
    public void testGetUser() {
        String username = "testuser";

        given()
        .when()
            .get("/user/" + username)
        .then()
            .statusCode(200)
            .body("username", equalTo(username));
    }

    @Test
    public void testPutUser()

        given()
            .header("Content-Type", "application/json")
            .body(updatedPayload)

        .when()
            .put("/user/testuser")

        .then()
            .statusCode(200)
            .body("message", equalTo("123"));
    }

    @Test
    public void testDeleteUser() {
        String username = "testuser";

        given()
        .when()
            .delete("/user/" + username)
        .then()
            .statusCode(200)
            .body("message", equalTo(username));
    }





}

