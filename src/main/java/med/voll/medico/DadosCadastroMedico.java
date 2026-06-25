package med.voll.medico;

import med.voll.endereco.DadosEndereco;

public record DadosCadastroMedico(String nome, String email, String crm, String telefone, Especialidade especialidade, DadosEndereco endereco) {

}
