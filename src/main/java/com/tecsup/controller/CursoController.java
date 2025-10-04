package com.tecsup.controller;

import com.tecsup.model.entities.Curso;
import com.tecsup.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("curso")
public class CursoController {

    @Autowired
    private CursoService servicio;

    // Redirigir al listado
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:/listar";
    }

    // Listar todos los cursos
    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de cursos");
        model.addAttribute("cursos", servicio.FindAllCourse());
        return "listView";
    }

    // Mostrar formulario para registrar un nuevo curso
    @GetMapping("/form")
    public String mostrarFormulario(Model model) {
        model.addAttribute("curso", new Curso());
        return "formView";
    }

    // Mostrar formulario para editar un curso existente
    @GetMapping("/form/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Curso curso = servicio.FindByIdCourse(Math.toIntExact(id));
        if (curso == null) {
            return "redirect:/listar";
        }
        model.addAttribute("curso", curso);
        return "formView"; // usa el mismo formulario
    }

    // Guardar curso (nuevo o editado)
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(@ModelAttribute Curso curso) {
        servicio.SaveCourse(curso);
        return "redirect:/listar";
    }

    // Eliminar curso
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        servicio.DeleteCourse(Math.toIntExact(id));
        return "redirect:/listar";
    }
}
