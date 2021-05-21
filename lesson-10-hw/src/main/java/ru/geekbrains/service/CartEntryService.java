package ru.geekbrains.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.dto.CartEntryDto;
import ru.geekbrains.persist.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartEntryService {

    private final CartEntryRepository cartEntryRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    public CartEntryService(CartEntryRepository cartEntryRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartEntryRepository = cartEntryRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<CartEntryDto> findAll() {
        return cartEntryRepository.findAll().stream()
                .map(CartEntryService::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<CartEntryDto> findById(long id) {
        return cartEntryRepository.findById(id)
                .map(CartEntryService::convertToDto);
    }

    public List<CartEntryDto> findByUserId(long userId) {
        return cartEntryRepository.findByUserId(userId).stream()
                .map(CartEntryService::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public CartEntryDto save(CartEntryDto cartEntryDto) {
        CartEntry saved = cartEntryRepository.save(convertToDao(cartEntryDto));
        cartEntryDto.setId(saved.getId());
        return cartEntryDto;
    }

    @Transactional
    public void delete(long id) {
        cartEntryRepository.deleteById(id);
    }

    @Transactional
    public void deleteCartEntryByUserId(long userId) {
        cartEntryRepository.deleteCartEntryByUserId(userId);
    }

    private static CartEntryDto convertToDto(CartEntry cartEntry) {
        return new CartEntryDto(cartEntry.getId(),
                cartEntry.getUser().getId(),
                cartEntry.getUser().getUsername(),
                cartEntry.getProduct().getId(),
                cartEntry.getProduct().getProductname(),
                cartEntry.getProduct().getProductCost(),
                cartEntry.getQuantity());
    }

    private CartEntry convertToDao(CartEntryDto cartEntryDto) {
        User user = userRepository.findById(cartEntryDto.getUser_id()).get();
        Product product = productRepository.findById(cartEntryDto.getProduct_id()).get();
        return new CartEntry(cartEntryDto.getId(), user, product, cartEntryDto.getQuantity());
    }
}
