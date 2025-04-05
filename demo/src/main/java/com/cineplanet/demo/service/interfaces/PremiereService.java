package com.cineplanet.demo.service.interfaces;

import com.cineplanet.demo.dto.PremiereSummaryDto;
import com.cineplanet.demo.entity.Premiere;

import java.util.List;

public interface PremiereService {
    List<PremiereSummaryDto> getAllPremieres();
}
