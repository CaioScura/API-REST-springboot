package med.voll.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.ValidacaoException;
import med.voll.api.domain.paciente.PacienteRepository;


@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta{
    
    @Autowired
    private PacienteRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        //escolha do medico opcional
        if(dados.idPaciente() == null){
            return;
        }

        var pacienteAtivo = repository.findAtivoById(dados.idPaciente());
        if(!pacienteAtivo){
            throw new ValidacaoException("Consulta não pode ser agendada por esse paciente");
        }
    }

}
