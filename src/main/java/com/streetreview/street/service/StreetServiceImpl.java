package com.streetreview.street.service;


import com.streetreview.member.handler.CustomException;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.street.dto.ReqStreetCreationDto;
import com.streetreview.street.repository.StreetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StreetServiceImpl implements StreetService {
    private final StreetRepository streetRepository;

    @Override
    public void createStreet(ReqStreetCreationDto reqStreetCreationDto) {
        //좌표가 이미 있으면 Exception 던짐
        streetRepository.findByXAndY(reqStreetCreationDto.getX(), reqStreetCreationDto.getY())
                .ifPresent(street -> {throw new CustomException(StatusCode.ALREADY_EXIST);});
        //없으면 저장
        streetRepository.save(reqStreetCreationDto.toStreetEntity());
    }
}
