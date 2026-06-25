package med.voll.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.voll.medico.DadosCadastroMedico;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    // requestbody no parametro serve para pegar o corpo da requisição e transformar em um objeto java, no caso aqui é uma string
    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroMedico dados) {
        System.out.println(dados);
        
    }

}
