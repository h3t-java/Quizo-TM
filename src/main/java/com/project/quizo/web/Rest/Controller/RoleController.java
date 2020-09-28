package com.project.quizo.web.Rest.Controller;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.project.quizo.resource.RoleDTO;
import com.project.quizo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping
	public ResponseEntity<List<RoleDTO>> getAllRoles() {
		return ResponseEntity.ok(roleService.findAllRoles().stream().map(RoleDTO::new).collect(toList()));
	}
	
}