package com.generation.meublogpessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import com.generation.meublogpessoal.model.Tema;
import com.generation.meublogpessoal.repository.TemaRepository;

@RestController
@RequestMapping ("/temas")
@CrossOrigin (origins = "*" , allowedHeaders = "*")

public class TemaController {
	
	@Autowired 
	private TemaRepository temaRepository;
	
	@GetMapping
	public ResponseEntity <List<Tema>> getAll (){
        return ResponseEntity.ok(temaRepository.findAll());
        
        
    }
	@GetMapping("/{id}")
    public ResponseEntity <Tema> getById(@PathVariable Long id){
        return temaRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.notFound().build());
        
        // select* from tb_postagens where id = id
	}  @GetMapping("/descricao/{descricao}") 
       public ResponseEntity<Object> getByTitulo(@PathVariable String descricao){
          return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));

             
	}  @PostMapping
       public ResponseEntity <Tema> postDescricao (@Valid @RequestBody Tema tema){
        return ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(tema));
}		
	@PutMapping
  	public ResponseEntity <Tema> putDescricao(@Valid @RequestBody Tema tema){
		        return temaRepository.findById(tema.getId())
		        .map(resposta -> ResponseEntity.ok().body(temaRepository.save(tema)))
		        .orElse(ResponseEntity.notFound().build());
			
			
}@DeleteMapping("/{id}")
@ResponseStatus (HttpStatus.NO_CONTENT)//Para trazer o status sem conteúdo
public void deleteTema (@Valid @PathVariable Long id){
    Optional<Tema> tema = temaRepository.findById(id); //como se fosse map

    if (tema.isEmpty()) //checagem se é vazio...
        throw new ResponseStatusException(HttpStatus.NOT_FOUND); //caso não encontre "joga" uma nova resposta

    temaRepository.deleteById(id); //chamo o método postagemRepository
       
}
}