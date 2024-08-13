package org.task.maid.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.task.maid.dto.PatronDto;
import org.task.maid.dto.pagination.PaginationDetails;
import org.task.maid.dto.pagination.PaginationDto;
import org.task.maid.entity.Patron;
import org.task.maid.exception.SystemErrorMessages;
import org.task.maid.exception.SystemException;
import org.task.maid.mapper.PaginationMapper;
import org.task.maid.mapper.PatronMapper;
import org.task.maid.repository.PatronRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PatronService {

    private PatronRepository patronRepository;
    private PatronMapper patronMapper;
    private PaginationMapper paginationMapper;

    @Cacheable(value = "patrons", key = "'getAllPatronsPaged' + #page + '-' + #size")
    public PaginationDto<PatronDto> getAllPatronsPaged(Integer page, Integer size) {
        Page<Patron> patronPage = patronRepository.findAll(PageRequest.of(page, size));
        PaginationDetails paginationDetails = new PaginationDetails(patronPage.getNumber(), patronPage.getTotalElements(), patronPage.getSize(), patronPage.getTotalPages());
        List<PatronDto> patronDtos = patronMapper.toDto(patronPage.getContent());
        return paginationMapper.toPaginationDto(patronDtos, paginationDetails);
    }

    @Cacheable(value = "patrons", key = "#id")
    public PatronDto getPatronDtoById(Integer id) {
        Patron patron = patronRepository.findById(id).orElseThrow(() -> new SystemException(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage()));
        return patronMapper.toDto(patron);
    }

    @CacheEvict(value = "patrons", allEntries = true)
    public PatronDto addPatron(PatronDto patronDto) {
        Patron patron = patronMapper.toEntity(patronDto);
        patron = patronRepository.save(patron);
        return patronMapper.toDto(patron);

    }

    @CachePut(value = "patrons", key = "#id")
    public PatronDto updatePatron(Integer id, PatronDto patronDto) {
        Patron patron = patronRepository.findById(id).orElseThrow(() -> new SystemException(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage()));
        patron = patronMapper.updateEntityFromDto(patronDto, patron);
        patron = patronRepository.save(patron);
        patronDto = patronMapper.toDto(patron);
        return patronDto;
    }

    @CacheEvict(value = "patrons", allEntries = true)
    public void deletePatron(Integer id) {
        Patron patron = patronRepository.findById(id).orElseThrow(() -> new SystemException(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage()));
        try {
            patronRepository.delete(patron);
        } catch (Exception e) {
            throw new SystemException(SystemErrorMessages.RESOURCE_IS_RELATED.getMessage());
        }
    }

}
