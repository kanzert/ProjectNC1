package com.team.app.backend.service.impl;

import com.team.app.backend.persistance.dao.OptionDao;
import com.team.app.backend.persistance.model.DefaultQuest;
import com.team.app.backend.persistance.model.Option;
import com.team.app.backend.persistance.model.SeqOption;
import com.team.app.backend.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
@Transactional
public class OptionServiceImpl implements OptionService {

    private final OptionDao optionDao;

    @Autowired
    public OptionServiceImpl(OptionDao optionDao) {
        this.optionDao = optionDao;
    }


    @Override
    public List<DefaultQuest> getDefaultOption(Long quest_id) {
        return optionDao.getDefaultQuest(quest_id);
    }

    @Override
    public List<Option> getOptions(Long quest_id) {
        return optionDao.getOptionQuest(quest_id);
    }

    @Override
    public List<SeqOption> getSeqOption(Long quest_id) {
        return optionDao.getSeqOptionQuest(quest_id);
    }
}
