package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.voll.medico.DadosCadastroMedico;
import med.voll.medico.Medico;
import med.voll.medico.MedicoRepository;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    // injecao de ddependencia, o spring vai criar uma instancia do MedicoRepository e injetar aqui
    @Autowired
    private MedicoRepository repository;

    // requestbody no parametro serve para pegar o corpo da requisição e transformar em um objeto java, no caso aqui é uma string
    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroMedico dados) {
        // System.out.println(dados);

        repository.save(new Medico(dados));
        
    }

}
