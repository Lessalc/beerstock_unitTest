package com.dio.beerstock.service;

import com.dio.beerstock.DTO.BeerDTOBuilder;
import com.dio.beerstock.dto.BeerDTO;
import com.dio.beerstock.entity.Beer;
import com.dio.beerstock.exception.BeerAlreadyRegisteredException;
import com.dio.beerstock.mapper.BeerMapper;
import com.dio.beerstock.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {

    private static final long INVALID_BEER_ID = 1L;

    @Mock
    private BeerRepository beerRepository;

    private BeerMapper beerMapper = BeerMapper.INSTANCE;

    @InjectMocks
    private BeerService beerService;

    @Test
    void whenBeerInformedThenItShouldBeCreated() throws BeerAlreadyRegisteredException {
        // Vamos criar um objeto exatamente igual o passado na classe
        BeerDTO expectedbeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedSavedBeer = beerMapper.toModel(expectedbeerDTO);
        // Temos nossos objeto

        // When - Primeiro fazendo com que o verify seja empty
        when(beerRepository.findByName(expectedbeerDTO.getName())).thenReturn(Optional.empty());
        // When - Segundo fazendo o save retornando o próprio objeto
        when(beerRepository.save(expectedSavedBeer)).thenReturn(expectedSavedBeer);

        //Then - Aqui é o método puro
        BeerDTO createdBeerDTO = beerService.createBeer(expectedbeerDTO);

        assertThat(createdBeerDTO.getId(), is(equalTo(expectedbeerDTO.getId())));
        assertThat(createdBeerDTO.getName(), is(equalTo(expectedbeerDTO.getName())));
        assertThat(createdBeerDTO.getQuantity(), is(equalTo(expectedbeerDTO.getQuantity())));

        assertThat(createdBeerDTO.getMax(), is(greaterThanOrEqualTo(createdBeerDTO.getQuantity())));

        // Por fim fazemos os teste para outros dados
//        assertEquals(beerDTO.getId(), createdBeerDTO.getId());
//        assertEquals(beerDTO.getName(), createdBeerDTO.getName());
    }

    @Test
    void whenAlreadyRegisteredBeerInformedThenAnExceptionShouldBeThrown(){
        // Given
        BeerDTO expectedbeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer duplicatedBeer = beerMapper.toModel(expectedbeerDTO);

        // when
        when(beerRepository.findByName(expectedbeerDTO.getName())).thenReturn(Optional.of(duplicatedBeer));

        // Then
        assertThrows(BeerAlreadyRegisteredException.class, () -> beerService.createBeer(expectedbeerDTO));
    }
}
