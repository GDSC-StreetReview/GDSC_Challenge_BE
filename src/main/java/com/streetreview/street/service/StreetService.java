package com.streetreview.street.service;


import com.streetreview.street.dto.ReqStreetCreationDto;
import com.streetreview.street.dto.ReqStreetIdDto;
import com.streetreview.street.dto.ReqStreetListDto;
import com.streetreview.street.dto.ResStreetListDto;

import java.util.List;

public interface StreetService {

    ReqStreetIdDto createStreet(ReqStreetCreationDto reqStreetCreationDto);

    List<ResStreetListDto> getNearStreetList(ReqStreetListDto reqStreetListDto);
    List<ResStreetListDto> getAllStreetList();
}
