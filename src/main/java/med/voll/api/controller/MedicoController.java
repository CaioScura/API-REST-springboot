package med.voll.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    // injecao por construtor (boa pratica recomendada pelo spring, substitui o @Autowired no campo)
    // antes: @Autowired private MedicoRepository repository;
    private final MedicoRepository repository;

    public MedicoController(MedicoRepository repository) {
        this.repository = repository;
    }

    // requestbody no parametro serve para pegar o corpo da requisição e transformar em um objeto java, no caso aqui é uma string
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroMedico dados) {
        // System.out.println(dados);

        repository.save(new Medico(dados));
        
    }

}
