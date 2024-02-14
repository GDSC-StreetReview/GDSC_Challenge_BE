package com.streetreview.street.service;


import com.streetreview.file.entity.Photo;
import com.streetreview.file.entity.PhotoType;
import com.streetreview.file.repository.PhotoRepository;
import com.streetreview.member.dto.Role;
import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.member.repository.MemberRepository;
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

    private static final Double maxDistance = 5000.0; //10km

    @Override
    @Transactional
    public ReqStreetIdDto createStreet(ReqStreetCreationDto reqStreetCreationDto, String role) {
        if(!role.equals("MANAGER")) {
            throw new CustomException(StatusCode.FORBIDDEN);
        }
        //좌표가 이미 있으면 Exception 던짐
        streetRepository.findByLocation(new GeoJsonPoint(reqStreetCreationDto.getY(), reqStreetCreationDto.getX()))
                .ifPresent(street -> {throw new CustomException(StatusCode.ALREADY_EXIST);});
        //없으면 저장
        return new ReqStreetIdDto(streetRepository.save(reqStreetCreationDto.toStreetEntity()).getId());
    }

    @Override
    public List<ResStreetListDto> getNearStreetList(ReqStreetListDto reqStreetListDto) {
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

    @Override
    public Street test() {
        Double myX = 37.485219;
        Double myY = 126.805708;
        Double targetX = 37.485237;
        Double targetY = 126.805706;

        Street targetStreet = streetRepository.findNear(myY, myX, maxDistance)
                .stream().filter(street -> street.isTarget(targetX, targetY)).findFirst()
                .orElseThrow(() -> new CustomException(StatusCode.NOT_LOCATION));

        //이 targetStreet를 이용해서 작성하면됨,
        //X좌표 = targetStreet.getLocation().getX();
        //Y좌표 = targetStreet.getLocation().getY();

        return targetStreet;
    }
}
