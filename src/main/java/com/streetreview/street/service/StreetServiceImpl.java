package com.streetreview.street.service;


import com.streetreview.file.entity.Photo;
import com.streetreview.file.entity.PhotoType;
import com.streetreview.file.repository.PhotoRepository;
import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.review.repository.ReviewRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StreetServiceImpl implements StreetService {
    private final StreetRepository streetRepository;
    private final PhotoRepository photoRepository;
    private final ReviewRepository reviewRepository;
    private static final Double maxDistance = 10000.0; //10km

    @Override
    @Transactional
    public ReqStreetIdDto createStreet(ReqStreetCreationDto reqStreetCreationDto) {
        //좌표가 이미 있으면 Exception 던짐
        streetRepository.findByLocation(new GeoJsonPoint(reqStreetCreationDto.getY(), reqStreetCreationDto.getX()))
                .ifPresent(street -> {throw new CustomException(StatusCode.ALREADY_EXIST);});
        //없으면 저장
        return new ReqStreetIdDto(streetRepository.save(reqStreetCreationDto.toStreetEntity()).getId());
    }

    @Override
    public List<ResStreetListDto> getNearStreetList(ReqStreetListDto reqStreetListDto) {
//        streetRepository.findNear(reqStreetListDto.getMyY(), reqStreetListDto.getMyX(), maxDistance)
//                .stream().filter(street -> street.getLocation().getY() == 1.123 && street.getLocation().getX() == 2.213)
//                .map(street -> {
//                    //1.123, 2.213 만 값이 들어와 street
//                    //리뷰 작성
//                    //reviewRepository.리뷰작성()
//
//                })

        return streetRepository.findNear(reqStreetListDto.getMyY(), reqStreetListDto.getMyX(), maxDistance)
                .stream().map(street -> {
                    List<String> photoUrlList = photoRepository.findByTargetIdAndType(street.getId(), PhotoType.STREET.getValue())
                            .stream().map(Photo::getFileUrl).collect(Collectors.toList());

                    Optional<Integer> reviewCount = reviewRepository.groupAndCountByXAndY(street.getLocation().getY(), street.getLocation().getX());
                    return street.toResStreetListDto(photoUrlList, reviewCount.orElse(0));
                }).collect(Collectors.toList());
    }

    @Override
    public List<ResStreetListDto> getAllStreetList() {
        return streetRepository.findAll()
                .stream().map(street -> {
                    List<String> photoUrlList = photoRepository.findByTargetIdAndType(street.getId(), PhotoType.STREET.getValue())
                            .stream().map(Photo::getFileUrl).collect(Collectors.toList());
                    Optional<Integer> reviewCount = reviewRepository.groupAndCountByXAndY(street.getLocation().getY(), street.getLocation().getX());
                    return street.toResStreetListDto(photoUrlList, reviewCount.orElse(0));
                }).collect(Collectors.toList());
    }
}
