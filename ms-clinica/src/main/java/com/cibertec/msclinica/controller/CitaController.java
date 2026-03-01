package com.cibertec.msclinica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.msclinica.dto.CitaDTO;
import com.cibertec.msclinica.service.CitaService;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

	@Autowired
	private CitaService service;
	
	@PostMapping
	public ResponseEntity<CitaDTO> agendar(@RequestBody CitaDTO dto){
		try {
			return ResponseEntity.ok(service.registrarCita(dto));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping
	public ResponseEntity<List<CitaDTO>> listarCitas(){
		try {
			List<CitaDTO> lista = service.listarCitas();
			return ResponseEntity.ok(lista);
		} catch (Exception e) {
			return ResponseEntity.noContent().build();
		}
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        service.eliminarCita(id);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}/estado")
    public ResponseEntity<CitaDTO> actualizarEstado(@PathVariable Long id){
        try {
            return ResponseEntity.ok(service.cambiarEstado(id, "ATENDIDA"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}