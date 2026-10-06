package med.voll.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
    //not blank para strings -> obrigatorio
    @NotBlank(message = "Logradouro é obrigatorio")
    String logradouro, 
    
    @NotBlank(message = "Bairro é obrigatorio")
    String bairro, 
    
    @NotBlank(message = "CEP é obrigatorio")
    @Pattern(regexp = "\\d{8}", message = "Formato de CEP inválido")//quantos digitos o numero deve ter, no caso entre 8 
    String cep, 
    
    @NotBlank(message = "Cidade é obrigatorio")
    String cidade, 
    
    @NotBlank(message = "UF é obrigatorio")
    String uf, 
    
    String complemento, 
    
    String numero) {

}
