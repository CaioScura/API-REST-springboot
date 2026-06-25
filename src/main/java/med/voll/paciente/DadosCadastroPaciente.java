package med.voll.paciente;

import med.voll.endereco.DadosEndereco;

public record DadosCadastroPaciente(String nome, String email, String telefone, String cpf,  DadosEndereco endereco) {

}
