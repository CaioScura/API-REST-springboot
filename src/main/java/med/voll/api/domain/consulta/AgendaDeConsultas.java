package med.voll.api.domain.consulta;

import med.voll.api.ApiApplication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;

@Service 
public class AgendaDeConsultas {

    private final ApiApplication apiApplication;

    @Autowired 
    private ConsultaRepository consultaRepository;

    //carregar do banco o medico
    @Autowired 
    private MedicoRepository medicoRepository;

    //carregar do banco o paciente
    @Autowired 
    private PacienteRepository pacienteRepository;


    ///// VALIDADORES /////
    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;



    AgendaDeConsultas(ApiApplication apiApplication) {
        this.apiApplication = apiApplication;
    }

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){
        //verifica se o paciente existe
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe");
            
        }

        //verifica se o medico existe(medico é opcional) 
        //entao tem uma validacao para saber se esta passando um valor e se esta verifica se existe)
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe");   
        }


        //percorre no validadores
        validadores.forEach(v -> v.validar(dados));


        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);


        //verifica se tem medico disponivel
        if (medico == null) {
            throw new ValidacaoException("Não existe médico disponível nessa data");
        }


        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    
    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if(dados.especialidade() == null){
            throw new ValidacaoException("Especialidade obrigatória quando médico nao for escolhido");
        }

        //vai trazer um medico aleatorio de acordo com a especialidade do medico e da data disponivel
        //cada um vem de parametro
        return medicoRepository.escolherMedicoAleatórioLivreNaData(dados.especialidade(), dados.data());
    }


    public void cancelar(DadosCancelamentoConsulta dados){
        if(!consultaRepository.existsById(dados.idConsulta())){
            throw new ValidacaoException("Id da consulta não existe");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }

}
