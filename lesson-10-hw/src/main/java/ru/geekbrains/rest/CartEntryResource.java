package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.BadRequestException;
import ru.geekbrains.NotFoundException;
import ru.geekbrains.dto.CartEntryDto;
import ru.geekbrains.service.CartEntryService;

import java.util.List;

@RequestMapping("/api/v1/cart")
@RestController
public class CartEntryResource {

    private final CartEntryService cartEntryService;

    @Autowired
    public CartEntryResource(CartEntryService cartEntryService) {
        this.cartEntryService = cartEntryService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<CartEntryDto> findAll() {
        return cartEntryService.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public CartEntryDto findById(@PathVariable("id") Long id) {
        CartEntryDto cartEntryDto = cartEntryService.findById(id)
                .orElseThrow(NotFoundException::new);
        return cartEntryDto;
    }

    @GetMapping(path = "/user/{id}", produces = "application/json")
    public List<CartEntryDto> findByUserId(@PathVariable("id") Long userId) {
        return cartEntryService.findByUserId(userId);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public CartEntryDto create(@RequestBody CartEntryDto cartEntryDto) {
        if (cartEntryDto.getId() != null) {
            throw new BadRequestException();
        }
        return cartEntryService.save(cartEntryDto);
    }

    @PutMapping(consumes = "application/json")
    public void update(@RequestBody CartEntryDto cartEntryDto) {
        if (cartEntryDto.getId() == null) {
            throw new BadRequestException();
        }
        cartEntryService.save(cartEntryDto);
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public void delete(@PathVariable("id") Long id) {
        cartEntryService.delete(id);
    }

    @DeleteMapping(path = "/user/{id}", produces = "application/json")
    public void deleteCartEntryByUserId(@PathVariable("id") Long userId) {
        cartEntryService.deleteCartEntryByUserId(userId);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundException(NotFoundException ex) {
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> badRequestException(BadRequestException ex) {
        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }
}
