package org.task.maid.mapper;

import org.mapstruct.Mapper;
import org.task.maid.dto.BorrowingRecordDto;
import org.task.maid.entity.BorrowingRecord;
import org.task.maid.mapper.common.EntityMapper;

@Mapper(componentModel = "spring")
public interface BorrowingRecordMapper extends EntityMapper<BorrowingRecord, BorrowingRecordDto> {
}
