package med.voll.api.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
    //not blank para strings -> obrigatorio
    @NotBlank
    String logradouro, 
    
    @NotBlank
    String bairro, 
    
    @NotBlank
    @Pattern(regexp = "\\d{8}")//quantos digitos o numero deve ter, no caso entre 8 
    String cep, 
    
    @NotBlank
    String cidade, 
    
    @NotBlank
    String uf, 
    
    String complemento, 
    
    String numero) {

}
