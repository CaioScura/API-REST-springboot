package med.voll.api.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.DadosEndereco;

/**
 * DadosAtualizacaoMedico
 */
public record DadosAtualizacaoMedico(
    @NotNull
    Long id, 
    
    String nome, 
    
    String telefone, 
    
    DadosEndereco endereco) {

}
