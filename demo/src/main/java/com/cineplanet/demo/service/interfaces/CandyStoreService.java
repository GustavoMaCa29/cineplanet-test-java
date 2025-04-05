package com.cineplanet.demo.service.interfaces;

import com.cineplanet.demo.dto.CandyStoreSummaryDto;
import com.cineplanet.demo.entity.CandyStore;

import java.util.List;

public interface CandyStoreService {
    List<CandyStoreSummaryDto> getAllCandyStores();
    CandyStore getCandyStoreById(Long id);
}
