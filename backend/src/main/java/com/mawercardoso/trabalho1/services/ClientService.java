package com.mawercardoso.trabalho1.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.mawercardoso.trabalho1.dto.ClientDTO;
import com.mawercardoso.trabalho1.entities.Client;
import com.mawercardoso.trabalho1.repositories.ClientRepository;
import com.mawercardoso.trabalho1.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> page =  repository.findAll(pageRequest);
		
		return page.map(x -> new ClientDTO(x));
		
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(()-> new ResourceNotFoundException("Entidade não encontrada"));
		return new ClientDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public ClientDTO insert(@RequestBody ClientDTO dto){
		Client entity = copytoEntity(dto);
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}

	private Client copytoEntity(ClientDTO dto) {
		Client entity = new Client();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
		return entity;
	}
	

}
