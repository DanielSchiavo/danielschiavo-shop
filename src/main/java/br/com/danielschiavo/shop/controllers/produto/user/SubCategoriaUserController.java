package br.com.danielschiavo.shop.controllers.produto.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.danielschiavo.shop.models.produto.subcategoria.MostrarSubCategoriaComCategoriaDTO;
import br.com.danielschiavo.shop.services.produto.user.SubCategoriaUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/shop")
@Tag(name = "Sub Categorias", description = "Todos endpoints relacionados com as subcategorias, uma subcategoria é dependente de uma categoria")
public class SubCategoriaUserController {
	
	@Autowired
	private SubCategoriaUserService subCategoriaService;
	
	@GetMapping("/publico/sub-categoria")
	@Operation(summary = "Lista todas as subcategorias existentes")
	public ResponseEntity<Page<MostrarSubCategoriaComCategoriaDTO>> listarSubCategorias(Pageable pageable){		
		Page<MostrarSubCategoriaComCategoriaDTO> listaSubCategorias = subCategoriaService.listarSubCategorias(pageable);
		return ResponseEntity.ok(listaSubCategorias);
	}
	
}