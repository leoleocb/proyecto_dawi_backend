package com.cibertec.msclinica.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.gestionmedica.entity.Cita;
import com.cibertec.msclinica.dto.MedicoDTO;
import com.cibertec.msclinica.dto.PacienteDTO;
import com.cibertec.msclinica.client.AdminClient;
import com.cibertec.msclinica.dto.CitaDTO;
import com.cibertec.msclinica.mapper.CitaMapper;
import com.cibertec.msclinica.repository.CitaRepository;

@Service
public class CitaService {

	@Autowired
	private CitaRepository repository;
	
	@Autowired
	private CitaMapper mapper;
	
	@Autowired
	private AdminClient adminClient;
	
	public CitaDTO registrarCita(CitaDTO dto) {
		
		PacienteDTO paciente = adminClient.obtenerPaciente(dto.getPacienteId());
		MedicoDTO medico = adminClient.obtenerMedico(dto.getMedicoId());
		
		if(paciente == null || medico == null) {
			throw new RuntimeException("Paciente o Medico no encontrados en el sistema");
		}
		
		Cita entidad = mapper.toEntity(dto);
		entidad.setEstado("PENDIENTE");
		
		Cita guardada = repository.save(entidad);
		return mapper.toDTO(guardada);
	}

	public List<CitaDTO> listarCitas() {
		return repository.findAll().stream()
				.map(mapper::toDTO)
				.collect(Collectors.toList());
	}
	
	public void eliminarCita(Long id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
    
    public CitaDTO cambiarEstado(Long id, String nuevoEstado) {
        Cita cita = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        
        cita.setEstado(nuevoEstado);
        return mapper.toDTO(repository.save(cita));
    }
}