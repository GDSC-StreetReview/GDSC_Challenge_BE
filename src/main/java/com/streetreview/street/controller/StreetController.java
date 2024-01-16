package com.streetreview.street.controller;


import com.streetreview.common.dto.Message;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.street.dto.ReqStreetCreationDto;
import com.streetreview.street.service.StreetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/street")
public class StreetController {
    private final StreetService streetService;

    @PostMapping("/registration")
    public ResponseEntity<Message> createAttendance(@RequestBody ReqStreetCreationDto reqStreetCreationDto) {
        streetService.createStreet(reqStreetCreationDto);
        return ResponseEntity.ok(new Message(StatusCode.OK));
    }
}
