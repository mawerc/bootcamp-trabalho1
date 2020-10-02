package com.mawercardoso.trabalho1.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.mawercardoso.trabalho1.dto.ClientDTO;
import com.mawercardoso.trabalho1.entities.Client;
import com.mawercardoso.trabalho1.repositories.ClientRepository;
import com.mawercardoso.trabalho1.services.exceptions.DatabaseException;
import com.mawercardoso.trabalho1.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> page =  clientRepository.findAll(pageRequest);
		
		return page.map(x -> new ClientDTO(x));
		
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		
		Optional<Client> obj = clientRepository.findById(id);
		Client entity = obj.orElseThrow(()-> new ResourceNotFoundException("Entidade não encontrada"));
		return new ClientDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public ClientDTO insert(@RequestBody ClientDTO dto){
		Client entity = new Client();
		copyDtoToEntity(dto, entity);
		entity = clientRepository.save(entity);
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client entity = clientRepository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = clientRepository.save(entity);
			return new ClientDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Cliente com Id " + id + " não existe");
		}
	}
	
	public void delete(Long id) {
		try {
				clientRepository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Cliente com Id " + id + " não existe");
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Esta operação representa uma violação de Integridade.");
		}
	}

	private void copyDtoToEntity(ClientDTO dto, Client entity) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
	}
	

}
