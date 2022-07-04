package com.bruckner.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bruckner.cobranca.model.StatusTitulo;
import com.bruckner.cobranca.model.Titulo;
import com.bruckner.cobranca.repository.TitulosRepository;

@Controller
@RequestMapping("/titulos")
public class TituloController {

    @Autowired
    private TitulosRepository titulosRepository;

    @ModelAttribute("todosStatusTitulo")
    public List<StatusTitulo> todosStatusTitulo() {
        return Arrays.asList(StatusTitulo.values());
    }

    @RequestMapping
    public ModelAndView pesquisar() {
        List<Titulo> titulos = titulosRepository.findAll();

        ModelAndView mv = new ModelAndView("PesquisaTitulos");
        mv.addObject("titulos", titulos);

        return mv;
    }

    @RequestMapping("/novo")
    public ModelAndView novo() {
        System.out.println("TituloController >> novo");
        ModelAndView mv = new ModelAndView("CadastroTitulo");
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView salvar(Titulo titulo) {
        titulosRepository.save(titulo);
        ModelAndView mv = new ModelAndView("CadastroTitulo");
        mv.addObject("mensagem", "Titulo salvo com sucesso!");
        return mv;
    }
}
