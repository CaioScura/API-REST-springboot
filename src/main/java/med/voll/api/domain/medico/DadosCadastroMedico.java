package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
    //validacao de campos obrigatorios, caso nao seja informado o spring vai retornar um erro 400
    @NotBlank(message = "Nome é obrigatorio")//mensagem de erro personalizada
    //not blank para strings
    String nome, 

    @NotBlank(message = "Email é obrigatorio")
    @Email(message = "Formato de email inválido")
    String email, 

    @NotBlank(message = "Telefone é obrigatorio")
    String telefone,

    @NotBlank(message = "CRM é obrigatorio")
    @Pattern(regexp = "\\d{4,6}", message = "Formato de CRM inválido")//quantos digitos o crm deve ter, no caso entre 4 e 6
    String crm, 

    @NotNull
    Especialidade especialidade, 

    @NotNull @Valid//para mandar a validacao para a classe DadosEndereco de dentro dele -> variavelendereco
    DadosEndereco endereco) {

}
