package com.mawercardoso.trabalho1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mawercardoso.trabalho1.dto.ClientDTO;
import com.mawercardoso.trabalho1.entities.Client;
import com.mawercardoso.trabalho1.repositories.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> page =  repository.findAll(pageRequest);
		
		return page.map(x -> new ClientDTO(x));
		
	}
	

}
