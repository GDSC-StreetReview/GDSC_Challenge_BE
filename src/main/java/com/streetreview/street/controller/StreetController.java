package com.streetreview.street.controller;


import com.streetreview.common.dto.Message;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.street.dto.ReqStreetCreationDto;
import com.streetreview.street.dto.ReqStreetListDto;
import com.streetreview.street.service.StreetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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

    @PostMapping("/near/view")
    public ResponseEntity<Message> viewNearStreetPoint(@RequestBody ReqStreetListDto reqStreetListDto) {
        return ResponseEntity.ok(new Message(StatusCode.OK, streetService.getNearStreetList(reqStreetListDto)));
    }

    @PostMapping("/all/view")
    public ResponseEntity<Message> viewAllStreetPoint() {
        return ResponseEntity.ok(new Message(StatusCode.OK, streetService.getAllStreetList()));
    }

    @PostMapping("/aa")
    public ResponseEntity<Message> test() {

        return ResponseEntity.ok(new Message(StatusCode.OK, streetService.test()));
    }
}
