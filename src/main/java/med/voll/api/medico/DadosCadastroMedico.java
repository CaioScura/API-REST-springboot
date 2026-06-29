package med.voll.api.medico;

import med.voll.api.endereco.DadosEndereco;

public record DadosCadastroMedico(String nome, String email, String crm, String telefone, Especialidade especialidade, DadosEndereco endereco) {

}
