package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroPaciente(
    @NotBlank(message = "Nome é obrigatorio")
    String nome, 
    
    @NotBlank(message = "Email é obrigatorio")
    @Email(message = "Formato de email inválido")
    String email, 
    
    @NotBlank(message = "Telefone é obrigatorio")
    String telefone, 
    
    @NotBlank(message = "CPF é obrigatorio")
    @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}", message = "Formato de CPF inválido")
    String cpf, 
    
    @NotBlank(message = "Endereço é obrigatorio")
    @Valid
    DadosEndereco endereco) {

}
