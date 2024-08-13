package org.task.maid.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.task.maid.dto.PatronDto;
import org.task.maid.dto.pagination.PaginationDto;
import org.task.maid.service.PatronService;

import java.net.URI;

@RestController
@RequestMapping("/patrons")
public class PatronController {

    private PatronService patronService;

    @GetMapping
    public ResponseEntity<PaginationDto<PatronDto>> getAllPatrons(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "20") Integer size) {
        PaginationDto<PatronDto> paginationDto = patronService.getAllPatronsPaged(page, size);
        return ResponseEntity.ok(paginationDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatronDto> getPatronById(@PathVariable("id") Integer id) {
        PatronDto patronDto = patronService.getPatronDtoById(id);
        return ResponseEntity.ok(patronDto);
    }

    @PostMapping
    public ResponseEntity<PatronDto> addPatron(@Valid @RequestBody PatronDto patronDto) {
        PatronDto savedPatron = patronService.addPatron(patronDto);
        return ResponseEntity.created(URI.create("/patrons/" + savedPatron.getId())).body(savedPatron);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatronDto> updatePatron(@PathVariable("id") Integer id, @RequestBody PatronDto patronDto) {
        patronDto = patronService.updatePatron(id, patronDto);
        return ResponseEntity.ok(patronDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable("id") Integer id) {
        patronService.deletePatron(id);
        return ResponseEntity.noContent().build();
    }
}
