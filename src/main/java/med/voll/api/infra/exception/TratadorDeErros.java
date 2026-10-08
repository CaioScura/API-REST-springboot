package med.voll.api.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

//indicar que é uma classe que vai tratar os erros da aplicação, e vai interceptar as exceções lançadas pelos controllers
@RestControllerAdvice 
public class TratadorDeErros {


    //serve para interceptar a exceção EntityNotFoundException, que é lançada quando um recurso não é encontrado no banco de dados
    //entrada de valores que nao existem no banco de dados
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        
        return ResponseEntity.notFound().build();
    }


    //serve para interceptar a exceção MethodArgumentNotValidException, que é lançada quando a validação dos dados de entrada falha
    //entrada de valores que nao sao validos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }



    //DTO de erro de validação, que vai ser retornado para o cliente quando a validação dos dados de entrada falhar
    //foi criado um record DTO aqui mesmo, pois so sera
    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
