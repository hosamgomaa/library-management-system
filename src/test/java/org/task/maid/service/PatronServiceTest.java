package org.task.maid.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.task.maid.dto.PatronDto;
import org.task.maid.dto.pagination.PaginationDetails;
import org.task.maid.dto.pagination.PaginationDto;
import org.task.maid.entity.Patron;
import org.task.maid.exception.SystemErrorMessages;
import org.task.maid.exception.SystemException;
import org.task.maid.mapper.PaginationMapper;
import org.task.maid.mapper.PatronMapper;
import org.task.maid.repository.PatronRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private PatronMapper patronMapper;

    @Mock
    private PaginationMapper paginationMapper;

    @InjectMocks
    private PatronService patronService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPatronsPaged() {
        int page = 0;
        int size = 10;

        List<Patron> patrons = Arrays.asList(new Patron(), new Patron());
        Page<Patron> patronPage = new PageImpl<>(patrons, PageRequest.of(page, size), patrons.size());

        List<PatronDto> patronDtos = List.of(new PatronDto(), new PatronDto());
        PaginationDetails paginationDetails = new PaginationDetails(page, patrons.size(), size, 1);
        PaginationDto<PatronDto> expectedPaginationDto = new PaginationDto<>(patronDtos, paginationDetails);

        when(patronRepository.findAll(any(Pageable.class))).thenReturn(patronPage);
        when(patronMapper.toDto(anyList())).thenReturn(patronDtos);
        when(paginationMapper.toPaginationDto(ArgumentMatchers.<PatronDto>anyList(), any(PaginationDetails.class))).thenReturn(expectedPaginationDto);

        PaginationDto<PatronDto> result = patronService.getAllPatronsPaged(page, size);

        assertEquals(expectedPaginationDto, result);
        verify(patronRepository).findAll(any(Pageable.class));
        verify(patronMapper).toDto(anyList());
        verify(paginationMapper).toPaginationDto(anyList(), any(PaginationDetails.class));
    }

    @Test
    void testGetPatronDtoById_Success() {
        int patronId = 1;
        Patron patron = new Patron();
        PatronDto patronDto = new PatronDto();

        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        when(patronMapper.toDto(patron)).thenReturn(patronDto);

        PatronDto result = patronService.getPatronDtoById(patronId);

        assertEquals(patronDto, result);
        verify(patronRepository).findById(patronId);
        verify(patronMapper).toDto(patron);
    }

    @Test
    void testGetPatronDtoById_NotFound() {
        int patronId = 1;

        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(SystemException.class, () -> patronService.getPatronDtoById(patronId));
        assertEquals(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage(), exception.getMessage());
        verify(patronRepository).findById(patronId);
    }

    @Test
    void testAddPatron_Success() {
        PatronDto patronDto = new PatronDto();
        Patron patron = new Patron();

        when(patronMapper.toEntity(patronDto)).thenReturn(patron);
        when(patronRepository.save(patron)).thenReturn(patron);
        when(patronMapper.toDto(patron)).thenReturn(patronDto);

        PatronDto result = patronService.addPatron(patronDto);

        assertEquals(patronDto, result);
        verify(patronMapper).toEntity(patronDto);
        verify(patronRepository).save(patron);
        verify(patronMapper).toDto(patron);
    }

    @Test
    void testUpdatePatron_Success() {
        int patronId = 1;
        PatronDto patronDto = new PatronDto();
        Patron patron = new Patron();

        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        when(patronMapper.updateEntityFromDto(patronDto, patron)).thenReturn(patron);
        when(patronRepository.save(patron)).thenReturn(patron);
        when(patronMapper.toDto(patron)).thenReturn(patronDto);

        PatronDto result = patronService.updatePatron(patronId, patronDto);

        assertEquals(patronDto, result);
        verify(patronRepository).findById(patronId);
        verify(patronMapper).updateEntityFromDto(patronDto, patron);
        verify(patronRepository).save(patron);
        verify(patronMapper).toDto(patron);
    }

    @Test
    void testUpdatePatron_NotFound() {
        int patronId = 1;
        PatronDto patronDto = new PatronDto();

        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(SystemException.class, () -> patronService.updatePatron(patronId, patronDto));
        assertEquals(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage(), exception.getMessage());
        verify(patronRepository).findById(patronId);
    }

    @Test
    void testDeletePatron_Success() {
        int patronId = 1;
        Patron patron = new Patron();

        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));

        patronService.deletePatron(patronId);

        verify(patronRepository).findById(patronId);
        verify(patronRepository).delete(patron);
    }

    @Test
    void testDeletePatron_NotFound() {
        int patronId = 1;

        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(SystemException.class, () -> patronService.deletePatron(patronId));
        assertEquals(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage(), exception.getMessage());
        verify(patronRepository).findById(patronId);
    }

    @Test
    void testDeletePatron_ExceptionOnDelete() {
        int patronId = 1;
        Patron patron = new Patron();

        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        doThrow(new RuntimeException()).when(patronRepository).delete(patron);

        Exception exception = assertThrows(SystemException.class, () -> patronService.deletePatron(patronId));
        assertEquals(SystemErrorMessages.RESOURCE_IS_RELATED.getMessage(), exception.getMessage());
        verify(patronRepository).findById(patronId);
        verify(patronRepository).delete(patron);
    }


}
