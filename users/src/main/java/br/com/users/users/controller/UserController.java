package br.com.users.users.controller;

import br.com.users.users.model.Usuario;
import br.com.users.users.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UserController {

    private final UserRepository usuarioRepository;

    public UserController(UserRepository userRepository) {
        this.usuarioRepository = userRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("usuario") Usuario usuario,
                         BindingResult binding,
                         RedirectAttributes ra,
                         Model model) {

        if (usuario.getId() == null && usuarioRepository.existsByEmail(usuario.getEmail())) {
            binding.rejectValue("email", "unique", "Já existe usuário com este e-mail");
        }

        if (binding.hasErrors()) {
            return "usuarios/form";
        }

        usuarioRepository.save(usuario);
        ra.addFlashAttribute("msgSucesso", "Usuário salvo com sucesso!");
        return "redirect:/usuarios";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        var usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            ra.addFlashAttribute("msgErro", "Usuário não encontrado");
            return "redirect:/usuarios";
        }
        model.addAttribute("usuario", usuario);
        return "usuarios/form";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            ra.addFlashAttribute("msgSucesso", "Usuário excluído.");
        } else {
            ra.addFlashAttribute("msgErro", "Usuário não encontrado.");
        }
        return "redirect:/usuarios";
    }
}