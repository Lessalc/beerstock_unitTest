package com.dio.beerstock.service;

import com.dio.beerstock.DTO.BeerDTOBuilder;
import com.dio.beerstock.dto.BeerDTO;
import com.dio.beerstock.entity.Beer;
import com.dio.beerstock.exception.BeerAlreadyRegisteredException;
import com.dio.beerstock.exception.BeerNotFoundException;
import com.dio.beerstock.mapper.BeerMapper;
import com.dio.beerstock.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    @Test
    void whenValidBeerNameIsGivenThenReturnABeer() throws BeerNotFoundException {
        BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedFoundBeer = beerMapper.toModel(expectedFoundBeerDTO);

        when(beerRepository.findByName(expectedFoundBeer.getName())).thenReturn(Optional.of(expectedFoundBeer));

        BeerDTO foundBeerDTO = beerService.findByName(expectedFoundBeer.getName());

        assertThat(foundBeerDTO, is(equalTo(expectedFoundBeerDTO)));
    }

    @Test
    void whenNotRegistredBeerNameIsGivenThenReturn() throws BeerNotFoundException {
        // Given
        BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        // When - Mockar
        when(beerRepository.findByName(expectedFoundBeerDTO.getName())).thenReturn(Optional.empty());

        // Then
        assertThrows(BeerNotFoundException.class, () -> beerService.findByName(expectedFoundBeerDTO.getName()));
    }

    @Test
    void whenListBeerIsCalledThenReturnAListOfBeers(){
        // Givin
        BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedFoundBeer = beerMapper.toModel(expectedFoundBeerDTO);

        //When
        when(beerRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundBeer));

        //Then
        List<BeerDTO> foundListBeerDTO = beerService.listAll();

        assertThat(foundListBeerDTO, is(not(empty())));
        assertThat(foundListBeerDTO.get(0), is(equalTo(expectedFoundBeerDTO)));

    }

    @Test
    void whenListBeerIsCalledThenReturnAEmptyList(){
        //When
        when(beerRepository.findAll()).thenReturn(Collections.emptyList());

        //Then
        List<BeerDTO> foundListBeerDTO = beerService.listAll();

        assertThat(foundListBeerDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenABeerShouldBeDeleted() throws BeerNotFoundException {
        //given
        BeerDTO expectedDeletedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedDeletedBeer = beerMapper.toModel(expectedDeletedBeerDTO);

        // When
        when(beerRepository.findById(expectedDeletedBeerDTO.getId())).thenReturn(Optional.of(expectedDeletedBeer));
        doNothing().when(beerRepository).deleteById(expectedDeletedBeerDTO.getId());

        // then
        beerService.deleteById(expectedDeletedBeerDTO.getId());

        verify(beerRepository, times(1)).findById(expectedDeletedBeerDTO.getId());
        verify(beerRepository, times(1)).deleteById(expectedDeletedBeerDTO.getId());
    }

    @Test
    void whenExclusionIsCalledWithInvalidIdThenAnExceptionShouldBeThown(){
        BeerDTO expectedDeletedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        when(beerRepository.findById(expectedDeletedBeerDTO.getId())).thenReturn(Optional.empty());

        assertThrows(BeerNotFoundException.class, () -> beerService.deleteById(expectedDeletedBeerDTO.getId()));
    }


}
