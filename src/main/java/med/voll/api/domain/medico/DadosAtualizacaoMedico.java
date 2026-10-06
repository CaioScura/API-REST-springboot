package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;

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
