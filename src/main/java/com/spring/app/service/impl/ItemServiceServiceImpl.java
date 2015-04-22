package com.spring.app.service.impl;

import com.spring.app.service.ItemServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ItemServiceServiceImpl implements ItemServiceService {

    private final Logger log = LoggerFactory.getLogger(ItemServiceServiceImpl.class);

}
