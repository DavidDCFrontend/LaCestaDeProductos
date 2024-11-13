package es.dsw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"Morbius", "JurassicWorld", "Lightyear", "Dumbledorf", "NumProductos", "TotalPagar"})
public class MainController {

	@GetMapping({"/", "/index"})
	public String index(Model model) {
		
		if(model.getAttribute("Morbius") == null) {
			initializeSessionAttributes(model);
		}
		
		return "index";
	} 
	
	@GetMapping({"/updateSession"})
	public String updateSession(@RequestParam(name="id") String filmName,
			                    @RequestParam(name="op") String operation,
			                    @RequestParam(name="price") int price,
			                    Model model) {
		
		// Obtenemos valores actuales
		int numProductos = (int) model.getAttribute("NumProductos");
		int totalPagar = (int) model.getAttribute("TotalPagar");
		int increaseFilm = (int) model.getAttribute(filmName);
		
		// Lógica de actualización de valores
		if("add".equals(operation)) {
			numProductos++;
			totalPagar+=price;
			increaseFilm++;
		} else if("substract".equals(operation)) {
			numProductos = Math.max(0, numProductos - 1);
			totalPagar = Math.max(0, totalPagar-price);
			increaseFilm = Math.max(0, increaseFilm - 1);
		}
		
		// Actualización de valores en el modelo
		model.addAttribute("NumProductos", numProductos);
		model.addAttribute("TotalPagar", totalPagar);
		model.addAttribute(filmName, increaseFilm);
	
		return "redirect:/index";
	}
	
	private void initializeSessionAttributes(Model model) {
		model.addAttribute("Morbius", 0);
		model.addAttribute("JurassicWorld", 0);
		model.addAttribute("Lightyear", 0);
		model.addAttribute("Dumbledorf", 0);
		model.addAttribute("NumProductos", 0);
		model.addAttribute("TotalPagar", 0);
	}
} 
