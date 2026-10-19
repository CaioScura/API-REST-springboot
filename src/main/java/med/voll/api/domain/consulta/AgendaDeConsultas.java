package med.voll.api.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;

@Service 
public class AgendaDeConsultas {

    @Autowired 
    private ConsultaRepository consultaRepository;

    //carregar do banco o medico
    @Autowired 
    private MedicoRepository medicoRepository;

    //carregar do banco o paciente
    @Autowired 
    private PacienteRepository pacienteRepository;

    public void agendar(DadosAgendamentoConsulta dados){
        //verifica se o paciente existe
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe");
            
        }

        //verifica se o medico existe(medico é opcional) 
        //entao tem uma validacao para saber se esta passando um valor e se esta verifica se existe)
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe");
            
        }

        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        var medico = medicoRepository.findById(dados.idMedico()).get();

        var consulta = new Consulta(null, medico, paciente, dados.data());
        consultaRepository.save(consulta);
    }

}
