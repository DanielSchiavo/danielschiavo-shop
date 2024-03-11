package br.com.danielschiavo.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.danielschiavo.shop.infra.security.DadosAutenticacaoDTO;
import br.com.danielschiavo.shop.infra.security.TokenDTO;
import br.com.danielschiavo.shop.infra.security.TokenJWTService;
import br.com.danielschiavo.shop.models.cliente.Cliente;
import br.com.danielschiavo.shop.services.AutenticacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/shop")
@Tag(name = "Login")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenJWTService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity<TokenDTO> login(@RequestBody @Valid DadosAutenticacaoDTO dadosAutenticacao) {
		
		var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dadosAutenticacao.login(), dadosAutenticacao.senha());
		var authentication = manager.authenticate(usernamePasswordAuthenticationToken);
		
		String token = tokenService.generateToken((Cliente) authentication.getPrincipal());
		
		TokenDTO tokenDTO = new TokenDTO(token);
		
		return ResponseEntity.ok(tokenDTO);
		
	}

}