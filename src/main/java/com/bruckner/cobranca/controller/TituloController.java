package com.bruckner.cobranca.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bruckner.cobranca.model.StatusTitulo;
import com.bruckner.cobranca.model.Titulo;
import com.bruckner.cobranca.service.CadastroTituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {

    // @Autowired
    // private TitulosRepository titulosRepository;

    @Autowired
    private CadastroTituloService tituloService;

    private static final String CADASTRO_VIEW = "CadastroTitulo";

    @ModelAttribute("todosStatusTitulo")
    public List<StatusTitulo> todosStatusTitulo() {
        return Arrays.asList(StatusTitulo.values());
    }

    @RequestMapping
    public ModelAndView pesquisar() {
        System.out.println("PESQUISAR");
        List<Titulo> titulos = tituloService.buscarTodos();

        ModelAndView mv = new ModelAndView("PesquisaTitulos");
        mv.addObject("titulos", titulos);

        return mv;
    }

    @RequestMapping("{id}")
    public ModelAndView editar(@PathVariable Long id) {
        System.out.println("EDITAR");
        Optional<Titulo> tituloOpt = tituloService.buscarPorId(id);
        ModelAndView mv = new ModelAndView(CADASTRO_VIEW);

        if (tituloOpt.isPresent()) {
            mv.addObject(tituloOpt.get());
            System.out.println(tituloOpt.get());
        } else {
            mv.addObject(new Titulo());
            mv.addObject("mensagem", "Titulo " + id + " não encontrado");
        }

        return mv;
    }

    @RequestMapping("/novo")
    public ModelAndView novo() {
        System.out.println("NOVO");
        ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
        mv.addObject(new Titulo());
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
        System.out.println("SALVAR");
        if (errors.hasErrors()) {
            return "CadastroTitulo";
        }

        try {
            String mensagemSucesso = (null == titulo.getCodigo()) ? "Titulo salvo com sucesso!"
                    : "Titulo atualizado com sucesso!";

            tituloService.salvar(titulo);
            attributes.addFlashAttribute("mensagem", mensagemSucesso);

            return "redirect:/titulos/novo";
        } catch (IllegalArgumentException e) {
            errors.rejectValue("dataVencimento", null, e.getMessage());
            return CADASTRO_VIEW;
        }

    }

    @RequestMapping(value = "{codigo}", method = RequestMethod.DELETE)
    public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
        System.out.println("EXCLUIR");
        Titulo titulo = tituloService.buscarPorId(codigo).get();
        tituloService.apagarTitulo(titulo);

        attributes.addFlashAttribute("mensagem", "Titulo excluído com sucesso!");
        return "redirect:/titulos";
    }

    @RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
    public @ResponseBody String receber(@PathVariable Long codigo) {
        System.out.println(">>> codigo: " + codigo);

        return tituloService.receber(codigo);

    }
}
