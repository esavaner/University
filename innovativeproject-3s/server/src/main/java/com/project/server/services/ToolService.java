package com.project.server.services;

import java.util.List;
import java.util.Optional;

import com.project.server.services.exceptions.ToolNotFoundException;
import com.project.server.model.Tool;
import com.project.server.repository.ToolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class ToolService  {

    @Autowired ToolRepository repository;

	public List<Tool> getTools() {
        return (List<Tool>) repository.findAll();
	}

	public Tool getToolByName(String name) {
		Optional<Tool> tool = repository.findByName(name);
		return (Tool) tool.orElseThrow(() -> new ToolNotFoundException(name));
	}

	public void add(Tool tool) {
        repository.save(tool);
	}

	public Tool getToolById(long id) {
        Optional<Tool> optionalusr = repository.findById(id);
        return (Tool) optionalusr.orElseThrow(() -> new ToolNotFoundException(id));	}

	public void delete(long id) {
        repository.deleteById(id);
	}

}
