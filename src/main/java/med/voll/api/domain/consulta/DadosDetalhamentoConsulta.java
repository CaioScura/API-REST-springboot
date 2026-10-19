package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

/**
 * DadosDetalhamentoConsulta
 */
public record DadosDetalhamentoConsulta(Long id, Long idMedico, Long idPaciente, LocalDateTime data) {

    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }

}
