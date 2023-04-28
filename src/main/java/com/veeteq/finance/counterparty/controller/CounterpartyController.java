package com.veeteq.finance.counterparty.controller;

import java.net.URI;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.veeteq.finance.counterparty.dto.CounterpartyDTO;
import com.veeteq.finance.counterparty.exception.ResourceNotFoundException;
import com.veeteq.finance.counterparty.service.CounterpartyService;

@RestController
@RequestMapping(path = "/api/counterparties")
public class CounterpartyController {
    private final Logger LOG = LoggerFactory.getLogger(CounterpartyController.class);
    
    private final CounterpartyService counterpartyService;

    @Autowired
    public CounterpartyController(CounterpartyService counterpartyService) {
        this.counterpartyService = counterpartyService;
    }
    
    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<CounterpartyDTO>> getAll() {
        
        List<CounterpartyDTO> all = counterpartyService.findAll();
        
        return ResponseEntity.ok().body(all);
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<CounterpartyDTO> findById(@PathVariable("id") Long id) {
        
        CounterpartyDTO counterparty = counterpartyService.findById(id);
        
        return ResponseEntity.ok().body(counterparty);
    }
   
    @PostMapping(path = {"", "/"})
    public ResponseEntity<CounterpartyDTO> create(@Valid @RequestBody CounterpartyDTO counterparty) {
      LOG.info("creating a new item");

      CounterpartyDTO created = counterpartyService.save(counterparty);
      LOG.info(MessageFormat.format("counterparty with id {0} created", created.getId()));

      URI location = ServletUriComponentsBuilder.fromCurrentRequest()
              .path("/{id}")
              .buildAndExpand(created.getId())
              .toUri();
      return ResponseEntity.created(location).body(created);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CounterpartyDTO> update(@PathVariable("id") Long id, @Valid @RequestBody CounterpartyDTO dto) throws ResourceNotFoundException {
        LOG.info("updating a counterparty with id: " + id);

        CounterpartyDTO updated = counterpartyService.update(id, dto);

        LOG.info(MessageFormat.format("Counterparty with id {0} updated", updated.getId()));                
        
        return ResponseEntity.ok().body(updated);
    }     
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CounterpartyDTO> delete(@PathVariable("id") Long id) throws ResourceNotFoundException {
      LOG.info("deleting the item with id: " + id);

      counterpartyService.deleteById(id);
      
      return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
      List<ObjectError> errors = ex.getBindingResult().getAllErrors();
      Map<String, String> map = new HashMap<>(errors.size());
      errors.forEach((error) -> {
        String key = ((FieldError) error).getField();
        String val = error.getDefaultMessage();
        map.put(key, val);
      });
      return ResponseEntity.badRequest().body(map);
    }
}