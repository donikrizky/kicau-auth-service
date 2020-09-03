package com.donikrizky.kicau.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donikrizky.kicau.authservice.service.ClientService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "Authentication Client Management System")
@RestController
@Validated
@RequestMapping("/client")
public class ClientController {

	@Autowired
	ClientService clientService;

	@ApiOperation(value = "Find Username By User Id From Client Instance", response = ResponseEntity.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer <access_token>")
	@GetMapping("/username-by-id/{userId}")
	public String findById(@PathVariable("userId") Integer userId) {
		return clientService.findUsernameByUserId(userId);

	}
	
}
