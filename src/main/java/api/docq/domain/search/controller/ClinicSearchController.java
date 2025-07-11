package api.docq.domain.search.controller;

import api.docq.domain.search.dto.response.ClinicSearchResponse;
import api.docq.domain.search.service.ClinicSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class ClinicSearchController {

    private final ClinicSearchService clinicSearchService;

    @GetMapping
    public ResponseEntity<List<ClinicSearchResponse>> searchClinic(@RequestParam String q) {
        return ResponseEntity.ok(clinicSearchService.searchClinic(q));
    }
}
