package com.streetreview.street.service;


import com.streetreview.file.entity.Photo;
import com.streetreview.file.entity.PhotoType;
import com.streetreview.file.repository.PhotoRepository;
import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.street.dto.ReqStreetCreationDto;
import com.streetreview.street.dto.ReqStreetIdDto;
import com.streetreview.street.dto.ReqStreetListDto;
import com.streetreview.street.dto.ResStreetListDto;
import com.streetreview.street.entity.Street;
import com.streetreview.street.repository.StreetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StreetServiceImpl implements StreetService {
    private final StreetRepository streetRepository;
    private final PhotoRepository photoRepository;
    private static final Double maxDistance = 10000.0; //10km

    @Override
    @Transactional
    public ReqStreetIdDto createStreet(ReqStreetCreationDto reqStreetCreationDto) {
        //좌표가 이미 있으면 Exception 던짐
        streetRepository.findByLocation(new GeoJsonPoint(reqStreetCreationDto.getX(), reqStreetCreationDto.getY()))
                .ifPresent(street -> {throw new CustomException(StatusCode.ALREADY_EXIST);});
        //없으면 저장
        return new ReqStreetIdDto(streetRepository.save(reqStreetCreationDto.toStreetEntity()).getId());
    }

    @Override
    public List<ResStreetListDto> getStreetList(ReqStreetListDto reqStreetListDto) {
        return streetRepository.findNear(reqStreetListDto.getMyY(), reqStreetListDto.getMyX(), maxDistance)
                .stream().map(street -> {
                    List<String> photoUrlList = photoRepository.findByTargetIdAndType(street.getId(), PhotoType.STREET.getValue())
                            .stream().map(Photo::getFileUrl).collect(Collectors.toList());

                    return street.toResStreetListDto(photoUrlList);
                }).collect(Collectors.toList());
    }
}
