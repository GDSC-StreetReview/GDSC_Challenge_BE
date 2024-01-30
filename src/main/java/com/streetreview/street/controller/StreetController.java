package com.streetreview.street.controller;


import com.streetreview.common.dto.Message;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.street.dto.ReqStreetCreationDto;
import com.streetreview.street.dto.ReqStreetListDto;
import com.streetreview.street.service.StreetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/street")
public class StreetController {
    private final StreetService streetService;

    @PostMapping("/registration")
    public ResponseEntity<Message> createStreetPoint(@RequestBody ReqStreetCreationDto reqStreetCreationDto) {
        return ResponseEntity.ok(new Message(StatusCode.OK, streetService.createStreet(reqStreetCreationDto)));
    }

    @PostMapping("/view")
    public ResponseEntity<Message> viewStreetPoint(@RequestBody ReqStreetListDto reqStreetListDto) {
        return ResponseEntity.ok(new Message(StatusCode.OK, streetService.getStreetList(reqStreetListDto)));
    }

}
