

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;

public class TestUser {
    String ct = "application/json";
    String uriUser = "https://petstore.swagger.io/v2/user";

    @Test
    public void testLogin(){
        // Configura
        String username = "Jonathan";
        String password = "123456";

        String resultadoEsperado = "logged in user session:";

        given()
        .contentType(ct)
        .log().all()

        // Executa
        .when()
        .get(uriUser + "/login?username="+ username +"&password="+ password +"")

        // Valida
        .then()
        .log().all()
        .statusCode(200)
        .body("code", is(200))
        .body("type", is("unknown"))
        .body("message" , containsString(resultadoEsperado))
        
;

    }



    
}
