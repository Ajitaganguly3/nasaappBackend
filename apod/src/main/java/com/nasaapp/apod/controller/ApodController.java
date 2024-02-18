package com.nasaapp.apod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nasaapp.apod.entity.Apod;
import com.nasaapp.apod.service.ApodService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/apod")
public class ApodController {

	// private static final Logger log = LoggerFactory.getLogger(Apod.class);

	@Autowired
	private ApodService apodService;

	@Operation(summary = "To List the details of APOD")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Third Party API working correctly", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Apod.class))),
			@ApiResponse(responseCode = "400", description = "Check the URL", content = @Content) })

	@GetMapping("/getApods")
	public ResponseEntity<?> getApods() {
		Apod dataList = apodService.getApods();
		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	@Operation(summary = "To List the details of APOD by Date")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Third Party API working correctly", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Apod.class))),
			@ApiResponse(responseCode = "400", description = "Check the URL", content = @Content) })

	@GetMapping("/apodByDate/{date}")
	public Apod getPictureByDate(@PathVariable String date) {
		return apodService.getApodByDate(date);

	}

}
