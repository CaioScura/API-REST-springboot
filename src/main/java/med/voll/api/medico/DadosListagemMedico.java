package med.voll.api.medico;

/**
 * DadosListagemMedico
 */
public record DadosListagemMedico(

    String nome,
    String email,
    String crm,
    Especialidade especialidade
) {

    //construtor que recebe um objeto medico e retorna os dados do medico
    public DadosListagemMedico(Medico medico) {
        this(
            medico.getNome(), 
            medico.getEmail(), 
            medico.getCrm(),
             medico.getEspecialidade()
            );
    }

}
