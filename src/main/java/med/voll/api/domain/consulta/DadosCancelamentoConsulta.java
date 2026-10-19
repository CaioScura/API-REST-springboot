package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

/**
 * DadosCancelamentoConsulta
 */
public record DadosCancelamentoConsulta(
    @NotNull
    Long idConsulta,

    @NotNull
    MotivoCancelamento motivo
) {

}
