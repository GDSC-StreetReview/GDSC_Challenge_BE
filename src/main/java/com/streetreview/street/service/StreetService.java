package com.streetreview.street.service;


import com.streetreview.street.dto.*;
import com.streetreview.street.entity.Street;

import java.util.List;

public interface StreetService {

    ReqStreetIdDto createStreet(ReqStreetCreationDto reqStreetCreationDto, String role);

    List<ResStreetListDto> getNearStreetList(ReqStreetListDto reqStreetListDto);
    List<ResStreetListDto> getAllStreetList();

    ResStreetListDto getStreet(ReqStreetViewDto reqStreetViewDto);
    Street test();
}
