package br.com.danielschiavo.shop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.danielschiavo.shop.infra.exceptions.ValidacaoException;
import br.com.danielschiavo.shop.infra.security.UsuarioAutenticadoService;
import br.com.danielschiavo.shop.models.cliente.Cliente;
import br.com.danielschiavo.shop.models.endereco.AlterarEnderecoDTO;
import br.com.danielschiavo.shop.models.endereco.CadastrarEnderecoDTO;
import br.com.danielschiavo.shop.models.endereco.Endereco;
import br.com.danielschiavo.shop.models.endereco.MostrarEnderecoDTO;
import br.com.danielschiavo.shop.repositories.EnderecoRepository;

@Service
public class EnderecoService {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private UsuarioAutenticadoService usuarioAutenticadoService;
	
	@Transactional
	public void deletarEnderecoPorIdToken(Long idEndereco) {
		Endereco endereco = verificarSeEnderecoExistePorIdEnderecoECliente(idEndereco);
		
		enderecoRepository.delete(endereco);
	}
	
	public List<MostrarEnderecoDTO> pegarEnderecosClientePorIdToken() {
		Cliente cliente = usuarioAutenticadoService.getCliente();
		
		var pageEndereco = enderecoRepository.findAllByCliente(cliente);
		return pageEndereco.stream().map(MostrarEnderecoDTO::converterParaMostrarEnderecoDTO).toList();
	}
	
	@Transactional
	public MostrarEnderecoDTO cadastrarNovoEnderecoPorIdToken(CadastrarEnderecoDTO novoEnderecoDTO) {
		var novoEndereco = new Endereco(novoEnderecoDTO);
		
		Cliente cliente = usuarioAutenticadoService.getCliente();
		
		novoEndereco.setCliente(cliente);
		
		if (novoEnderecoDTO.enderecoPadrao() == true) {
			
			var optionalEndereco = enderecoRepository.findByClienteAndEnderecoPadraoTrue(cliente);
			if (optionalEndereco.isPresent()) {
				var endereco = optionalEndereco.get();
				endereco.setEnderecoPadrao(false);
				enderecoRepository.save(endereco);
			}
			
			novoEndereco.setEnderecoPadrao(true);
		}
		
		enderecoRepository.save(novoEndereco);
		
		return new MostrarEnderecoDTO(novoEndereco);
	}
	
	@Transactional
	public MostrarEnderecoDTO alterarEnderecoPorIdToken(AlterarEnderecoDTO enderecoDTO, Long idEndereco) {
		var endereco = enderecoRepository.findById(idEndereco).get();
		
		endereco.alterarEndereco(enderecoDTO);
		
		enderecoRepository.save(endereco);
		
		return new MostrarEnderecoDTO(endereco);
	}
	
	
//	------------------------------
//	------------------------------
//	METODOS UTILITARIOS
//	------------------------------
//	------------------------------

	public Endereco verificarSeEnderecoExistePorIdEnderecoECliente(Long idEndereco) {
		Cliente cliente = usuarioAutenticadoService.getCliente();
		Optional<Endereco> optionalEndereco = enderecoRepository.findByIdAndCliente(idEndereco, cliente);
		if (optionalEndereco.isPresent()) {
			return optionalEndereco.get();
		}
		else {
			throw new ValidacaoException("Não existe Endereço com o ID " + idEndereco + " cadastrado para o Cliente ID " + cliente.getId());
		}
	}
	
	public Endereco verificarID(Long clienteId, Long enderecoId) {
		Optional<Endereco> optionalEndereco = enderecoRepository.findByClienteIdAndEnderecoId(clienteId, enderecoId);
		if (optionalEndereco.isPresent()) {
			Endereco endereco = optionalEndereco.get();
			return endereco;
		} else {	
			throw new RuntimeException("ID do Endereco ou Cliente inexistente! ");
		}
	}

	
}
