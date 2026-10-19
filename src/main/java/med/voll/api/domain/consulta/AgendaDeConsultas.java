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
        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        var medico = medicoRepository.findById(dados.idMedico()).get();

        var consulta = new Consulta(null, medico, paciente, dados.data());
        consultaRepository.save(consulta);
    }

}
